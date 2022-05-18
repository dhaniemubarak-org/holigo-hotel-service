package id.holigo.services.holigohotelservice.web.model;

import id.holigo.services.holigohotelservice.web.model.detailHotel.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelDetailFareDto {

    private Long id;
    private String name;
    private String type;
    private Double rating;
    private List<HotelAdditionalInformationDto> additionalInformations;
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
}
