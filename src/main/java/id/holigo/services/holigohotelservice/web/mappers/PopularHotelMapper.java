package id.holigo.services.holigohotelservice.web.mappers;

import org.mapstruct.Mapper;

import id.holigo.services.holigohotelservice.domain.Cities;
import id.holigo.services.holigohotelservice.domain.PopularHotel;
import id.holigo.services.holigohotelservice.web.model.PopularHotelByCityDto;
import id.holigo.services.holigohotelservice.web.model.PopularHotelDto;

@Mapper
public interface PopularHotelMapper {
    
    PopularHotelDto popularHotelToPopularHotelDto(PopularHotel popularHotel);

    PopularHotel popularHotelDtoToPopularHotel(PopularHotelDto popularHotelDto);

    Cities popularHotelByCityDtoToCities(PopularHotelByCityDto popularHotelByCityDto);

    PopularHotelByCityDto citiesToPopularHotelByCityDto(Cities cities);
}
