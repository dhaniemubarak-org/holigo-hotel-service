package id.holigo.services.holigohotelservice.web.model.detailHotel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import id.holigo.services.holigohotelservice.web.model.detailHotel.hotelRooms.HotelRoomDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDto {

    private Long id;
    private String name;
    private String type;
    private Double rating;
    private List<String> additionalInformations;
    private HotelInformationDto hotelInformation;
    private List<HotelStoryDto> stories;
    private HotelLocationDto location;
    private List<HotelImagesDto> images;
    private List<HotelMainFacilityDto> mainFacility;
    private List<HotelNearbyPlaceDto> nearbyPlaces;
    private List<HotelNearbyPlaceDto> popularAreas;
    private HotelDescriptionDto description;
    private List<HotelRulesDto> rules;
    private HotelPolicyDto policy;
    private HoligoReviewDto holigoReview;
    private List<HotelFacilityDto> facilities;
    @JsonIgnore
    private List<HotelRoomDto> rooms;
}
