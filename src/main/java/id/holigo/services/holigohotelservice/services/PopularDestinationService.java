package id.holigo.services.holigohotelservice.services;

import java.util.List;

import id.holigo.services.holigohotelservice.web.model.PopularDestinationDto;

public interface PopularDestinationService {
    List<PopularDestinationDto> getListDestination();
}
