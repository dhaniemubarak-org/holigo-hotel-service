package id.holigo.services.holigohotelservice.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.holigo.services.holigohotelservice.domain.FacilityCategories;
import id.holigo.services.holigohotelservice.domain.HotelFacilities;
import id.holigo.services.holigohotelservice.domain.Hotels;
import id.holigo.services.holigohotelservice.repositories.HotelFacilitiesRepository;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelFacilityDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HotelFacilitiesServiceImpl implements HotelFacilitiesService {

    @Autowired
    private HotelFacilitiesRepository hotelFacilitiesRepository;

    @Override
    public List<HotelFacilityDto> getFacilityHotel(Hotels hotelId) {
        List<FacilityCategories> categoryFacilities = hotelFacilitiesRepository.findAllByHotelId(hotelId);
        List<HotelFacilityDto> facilityDto = new ArrayList<>();
        categoryFacilities.stream().forEach((facilityCategories) -> {
            log.info("Category Id -> {}", facilityCategories);
            HotelFacilityDto hotelFacilityDto = new HotelFacilityDto();
            hotelFacilityDto.setIcon(facilityCategories.getIcon());
            hotelFacilityDto.setCategory(facilityCategories.getName());
            List<HotelFacilities> listFacilities = hotelFacilitiesRepository.findByCategory(facilityCategories);
            List<String> stringFacilities = new ArrayList<>();
            listFacilities.stream().forEach((fclty) -> {
                stringFacilities.add(fclty.getFacilities().getName());
            });
            hotelFacilityDto.setItems(stringFacilities);

            facilityDto.add(hotelFacilityDto);
        });
        return facilityDto;
    }
}
