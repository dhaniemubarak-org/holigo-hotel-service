package id.holigo.services.holigohotelservice.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import id.holigo.services.holigohotelservice.services.HotelFareService;
import id.holigo.services.holigohotelservice.web.model.HotelFareDto;
import id.holigo.services.holigohotelservice.web.model.requests.RequestFareDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;

@RestController
@RequestMapping("api/v1/hotels/fare")
public class HotelFaresController {

    @Autowired
    private HotelFareService hotelFareService;

    @PostMapping("")
    public ResponseEntity<HotelFareDto> postFareHotel(@RequestHeader("user-id") Long userId, @RequestBody RequestFareDto requestFareDto) throws JMSException, JsonMappingException, JsonProcessingException {

        HotelFareDto hotelFareDto = hotelFareService.submitFareHotel(userId, requestFareDto);
        return new ResponseEntity<HotelFareDto>(hotelFareDto, HttpStatus.CREATED);
    }
}
