package id.holigo.services.holigohotelservice.services.supplier;

import id.holigo.services.common.model.retross.request.DetailHotelRequestDto;
import id.holigo.services.common.model.retross.response.DetailHotelResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "hotel-retross", url = "http://ws.retross.com")
public interface HotelRetrossServiceFeignClient {
    public static final String RETROSS_HOTEL_API_HOST = "/hotel/";

    @RequestMapping(method = RequestMethod.POST, value = RETROSS_HOTEL_API_HOST)
    ResponseEntity<String> getHotelDetail(@RequestBody DetailHotelRequestDto requestDto);
}
