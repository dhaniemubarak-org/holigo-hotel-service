package id.holigo.services.holigohotelservice.web.controller;

import id.holigo.services.holigohotelservice.services.HotelCalendarService;
import id.holigo.services.holigohotelservice.web.model.calender.FareCalendarDto;
import id.holigo.services.holigohotelservice.web.model.calender.ListCalendarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels/calendar")
public class HotelCalendarController {

    @Autowired
    private HotelCalendarService hotelCalendarService;

    @GetMapping("")
    public ResponseEntity<ListCalendarDto> getCalendarHotel(@RequestHeader("user-id") Long userId, @RequestParam("destinationId") Long destinationId, @RequestParam("month") String month, @RequestParam("year") String year) {
        List<FareCalendarDto> listFare = hotelCalendarService.getPriceByHotelId(userId, destinationId, month, year);
        ListCalendarDto listCalendarDto = ListCalendarDto.builder().data(listFare).build();
        return new ResponseEntity<ListCalendarDto>(listCalendarDto, HttpStatus.OK);
    }
}
