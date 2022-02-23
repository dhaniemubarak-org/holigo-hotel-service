package id.holigo.services.holigohotelservice.web.mappers;

import org.mapstruct.Mapper;

import id.holigo.services.holigohotelservice.domain.PopularDestination;
import id.holigo.services.holigohotelservice.web.model.PopularDestinationDto;

@Mapper
public interface PopularDestinationMapper {

    PopularDestination popularDestinationDtoToPopularDestination(PopularDestinationDto popularDestinationDto);

    PopularDestinationDto popularDestinationToPopularDestinationDto(PopularDestination popularDestination);
}
