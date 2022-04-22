package id.holigo.services.holigohotelservice.web.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import id.holigo.services.holigohotelservice.services.HotelAvailableService;
import id.holigo.services.holigohotelservice.web.model.DetailHotelForListDto;
import id.holigo.services.holigohotelservice.web.model.HotelAvailablePaginateForUser;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDto;

@RestController
@RequestMapping("/api/v1/hotel/available")
public class HotelAvailableController {
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    @Autowired
    private HotelAvailableService hotelAvailableService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<HotelDto> detailHotelAvailable(@PathVariable("id") Long id) {
        HotelDto hotelDto = hotelAvailableService.getDetailHotelAvailable(id);
        return new ResponseEntity<HotelDto>(hotelDto, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/rooms")
    public ResponseEntity<?> detailHotelAvailableRoom(@PathVariable("id") Long id,
                                                      @RequestHeader("user-id") Long userId,
                                                      @RequestParam("checkIn") Date checkIn,
                                                      @RequestParam("checkOut") Date checkOut,
                                                      @RequestParam("adultAmount") Short adultAmount,
                                                      @RequestParam("childAmount") Short childAmount,
                                                      @RequestParam("childAge") String childAge,
                                                      @RequestParam("roomAmount") Short roomAmount) {

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}/test")
    public ResponseEntity<List<DetailHotelForListDto>> getAllAvailable(@PathVariable("id") Long id,
                                                                       @RequestParam("checkIn") Date checkIn, @RequestParam("cityId") Integer cityId) {

        List<DetailHotelForListDto> hotelDto = hotelAvailableService.generateAvailableHotel(checkIn, cityId);
        return new ResponseEntity<List<DetailHotelForListDto>>(hotelDto, HttpStatus.OK);
    }

    @GetMapping(path = "")
    public ResponseEntity<HotelAvailablePaginateForUser> getAllAvailable(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "cityId") Long cityId,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @RequestParam(value = "rating", required = false) String rating,
            @RequestParam(value = "facilities", required = false) String facilities,
            @RequestParam(value = "types", required = false) String types,
            @RequestParam(value = "checkIn", required = false) Date checkIn,
            @RequestParam(value = "checkOut", required = false) Date checkOut) {
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        Sort sort = Sort.by("createdAt").descending();

        if (sortBy != null && orderBy != null) {
            sortBy = sortBy.toUpperCase();
            orderBy = orderBy.toUpperCase();

            switch (sortBy) {
                case "PRICE":
                    if (orderBy.equals("ASC")) {
                        sort = Sort.by("fareAmount").ascending();
                    } else {
                        sort = Sort.by("fareAmount").descending();
                    }
                    break;
                case "RATING":
                    if (orderBy.equals("ASC")) {
                        sort = Sort.by("rating").ascending();
                    } else {
                        sort = Sort.by("rating").descending();
                    }
                    break;
            }
        }

        HotelAvailablePaginateForUser hotelAvailableList = hotelAvailableService.listHotelForUser(cityId,
                PageRequest.of(pageNumber, pageSize, sort), rating, facilities, types, checkIn, checkOut);

        return new ResponseEntity<>(hotelAvailableList, HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<?> postAvailable(@RequestBody DetailHotelForListDto detailHotelForListDto, @RequestParam("cityId") Integer cityId) {
        hotelAvailableService.postHotelAvailable(detailHotelForListDto, cityId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
