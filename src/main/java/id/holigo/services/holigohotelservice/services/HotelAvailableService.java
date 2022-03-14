package id.holigo.services.holigohotelservice.services;

import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDto;

public interface HotelAvailableService {
    HotelDto getDetailHotelAvailable(Long id);
}
