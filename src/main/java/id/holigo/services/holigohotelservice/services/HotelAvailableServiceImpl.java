package id.holigo.services.holigohotelservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.holigo.services.holigohotelservice.domain.Hotels;
import id.holigo.services.holigohotelservice.repositories.HotelRepository;
import id.holigo.services.holigohotelservice.web.mappers.HotelMapper;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDto;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HotelAvailableServiceImpl implements HotelAvailableService {

        @Autowired
        private HotelRepository hotelRepository;

        @Autowired
        private HotelMapper hotelMapper;

        public HotelDto getDetailHotelAvailable(Long id) {
                // MASIH DUMMY
                // HotelPolicyDto hotelPolicy = HotelPolicyDto.builder().longPolicy(
                //                 "Hewan peliharaan diizinkan , biaya tambahan berlaku. Hotel memperbolehkan check-in awal, bergantung pada ketersediaan kamar, dengan biaya tambahan. Hotel memperbolehkan check-out lebih lama, bergantung pada ketersediaan kamar, dengan biaya tambahan. Hotel ini akan mengenakan biaya tambahan untuk transportasi sebesar IDR 330,000.NOTICE: TEMPORARY CLOSURE & UPDATE OF PUBLIC FACILITIES To support and follow the Large Scale Social Restriction regulation from the local authorities and to prioritize the health and safety for all our guests, please be informed that the facilities: Gym & Fitness Center, Swimming Pool, Cubbies Playground, Spa and Sauna will be temporary closed until further notice. Restaurant will open from 06.00 AM to 10.30 PM (Monday - Friday) and 07.00 AM to 11.00 PM (Saturday - Sunday). Breakfast will be delivered to the room, restaurant not opened for dine-in, and only serving in room dining & take away. And facilities noticement: free parking listed is only applicable for one car per unit. We apologize for any inconvenience caused. Thank you for your understanding.")
                //                 .shortPolicy(
                //                                 "Hewan peliharaan diizinkan , biaya tambahan berlaku. Hotel memperbolehkan check-in awal, bergantung pada ketersediaan kamar, dengan biaya tambahan.")
                //                 .build();
                // HotelDetailInformationDto detailInformationDto = HotelDetailInformationDto.builder()
                //                 .headerUrl("https://imagekit.io/holigo/XXX.png")
                //                 .illustration("https://imagekit.io/holigo/XXX.png")
                //                 .title("Apa itu InDOnesia CARE?").body("\nSelamat Datang.").build();
                // HotelInformationDto hotelInformationDto = HotelInformationDto.builder().imageUrl("https://XXX.co")
                //                 .title("InDOnesia CARE")
                //                 .subtitle("Akomodasi ini bersih, aman, dan tersertifikasi CHSE.")
                //                 .detail(detailInformationDto).build();

                HotelDto hotelDto = new HotelDto();
                // hotelDto.setId(id);
                // hotelDto.setName("Ascott Sudirman Jakarta");
                // hotelDto.setType("Hotel");
                // hotelDto.setRating(5.0);
                // List<String> additionalInformation = Arrays.asList("8 Orang baru saja melakukan booking!",
                //                 "25 Orang melihat hotel ini!");
                // hotelDto.setAdditionalInformations(additionalInformation);
                // hotelDto.setPolicy(hotelPolicy);
                // hotelDto.setHotelInformation(hotelInformationDto);
                Optional<Hotels> hotel = hotelRepository.findById(id);
                log.info("Response Hotel -> {}", hotel.get().getFacilities());
                hotelDto = hotelMapper.hotelsToHotelDto(hotel.get());
                return hotelDto;

        }
}
