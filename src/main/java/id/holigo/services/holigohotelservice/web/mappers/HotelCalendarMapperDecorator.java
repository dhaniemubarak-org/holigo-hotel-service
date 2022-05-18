package id.holigo.services.holigohotelservice.web.mappers;

import id.holigo.services.holigohotelservice.domain.HotelAvailable;
import id.holigo.services.holigohotelservice.web.model.calender.FareCalendarDto;

import java.math.BigDecimal;

public class HotelCalendarMapperDecorator implements HotelCalendarMapper{

    @Override
    public FareCalendarDto hotelAvailableToFareCalendarDto(HotelAvailable hotelAvailable){
        FareCalendarDto fareCalendarDto = new FareCalendarDto();
        BigDecimal fareAmount = hotelAvailable.getFareAmount().divide(BigDecimal.valueOf(1000));

        fareCalendarDto.setFare(round(fareAmount,0,true));
        fareCalendarDto.setDate(hotelAvailable.getCheckIn());

        return fareCalendarDto;
    }

    public static BigDecimal round(BigDecimal d, int scale, boolean roundUp) {
        int mode = (roundUp) ? BigDecimal.ROUND_UP : BigDecimal.ROUND_DOWN;
        return d.setScale(scale, mode);
    }
}
