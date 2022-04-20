package id.holigo.services.holigohotelservice.web.mappers;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import id.holigo.services.holigohotelservice.repositories.HotelMainFacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import id.holigo.services.holigohotelservice.domain.Cities;
import id.holigo.services.holigohotelservice.domain.HotelAvailable;
import id.holigo.services.holigohotelservice.domain.HotelPolicies;
import id.holigo.services.holigohotelservice.domain.Hotel;
import id.holigo.services.holigohotelservice.domain.Provinces;
import id.holigo.services.holigohotelservice.repositories.CitiesRepository;
import id.holigo.services.holigohotelservice.repositories.ProvinceRepository;
import id.holigo.services.holigohotelservice.services.HotelFacilitiesService;
import id.holigo.services.holigohotelservice.web.model.DetailHotelForListDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDescriptionDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelFacilityDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelImagesDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelLocationDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelPolicyDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelPriceDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelTagDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.RatingReviewDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.hotelRooms.HotelRoomDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.hotelRooms.RoomAmenityDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HotelMapperDecorator implements HotelMapper {

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private HotelFacilitiesService hotelFacilitiesService;

    @Autowired
    private HotelRoomMapper hotelRoomMapper;

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private HotelMainFacilityRepository hotelMainFacilityRepository;

    @Override
    public HotelDto hotelsToHotelDto(Hotel hotels) {
        HotelDto hotelDto = hotelMapper.hotelsToHotelDto(hotels);

        log.info("Hotel Facilities -> {}", hotels.getMainFacility());
        HotelDescriptionDto descriptionDto = new HotelDescriptionDto();
        hotels.getDescriptions().forEach((desc) -> {
            log.info("Description -> {}", desc.getType());
            if (desc.getType().equals("short")) {
                descriptionDto.setShortDesc(desc.getText());
            } else if (desc.getType().equals("long")) {
                descriptionDto.setLongDesc(desc.getText());
            }
        });
        hotelDto.setDescription(descriptionDto);

        HotelPolicyDto policyDto = new HotelPolicyDto();
        for (HotelPolicies hotelPolicies : hotels.getPolicies()) {
            if (hotelPolicies.getType().equals("short")) {
                policyDto.setShortPolicy(hotelPolicies.getText());
            } else if (hotelPolicies.getType().equals("long")) {
                policyDto.setLongPolicy(hotelPolicies.getText());
            }
        }
        hotelDto.setPolicy(policyDto);

        List<String> additionalInformation = new ArrayList<>();
        hotels.getAdditionalInformations().forEach((information) -> {
            additionalInformation.add(information.getInformation());
        });
        hotelDto.setAdditionalInformations(additionalInformation);

        List<HotelFacilityDto> facilityDto = hotelFacilitiesService.getFacilityHotel(hotels);
        hotelDto.setFacilities(facilityDto);

        List<HotelRoomDto> listRoomDtos = hotels.getRooms().stream().map(hotelRoomMapper::hotelsToHotelRoomDto)
                .toList();
        hotelDto.setRooms(listRoomDtos);

        return hotelDto;
    }

    @Override
    public DetailHotelForListDto hotelAvailableToDetailHotelForUserDto(HotelAvailable hotelAvailable) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("Mapping Hotel -> {}", hotelAvailable.getName());

        DetailHotelForListDto detailHotelForListDto = new DetailHotelForListDto();
        detailHotelForListDto.setId(hotelAvailable.getId().toString());
        detailHotelForListDto.setName(hotelAvailable.getName());
        detailHotelForListDto.setType(hotelAvailable.getType());
        detailHotelForListDto.setRating(hotelAvailable.getRating());

        try {
            List<String> imageUrl = objectMapper.readValue(hotelAvailable.getImageUrl(),
                    new TypeReference<List<String>>() {
                    });
            detailHotelForListDto.setImageUrl(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            HotelTagDto tag = objectMapper.readValue(hotelAvailable.getTag(), HotelTagDto.class);
            detailHotelForListDto.setTag(tag);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<RatingReviewDto> ratingReviewDtos = objectMapper.readValue(hotelAvailable.getRatingReviews(),
                    new TypeReference<List<RatingReviewDto>>() {
                    });
            detailHotelForListDto.setRatingReviews(ratingReviewDtos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            HotelLocationDto locationDto = objectMapper.readValue(hotelAvailable.getLocation(), HotelLocationDto.class);
            detailHotelForListDto.setLocation(locationDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<String> facilities = objectMapper.readValue(hotelAvailable.getFacilities(),
                    new TypeReference<List<String>>() {
                    });
            detailHotelForListDto.setFacilities(facilities);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<String> amenities = objectMapper.readValue(hotelAvailable.getAmenities(),
                    new TypeReference<List<String>>() {
                    });
            detailHotelForListDto.setAmenities(amenities);
        } catch (Exception e) {
            e.printStackTrace();
        }

        detailHotelForListDto.setLatitude(hotelAvailable.getLatitude());
        detailHotelForListDto.setLongitude(hotelAvailable.getLongitude());
        detailHotelForListDto.setCity(hotelAvailable.getCity());
        detailHotelForListDto.setFareType(hotelAvailable.getFareType());
        detailHotelForListDto.setFareAmount(hotelAvailable.getFareAmount());
        detailHotelForListDto.setNormalFare(hotelAvailable.getNormalFare());
        detailHotelForListDto.setPoint(hotelAvailable.getPoint());

        try {
            HotelPriceDto price = objectMapper.readValue(hotelAvailable.getPrice(), HotelPriceDto.class);
            detailHotelForListDto.setPrice(price);
        } catch (Exception e) {
            e.printStackTrace();
        }

        detailHotelForListDto.setFreeBreakfast(Boolean.valueOf(hotelAvailable.getFreeBreakfast().toString()));
        detailHotelForListDto.setRefundable(Boolean.valueOf(hotelAvailable.getRefundable().toString()));
        detailHotelForListDto.setFreeRefundable(Boolean.valueOf(hotelAvailable.getFreeRefundable().toString()));
        // DetailHotelForListDto detailHotelForListDto =
        // hotelMapper.hotelAvailableToDetailHotelForUserDto(hotelAvailable);

        return detailHotelForListDto;
    }

    @Override
    public HotelAvailable detailHotelDtoToHotelAvailable(DetailHotelForListDto detailHotelForListDto, Integer cityId) {

        HotelAvailable hotelAvailable = new HotelAvailable();
        ObjectMapper objectMapper = new ObjectMapper();

        hotelAvailable.setId(Long.valueOf((detailHotelForListDto.getId())));
        hotelAvailable.setName(detailHotelForListDto.getName());
        hotelAvailable.setType(detailHotelForListDto.getType());

        try {
            String images = objectMapper.writeValueAsString(detailHotelForListDto.getImageUrl());
            hotelAvailable.setImageUrl(images);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            hotelAvailable.setTag(objectMapper.writeValueAsString(detailHotelForListDto.getTag()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        hotelAvailable.setRating(detailHotelForListDto.getRating());

        try {
            String hotelReviews = objectMapper.writeValueAsString(detailHotelForListDto.getRatingReviews());
            hotelAvailable.setRatingReviews(hotelReviews);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String location = objectMapper.writeValueAsString(detailHotelForListDto.getLocation());
            hotelAvailable.setLocation(location);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Optional<Cities> city = citiesRepository.findById(Long.valueOf(cityId));
        Provinces province = null;
        if(city.isPresent()){
            province = city.get().getProvince();
            hotelAvailable.setCityId(city.get());
            hotelAvailable.setProvinceId(province);
        }else{
            return null;
        }

        try {
            String facilities = objectMapper.writeValueAsString(detailHotelForListDto.getFacilities());
            hotelAvailable.setFacilities(facilities);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String amenities = objectMapper.writeValueAsString(detailHotelForListDto.getAmenities());
            hotelAvailable.setAmenities(amenities);
        } catch (Exception e) {
            e.printStackTrace();
        }

        hotelAvailable.setLatitude(detailHotelForListDto.getLatitude());
        hotelAvailable.setLongitude(detailHotelForListDto.getLongitude());
        hotelAvailable.setCheckIn(detailHotelForListDto.getCheckIn());
        hotelAvailable.setCheckOut(detailHotelForListDto.getCheckOut());
        hotelAvailable.setCity(city.get().getNameEn() + ", Indonesia");
        hotelAvailable.setFareType(detailHotelForListDto.getFareType());
        hotelAvailable.setFareAmount(detailHotelForListDto.getFareAmount());
        hotelAvailable.setNormalFare(detailHotelForListDto.getNormalFare());
        hotelAvailable.setPoint(detailHotelForListDto.getPoint());

        try {
            String price = objectMapper.writeValueAsString(detailHotelForListDto.getPrice());
            hotelAvailable.setPrice(price);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Short freeBreakFast = detailHotelForListDto.getFreeBreakfast() ? Short.valueOf("1") : Short.valueOf("0");
        Short refundable = detailHotelForListDto.getRefundable() ? Short.valueOf("1") : Short.valueOf("0");
        Short freeRefundable = detailHotelForListDto.getFreeRefundable() ? Short.valueOf("1") : Short.valueOf("0");
        hotelAvailable.setFreeBreakfast(freeBreakFast);
        hotelAvailable.setRefundable(refundable);
        hotelAvailable.setFreeRefundable(freeRefundable);

        hotelAvailable.setHotelId(Long.valueOf(detailHotelForListDto.getId()));

        return hotelAvailable;
    }

    @Override
    public DetailHotelForListDto hotelDtoToDetailHotelForListDto(HotelDto hotelDto) {
        DetailHotelForListDto detailHotelForListDto = hotelMapper.hotelDtoToDetailHotelForListDto(hotelDto);


        detailHotelForListDto.setLatitude(hotelDto.getLocation().getLatitude());
        detailHotelForListDto.setLongitude(hotelDto.getLocation().getLongitude());
        detailHotelForListDto.setCity(hotelDto.getLocation().getCity() + ", " + hotelDto.getLocation().getProvince());
        detailHotelForListDto.setFareType("PERNIGHT");
        detailHotelForListDto.setFareAmount(new BigDecimal("666666.00"));
        detailHotelForListDto.setNormalFare(new BigDecimal("777777.00"));
        detailHotelForListDto.setPoint(new BigDecimal("3333"));

        HotelPriceDto price = new HotelPriceDto();
        price.setFareAmount(new BigDecimal("666666.00"));
        price.setNormalFare(new BigDecimal("777777.00"));
        price.setPoint(new BigDecimal("3333.00"));
        price.setType("PERNIGHT");
        detailHotelForListDto.setPrice(price);

        List<String> listFacilities = new ArrayList<>();
        for (HotelFacilityDto hotelFacilityDto : hotelDto.getFacilities()) {
            for (String facility : hotelFacilityDto.getItems()) {
                boolean isContainsFacility = listFacilities.contains(facility);
                if(!isContainsFacility){
                    listFacilities.add(facility);
                }
            }
        }
        detailHotelForListDto.setFacilities(listFacilities);

        List<String> listAmenities = new ArrayList<>();
        for (HotelRoomDto hotelRoomDto : hotelDto.getRooms()) {
            log.info("Log List Amenities -> {}", listAmenities);
            for (RoomAmenityDto amenities : hotelRoomDto.getAmenities()) {
                for (String textAmenities : amenities.getList()) {
                    log.info("Amenities Logs -> {}", textAmenities);
                    boolean isContainAmenities = listAmenities.contains(textAmenities);
                    if(!isContainAmenities){
                        listAmenities.add(textAmenities);
                    }
                }
            }
        }
        detailHotelForListDto.setAmenities(listAmenities);

        List<String> imageUrl = new ArrayList<>();
        for (HotelImagesDto hotelImage : hotelDto.getImages().stream().limit(3).toList()) {
            imageUrl.add(hotelImage.getImageUrl());
        }
        detailHotelForListDto.setImageUrl(imageUrl);

        detailHotelForListDto.setFreeBreakfast(false);
        detailHotelForListDto.setRefundable(false);
        detailHotelForListDto.setFreeRefundable(false);

        detailHotelForListDto.setCheckIn(Date.valueOf("2022-04-15"));
        detailHotelForListDto.setCheckIn(Date.valueOf("2022-04-16"));

        return detailHotelForListDto;
    }

}
