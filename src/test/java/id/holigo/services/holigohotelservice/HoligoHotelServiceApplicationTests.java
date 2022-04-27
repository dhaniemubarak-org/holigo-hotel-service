package id.holigo.services.holigohotelservice;

import id.holigo.services.common.model.retross.request.DetailHotelRequestDto;
import id.holigo.services.common.model.retross.response.DetailHotelResponseDto;
import id.holigo.services.holigohotelservice.services.supplier.HotelRetrossService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class HoligoHotelServiceApplicationTests {

    @Autowired
    private HotelRetrossService hotelRetrossService;

    @Test
    void contextLoads() {
        DetailHotelRequestDto requestDto = new DetailHotelRequestDto();
        requestDto.setCheckin("2022-05-15");
        requestDto.setCheckout("2022-05-16");
        requestDto.setRoom("1");
        requestDto.setAdt("1");
        requestDto.setChd("0");
        requestDto.setAction("detail_hotel_v2");
        requestDto.setApp("information");
        requestDto.setH_id("ID10001998");
        requestDto.setRoom_type("");

        DetailHotelResponseDto responseDto = hotelRetrossService.getDetailHotel(requestDto);
        log.info("Response Retross -> {}", responseDto.getError_code());
    }

}
