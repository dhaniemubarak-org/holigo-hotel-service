package id.holigo.services.holigohotelservice.web.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import id.holigo.services.holigohotelservice.domain.Hotels;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDto;

@Mapper
@DecoratedWith(HotelMapperDecorator.class)
public interface HotelMapper {

    @Mapping(source = "hotel.location.country.name", target = "location.country")
    @Mapping(source = "hotel.location.province.name", target = "location.province")
    @Mapping(source = "hotel.location.city.name", target = "location.city")
    @Mapping(source = "hotel.location.district.name", target = "location.district")
    @Mapping(source = "hotel.location.detail", target = "location.address")
    @Mapping(source = "hotel.hotelType.name", target = "type")
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "policy", ignore = true)
    @Mapping(target = "additionalInformations", ignore = true)
    @Mapping(target =  "facilities", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    public HotelDto hotelsToHotelDto(Hotels hotel);
}
