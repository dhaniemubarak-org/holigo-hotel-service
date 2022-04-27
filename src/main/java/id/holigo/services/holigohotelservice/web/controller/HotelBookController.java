package id.holigo.services.holigohotelservice.web.controller;

import id.holigo.services.holigohotelservice.web.model.requests.RequestBookDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/hotels/book")
public class HotelBookController {

    @PostMapping("")
    public ResponseEntity<?> postBookHotel(@RequestHeader("user-id") Long userId, @RequestBody RequestBookDto requestBookDto){

        return null;
    }
}
