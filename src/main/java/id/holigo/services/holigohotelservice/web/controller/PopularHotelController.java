package id.holigo.services.holigohotelservice.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.holigo.services.holigohotelservice.services.PopularHotelService;
import id.holigo.services.holigohotelservice.web.model.ListPopularHotelDto;
import id.holigo.services.holigohotelservice.web.model.PopularHotelByCityDto;

@RestController
@RequestMapping("/api/v1/hotels/populars")
public class PopularHotelController {

    @Autowired
    private PopularHotelService popularHotelService;

    @GetMapping
    public ResponseEntity<ListPopularHotelDto> listPopularHotel() {
        List<PopularHotelByCityDto> listPopularHotel = popularHotelService.getPopularByCity();
        ListPopularHotelDto listPopularHotelDto = ListPopularHotelDto.builder().cities(listPopularHotel).build();

        return new ResponseEntity<ListPopularHotelDto>(listPopularHotelDto, HttpStatus.OK);
    }
}
