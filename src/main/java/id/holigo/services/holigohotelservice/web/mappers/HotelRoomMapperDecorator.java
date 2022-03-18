package id.holigo.services.holigohotelservice.web.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import id.holigo.services.holigohotelservice.domain.HotelRooms;
import id.holigo.services.holigohotelservice.services.HotelFacilitiesService;
import id.holigo.services.holigohotelservice.web.model.detailHotel.hotelRooms.HotelRoomDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.hotelRooms.RoomAmenityDto;

public class HotelRoomMapperDecorator implements HotelRoomMapper{
    
    @Autowired
    private HotelRoomMapper hotelRoomMapper;

    @Autowired
    private HotelFacilitiesService hotelFacilitiesService;

    @Override
    public HotelRoomDto hotelsToHotelRoomDto(HotelRooms hotelRooms){
        HotelRoomDto hotelRoomDto = hotelRoomMapper.hotelsToHotelRoomDto(hotelRooms);
        List<RoomAmenityDto> roomAmenity = hotelFacilitiesService.getAmenitiesHotelRoom(hotelRooms);
        hotelRoomDto.setAmenities(roomAmenity);
        return hotelRoomDto;
    }


}
