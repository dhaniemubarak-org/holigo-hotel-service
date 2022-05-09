package id.holigo.services.holigohotelservice.web.controller;

import id.holigo.services.holigohotelservice.services.HotelBookService;
import id.holigo.services.holigohotelservice.web.model.requests.RequestBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.jms.JMSException;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/hotels/book")
public class HotelBookController {

    @Autowired
    private HotelBookService hotelBookService;

    @PostMapping("")
    public ResponseEntity<?> postBookHotel(@RequestHeader("user-id") Long userId, @RequestBody RequestBookDto requestBookDto) throws JMSException {

        UUID transactionId = hotelBookService.postBookHotel(userId, requestBookDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(
                UriComponentsBuilder.fromPath("/api/v1/transactions/{id}")
                        .buildAndExpand(transactionId)
                        .toUri()
        );
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }
}
