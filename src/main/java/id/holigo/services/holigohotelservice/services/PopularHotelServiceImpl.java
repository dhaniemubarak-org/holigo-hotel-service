package id.holigo.services.holigohotelservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.holigo.services.holigohotelservice.repositories.PopularHotelRepository;
import id.holigo.services.holigohotelservice.web.model.PopularHotelByCityDto;

@Service
public class PopularHotelServiceImpl implements PopularHotelService{

    @Autowired
    private PopularHotelRepository popularHotelRepository;
    
    public List<PopularHotelByCityDto> getPopularByCity(){
        return null;
    }
}
