package id.holigo.services.holigohotelservice.services;

import java.util.List;

import id.holigo.services.holigohotelservice.domain.Hotels;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelFacilityDto;

public interface HotelFacilitiesService {
    public List<HotelFacilityDto> getFacilityHotel(Hotels hotelId);
}
