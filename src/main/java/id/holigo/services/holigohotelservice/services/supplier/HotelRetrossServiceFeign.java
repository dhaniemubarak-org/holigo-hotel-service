package id.holigo.services.holigohotelservice.services.supplier;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.holigo.services.common.model.retross.request.DetailHotelRequestDto;
import id.holigo.services.common.model.retross.response.DetailHotelResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelRetrossServiceFeign implements HotelRetrossService {

    private final HotelRetrossServiceFeignClient hotelRetrossServiceFeignClient;

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public DetailHotelResponseDto getDetailHotel(DetailHotelRequestDto requestDto) {
        log.info("Calling Retross Hotel Detail -> {}", requestDto);
        ResponseEntity<String> responseEntity = hotelRetrossServiceFeignClient.getHotelDetail(requestDto);
        DetailHotelResponseDto responseDto = new DetailHotelResponseDto();
        try{
            responseDto = objectMapper.readValue(responseEntity.getBody(), DetailHotelResponseDto.class);
        }catch (Exception e){
            log.info("Error Mapping Response Detail -> {}", e.getMessage());
        }
        log.info("Response Retross Hotel Detail -> {}", responseDto);
        return responseDto;
    }

}
