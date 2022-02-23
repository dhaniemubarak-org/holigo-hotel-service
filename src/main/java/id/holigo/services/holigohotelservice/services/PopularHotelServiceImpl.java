package id.holigo.services.holigohotelservice.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.holigo.services.holigohotelservice.domain.Cities;
import id.holigo.services.holigohotelservice.repositories.CityRepository;
import id.holigo.services.holigohotelservice.web.mappers.PopularHotelMapper;
import id.holigo.services.holigohotelservice.web.model.PopularHotelByCityDto;

@Service
public class PopularHotelServiceImpl implements PopularHotelService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PopularHotelMapper popularHotelMapper;

    public List<PopularHotelByCityDto> getPopularByCity() {
        List<String> names = new ArrayList<>();
        names.add("Jakarta");
        names.add("Bandung");
        names.add("Bali");
        List<Cities> cities = cityRepository.findByCitiesNames(names);
        List<PopularHotelByCityDto> listPopularDto = cities.stream()
                .map(popularHotelMapper::citiesToPopularHotelByCityDto).toList();

        return listPopularDto;
    }
}
