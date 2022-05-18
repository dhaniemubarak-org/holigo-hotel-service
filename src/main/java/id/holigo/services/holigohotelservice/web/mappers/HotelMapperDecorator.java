package id.holigo.services.holigohotelservice.web.mappers;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import id.holigo.services.common.model.FareDetailDto;
import id.holigo.services.common.model.FareDto;
import id.holigo.services.holigohotelservice.repositories.HotelMainFacilityRepository;
import id.holigo.services.holigohotelservice.services.fares.FareDetailService;
import id.holigo.services.holigohotelservice.web.model.HotelAdditionalInformationDto;
import id.holigo.services.holigohotelservice.web.model.HotelDetailFareDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.*;
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

    @Autowired
    private FareDetailService fareDetailService;

    @Override
    public HotelDto hotelsToHotelDto(Hotel hotels) {
        HotelDto hotelDto = hotelMapper.hotelsToHotelDto(hotels);

        HotelDescriptionDto descriptionDto = new HotelDescriptionDto();
        hotels.getDescriptions().forEach((desc) -> {
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

        List<HotelFacilityDto> facilityDto = hotelFacilitiesService.getFacilityHotel(hotels);
        hotelDto.setFacilities(facilityDto);

        List<HotelRoomDto> listRoomDtos = hotels.getRooms().stream().map(hotelRoomMapper::hotelsToHotelRoomDto)
                .toList();
        hotelDto.setRooms(listRoomDtos);

        List<HotelAdditionalInformationDto> additional = new ArrayList<>();
        HotelAdditionalInformationDto additionalInformation = new HotelAdditionalInformationDto();
        additionalInformation.setInformation("8 Orang baru saja melakukan booking");
        additionalInformation.setIconUrl("https://ik.imagekit.io/holigo/fasilitas_hotel/visible_bvsCxa8j7.png");

        HotelAdditionalInformationDto addinf2 = new HotelAdditionalInformationDto();
        addinf2.setIconUrl("https://ik.imagekit.io/holigo/fasilitas_hotel/visible_bvsCxa8j7.png");
        addinf2.setInformation("128 Orang melihat hotel ini.");
        additional.add(additionalInformation);
        additional.add(addinf2);
        hotelDto.setAdditionalInformations(additional);

        HotelDetailInformationDto detailInformationDto = new HotelDetailInformationDto();
        detailInformationDto.setHeaderUrl("https://ik.imagekit.io/holigo/default-image.jpg");
        detailInformationDto.setIllustration("https://ik.imagekit.io/holigo/default-image.jpg");
        detailInformationDto.setTitle("Apa itu InDOnesia cARE?");
        detailInformationDto.setBody("Akomodasi ini bersih, aman dan tersertifikasi CHSE dari Kementerian Pariwisata dan Ekonomi Kreatif.");

        HotelInformationDto hotelInformationDto = new HotelInformationDto();
        hotelInformationDto.setTitle("InDOnesia cARE");
        hotelInformationDto.setImageUrl("https://ik.imagekit.io/holigo/Icon/idocare_InuJi4wb3.png");
        hotelInformationDto.setSubtitle("Akomodasi ini bersih, aman dan tersertifikasi CHSE dari Kementerian Pariwisata dan Ekonomi Kreatif.");
        hotelInformationDto.setDetail(detailInformationDto);

        hotelDto.setHotelInformation(hotelInformationDto);

        List<HotelReviewContentDto> listContent = new ArrayList<>();
        HoligoReviewDto holigoReviewDto = new HoligoReviewDto();

        HotelReviewUserDto user1 = HotelReviewUserDto.builder().name("Andika Aditya Wicaksono").imageUrl("https://ik.imagekit.io/holigo/default-image.jpg").build();
        HotelReviewContentDto content1 = new HotelReviewContentDto();
        content1.setUser(user1);
        content1.setCountry("Indonesia");
        content1.setCreatedAt(Timestamp.valueOf("2022-04-22 10:01:22"));
        content1.setRating(4.9);
        content1.setBody("Keren banget buat kepantai cuman jalan kaki. Pelayanannya juga ok banget, staff ramah, dan kebersihannya terjamin banget. Recommended pokoknya!");

        List<String> images1 = new ArrayList<>();
        images1.add("https://ik.imagekit.io/holigo/sample_images/hotel/kebun/angsana_1br/1_foiuWijna.jpg");
        images1.add("https://ik.imagekit.io/holigo/sample_images/hotel/kebun/exterior/9_-gEKJO33e.webp");
        images1.add("https://ik.imagekit.io/holigo/sample_images/hotel/kebun/kamboja_family/4_JWyMqBnqH.jpg");
        content1.setImages(images1);
        content1.setIsLike(true);
        content1.setIsShow(true);
        listContent.add(content1);

        HotelReviewUserDto user2 = HotelReviewUserDto.builder().name("Manunggal Surya Kencana").imageUrl("https://ik.imagekit.io/holigo/default-image.jpg").build();
        HotelReviewContentDto content2 = new HotelReviewContentDto();
        content2.setUser(user2);
        content2.setCountry("Indonesia");
        content2.setCreatedAt(Timestamp.valueOf("2022-05-10 09:30:22"));
        content2.setRating(3.0);
        content2.setBody("Kamar bersih dan nyaman tapi sayang AC nya tidak terlalu dingin. Saya sudah komplain untuk minta ganti kamar tapi pihak hotel berbelit untuk menentukan keputusan.");
        List<String> images2 = new ArrayList<>();
        images2.add("https://ik.imagekit.io/holigo/sample_images/hotel/sthala/deluxe_family_studio_room/3_5qwVe6UKQ.jpg");
        images2.add("https://ik.imagekit.io/holigo/sample_images/hotel/sthala/exterior/3_uAJyQGdxqR.webp");
        content2.setImages(images2);
        content2.setIsLike(false);
        content2.setIsShow(true);
        listContent.add(content2);

        holigoReviewDto.setTitle("4.0 - Fantastis");
        holigoReviewDto.setSubtitle("Berdasarkan 2 ulasan");
        holigoReviewDto.setIcon("https://ik.imagekit.io/holigo/Icon/logo-vanilla_6xIE2Z0c_.png");
        holigoReviewDto.setContent(listContent);

        HotelNearbyPlaceDto nearby1 = new HotelNearbyPlaceDto();
        nearby1.setName("Soekarno Hatta International Airport (CGK)");
        nearby1.setCategory("Transportasi");
        nearby1.setDistance("22 km");
        nearby1.setIcon("https://ik.imagekit.io/holigo/fasilitas_hotel/take-off_Xt8gdHmEixd.png");

        HotelNearbyPlaceDto nearby2 = new HotelNearbyPlaceDto();
        nearby2.setName("Pantai Pasir Putih");
        nearby2.setCategory("Wisata Alam");
        nearby2.setDistance("18 km");
        nearby2.setIcon("https://ik.imagekit.io/holigo/fasilitas_hotel/Park_cSujUHjvchA.png");

        HotelNearbyPlaceDto nearby3 = new HotelNearbyPlaceDto();
        nearby3.setName("Cengkareng Golf Club");
        nearby3.setCategory("Olahraga");
        nearby3.setDistance("12 km");
        nearby3.setIcon("https://ik.imagekit.io/holigo/fasilitas_hotel/golf_oi-HoTVxw.png");

        List<HotelNearbyPlaceDto> popularAreas = new ArrayList<>();
        popularAreas.add(nearby1);
        popularAreas.add(nearby2);
        popularAreas.add(nearby3);

        hotelDto.setPopularAreas(popularAreas);

        HotelNearbyPlaceDto nearby4 = new HotelNearbyPlaceDto();
        nearby4.setName("KFC Terminal 2");
        nearby4.setCategory("Restoran");
        nearby4.setDistance("0.2 km");
        nearby4.setIcon("https://ik.imagekit.io/holigo/fasilitas_hotel/Restaurant_V5CEQ45R1B.png");

        HotelNearbyPlaceDto nearby5 = new HotelNearbyPlaceDto();
        nearby5.setName("Solaria Soewarna");
        nearby5.setCategory("Restoran");
        nearby5.setDistance("0.6 km");
        nearby5.setIcon("https://ik.imagekit.io/holigo/fasilitas_hotel/Restaurant_V5CEQ45R1B.png");

        HotelNearbyPlaceDto nearby6 = new HotelNearbyPlaceDto();
        nearby6.setName("Jakarta Convention Center");
        nearby6.setCategory("Bisnis");
        nearby6.setDistance("0 m");
        nearby6.setIcon("https://ik.imagekit.io/holigo/fasilitas_hotel/bag_n0kLoeqFz.png");

        List<HotelNearbyPlaceDto> nearbyList = new ArrayList<>();
        nearbyList.add(nearby4);
        nearbyList.add(nearby5);
        nearbyList.add(nearby6);
        hotelDto.setNearbyPlaces(nearbyList);

        hotelDto.setHoligoReview(holigoReviewDto);

        HotelTagDto hotelTag = HotelTagDto.builder().name("Pilihan Keluarga").color("#A17A0F").background("#FFF9E9").build();
        hotelDto.setTag(hotelTag);

        HotelPolicyDto hotelPolicyDto = new HotelPolicyDto();
        hotelPolicyDto.setLongPolicy("<p><strong>Anak</strong><br />Tamu umur berapa pun bisa menginap di sini.</p>\n" +
                "<p>Anak-anak 6 tahun ke atas dianggap sebagai tamu dewasa.</p>\n" +
                "<p>Pastikan umur anak yang menginap sesuai dengan detail pemesanan. Jika berbeda, tamu mungkin akan dikenakan biaya tambahan saat check-in.</p>\n" +
                "<p><br /><strong>Deposit</strong><br />Tamu tidak perlu membayar deposit saat check-in.</p>\n" +
                "<p><br /><strong>Umur</strong><br />Tamu umur berapa pun bisa menginap di sini.</p>\n" +
                "<p><br /><strong>Sarapan</strong><br />Sarapan tersedia pukul 06:30 - 10:30 waktu lokal.</p>\n" +
                "<p><br /><strong>Hewan peliharaan</strong><br />Tidak diperbolehkan membawa hewan peliharaan.</p>\n" +
                "<p><br /><strong>Merokok</strong><br />Kamar bebas asap rokok.</p>\n" +
                "<p><br /><strong>Alkohol</strong><br />Minuman beralkohol diperbolehkan.</p>");
        hotelPolicyDto.setShortPolicy("<p><strong>Anak</strong><br />Tamu umur berapa pun bisa menginap di sini.</p>\n" +
                "<p>Anak-anak 6 tahun ke atas dianggap sebagai tamu dewasa.</p>\n" +
                "<p>Pastikan umur anak yang menginap sesuai dengan detail pemesanan. Jika berbeda, tamu mungkin akan dikenakan biaya tambahan saat check-in.</p>\n" +
                "<p><br /><strong>Deposit</strong><br />Tamu tidak perlu membayar deposit saat check-in.</p>\n" +
                "<p><br /><strong>Umur</strong><br />Tamu umur berapa pun bisa menginap di sini.</p>\n" +
                "<p><br /><strong>Sarapan</strong><br />Sarapan tersedia pukul 06:30 - 10:30 waktu lokal.</p>\n" +
                "<p><br /><strong>Hewan peliharaan</strong><br />Tidak diperbolehkan membawa hewan peliharaan.</p>\n" +
                "<p><br /><strong>Merokok</strong><br />Kamar bebas asap rokok.</p>\n" +
                "<p><br /><strong>Alkohol</strong><br />Minuman beralkohol diperbolehkan.</p>");
        hotelDto.setPolicy(hotelPolicyDto);
        return hotelDto;
    }

    @Override
    public DetailHotelForListDto hotelAvailableToDetailHotelForUserDto(HotelAvailable hotelAvailable) {
        ObjectMapper objectMapper = new ObjectMapper();
        DetailHotelForListDto detailHotelForListDto = new DetailHotelForListDto();
        detailHotelForListDto.setId(hotelAvailable.getHotelId().toString());
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
        HotelTagDto hotelTag = HotelTagDto.builder().name("Pilihan Keluarga").color("#A17A0F").background("#FFF9E9").build();
        detailHotelForListDto.setTag(hotelTag);

        RatingReviewDto reviewDto = RatingReviewDto.builder().provider("Holigo Review").rating(Double.valueOf(4.6)).icon("").build();
        detailHotelForListDto.setRatingReviews(reviewDto);
//        try {
//            List<RatingReviewDto> ratingReviewDtos = objectMapper.readValue(hotelAvailable.getRatingReviews(),
//                    new TypeReference<List<RatingReviewDto>>() {
//                    });
//            detailHotelForListDto.setRatingReviews(ratingReviewDtos);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

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
        if (city.isPresent()) {
            province = city.get().getProvince();
            hotelAvailable.setCityId(city.get());
            hotelAvailable.setProvinceId(province);
        } else {
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
                if (!isContainsFacility) {
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
                    if (!isContainAmenities) {
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

        return detailHotelForListDto;
    }

    @Override
    public DetailHotelForListDto hotelDtoToDetailHotelForListDto(HotelDto hotelDto, Date checkIn, BigDecimal fareAmount, BigDecimal ntaAmount) {
        DetailHotelForListDto detailHotelForListDto = hotelMapper.hotelDtoToDetailHotelForListDto(hotelDto, checkIn, fareAmount, ntaAmount);
        log.info("Fare Amount -> {}", fareAmount);
        log.info("Nta Amount -> {}", ntaAmount);
        BigDecimal nraAmount = fareAmount.subtract(ntaAmount);

        FareDetailDto fareDetailDto = new FareDetailDto();
        fareDetailDto.setProductId(28);
        fareDetailDto.setNraAmount(nraAmount);
        fareDetailDto.setNtaAmount(ntaAmount);
        fareDetailDto.setUserId(Long.valueOf(5));

        FareDto fareDto = null;
        try {
            fareDto = fareDetailService.getDetailProduct(fareDetailDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        detailHotelForListDto.setLatitude(hotelDto.getLocation().getLatitude());
        detailHotelForListDto.setLongitude(hotelDto.getLocation().getLongitude());
        detailHotelForListDto.setCity(hotelDto.getLocation().getCity() + ", " + hotelDto.getLocation().getProvince());
        detailHotelForListDto.setFareType("PERNIGHT");
        detailHotelForListDto.setFareAmount(fareDto.getFareAmount());
        detailHotelForListDto.setNormalFare(fareAmount);
        detailHotelForListDto.setPoint(fareDto.getHpAmount());

        HotelPriceDto price = new HotelPriceDto();
        price.setFareAmount(fareDto.getFareAmount());
        price.setNormalFare(fareAmount);
        price.setPoint(fareDto.getHpAmount());
        price.setType("PERNIGHT");
        detailHotelForListDto.setPrice(price);

        List<String> listFacilities = new ArrayList<>();
        for (HotelFacilityDto hotelFacilityDto : hotelDto.getFacilities()) {
            for (String facility : hotelFacilityDto.getItems()) {
                boolean isContainsFacility = listFacilities.contains(facility);
                if (!isContainsFacility) {
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
                    if (!isContainAmenities) {
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

        detailHotelForListDto.setCheckIn(checkIn);
        return detailHotelForListDto;
    }

    @Override
    public HotelDetailFareDto hotelDtoToHotelDetailFareDto(HotelDto hotelDto) {
        return hotelMapper.hotelDtoToHotelDetailFareDto(hotelDto);
    }


}
