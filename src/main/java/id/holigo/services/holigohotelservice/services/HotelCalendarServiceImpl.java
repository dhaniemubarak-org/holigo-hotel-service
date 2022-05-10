package id.holigo.services.holigohotelservice.services;

import id.holigo.services.holigohotelservice.domain.HotelAvailable;
import id.holigo.services.holigohotelservice.repositories.HotelAvailableRepository;
import id.holigo.services.holigohotelservice.web.mappers.HotelCalendarMapper;
import id.holigo.services.holigohotelservice.web.model.calender.FareCalendarDto;
import id.holigo.services.holigohotelservice.web.model.calender.ListCalendarDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HotelCalendarServiceImpl implements HotelCalendarService{

    @Autowired
    private HotelAvailableRepository hotelAvailableRepository;

    @Autowired
    private HotelCalendarMapper hotelCalendarMapper;

    @Override
    public List<FareCalendarDto> getPriceByHotelId(Long userId, Long destinationId, String month, String year){
        List<HotelAvailable> listHotelAvailable = hotelAvailableRepository.findByHotelAndBetweenDate(destinationId, year, month);
        log.info("Total Data -> {}", listHotelAvailable.size());
        List<FareCalendarDto> listCalendar = listHotelAvailable.stream().map(hotelCalendarMapper::hotelAvailableToFareCalendarDto).toList();

        return listCalendar;
    }
}
