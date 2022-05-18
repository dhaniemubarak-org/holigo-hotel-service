package id.holigo.services.holigohotelservice.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.holigo.services.common.model.FareDetailDto;
import id.holigo.services.common.model.FareDto;
import id.holigo.services.common.model.retross.request.DetailHotelRequestDto;
import id.holigo.services.common.model.retross.response.DetailHotelResponseDto;
import id.holigo.services.common.model.retross.response.ResponseRoomDto;
import id.holigo.services.holigohotelservice.domain.*;
import id.holigo.services.holigohotelservice.repositories.*;
import id.holigo.services.holigohotelservice.services.fares.FareDetailService;
import id.holigo.services.holigohotelservice.services.supplier.HotelRetrossService;
import id.holigo.services.holigohotelservice.web.clients.CmsHotel;
import id.holigo.services.holigohotelservice.web.exceptions.NotFoundException;
import id.holigo.services.holigohotelservice.web.exceptions.RetrossErrorException;
import id.holigo.services.holigohotelservice.web.mappers.HotelInquiryMapper;
import id.holigo.services.holigohotelservice.web.model.*;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelPriceDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelStoryDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.hotelRooms.RoomAmenityDto;
import id.holigo.services.holigohotelservice.web.model.room.DetailRoomDtoForUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import id.holigo.services.holigohotelservice.repositories.spesification.GenericOrSpecification;
import id.holigo.services.holigohotelservice.repositories.spesification.GenericSpecification;
import id.holigo.services.holigohotelservice.repositories.spesification.SearchCriteria;
import id.holigo.services.holigohotelservice.repositories.spesification.SearchOperation;
import id.holigo.services.holigohotelservice.web.mappers.HotelMapper;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;

@Service
@Slf4j
public class HotelAvailableServiceImpl implements HotelAvailableService {

    @Autowired
    private HotelAvailableRepository hotelAvailableRepository;

    @Autowired
    private HotelAddressRepository hotelAddressRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private HotelInquiryRepository hotelInquiryRepository;

    @Autowired
    private HotelRetrossService hotelRetrossService;

    @Autowired
    private HotelSupplierCodeRepository hotelSupplierCodeRepository;

    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    @Autowired
    private HotelRoomPriceRepository hotelRoomPricesRepository;

    @Autowired
    private HotelInquiryMapper hotelInquiryMapper;

    @Autowired
    private HotelFacilitiesService hotelFacilitiesService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FareDetailService fareDetailService;

    @Autowired
    private CmsHotel cmsHotel;

    public HotelDto getDetailHotelAvailable(Long id) {

        HotelDto hotelDto = new HotelDto();
        log.info("Hotels ID -> {}", id);
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (hotel.isPresent()) {
            log.info("Response Hotel -> {}", hotel.get().getFacilities());
            hotelDto = hotelMapper.hotelsToHotelDto(hotel.get());
            return hotelDto;
        } else {
            return null;
        }

    }

    @Override
    public HotelAvailablePaginateForUser listHotelForUser(Long destination, PageRequest pageRequest,
                                                          String rating, String facilities, String types, Date checkIn, Date checkOut, String destinationType) {
        HotelAvailablePaginateForUser hotelAvailablePaginateForUser;
        Page<HotelAvailable> hotelPage;
        GenericSpecification<HotelAvailable> andSpecification = new GenericSpecification<HotelAvailable>();
        GenericOrSpecification<HotelAvailable> orSpecification = new GenericOrSpecification<HotelAvailable>();
        log.info("City Id -> {}", destination);
        Optional<Cities> city = citiesRepository.findById(destination);

        if(destinationType.equals("city") || destinationType.equals("kota")){
            if (city.isEmpty()) {
                return null;
            }
            andSpecification.add(new SearchCriteria("cityId", city.get(), SearchOperation.EQUAL));
        } else if(destinationType.equals("wilayah") || destinationType.equals("area")){
//            Find by Wilayah
//            andSpecification.add(new SearchCriteria("area", areaName), SearchOperation.MATCH;
        }
        andSpecification.add(new SearchCriteria("checkIn", checkIn, SearchOperation.EQUAL));

        // orSpecification.add(new SearchCriteria("cityId", city.get(),
        // SearchOperation.EQUAL));

        if (rating != null && !rating.equals("")) {
            List<String> ratings = new ArrayList<String>(Arrays.asList(rating.split(",")));
            if (ratings.size() <= 0) {
                andSpecification
                        .add(new SearchCriteria("rating", rating,
                                SearchOperation.MATCH));
            }
            for (String starRating : ratings) {
                andSpecification
                        .add(new SearchCriteria("rating", Double.valueOf(starRating),
                                SearchOperation.EQUAL));
            }
        }

        if (facilities != null && !facilities.equals("")) {

            List<String> facility = new ArrayList<String>(Arrays.asList(facilities.split(",")));
            if (facility.size() <= 0) {
                andSpecification
                        .add(new SearchCriteria("facilities", facilities,
                                SearchOperation.MATCH));
            }

            for (String fclty : facility) {
                andSpecification
                        .add(new SearchCriteria("facilities", fclty, SearchOperation.MATCH));
            }
        }

        if (types != null && !types.equals("")) {
            List<String> listType = new ArrayList<String>(Arrays.asList(types.split(",")));
            if (listType.size() <= 0) {
                orSpecification.add(new SearchCriteria("type", types, SearchOperation.EQUAL));
            }
            for (String hotelType : listType) {
                log.info("Cari Tipe Kamar -> {}", hotelType);
                orSpecification.add(new SearchCriteria("type", hotelType, SearchOperation.EQUAL));
            }
        }
        hotelPage = hotelAvailableRepository.findAll(Specification.where(orSpecification).and(andSpecification),
                pageRequest);

//        List<HotelAvailablePaginateForUser> paginate = new ArrayList<>();
//        hotelPage.getContent().forEach(page -> {
//            paginate.add(hotelMapper.hotelAvailableToDetailHotelForUserDto());
//        });

        hotelAvailablePaginateForUser = new HotelAvailablePaginateForUser(
                hotelPage.getContent().stream().map(hotelMapper::hotelAvailableToDetailHotelForUserDto)
                        .toList(),
                PageRequest.of(hotelPage.getPageable().getPageNumber(),
                        hotelPage.getPageable().getPageSize()),
                hotelPage.getTotalElements());

        return hotelAvailablePaginateForUser;
    }

    @Override
    public void postHotelAvailable(DetailHotelForListDto detailHotelForListDto, Integer cityId) {
        HotelAvailable hotelAvailable = hotelMapper.detailHotelDtoToHotelAvailable(detailHotelForListDto, cityId);
        Optional<HotelAvailable> fetchHotelAvailable = hotelAvailableRepository
                .findByNameAndCheckIn(hotelAvailable.getName(), hotelAvailable.getCheckIn());
        if (fetchHotelAvailable.isEmpty()) {
            hotelAvailableRepository.save(hotelAvailable);
        }
    }

    @Override
    @Transactional
    public List<DetailHotelForListDto> generateAvailableHotel(Date checkIn, Integer cityId) {
        Optional<Cities> city = citiesRepository.findById(Long.valueOf(cityId));
        if (city.isEmpty()) {
            return null;
        }
        List<HotelAddresses> hotelAddresses = hotelAddressRepository.findAllByCity(city.get());
        List<Long> listHotelId = new ArrayList<>();
        hotelAddresses.forEach(listAddress -> {
            listHotelId.add(listAddress.getHotel().getId());
        });
        List<Hotel> listHotel = hotelRepository.findAllByIdIn(listHotelId);
        List<HotelDto> listHotelDto = listHotel.stream().map(hotelMapper::hotelsToHotelDto).toList();
        List<DetailHotelForListDto> listDetailHotel = listHotelDto.stream()
                .map(hotelMapper::hotelDtoToDetailHotelForListDto).toList();
        for (DetailHotelForListDto detailHotelForListDto : listDetailHotel) {
            log.info("Nama hotel -> {}", detailHotelForListDto.getName());
            detailHotelForListDto.setCheckIn(checkIn);
            postHotelAvailable(detailHotelForListDto, cityId);
        }
        return listDetailHotel;
    }

    public void generateAvailableFromCms(Integer cityId, Date startDate, Date endDate) {
        Optional<Cities> city = citiesRepository.findById(Long.valueOf(cityId));
        if (city.isEmpty()) {
            log.info("Empty City!");
        }
        log.info("Getting Repository Hotel Address!");
        List<HotelAddresses> hotelAddresses = hotelAddressRepository.findAllByCity(city.get());
        List<Long> listHotelId = new ArrayList<>();
        hotelAddresses.forEach(listAddress -> {
            listHotelId.add(listAddress.getHotel().getId());
        });
        listHotelId.forEach(id -> {
            Hotel hotel = hotelRepository.findById(id).orElseThrow(NotFoundException::new);
            log.info("Get Hotel -> {}", hotel.getName());
            HotelDto hotelDto = hotelMapper.hotelsToHotelDto(hotel);
            List<HotelPricesDto> listHotelPrice = cmsHotel.pullingHotelIndexPrice(hotelDto.getId(), startDate, endDate);
            listHotelPrice.forEach(hotelPrice -> {
                DetailHotelForListDto detailHotelForListDto = new DetailHotelForListDto();
                detailHotelForListDto = hotelMapper.hotelDtoToDetailHotelForListDto(hotelDto, hotelPrice.getDate(), hotelPrice.getPrice(), hotelPrice.getNta());
                postHotelAvailable(detailHotelForListDto, cityId);
            });
        });
    }

    @Override
    @Transactional
    public InquiryRoomDto inquiryRoom(Long destinationId, Long userId, Date checkIn, Date checkOut, Short adultAmount, Short childAmount, String childAge, Short roomAmount)
            throws JMSException, JsonMappingException, JsonProcessingException {

        log.info("Trying to create Inquiry!");

        Hotel hotel = hotelRepository.findById(destinationId).orElseThrow(NotFoundException::new);
        HotelSupplierCode hotelSupplierCode = hotelSupplierCodeRepository.findByHotel(hotel).orElseThrow(NotFoundException::new);

        InquiryRoomDto inquiryRoomDto = new InquiryRoomDto();
        InquiryDto inquiryDto = new InquiryDto();
        HotelInquiry hotelInquiry = new HotelInquiry();
        Optional<HotelInquiry> fetchHotelInquiry = hotelInquiryRepository.findByCustom(userId, checkIn, roomAmount, destinationId, adultAmount, childAmount);
        if (fetchHotelInquiry.isPresent()) {
            hotelInquiry = fetchHotelInquiry.get();
        } else {
            hotelInquiry.setUserId(userId);
            hotelInquiry.setCheckIn(checkIn);
            hotelInquiry.setCheckOut(checkOut);
            hotelInquiry.setAdultAmount(adultAmount);
            hotelInquiry.setChildAmount(childAmount);
            hotelInquiry.setChildAge(childAge);
            hotelInquiry.setRoomAmount(roomAmount);
            hotelInquiry.setDestinationId(destinationId);
            hotelInquiry.setDestinationType("HOTEL");
            hotelInquiryRepository.save(hotelInquiry);
        }
        inquiryDto = hotelInquiryMapper.hotelInquiryToInquiryDto(hotelInquiry);
        inquiryRoomDto.setInquiry(inquiryDto);
        inquiryRoomDto.setName(hotel.getName());
        inquiryRoomDto.setStarRating(hotel.getRating());

        //===== Trying sending to Retross =====//
        DetailHotelRequestDto requestDto = new DetailHotelRequestDto();
        requestDto.setH_id(hotelSupplierCode.getCode());
        requestDto.setCheckin(hotelInquiry.getCheckIn().toString());
        requestDto.setCheckout(hotelInquiry.getCheckOut().toString());
        requestDto.setRoom(hotelInquiry.getRoomAmount().toString());
        requestDto.setAdt(hotelInquiry.getAdultAmount().toString());
        requestDto.setChd(hotelInquiry.getChildAmount().toString());
        requestDto.setAction("detail_hotel_v2");
        requestDto.setApp("information");
        requestDto.setRoom_type("");

        try {
            log.info("Request Retross Dto -> {} ", objectMapper.writeValueAsString(requestDto));
        } catch (Exception e) {
            e.getMessage();
        }
        DetailHotelResponseDto responseDto = hotelRetrossService.getDetailHotel(requestDto);
        List<DetailRoomDtoForUser> listRooms = new ArrayList<>();
        if (responseDto.getError_code().equals("001")) {
            throw new RetrossErrorException(responseDto.getError_msg());
        } else {
            for (ResponseRoomDto roomDto : responseDto.getDetails().getRooms()) {
                String[] roomId = roomDto.getRoom_id().split("\\|");
                log.info("Room Id -> {}", roomId);
                Optional<HotelRooms> fetchHotelRoom = hotelRoomRepository.findByRoomSupplierId(roomId[0]);
                HotelRooms hotelRooms = new HotelRooms();
                if (fetchHotelRoom.isEmpty()) {
                    hotelRooms.setHotel(hotel);
                    hotelRooms.setName(roomDto.getCharacteristic());
                    hotelRooms.setRoomSupplierId(roomDto.getRoom_id());
                    hotelRooms.setMaxAdults(Short.valueOf("2"));
                    hotelRooms.setMaxOccupancy(Short.valueOf("2"));
                    hotelRooms.setMaxChilds(Short.valueOf("1"));
                    List<HotelRoomImages> hotelImages = new ArrayList<>();
                    hotelRooms.setImages(hotelImages);
                    hotelRoomRepository.save(hotelRooms);
                } else {
                    hotelRooms = fetchHotelRoom.get();
                }

                HotelRoomPrices hotelRoomPrices = new HotelRoomPrices();
                Optional<HotelRoomPrices> fetchHotelRoomPrices = hotelRoomPricesRepository.firstOrFail(
                        roomDto.getBed(), roomDto.getBoard(), roomDto.getCharacteristic(), hotelInquiry.getCheckIn(), hotel, hotelRooms, responseDto.getSelectedID(), roomDto.getSelectedIDroom()
                );
                log.info("NtaAmount -> {}", roomDto.getNta());
                log.info("PriceAmount -> {}", roomDto.getPrice());

                BigDecimal nraAmount = roomDto.getPrice().subtract(roomDto.getNta());

                FareDetailDto fareDetailDto = new FareDetailDto();
                fareDetailDto.setProductId(28);
                fareDetailDto.setNraAmount(nraAmount);
                fareDetailDto.setNtaAmount(roomDto.getNta());
                fareDetailDto.setUserId(userId);

                FareDto fareDto = fareDetailService.getDetailProduct(fareDetailDto);

                if (fetchHotelRoomPrices.isEmpty()) {
                    hotelRoomPrices.setBoard(roomDto.getBoard());
                    hotelRoomPrices.setCharacteristic(roomDto.getCharacteristic());
                    hotelRoomPrices.setCheckIn(hotelInquiry.getCheckIn());
                    hotelRoomPrices.setCheckOut(hotelInquiry.getCheckOut());
                    hotelRoomPrices.setHotel(hotel);
                    hotelRoomPrices.setRoom(hotelRooms);
                    hotelRoomPrices.setSelectedId(responseDto.getSelectedID());
                    hotelRoomPrices.setSelectedRoomId(roomDto.getSelectedIDroom());
                    hotelRoomPrices.setBoard(hotelRoomPrices.getBoard());
                    hotelRoomPrices.setBedType(roomDto.getBed());
                    hotelRoomPrices.setCharacteristic(hotelRoomPrices.getCharacteristic());
                    hotelRoomPrices.setCheckIn(hotelInquiry.getCheckIn());
                    hotelRoomPrices.setCheckOut(hotelInquiry.getCheckOut());
                    hotelRoomPrices.setNtaAmount(roomDto.getNta());
                    hotelRoomPrices.setFareAmount(fareDto.getFareAmount());
                    hotelRoomPrices.setPriceAmount(roomDto.getPrice());
                    hotelRoomPrices.setIsFreeBreakFast(roomDto.getBoard().contains("Breakfast"));
                    hotelRoomPrices.setCpAmount(fareDto.getCpAmount());
                    hotelRoomPrices.setMpAmount(fareDto.getMpAmount());
                    hotelRoomPrices.setIpAmount(fareDto.getIpAmount());
                    hotelRoomPrices.setHpAmount(fareDto.getHpAmount());
                    hotelRoomPrices.setHvAmount(fareDto.getHvAmount());
                    hotelRoomPrices.setPrAmount(fareDto.getPrAmount());
                    hotelRoomPrices.setIpcAmount(fareDto.getIpcAmount());
                    hotelRoomPrices.setHpcAmount(fareDto.getHpcAmount());
                    hotelRoomPrices.setPrcAmount(fareDto.getPrcAmount());
                    hotelRoomPrices.setLossAmount(fareDto.getLossAmount());

                    hotelRoomPricesRepository.save(hotelRoomPrices);
                } else {
                    hotelRoomPrices = fetchHotelRoomPrices.get();
                }
                List<String> images = new ArrayList<>();
                if (hotelRooms.getImages().size() > 0 && hotelRooms.getImages() != null) {
                    for (HotelRoomImages hotelRoomImages : hotelRooms.getImages()) {
                        images.add(hotelRoomImages.getImageUrl());
                    }
                }

                List<RoomAmenityDto> listAmenity = hotelFacilitiesService.getAmenitiesHotelRoom(hotelRooms);

                DetailRoomDtoForUser detailRoomDtoForUser = new DetailRoomDtoForUser();
                detailRoomDtoForUser.setId(hotelRoomPrices.getId());
                detailRoomDtoForUser.setCategory(hotelRooms.getName());
                detailRoomDtoForUser.setImages(images);
                detailRoomDtoForUser.setName(hotelRoomPrices.getBedType());
                detailRoomDtoForUser.setIsFreeBreakFast(hotelRoomPrices.getIsFreeBreakFast());
                detailRoomDtoForUser.setIsRefundable(false);
                detailRoomDtoForUser.setIsFreeRefundable(false);
                detailRoomDtoForUser.setMaxOccupancy(Integer.valueOf(hotelRooms.getMaxOccupancy()));
                detailRoomDtoForUser.setMaxAdult(Integer.valueOf(hotelRooms.getMaxAdults()));
                detailRoomDtoForUser.setMaxChildren(Integer.valueOf(hotelRooms.getMaxChilds()));
                detailRoomDtoForUser.setFareAmount(hotelRoomPrices.getFareAmount());
                detailRoomDtoForUser.setNormalAmount(hotelRoomPrices.getPriceAmount());
                detailRoomDtoForUser.setHpAmount(hotelRoomPrices.getHpAmount());
                detailRoomDtoForUser.setRoomDescription(hotelRooms.getDescription());
                detailRoomDtoForUser.setAmenities(listAmenity);

                listRooms.add(detailRoomDtoForUser);
            }
            inquiryRoomDto.setRooms(listRooms);
        }

        return inquiryRoomDto;
    }

}
