package id.holigo.services.holigohotelservice.web.mappers;

import id.holigo.services.holigohotelservice.domain.HotelAvailable;
import id.holigo.services.holigohotelservice.web.model.calender.FareCalendarDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper
@DecoratedWith(HotelCalendarMapperDecorator.class)
public interface HotelCalendarMapper {
    FareCalendarDto hotelAvailableToFareCalendarDto(HotelAvailable hotelAvailable);
}
