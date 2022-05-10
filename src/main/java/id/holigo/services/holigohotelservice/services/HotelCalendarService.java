package id.holigo.services.holigohotelservice.services;

import id.holigo.services.holigohotelservice.web.model.calender.FareCalendarDto;

import java.util.List;

public interface HotelCalendarService {
    List<FareCalendarDto> getPriceByHotelId(Long userId, Long destinationId, String month, String year);
}
