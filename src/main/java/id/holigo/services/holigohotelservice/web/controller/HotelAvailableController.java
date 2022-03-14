package id.holigo.services.holigohotelservice.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.holigo.services.holigohotelservice.services.HotelAvailableService;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDto;

@RestController
@RequestMapping("/api/v1/hotel/available")
public class HotelAvailableController {

    @Autowired
    private HotelAvailableService hotelAvailableService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<HotelDto> detailHotelAvailable(@PathVariable("id") Long id) {
        HotelDto hotelDto = hotelAvailableService.getDetailHotelAvailable(id);
        return new ResponseEntity<HotelDto>(hotelDto, HttpStatus.OK);
    }
}
