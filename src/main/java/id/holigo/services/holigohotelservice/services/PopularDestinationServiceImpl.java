package id.holigo.services.holigohotelservice.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.holigo.services.holigohotelservice.domain.PopularDestination;
import id.holigo.services.holigohotelservice.repositories.PopularDestinationRepository;
import id.holigo.services.holigohotelservice.web.mappers.PopularDestinationMapper;
import id.holigo.services.holigohotelservice.web.model.PopularDestinationDto;

@Service
public class PopularDestinationServiceImpl implements PopularDestinationService {

    @Autowired
    private PopularDestinationRepository popularDestinationRepository;

    @Autowired
    private PopularDestinationMapper popularDestinationMapper;

    public List<PopularDestinationDto> getListDestination() {
        List<String> names = new ArrayList<>();
        names.add("Jakarta");
        names.add("Bandung");
        names.add("Bali");
        List<PopularDestination> listPopularDestination = popularDestinationRepository.findAllByCity(names);
        List<PopularDestinationDto> listPopularDestinationDto = listPopularDestination.stream()
                .map(popularDestinationMapper::popularDestinationToPopularDestinationDto).toList();

        return listPopularDestinationDto;
    }
}
