package id.holigo.services.holigohotelservice.web.mappers;

import org.mapstruct.AfterMapping;

import id.holigo.services.holigohotelservice.domain.Hotels;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelLocationDto;

public abstract class HotelMapperDecorator{

    private HotelMapper hotelMapper;

    public HotelMapperDecorator(HotelMapper hotelMapper) {
        this.hotelMapper = hotelMapper;
    }

    @AfterMapping
    public HotelDto hotelsToHotelDto(Hotels hotel) {
        HotelDto hotelDto = new HotelDto();
        hotelDto.setName(hotel.getName());
        hotelDto.setType(hotel.getHotelType().getName());
        hotelDto.setRating(hotel.getRating());
        HotelLocationDto hotelLocationDto = new HotelLocationDto();
        hotelLocationDto.setCountry(hotel.getLocation().getCountry().getName());
        hotelLocationDto.setProvince(hotel.getLocation().getProvince().getName());
        hotelLocationDto.setCity(hotel.getLocation().getCity().getName());
        hotelLocationDto.setDistrict(hotel.getLocation().getDistrict().getName());
        hotelLocationDto.setAddress(hotel.getLocation().getDetail());
        hotelLocationDto.setLatitude(hotel.getLocation().getLatitude());
        hotelDto.setLocation(HotelLocationDto.builder().address(hotel.getLocation().getDetail())
                .country(hotel.getLocation().getCity().getName()).province(hotel.getLocation().getProvince().getName())
                .city(hotel.getLocation().getCity().getName()).district(hotel.getLocation().getDistrict().getName())
                .latitude(hotel.getLocation().getLatitude()).longitude(hotel.getLocation().getLongitude()).build());
        return hotelDto;
    }

}
