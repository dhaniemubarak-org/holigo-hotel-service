package id.holigo.services.holigohotelservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import id.holigo.services.common.model.FareDetailDto;
import id.holigo.services.common.model.FareDto;
import id.holigo.services.holigohotelservice.domain.HotelFares;
import id.holigo.services.holigohotelservice.domain.HotelInquiry;
import id.holigo.services.holigohotelservice.domain.HotelRoomPrices;
import id.holigo.services.holigohotelservice.repositories.HotelFareRepository;
import id.holigo.services.holigohotelservice.repositories.HotelInquiryRepository;
import id.holigo.services.holigohotelservice.repositories.HotelRoomPriceRepository;
import id.holigo.services.holigohotelservice.services.fares.FareDetailService;
import id.holigo.services.holigohotelservice.web.exceptions.NotFoundException;
import id.holigo.services.holigohotelservice.web.mappers.HotelMapper;
import id.holigo.services.holigohotelservice.web.model.AccomodationDto;
import id.holigo.services.holigohotelservice.web.model.AccomodationRoomDto;
import id.holigo.services.holigohotelservice.web.model.HotelFareDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDto;
import id.holigo.services.holigohotelservice.web.model.requests.RequestFareDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelFareServiceImpl implements HotelFareService {

    @Autowired
    private HotelInquiryRepository hotelInquiryRepository;

    @Autowired
    private HotelRoomPriceRepository hotelRoomPriceRepository;

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private FareDetailService fareDetailService;

    @Autowired
    private HotelFareRepository hotelFareRepository;

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
    @Override
    public HotelFareDto submitFareHotel(Long userId, RequestFareDto requestFareDto) throws JMSException, JsonMappingException, JsonProcessingException {
        HotelInquiry hotelInquiry = hotelInquiryRepository.findById(requestFareDto.getInquiryId()).orElseThrow(NotFoundException::new);

        HotelRoomPrices hotelRoomPrices = hotelRoomPriceRepository.findById(requestFareDto.getRoomId()).orElseThrow(NotFoundException::new);

        Long totalNight = getDifferenceDays(hotelInquiry.getCheckIn(), hotelInquiry.getCheckOut());
        log.info("Total Malam -> {}", totalNight);

        BigDecimal totalEstimationPrice = hotelRoomPrices.getPriceAmount().multiply(BigDecimal.valueOf(hotelInquiry.getRoomAmount()));
        totalEstimationPrice = totalEstimationPrice.multiply(BigDecimal.valueOf(totalNight));

        BigDecimal totalFare = hotelRoomPrices.getFareAmount().multiply(BigDecimal.valueOf(hotelInquiry.getRoomAmount()));
        totalFare = totalFare.multiply(BigDecimal.valueOf(totalNight));

        BigDecimal totalNtaAmount = hotelRoomPrices.getNtaAmount().multiply(BigDecimal.valueOf(hotelInquiry.getRoomAmount()));
        totalNtaAmount = totalNtaAmount.multiply(BigDecimal.valueOf(totalNight));

        BigDecimal totalNraAmount = totalFare.subtract(totalNtaAmount);

        FareDetailDto fareDetailDto = FareDetailDto.builder().nraAmount(totalNraAmount).ntaAmount(totalNtaAmount).productId(28).userId(userId).build();
        FareDto fareDto = fareDetailService.getDetailProduct(fareDetailDto);

        HotelFares hotelFares = new HotelFares();
        hotelFares.setUserId(userId);
        hotelFares.setHotel(hotelRoomPrices.getHotel());
        hotelFares.setRoom(hotelRoomPrices.getRoom());
        hotelFares.setSelectedId(hotelRoomPrices.getSelectedId());
        hotelFares.setSelectedRoomId(hotelRoomPrices.getSelectedRoomId());
        hotelFares.setCheckIn(hotelInquiry.getCheckIn());
        hotelFares.setCheckOut(hotelInquiry.getCheckOut());
        hotelFares.setNtaAmount(totalNtaAmount);
        hotelFares.setNraAmount(totalNraAmount);
        hotelFares.setFareAmount(fareDto.getFareAmount());
        hotelFares.setTotalEstimationPrice(totalEstimationPrice);
        hotelFares.setCpAmount(fareDto.getCpAmount());
        hotelFares.setMpAmount(fareDto.getMpAmount());
        hotelFares.setIpAmount(fareDto.getIpAmount());
        hotelFares.setIpcAmount(fareDto.getIpcAmount());
        hotelFares.setHpAmount(fareDto.getHpAmount());
        hotelFares.setHpcAmount(fareDto.getHpcAmount());
        hotelFares.setPrAmount(fareDto.getPrAmount());
        hotelFares.setPrcAmount(fareDto.getPrcAmount());
        hotelFares.setHvAmount(fareDto.getHvAmount());
        hotelFares.setLossAmount(fareDto.getLossAmount());
        hotelFares.setInquiry(hotelInquiry);

        HotelFares savedHotelFares = hotelFareRepository.save(hotelFares);

        HotelFareDto hotelFareDto = new HotelFareDto();
        hotelFareDto.setId(savedHotelFares.getId());
        hotelFareDto.setFareAmount(savedHotelFares.getFareAmount());
        hotelFareDto.setEstimationPrice(savedHotelFares.getTotalEstimationPrice());
        hotelFareDto.setHpAmount(savedHotelFares.getHpAmount());
        hotelFareDto.setNtaAmount(savedHotelFares.getNtaAmount());

        HotelDto hotelDto = hotelMapper.hotelsToHotelDto(savedHotelFares.getHotel());

        AccomodationRoomDto accomodationRoomDto = new AccomodationRoomDto();
        accomodationRoomDto.setId(hotelRoomPrices.getId());
        accomodationRoomDto.setName(hotelRoomPrices.getRoom().getName());
        accomodationRoomDto.setBoard(hotelRoomPrices.getBoard());
        accomodationRoomDto.setBedType(hotelRoomPrices.getBoard());
        accomodationRoomDto.setPrice(hotelRoomPrices.getFareAmount());
        AccomodationDto accomodationDto = new AccomodationDto();
        accomodationDto.setInquiryId(hotelInquiry.getId());
        accomodationDto.setHotel(hotelMapper.hotelDtoToHotelDetailFareDto(hotelDto));
        accomodationDto.setRoom(accomodationRoomDto);

        hotelFareDto.setAccomodation(accomodationDto);

        return hotelFareDto;
    }
}
