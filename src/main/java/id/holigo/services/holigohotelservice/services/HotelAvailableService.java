package id.holigo.services.holigohotelservice.services;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;

import id.holigo.services.holigohotelservice.web.model.DetailHotelForListDto;
import id.holigo.services.holigohotelservice.web.model.HotelAvailablePaginateForUser;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDto;

public interface HotelAvailableService {
    HotelDto getDetailHotelAvailable(Long id);

    HotelAvailablePaginateForUser listHotelForUser(Long destination, PageRequest pageRequest, String rating,
            String facilities, String types, Date checkIn, Date checkOut);

    void postHotelAvailable(DetailHotelForListDto detailHotelForListDto);

    public List<DetailHotelForListDto> generateAvailableHotel(Date checkIn);

}
