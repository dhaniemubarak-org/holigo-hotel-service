package id.holigo.services.holigohotelservice.schedules;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import id.holigo.services.common.model.HotelDtoForExternal;
import id.holigo.services.holigohotelservice.domain.Amenities;
import id.holigo.services.holigohotelservice.domain.AmenityCategories;
import id.holigo.services.holigohotelservice.domain.Cities;
import id.holigo.services.holigohotelservice.domain.Countries;
import id.holigo.services.holigohotelservice.domain.Districts;
import id.holigo.services.holigohotelservice.domain.Facilities;
import id.holigo.services.holigohotelservice.domain.FacilityCategories;
import id.holigo.services.holigohotelservice.domain.Hotel;
import id.holigo.services.holigohotelservice.domain.HotelAddresses;
import id.holigo.services.holigohotelservice.domain.HotelDescription;
import id.holigo.services.holigohotelservice.domain.HotelFacilities;
import id.holigo.services.holigohotelservice.domain.HotelImages;
import id.holigo.services.holigohotelservice.domain.HotelMainFacilities;
import id.holigo.services.holigohotelservice.domain.HotelNearbyPlaces;
import id.holigo.services.holigohotelservice.domain.HotelPolicies;
import id.holigo.services.holigohotelservice.domain.HotelPopularAreas;
import id.holigo.services.holigohotelservice.domain.HotelRoomAmenities;
import id.holigo.services.holigohotelservice.domain.HotelRoomImages;
import id.holigo.services.holigohotelservice.domain.HotelRooms;
import id.holigo.services.holigohotelservice.domain.HotelTypes;
import id.holigo.services.holigohotelservice.domain.MainFacilities;
import id.holigo.services.holigohotelservice.domain.Provinces;
import id.holigo.services.holigohotelservice.repositories.AmenitiesCategoryRepository;
import id.holigo.services.holigohotelservice.repositories.AmenitiesRepository;
import id.holigo.services.holigohotelservice.repositories.CitiesRepository;
import id.holigo.services.holigohotelservice.repositories.CountriesRepository;
import id.holigo.services.holigohotelservice.repositories.DistrictsRepository;
import id.holigo.services.holigohotelservice.repositories.FacilityCategoriesRepository;
import id.holigo.services.holigohotelservice.repositories.FacilityRepository;
import id.holigo.services.holigohotelservice.repositories.HotelAddressRepository;
import id.holigo.services.holigohotelservice.repositories.HotelDescriptionRepository;
import id.holigo.services.holigohotelservice.repositories.HotelFacilitiesRepository;
import id.holigo.services.holigohotelservice.repositories.HotelImageRepository;
import id.holigo.services.holigohotelservice.repositories.HotelMainFacilityRepository;
import id.holigo.services.holigohotelservice.repositories.HotelNearbyPlaceRepository;
import id.holigo.services.holigohotelservice.repositories.HotelPolicyRepository;
import id.holigo.services.holigohotelservice.repositories.HotelPopularAreaRepository;
import id.holigo.services.holigohotelservice.repositories.HotelRepository;
import id.holigo.services.holigohotelservice.repositories.HotelRoomAmenitiesRepository;
import id.holigo.services.holigohotelservice.repositories.HotelRoomImageRepository;
import id.holigo.services.holigohotelservice.repositories.HotelRoomRepository;
import id.holigo.services.holigohotelservice.repositories.HotelTypeRepository;
import id.holigo.services.holigohotelservice.repositories.MainFacilityRepository;
import id.holigo.services.holigohotelservice.repositories.ProvinceRepository;
import id.holigo.services.holigohotelservice.services.external.HotelExternalService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PullingHotelFromExternal {

    @Autowired
    private HotelExternalService hotelExternalService;

    @Autowired
    private CountriesRepository countriesRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private CitiesRepository citiesRepository;
    @Autowired
    private DistrictsRepository districtsRepository;
    @Autowired
    private HotelAddressRepository hotelAddressRepository;
    @Autowired
    private HotelImageRepository hotelImageRepository;
    @Autowired
    private MainFacilityRepository mainFacilityRepository;
    @Autowired
    private HotelNearbyPlaceRepository hotelNearbyPlaceRepository;
    @Autowired
    private HotelPopularAreaRepository hotelPopularAreaRepository;
    @Autowired
    private HotelDescriptionRepository hotelDescriptionRepository;
    @Autowired
    private HotelPolicyRepository hotelPolicyRepository;
    @Autowired
    private FacilityCategoriesRepository facilityCategoriesRepository;
    @Autowired
    private FacilityRepository facilityRepository;
    @Autowired
    private HotelFacilitiesRepository hotelFacilitiesRepository;
    @Autowired
    private HotelMainFacilityRepository hotelMainFacilityRepository;

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelTypeRepository hotelTypeRepository;

    // @Scheduled(fixedDelay = 360000)
    public void pulling(Integer cityId) {
        HotelDtoForExternal hotelDtoForExternal;
        int pageNumber = 0;
        do {
            hotelDtoForExternal = hotelExternalService.getHotelByCityId(cityId, 1, pageNumber);
            for (JsonNode hotel : hotelDtoForExternal.getData()) {
                log.info("Hotel External -> {}", hotel.get("name").asText());
                storeHotel(hotel);
                // for (JsonNode jsonRooms : hotel.get("rooms")) {
                // log.info("Nama Kamar -> {}", jsonRooms.get("name").asText());
                // for (JsonNode jsonAmenities : jsonRooms.get("amenities")) {
                // log.info("Amenities Data-> {}", jsonAmenities.get("category").asText());
                // for (JsonNode jsonList : jsonAmenities.get("list")) {
                // log.info("List Json -> {}", jsonList.asText());
                // }
                // }
                // }

            }

            if ((int) hotelDtoForExternal.getTotalPages() >= (int) pageNumber) {
                pageNumber = pageNumber + 1;
            }
        } while (!hotelDtoForExternal.getLast());

    }

    @Transactional
    public void storeHotel(JsonNode jsonHotel) {

        // Saving Countries
        Countries country = new Countries();
        Optional<Countries> fetchCountry = countriesRepository
                .findByName(jsonHotel.get("location").get("country").asText());
        if (fetchCountry.isPresent()) {
            country = fetchCountry.get();
        }else{
            country.setName(jsonHotel.get("location").get("country").asText());
            countriesRepository.save(country);
        }

        Provinces province = new Provinces();
        Optional<Provinces> fetchProvincy = provinceRepository
                .findByName(jsonHotel.get("location").get("province").asText());
        if (fetchProvincy.isPresent()) {
            province = fetchProvincy.get();
        } else {
            province.setCountry(country);
            province.setName(jsonHotel.get("location").get("province").asText());
            provinceRepository.save(province);
        }

        Cities city = new Cities();
        Optional<Cities> fetchCity = citiesRepository
                .findByNameEnAndProvince(jsonHotel.get("location").get("city").asText(), province);
        if (fetchCity.isPresent()) {
            city = fetchCity.get();
        } else {
            city.setCountry(country);
            city.setNameEn(jsonHotel.get("location").get("city").asText());
            city.setNameId(jsonHotel.get("location").get("city").asText());
            city.setProvince(province);
            citiesRepository.save(city);
        }

        Districts district = new Districts();
        Optional<Districts> fetchDistrict = districtsRepository
                .findByNameAndCity(jsonHotel.get("location").get("district").asText(), city);
        if (fetchDistrict.isPresent()) {
            district = fetchDistrict.get();
        } else {
            district.setCity(city);
            district.setCountry(country);
            district.setProvince(province);
            district.setName(jsonHotel.get("location").get("district").asText());
            districtsRepository.save(district);
        }

        // Saving HotelType
        HotelTypes hotelType = new HotelTypes();
        Optional<HotelTypes> fetchHotelType = hotelTypeRepository.findByName(jsonHotel.get("type").asText());
        if (fetchHotelType.isPresent()) {
            hotelType = fetchHotelType.get();
        } else {
            hotelType.setName(jsonHotel.get("type").asText());
            hotelTypeRepository.save(hotelType);
        }

        Hotel hotel = new Hotel();
        Optional<Hotel> fetchHotel = hotelRepository.findByName(jsonHotel.get("name").asText());
        if (fetchHotel.isPresent()) {
            hotel = fetchHotel.get();
        } else {
            hotel.setName(jsonHotel.get("name").asText());
            hotel.setRating(Double.valueOf(jsonHotel.get("starRating").asText()));
            hotel.setHotelType(hotelType);
            hotelRepository.save(hotel);
        }

        HotelAddresses hotelAddresses = new HotelAddresses();
        Optional<HotelAddresses> fetchLocation = hotelAddressRepository.findByHotel(hotel);
        if (fetchLocation.isPresent()) {
            hotelAddresses = fetchLocation.get();
        } else {
            hotelAddresses.setHotel(hotel);
            hotelAddresses.setCountry(country);
            hotelAddresses.setProvince(province);
            hotelAddresses.setCity(city);
            hotelAddresses.setDistrict(district);
            hotelAddresses.setDetail(jsonHotel.get("location").get("address").asText());
            hotelAddresses.setLatitude(Double.valueOf(jsonHotel.get("location").get("latitude").asText()));
            hotelAddresses.setLongitude(Double.valueOf(jsonHotel.get("location").get("longitude").asText()));
            hotelAddressRepository.save(hotelAddresses);
        }

        for (JsonNode images : jsonHotel.get("images")) {
            Optional<HotelImages> fetchImages = hotelImageRepository.findByHotelAndCategoryAndImageUrl(hotel,
                    images.get("category").asText(), images.get("imageUrl").asText());
            if (fetchImages.isEmpty()) {
                HotelImages hotelImages = new HotelImages();
                hotelImages.setCategory(images.get("category").asText());
                hotelImages.setHotel(hotel);
                hotelImages.setImageUrl(images.get("imageUrl").asText());
                hotelImages.setUploadedBy("hotel");
                hotelImageRepository.save(hotelImages);
            }
        }

        for (JsonNode jsonMainfacility : jsonHotel.get("mainFacility")) {
            MainFacilities mainFacility = new MainFacilities();
            Optional<MainFacilities> fetchMainFacility = mainFacilityRepository.findByLabel(jsonMainfacility.asText());
            if (fetchMainFacility.isPresent()) {
                mainFacility = fetchMainFacility.get();
            } else {
                mainFacility.setLabel(jsonMainfacility.asText());
                mainFacilityRepository.save(mainFacility);
            }

            HotelMainFacilities hotelMainFacility = new HotelMainFacilities();
            Optional<HotelMainFacilities> fetchHotelMain = hotelMainFacilityRepository.findByHotelAndMainFacility(hotel,
                    mainFacility);
            if (fetchHotelMain.isEmpty()) {
                hotelMainFacility.setHotel(hotel);
                hotelMainFacility.setMainFacility(mainFacility);
                hotelMainFacilityRepository.save(hotelMainFacility);
            }

        }

        for (JsonNode jsonNearby : jsonHotel.get("nearbyPlaces")) {
            HotelNearbyPlaces hotelNearbyPlaces = new HotelNearbyPlaces();
            Optional<HotelNearbyPlaces> fetchNearbyPlaces = hotelNearbyPlaceRepository.findByNameAndCategoryAndHotel(
                    jsonNearby.get("name").asText(), jsonNearby.get("category").asText(), hotel);
            if (fetchNearbyPlaces.isEmpty()) {
                hotelNearbyPlaces.setCategory(jsonNearby.get("category").asText());
                hotelNearbyPlaces.setName(jsonNearby.get("name").asText());
                hotelNearbyPlaces.setDistance(jsonNearby.get("distance").asText());
                hotelNearbyPlaces.setLatitude(Double.valueOf(jsonNearby.get("latitude").asText()));
                hotelNearbyPlaces.setLongitude(Double.valueOf(jsonNearby.get("longitude").asText()));
                hotelNearbyPlaces.setHotel(hotel);
                hotelNearbyPlaceRepository.save(hotelNearbyPlaces);
            }
        }

        for (JsonNode jsonPopularAreas : jsonHotel.get("popularAreas")) {
            HotelPopularAreas hotelPopularAreas = new HotelPopularAreas();
            Optional<HotelPopularAreas> fetchPopular = hotelPopularAreaRepository.findByNameAndCategoryAndHotel(
                    jsonPopularAreas.get("name").asText(), jsonPopularAreas.get("category").asText(), hotel);
            if (fetchPopular.isEmpty()) {
                hotelPopularAreas.setCategory(jsonPopularAreas.get("category").asText());
                hotelPopularAreas.setName(jsonPopularAreas.get("name").asText());
                hotelPopularAreas.setDistance(jsonPopularAreas.get("distance").asText());
                hotelPopularAreas.setLatitude(Double.valueOf(jsonPopularAreas.get("latitude").asText()));
                hotelPopularAreas.setLongitude(Double.valueOf(jsonPopularAreas.get("longitude").asText()));
                hotelPopularAreas.setHotel(hotel);
                hotelPopularAreaRepository.save(hotelPopularAreas);
            }
        }

        HotelDescription hotelDescription = new HotelDescription();
        Optional<HotelDescription> fetchDescription = hotelDescriptionRepository.findByHotel(hotel);
        if (fetchDescription.isEmpty()) {
            hotelDescription.setHotel(hotel);
            hotelDescription.setText(jsonHotel.get("description").asText());
            hotelDescription.setType("long");
            hotelDescriptionRepository.save(hotelDescription);
        }

        HotelPolicies hotelPolicy = new HotelPolicies();
        Optional<HotelPolicies> fetchPolicies = hotelPolicyRepository.findByHotel(hotel);
        if (fetchPolicies.isEmpty()) {
            hotelPolicy.setHotel(hotel);
            hotelPolicy.setType("long");
            hotelPolicy.setText(jsonHotel.get("policy").asText());
            hotelPolicyRepository.save(hotelPolicy);
        }

        for (JsonNode jsonFacilities : jsonHotel.get("facilities")) {
            FacilityCategories facilityCategories = new FacilityCategories();
            Optional<FacilityCategories> fetchFacilityCategories = facilityCategoriesRepository
                    .findByName(jsonFacilities.get("category").asText());
            if (fetchFacilityCategories.isPresent()) {
                facilityCategories = fetchFacilityCategories.get();
            } else {
                facilityCategories.setName(jsonFacilities.get("category").asText());
                facilityCategoriesRepository.save(facilityCategories);
            }

            for (JsonNode facilities : jsonFacilities.get("items")) {
                Facilities facility = new Facilities();
                Optional<Facilities> fetchFacility = facilityRepository.findByName(facilities.asText());
                if (fetchFacility.isPresent()) {
                    facility = fetchFacility.get();
                } else {
                    facility.setCategory(facilityCategories);
                    facility.setName(facilities.asText());
                    facilityRepository.save(facility);
                }

                HotelFacilities hotelFacilities = new HotelFacilities();
                Optional<HotelFacilities> fetchHotelFacilites = hotelFacilitiesRepository
                        .findByHotelsAndFacilities(hotel, facility);
                if (fetchHotelFacilites.isEmpty()) {
                    hotelFacilities.setCategory(facility.getCategory());
                    hotelFacilities.setFacilities(facility);
                    hotelFacilities.setHotels(hotel);
                    hotelFacilitiesRepository.save(hotelFacilities);
                }
            }

        }

        for (JsonNode hotelRoom : jsonHotel.get("rooms")) {
            saveRoom(hotel, hotelRoom);
        }
    }

    @Autowired
    private HotelRoomRepository hotelRoomRepository;
    @Autowired
    private AmenitiesRepository amenitiesRepository;
    @Autowired
    private AmenitiesCategoryRepository amenitiesCategoryRepository;
    @Autowired
    private HotelRoomAmenitiesRepository hotelRoomAmenitiesRepository;
    @Autowired
    private HotelRoomImageRepository hotelRoomImageRepository;

    @Transactional
    public void saveRoom(Hotel hotel, JsonNode jsonRoom) {

        HotelRooms hotelRoom = new HotelRooms();
        Optional<HotelRooms> fetchRooms = hotelRoomRepository.findByNameAndHotel(jsonRoom.get("name").asText(), hotel);
        if (fetchRooms.isPresent()) {
            hotelRoom = fetchRooms.get();
        } else {
            hotelRoom.setHotel(hotel);
            hotelRoom.setName(jsonRoom.get("name").asText());
            hotelRoom.setSize(jsonRoom.get("size").asText());
            hotelRoom.setMaxOccupancy(Short.valueOf(jsonRoom.get("maxOccupancy").asText()));
            hotelRoom.setDescription(jsonRoom.get("description").asText());
            hotelRoomRepository.save(hotelRoom);
        }

        for (JsonNode jsonAmenities : jsonRoom.get("amenities")) {

            AmenityCategories amenityCategories = new AmenityCategories();
            Optional<AmenityCategories> fetchAmenityCategory = amenitiesCategoryRepository
                    .findByName(jsonAmenities.get("category").asText());
            if (fetchAmenityCategory.isPresent()) {
                amenityCategories = fetchAmenityCategory.get();
            } else {
                amenityCategories.setName(jsonAmenities.get("category").asText());
                amenitiesCategoryRepository.save(amenityCategories);
            }

            for (JsonNode jsonListAmenities : jsonAmenities.get("list")) {
                Amenities amenities = new Amenities();
                Optional<Amenities> fetchAmenities = amenitiesRepository.findByName(jsonListAmenities.asText());
                if (fetchAmenities.isPresent()) {
                    amenities = fetchAmenities.get();
                } else {
                    amenities.setCategory(amenityCategories);
                    amenities.setName(jsonListAmenities.asText());
                    amenitiesRepository.save(amenities);
                }

                HotelRoomAmenities hotelRoomAmenities = new HotelRoomAmenities();
                Optional<HotelRoomAmenities> fetchHotelRoomAmenity = hotelRoomAmenitiesRepository
                        .findByAmenitiesAndRooms(amenities, hotelRoom);
                if (fetchHotelRoomAmenity.isEmpty()) {
                    hotelRoomAmenities.setAmenities(amenities);
                    hotelRoomAmenities.setCategory(amenityCategories);
                    hotelRoomAmenities.setRooms(hotelRoom);
                    hotelRoomAmenitiesRepository.save(hotelRoomAmenities);
                }
            }
        }

        for (JsonNode jsonImages : jsonRoom.get("images")) {
            HotelRoomImages hotelRoomImages = new HotelRoomImages();
            Optional<HotelRoomImages> fetchHotelRoomImages = hotelRoomImageRepository.findByRoomAndImageUrl(hotelRoom,
                    jsonImages.get("imageUrl").asText());
            if (fetchHotelRoomImages.isEmpty()) {
                hotelRoomImages.setCategory(jsonImages.get("category").asText());
                hotelRoomImages.setHotel(hotel);
                hotelRoomImages.setImageUrl(jsonImages.get("imageUrl").asText());
                hotelRoomImages.setRoom(hotelRoom);
                hotelRoomImageRepository.save(hotelRoomImages);
            }
        }

    }
}
