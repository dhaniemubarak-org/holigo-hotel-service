package id.holigo.services.holigohotelservice.web.controller;

import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelStoryDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stories")
public class StoriesController {

    @GetMapping(path = "")
    public ResponseEntity<?> getStoriesAvailable(@RequestParam("destinationType") String storiesType, @RequestParam("destinationId") String destinationId){

        List<HotelStoryDto> listStories = new ArrayList<>();

        HotelStoryDto story1 = new HotelStoryDto();
        story1.setId(Long.valueOf(96150));
        story1.setCoverUrl("https://hotelimage.smartinpays.com/HotelImage/ID10002208/Photos/f009c692-6de9-4989-ae2a-32de46988130.jpg");
        story1.setVideoUrl("https://ik.imagekit.io/holigo/Video/hotel/story/story-1.mp4");
        story1.setName("Verse Cirebon");
        story1.setRating(Double.valueOf(4.0));
        story1.setFareAmount(BigDecimal.valueOf(310000.00));
        story1.setTag("Suite Room");
        listStories.add(story1);

        HotelStoryDto story2 = new HotelStoryDto();
        story2.setId(Long.valueOf(96456));
        story2.setCoverUrl("https://hotelimage.smartinpays.com/HotelImage/ID10002515/Photos/3dd9f0b0-ae75-49d7-8e11-2dc942dbd63f.jpg");
        story2.setVideoUrl("https://ik.imagekit.io/holigo/Video/hotel/story/story-2.MP4");
        story2.setName("The Rich Jogja (Formerly The Sahid Rich Yogyakarta)");
        story2.setRating(Double.valueOf(4.0));
        story2.setFareAmount(BigDecimal.valueOf(512000.00));
        story2.setTag("Deluxe Room");
        listStories.add(story2);

        return new ResponseEntity<>(listStories, HttpStatus.OK);
    }
}
