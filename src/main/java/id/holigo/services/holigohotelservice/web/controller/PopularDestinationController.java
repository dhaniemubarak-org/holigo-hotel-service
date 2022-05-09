package id.holigo.services.holigohotelservice.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.holigo.services.holigohotelservice.services.PopularDestinationService;
import id.holigo.services.holigohotelservice.web.model.ListPopularDestinationDto;
import id.holigo.services.holigohotelservice.web.model.PopularDestinationDto;

@RestController
@RequestMapping("/api/v1/hotels/popularDestinations")
public class PopularDestinationController {

    @Autowired
    private PopularDestinationService popularDestinationService;

    @GetMapping
    public ResponseEntity<ListPopularDestinationDto> listPopularDestination() {
        List<PopularDestinationDto> listDestination = popularDestinationService.getListDestination();
        ListPopularDestinationDto listPopularDestinationDto = ListPopularDestinationDto.builder().data(listDestination)
                .build();

        return new ResponseEntity<ListPopularDestinationDto>(listPopularDestinationDto, HttpStatus.OK);

    }
}
