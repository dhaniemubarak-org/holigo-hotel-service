package id.holigo.services.holigohotelservice.web.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import id.holigo.services.holigohotelservice.domain.HotelRooms;
import id.holigo.services.holigohotelservice.web.model.detailHotel.hotelRooms.HotelRoomDto;

@Mapper
@DecoratedWith(HotelRoomMapperDecorator.class)
public interface HotelRoomMapper {
    
    @Mapping(target = "amenities", ignore = true)
    HotelRoomDto hotelsToHotelRoomDto(HotelRooms hotelRooms);
    
}
