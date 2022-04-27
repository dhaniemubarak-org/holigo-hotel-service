package id.holigo.services.holigohotelservice.services.supplier;

import id.holigo.services.common.model.retross.request.DetailHotelRequestDto;
import id.holigo.services.common.model.retross.response.DetailHotelResponseDto;

public interface HotelRetrossService {

    public DetailHotelResponseDto getDetailHotel(DetailHotelRequestDto requestDto);
}
