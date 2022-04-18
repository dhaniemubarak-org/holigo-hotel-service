package id.holigo.services.holigohotelservice.services.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import id.holigo.services.common.model.HotelDtoForExternal;

@FeignClient(name = "hotel-external-service")
public interface HotelExternalServiceFeignClient {

    public static final String HOTEL_EXTERNAL_DETAIL_BY_CITY_ID = "/api/v1/traveloka/hotels";

    @RequestMapping(method = RequestMethod.GET, value = HOTEL_EXTERNAL_DETAIL_BY_CITY_ID)
    ResponseEntity<HotelDtoForExternal> getHotelByCityId(@RequestParam int cityId, @RequestParam int pageSize,
            @RequestParam int pageNumber);
}
