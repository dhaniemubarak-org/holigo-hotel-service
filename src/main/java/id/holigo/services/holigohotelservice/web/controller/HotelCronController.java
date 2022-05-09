package id.holigo.services.holigohotelservice.web.controller;

import id.holigo.services.holigohotelservice.services.HotelCronService;
import id.holigo.services.holigohotelservice.web.clients.CmsHotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
@RequestMapping("/api/v1/hotels/jobs")
public class HotelCronController {

    @Autowired
    private HotelCronService hotelCronService;

    @Autowired
    private CmsHotel cmsHotel;

    @GetMapping(path = "/pulling-hotel-external")
    public ResponseEntity<?> pullingHotelAvailable(@RequestParam("checkIn") Date checkIn, @RequestParam("cityId") int cityId) {
        hotelCronService.runningPullFromExternal(checkIn, cityId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/pulling-hotel-sip")
    public ResponseEntity<?> pullingHotelSIP(@RequestParam("lastId") Integer lastId){
        cmsHotel.getByHotel(lastId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
