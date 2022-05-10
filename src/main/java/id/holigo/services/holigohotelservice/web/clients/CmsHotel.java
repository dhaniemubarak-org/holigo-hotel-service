package id.holigo.services.holigohotelservice.web.clients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.holigo.services.holigohotelservice.domain.*;
import id.holigo.services.holigohotelservice.repositories.*;
import id.holigo.services.holigohotelservice.web.model.GetHotelPricesDto;
import id.holigo.services.holigohotelservice.web.model.HotelPricesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class CmsHotel {

    private final RestTemplate restTemplate;

    private static final String apiHost = "http://127.0.0.1:8000/";

    @Autowired
    private HotelSupplierCodeRepository hotelSupplierCodeRepository;

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private HotelTypeRepository hotelTypeRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelAddressRepository hotelAddressRepository;

    @Autowired
    private HotelImageRepository hotelImageRepository;

    @Autowired
    private MainFacilityRepository mainFacilityRepository;

    @Autowired
    private HotelMainFacilityRepository hotelMainFacilityRepository;

    @Autowired
    private FacilityCategoriesRepository facilityCategoriesRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private HotelFacilitiesRepository hotelFacilitiesRepository;

    @Autowired
    private HotelDescriptionRepository hotelDescriptionRepository;

    @Autowired
    private HotelContactRepository hotelContactRepository;

    @Autowired
    private HotelRuleRepository hotelRuleRepository;

    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    @Autowired
    private HotelRoomImageRepository hotelRoomImageRepository;

    @Bean
    public RestTemplate restTemplate() {
        final RestTemplate restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }

    public CmsHotel(RestTemplateBuilder restTemplate) {
        this.restTemplate = restTemplate.build();
    }

    // @Scheduled(fixedDelay = 36000000)
    @Transactional
    public void gettingHotelName() {
        ObjectMapper objectMapper = new ObjectMapper();
        Optional<Countries> fetchCountries = countriesRepository.findById("ID");

        List<Cities> listCities = citiesRepository.findAllByCountry(fetchCountries.get());
        listCities.forEach(cities -> {
            int pages = 1;

            ResponseCmsHotel data = new ResponseCmsHotel();
            do {
                try {
                    String response = restTemplate.getForObject(apiHost + "hotels?page=" + pages
                            + "&country=ID&per_page=1&city=" + cities.getId(), String.class);
                    data = objectMapper.readValue(response, ResponseCmsHotel.class);
                } catch (Exception e) {
                    log.info("Error Message -> {}", e.getMessage());
                    e.printStackTrace();
                }
                for (JsonNode hotel : data.getData()) {
                    createHotel(hotel);
                }
                pages++;
            } while (pages < data.getMeta().get("last_page").asInt());
        });
    }

    //    @Scheduled(fixedDelay = 360000000)
    public void getByHotel(Integer lastId) {
        List<Hotel> listHotel = hotelRepository.findAllByIdGreaterThan(lastId);
        listHotel.forEach(this::createHotel);
    }

    @Transactional
    public void createHotel(JsonNode hotelNode) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode detailHotel = null;
        try {
            String response = restTemplate.getForObject(apiHost + "hotels/" + hotelNode.get("id").asText(),
                    String.class);
            detailHotel = objectMapper.readValue(response, JsonNode.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HotelTypes hotelTypes = new HotelTypes();
        assert detailHotel != null;
        Optional<HotelTypes> fetchHotelType = hotelTypeRepository
                .findByName(detailHotel.get("types").get("name_en").asText());
        if (fetchHotelType.isPresent()) {
            hotelTypes = fetchHotelType.get();
        } else {
            hotelTypes.setName(detailHotel.get("types").get("name_id").asText());
            hotelTypeRepository.save(hotelTypes);
        }

        Hotel hotel = new Hotel();
        Optional<Hotel> fetchHotel = hotelRepository.findByName(detailHotel.get("name").asText());
        if (fetchHotel.isPresent()) {
            hotel = fetchHotel.get();
        } else {
            hotel.setName(detailHotel.get("name").asText());
            hotel.setRating(detailHotel.get("star_rating").asDouble());
            hotel.setHotelType(hotelTypes);
            hotelRepository.save(hotel);
        }
        log.info("Hotel Name -> {}", hotel.getName());

        HotelAddresses hotelAddresses = new HotelAddresses();
        Optional<HotelAddresses> fetchHotelAddress = hotelAddressRepository.findByHotel(hotel);
        Optional<Cities> fetchCity = citiesRepository.findById(detailHotel.get("addresses").get("city_id").asLong());
        Cities city = fetchCity.get();
        if (fetchHotelAddress.isEmpty()) {
            hotelAddresses.setHotel(hotel);
            hotelAddresses.setCity(city);
            hotelAddresses.setCountry(city.getCountry());
            hotelAddresses.setDetail(detailHotel.get("addresses").get("detail").asText());
            hotelAddresses.setLatitude(detailHotel.get("addresses").get("latitude").asDouble());
            hotelAddresses.setLongitude(detailHotel.get("addresses").get("longitude").asDouble());
            hotelAddresses.setZipCode(detailHotel.get("addresses").get("zip_code").asText());
            hotelAddresses.setProvince(null);
            hotelAddresses.setDistrict(null);
            hotelAddressRepository.save(hotelAddresses);
        }

        for (JsonNode assets : detailHotel.get("assets")) {
            Optional<HotelImages> fetchImages = hotelImageRepository.findByHotelAndCategoryAndImageUrl(hotel,
                    assets.get("category").asText(), assets.get("source_url").asText());
            if (fetchImages.isEmpty()) {
                HotelImages hotelImages = new HotelImages();
                hotelImages.setHotel(hotel);
                hotelImages.setImageUrl(assets.get("source_url").asText());
                hotelImages.setCategory(assets.get("category").asText());
                hotelImages.setUploadedBy("SUPPLIER");
                hotelImages.setPathUrl(assets.get("path").asText());
                hotelImages.setIsActive(assets.get("is_active").asBoolean());
                hotelImages.setIsMain(assets.get("is_main").asBoolean());
                hotelImageRepository.save(hotelImages);
            }
        }

        for (JsonNode descriptions : detailHotel.get("description")) {
            HotelDescription hotelDescription = new HotelDescription();
            Optional<HotelDescription> fetchHotelDescription = hotelDescriptionRepository.findByHotelAndType(hotel,
                    descriptions.get("type").asText());
            if (fetchHotelDescription.isEmpty()) {
                hotelDescription.setHotel(hotel);
                hotelDescription.setType(descriptions.get("type").asText());
                hotelDescription.setText(descriptions.get("text_id").asText());
                hotelDescription.setTextEn(descriptions.get("text_en").asText());
                hotelDescriptionRepository.save(hotelDescription);
            }
        }

        for (JsonNode contacts : detailHotel.get("contacts")) {
            HotelContacts hotelContacts = new HotelContacts();
            Optional<HotelContacts> fetchHotelContacts = hotelContactRepository.findByHotelAndDetail(hotel,
                    contacts.get("detail").asText());
            if (fetchHotelContacts.isEmpty()) {
                hotelContacts.setHotel(hotel);
                hotelContacts.setDetail(contacts.get("detail").asText());
                hotelContacts.setType(contacts.get("type").asText());
                hotelContactRepository.save(hotelContacts);
            }
        }

        for (JsonNode rules : detailHotel.get("rules")) {
            HotelRules hotelRules = new HotelRules();
            Optional<HotelRules> fetchHotelRules = hotelRuleRepository.findByHotelAndLabel(hotel,
                    rules.get("key").asText());
            if (fetchHotelRules.isEmpty()) {
                hotelRules.setHotel(hotel);
                hotelRules.setLabel(rules.get("key").asText());
                hotelRules.setValue(rules.get("value").asText());
                hotelRuleRepository.save(hotelRules);
            }
        }

        HotelSupplierCode hotelSupplierCode = new HotelSupplierCode();
        Optional<HotelSupplierCode> fetchHotelSupplierCode = hotelSupplierCodeRepository.findByHotel(hotel);
        if (fetchHotelSupplierCode.isEmpty()) {
            hotelSupplierCode.setHotel(hotel);
            hotelSupplierCode
                    .setSupplierId(Short.valueOf(detailHotel.get("supplier_code").get("supplier_id").asText()));
            hotelSupplierCode.setCode(detailHotel.get("supplier_code").get("code").asText());
            hotelSupplierCodeRepository.save(hotelSupplierCode);
        }

        JsonNode dataFacility = null;
        try {
            String response = restTemplate.getForObject(
                    apiHost + "hotels/" + hotelNode.get("id").asText() + "/facilities", String.class);
            dataFacility = objectMapper.readValue(response, JsonNode.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (JsonNode mainFacility : dataFacility.get("data").get("main_facilities")) {
            String[] arrSplit = mainFacility.get("name").asText().split("\\|");
            MainFacilities mainFacilities = new MainFacilities();
            Optional<MainFacilities> fetchMainFacility = mainFacilityRepository.findByLabel(arrSplit[1]);
            if (fetchMainFacility.isEmpty()) {
                mainFacilities.setLabel(arrSplit[1]);
                mainFacilities.setLabelEn(arrSplit[0]);
                mainFacilities.setImageUrl(mainFacility.get("icon_url").asText());
                mainFacilityRepository.save(mainFacilities);
            } else {
                mainFacilities = fetchMainFacility.get();
            }

            HotelMainFacilities hotelMainFacilities = new HotelMainFacilities();
            Optional<HotelMainFacilities> fetchHotelMainFacilities = hotelMainFacilityRepository
                    .findByHotelAndMainFacility(hotel, mainFacilities);
            if (fetchHotelMainFacilities.isEmpty()) {
                hotelMainFacilities.setHotel(hotel);
                hotelMainFacilities.setMainFacility(mainFacilities);
                hotelMainFacilityRepository.save(hotelMainFacilities);
            }
        }

        for (JsonNode categoryFacility : dataFacility.get("data").get("facilities")) {
            String[] arrSplit = categoryFacility.get("name").asText().split("\\|");
            if (arrSplit.length < 2) {
                arrSplit[0] = categoryFacility.get("name").asText();
                arrSplit[1] = categoryFacility.get("name").asText();
            }
            FacilityCategories facilityCategories = new FacilityCategories();
            Optional<FacilityCategories> fetchCategory = facilityCategoriesRepository.findByName(arrSplit[1]);
            if (fetchCategory.isEmpty()) {
                facilityCategories.setName(arrSplit[1]);
                facilityCategories.setNameEn(arrSplit[0]);
                facilityCategories.setIcon(categoryFacility.get("icon_url").asText());
                facilityCategoriesRepository.save(facilityCategories);
            } else {
                facilityCategories = fetchCategory.get();
            }

            for (JsonNode childs : categoryFacility.get("childs")) {
                Facilities facilities = new Facilities();
                String[] splitFacility = childs.get("name").asText().split("\\|");
                Optional<Facilities> fetchFacilities;
                if (splitFacility.length < 2) {
                    splitFacility[0] = childs.get("name").asText();
                    fetchFacilities = facilityRepository.findByName(splitFacility[0]);
                    facilities.setName(splitFacility[0]);
                } else {
                    fetchFacilities = facilityRepository.findByName(splitFacility[1]);
                    facilities.setName(splitFacility[1]);
                }

                if (fetchFacilities.isEmpty()) {
                    facilities.setCategory(facilityCategories);
                    facilities.setIsShow(true);
                    facilities.setNameEn(splitFacility[0]);
                    facilityRepository.save(facilities);
                } else {
                    facilities = fetchFacilities.get();
                }

                HotelFacilities hotelFacilities = new HotelFacilities();
                Optional<HotelFacilities> fetchHotelFacilities = hotelFacilitiesRepository
                        .findByHotelsAndFacilities(hotel, facilities);
                if (fetchHotelFacilities.isEmpty()) {
                    hotelFacilities.setHotels(hotel);
                    hotelFacilities.setCategory(facilityCategories);
                    hotelFacilities.setFacilities(facilities);
                    hotelFacilitiesRepository.save(hotelFacilities);
                }
            }
        }

        for (JsonNode rooms : detailHotel.get("rooms")) {
            createRooms(rooms, hotel);
        }
    }

    @Transactional
    public void createHotel(Hotel hotelNode) {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode detailHotel = null;
        try {
            String response = restTemplate.getForObject(apiHost + "hotels/" + hotelNode.getId(),
                    String.class);
            detailHotel = objectMapper.readValue(response, JsonNode.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HotelTypes hotelTypes = new HotelTypes();
        assert detailHotel != null;
        Optional<HotelTypes> fetchHotelType = hotelTypeRepository
                .findByName(detailHotel.get("types").get("name_en").asText());
        if (fetchHotelType.isPresent()) {
            hotelTypes = fetchHotelType.get();
        } else {
            hotelTypes.setName(detailHotel.get("types").get("name_id").asText());
            hotelTypeRepository.save(hotelTypes);
        }

        JsonNode addresses = detailHotel.get("addresses");
        if(addresses.isNull()){
            return;
        }

        Cities city = new Cities();
        Long cityId = detailHotel.get("addresses").get("city_id").asLong();
        if(cityId != null){
            log.info("City Id nya NULL nich!");
        }else{
            log.info("Ada Error nih di cityId!");
        }
        Optional<Cities> fetchCity = citiesRepository.findById(cityId);
        if (fetchCity.isPresent()) {
            city = fetchCity.get();
        } else {
            city = null;
        }

        Hotel hotel = hotelNode;
        // Optional<Hotel> fetchHotel =
        // hotelRepository.findByName(detailHotel.get("name").asText());
        // if (fetchHotel.isPresent()) {
        // hotel = fetchHotel.get();
        // } else {
        // hotel.setName(detailHotel.get("name").asText());
        // hotel.setRating(detailHotel.get("star_rating").asDouble());
        // hotel.setHotelType(hotelTypes);
        // hotelRepository.save(hotel);
        // }
        log.info("Hotel Name -> {}", hotel.getName());

        HotelAddresses hotelAddresses = new HotelAddresses();
        Optional<HotelAddresses> fetchHotelAddress = hotelAddressRepository.findByHotel(hotel);
        if (fetchHotelAddress.isEmpty()) {
            hotelAddresses.setHotel(hotel);
            hotelAddresses.setCity(city);
            hotelAddresses.setCountry(city.getCountry());
            hotelAddresses.setDetail(detailHotel.get("addresses").get("detail").asText());
            hotelAddresses.setLatitude(detailHotel.get("addresses").get("latitude").asDouble());
            hotelAddresses.setLongitude(detailHotel.get("addresses").get("longitude").asDouble());
            hotelAddresses.setZipCode(detailHotel.get("addresses").get("zip_code").asText());
            hotelAddresses.setProvince(null);
            hotelAddresses.setDistrict(null);
            hotelAddressRepository.save(hotelAddresses);
        }

        for (JsonNode assets : detailHotel.get("assets")) {
            Optional<HotelImages> fetchImages = hotelImageRepository.findByHotelAndCategoryAndImageUrl(hotel,
                    assets.get("category").asText(), assets.get("source_url").asText());
            if (fetchImages.isEmpty()) {
                HotelImages hotelImages = new HotelImages();
                hotelImages.setHotel(hotel);
                hotelImages.setImageUrl(assets.get("source_url").asText());
                hotelImages.setCategory(assets.get("category").asText());
                hotelImages.setUploadedBy("SUPPLIER");
                hotelImages.setPathUrl(assets.get("path").asText());
                hotelImages.setIsActive(assets.get("is_active").asBoolean());
                hotelImages.setIsMain(assets.get("is_main").asBoolean());
                hotelImageRepository.save(hotelImages);
            }
        }

        for (JsonNode descriptions : detailHotel.get("description")) {
            HotelDescription hotelDescription = new HotelDescription();
            Optional<HotelDescription> fetchHotelDescription = hotelDescriptionRepository.findByHotelAndType(hotel,
                    descriptions.get("type").asText());
            if (fetchHotelDescription.isEmpty()) {
                hotelDescription.setHotel(hotel);
                hotelDescription.setType(descriptions.get("type").asText());
                hotelDescription.setText(descriptions.get("text_id").asText());
                hotelDescription.setTextEn(descriptions.get("text_en").asText());
                hotelDescriptionRepository.save(hotelDescription);
            }
        }

        for (JsonNode contacts : detailHotel.get("contacts")) {
            HotelContacts hotelContacts = new HotelContacts();
            Optional<HotelContacts> fetchHotelContacts = hotelContactRepository.findByHotelAndDetail(hotel,
                    contacts.get("detail").asText());
            if (fetchHotelContacts.isEmpty()) {
                hotelContacts.setHotel(hotel);
                hotelContacts.setDetail(contacts.get("detail").asText());
                hotelContacts.setType(contacts.get("type").asText());
                hotelContactRepository.save(hotelContacts);
            }
        }

        for (JsonNode rules : detailHotel.get("rules")) {
            HotelRules hotelRules = new HotelRules();
            Optional<HotelRules> fetchHotelRules = hotelRuleRepository.findByHotelAndLabel(hotel,
                    rules.get("key").asText());
            if (fetchHotelRules.isEmpty()) {
                hotelRules.setHotel(hotel);
                hotelRules.setLabel(rules.get("key").asText());
                hotelRules.setValue(rules.get("value").asText());
                hotelRuleRepository.save(hotelRules);
            }
        }

        HotelSupplierCode hotelSupplierCode = new HotelSupplierCode();
        Optional<HotelSupplierCode> fetchHotelSupplierCode = hotelSupplierCodeRepository.findByHotel(hotel);
        if (fetchHotelSupplierCode.isEmpty()) {
            hotelSupplierCode.setHotel(hotel);
            hotelSupplierCode
                    .setSupplierId(Short.valueOf(detailHotel.get("supplier_code").get("supplier_id").asText()));
            hotelSupplierCode.setCode(detailHotel.get("supplier_code").get("code").asText());
            hotelSupplierCodeRepository.save(hotelSupplierCode);
        }

        JsonNode dataFacility = null;
        try {
            String response = restTemplate
                    .getForObject(apiHost + "hotels/" + hotelNode.getId() + "/facilities", String.class);
            dataFacility = objectMapper.readValue(response, JsonNode.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (JsonNode mainFacility : dataFacility.get("data").get("main_facilities")) {
            String[] arrSplit = mainFacility.get("name").asText().split("\\|");
            MainFacilities mainFacilities = new MainFacilities();
            Optional<MainFacilities> fetchMainFacility = mainFacilityRepository.findByLabel(arrSplit[1]);
            if (fetchMainFacility.isEmpty()) {
                mainFacilities.setLabel(arrSplit[1]);
                mainFacilities.setLabelEn(arrSplit[0]);
                mainFacilities.setImageUrl(mainFacility.get("icon_url").asText());
                mainFacilityRepository.save(mainFacilities);
            } else {
                mainFacilities = fetchMainFacility.get();
            }

            HotelMainFacilities hotelMainFacilities = new HotelMainFacilities();
            Optional<HotelMainFacilities> fetchHotelMainFacilities = hotelMainFacilityRepository
                    .findByHotelAndMainFacility(hotel, mainFacilities);
            if (fetchHotelMainFacilities.isEmpty()) {
                hotelMainFacilities.setHotel(hotel);
                hotelMainFacilities.setMainFacility(mainFacilities);
                hotelMainFacilityRepository.save(hotelMainFacilities);
            }
        }

        for (JsonNode categoryFacility : dataFacility.get("data").get("facilities")) {
            String[] arrSplit = categoryFacility.get("name").asText().split("\\|");
            if (arrSplit.length < 2) {
                arrSplit[0] = categoryFacility.get("name").asText();
                arrSplit[1] = categoryFacility.get("name").asText();
            }
            FacilityCategories facilityCategories = new FacilityCategories();
            Optional<FacilityCategories> fetchCategory = facilityCategoriesRepository.findByName(arrSplit[1]);
            if (fetchCategory.isEmpty()) {
                facilityCategories.setName(arrSplit[1]);
                facilityCategories.setNameEn(arrSplit[0]);
                facilityCategories.setIcon(categoryFacility.get("icon_url").asText());
                facilityCategoriesRepository.save(facilityCategories);
            } else {
                facilityCategories = fetchCategory.get();
            }

            for (JsonNode childs : categoryFacility.get("childs")) {
                log.info("Child Facility -> {}", childs.get("name").asText());
                Facilities facilities = new Facilities();
                String[] splitFacility = childs.get("name").asText().split("\\|");
                log.info("Split Facility -> {}", (Object) splitFacility);
                log.info("Split Length -> {}", splitFacility.length);
                Optional<Facilities> fetchFacilities;
                if (splitFacility.length < 2) {
                    splitFacility[0] = childs.get("name").asText();
                    fetchFacilities = facilityRepository.findByName(splitFacility[0]);
                    facilities.setName(splitFacility[0]);
                } else {
                    fetchFacilities = facilityRepository.findByName(splitFacility[1]);
                    facilities.setName(splitFacility[1]);
                }

                if (fetchFacilities.isEmpty()) {
                    facilities.setCategory(facilityCategories);
                    facilities.setIsShow(true);
                    facilities.setNameEn(splitFacility[0]);
                    facilityRepository.save(facilities);
                } else {
                    facilities = fetchFacilities.get();
                }

                HotelFacilities hotelFacilities = new HotelFacilities();
                Optional<HotelFacilities> fetchHotelFacilities = hotelFacilitiesRepository
                        .findByHotelsAndFacilities(hotel, facilities);
                if (fetchHotelFacilities.isEmpty()) {
                    hotelFacilities.setHotels(hotel);
                    hotelFacilities.setCategory(facilityCategories);
                    hotelFacilities.setFacilities(facilities);
                    hotelFacilitiesRepository.save(hotelFacilities);
                }
            }
        }

        for (JsonNode rooms : detailHotel.get("rooms")) {
            createRooms(rooms, hotel);
        }
    }

    @Transactional
    public void createRooms(JsonNode jsonRoom, Hotel hotel) {
        HotelRooms hotelRooms = new HotelRooms();
        Optional<HotelRooms> fetchHotelRooms = hotelRoomRepository.findByNameAndHotel(jsonRoom.get("name").asText(),
                hotel);
        if (fetchHotelRooms.isEmpty()) {
            hotelRooms.setHotel(hotel);
            hotelRooms.setRoomSupplierId(jsonRoom.get("room_supplier_code").asText());
            hotelRooms.setName(jsonRoom.get("name").asText());
            hotelRooms.setDescription(jsonRoom.get("description").asText());
            hotelRooms.setSize(jsonRoom.get("size").asText());
            hotelRooms.setMaxOccupancy(Short.valueOf(jsonRoom.get("max_occupancy").asText()));
            hotelRooms.setMaxChilds(Short.valueOf(jsonRoom.get("max_childs").asText()));
            hotelRooms.setMaxAdults(Short.valueOf(jsonRoom.get("max_adults").asText()));
            hotelRoomRepository.save(hotelRooms);
        } else {
            hotelRooms = fetchHotelRooms.get();
        }

        for (JsonNode assets : jsonRoom.get("assets")) {
            HotelRoomImages hotelRoomImages = new HotelRoomImages();
            Optional<HotelRoomImages> fetchHotelRoomImages = hotelRoomImageRepository.findByRoomAndImageUrl(hotelRooms,
                    assets.get("source_url").asText());
            if (fetchHotelRoomImages.isEmpty()) {
                hotelRoomImages.setHotel(hotel);
                hotelRoomImages.setRoom(hotelRooms);
                hotelRoomImages.setImageUrl(assets.get("source_url").asText());
                hotelRoomImages.setPathUrl(assets.get("path").asText());
                hotelRoomImages.setUploadedBy("SUPPLIER");
                hotelRoomImageRepository.save(hotelRoomImages);
            }
        }
    }

    // @Transactional
    // @Scheduled(fixedDelay = 360000)
    public void getCities() {
        List<Countries> listCountries = countriesRepository.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        listCountries.forEach(countries -> {
            List<JsonNode> listCities = new ArrayList<>();
            try {
                String response = restTemplate
                        .getForObject(apiHost + "cities?country_id=" + countries.getId(), String.class);
                listCities = objectMapper.readValue(response, new TypeReference<List<JsonNode>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            listCities.forEach(node -> {
                Optional<Cities> fetchCity = citiesRepository.findByNameEnAndProvince(node.get("name_en").asText(),
                        null);
                if (fetchCity.isEmpty()) {
                    Cities city = new Cities();
                    city.setCode(node.get("code").asText());
                    city.setCountry(countries);
                    city.setNameEn(node.get("name_en").asText());
                    city.setNameId(node.get("name_id").asText());
                    city.setProvince(null);
                    city.setIsActive(node.get("is_active").asBoolean());
                    citiesRepository.save(city);
                }
            });
        });
    }

    public List<HotelPricesDto> pullingHotelIndexPrice(Long id, Date startDate, Date endDate){
        ObjectMapper objectMapper = new ObjectMapper();
        GetHotelPricesDto getHotelPricesDto = new GetHotelPricesDto();
        try{
            String response = restTemplate.getForObject(apiHost + "/hotels/" + id + "/prices?start_date=" + startDate + "&end_date=" + endDate, String.class);
            log.info("Response -> {}", response);
            getHotelPricesDto = objectMapper.readValue(response, GetHotelPricesDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return getHotelPricesDto.getData();
    }

}
