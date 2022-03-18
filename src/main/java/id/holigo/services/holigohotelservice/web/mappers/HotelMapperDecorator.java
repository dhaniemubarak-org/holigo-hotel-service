package id.holigo.services.holigohotelservice.web.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import id.holigo.services.holigohotelservice.domain.HotelPolicies;
import id.holigo.services.holigohotelservice.domain.Hotels;
import id.holigo.services.holigohotelservice.services.HotelFacilitiesService;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDescriptionDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelFacilityDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelPolicyDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.hotelRooms.HotelRoomDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HotelMapperDecorator implements HotelMapper {

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private HotelFacilitiesService hotelFacilitiesService;

    @Autowired
    private HotelRoomMapper hotelRoomMapper;

    @Override
    public HotelDto hotelsToHotelDto(Hotels hotels) {
        HotelDto hotelDto = hotelMapper.hotelsToHotelDto(hotels);

        HotelDescriptionDto descriptionDto = new HotelDescriptionDto();
        hotels.getDescriptions().stream().forEach((desc) -> {
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

        List<String> additionalInformations = new ArrayList<>();
        hotels.getAdditionalInformations().stream().forEach((information) -> {
            additionalInformations.add(information.getInformation());
        });
        hotelDto.setAdditionalInformations(additionalInformations);

        List<HotelFacilityDto> facilityDto = hotelFacilitiesService.getFacilityHotel(hotels);
        hotelDto.setFacilities(facilityDto);

        List<HotelRoomDto> listRoomDtos = hotels.getRooms().stream().map(hotelRoomMapper::hotelsToHotelRoomDto)
                .toList();
        hotelDto.setRooms(listRoomDtos);

        return hotelDto;
    }

}
