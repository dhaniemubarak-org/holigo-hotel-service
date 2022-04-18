package id.holigo.services.holigohotelservice.services.external;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import id.holigo.services.common.model.HotelDtoForExternal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class HotelExternalServiceFeign implements HotelExternalService {

    private final HotelExternalServiceFeignClient hotelExternalServiceFeignClient;

    @Override
    public HotelDtoForExternal getHotelByCityId(Integer cityId, int pageSize,
            int pageNumber) {
        log.info("Calling Hotel External with cityId -> {}", cityId);

        ResponseEntity<HotelDtoForExternal> responseEntity = hotelExternalServiceFeignClient.getHotelByCityId(cityId,
                pageSize, pageNumber);

        // log.info("ResponseEntity Body -> {}", responseEntity.getBody());

        return responseEntity.getBody();
    }

}
