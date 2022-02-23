package id.holigo.services.holigohotelservice.services;

import java.util.List;

import id.holigo.services.holigohotelservice.web.model.PopularHotelByCityDto;

public interface PopularHotelService {
    
    List<PopularHotelByCityDto> getPopularByCity();
}
