package id.holigo.services.holigohotelservice.services;

import java.util.List;

import id.holigo.services.holigohotelservice.domain.HotelRooms;
import id.holigo.services.holigohotelservice.domain.Hotels;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelFacilityDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.hotelRooms.RoomAmenityDto;

public interface HotelFacilitiesService {
    public List<HotelFacilityDto> getFacilityHotel(Hotels hotelId);
    public List<RoomAmenityDto> getAmenitiesHotelRoom(HotelRooms hotelRoom);
}
