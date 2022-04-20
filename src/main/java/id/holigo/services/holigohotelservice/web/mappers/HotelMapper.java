package id.holigo.services.holigohotelservice.web.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import id.holigo.services.holigohotelservice.domain.HotelAvailable;
import id.holigo.services.holigohotelservice.domain.Hotel;
import id.holigo.services.holigohotelservice.web.model.DetailHotelForListDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDto;

@Mapper
@DecoratedWith(HotelMapperDecorator.class)
public interface HotelMapper {

    @Mapping(source = "hotel.location.country.name", target = "location.country")
    @Mapping(source = "hotel.location.province.name", target = "location.province")
    @Mapping(source = "hotel.location.city.nameId", target = "location.city")
    @Mapping(source = "hotel.location.district.name", target = "location.district")
    @Mapping(source = "hotel.location.detail", target = "location.address")
    @Mapping(source = "hotel.hotelType.name", target = "type")
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "policy", ignore = true)
    @Mapping(target = "additionalInformations", ignore = true)
    @Mapping(target =  "facilities", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    public HotelDto hotelsToHotelDto(Hotel hotel);

    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "tag", ignore = true)
    @Mapping(target = "ratingReviews", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "facilities", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "freeBreakfast", ignore = true)
    @Mapping(target = "refundable", ignore = true)
    @Mapping(target = "freeRefundable", ignore = true)
    public DetailHotelForListDto hotelAvailableToDetailHotelForUserDto(HotelAvailable hotelAvailable);

    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "tag", ignore = true)
    @Mapping(target = "ratingReviews", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "facilities", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "freeBreakfast", ignore = true)
    @Mapping(target = "refundable", ignore = true)
    @Mapping(target = "freeRefundable", ignore = true)
    @Mapping(target = "cityId", ignore = true)
    public HotelAvailable detailHotelDtoToHotelAvailable(DetailHotelForListDto detailHotelForListDto, Integer cityId);

    @Mapping(target = "facilities", ignore = true)
    public DetailHotelForListDto hotelDtoToDetailHotelForListDto(HotelDto hotelDto);
}
