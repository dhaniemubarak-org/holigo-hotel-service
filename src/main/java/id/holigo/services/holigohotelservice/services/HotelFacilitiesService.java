package id.holigo.services.holigohotelservice.services;

import java.util.List;

import id.holigo.services.holigohotelservice.domain.HotelRooms;
import id.holigo.services.holigohotelservice.domain.Hotel;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelFacilityDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.hotelRooms.RoomAmenityDto;

public interface HotelFacilitiesService {
    public List<HotelFacilityDto> getFacilityHotel(Hotel hotelId);
    public List<RoomAmenityDto> getAmenitiesHotelRoom(HotelRooms hotelRoom);
}
