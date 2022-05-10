package id.holigo.services.holigohotelservice.web.mappers;

import id.holigo.services.holigohotelservice.domain.HotelAvailable;
import id.holigo.services.holigohotelservice.web.model.calender.FareCalendarDto;

public class HotelCalendarMapperDecorator implements HotelCalendarMapper{

    @Override
    public FareCalendarDto hotelAvailableToFareCalendarDto(HotelAvailable hotelAvailable){
        FareCalendarDto fareCalendarDto = new FareCalendarDto();
        fareCalendarDto.setFare(hotelAvailable.getFareAmount());
        fareCalendarDto.setDate(hotelAvailable.getCheckIn());

        return fareCalendarDto;
    }
}
