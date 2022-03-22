package id.holigo.services.holigohotelservice.services;

import org.springframework.data.domain.PageRequest;

import id.holigo.services.holigohotelservice.web.model.DetailHotelForListDto;
import id.holigo.services.holigohotelservice.web.model.HotelAvailablePaginateForUser;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDto;

public interface HotelAvailableService {
    HotelDto getDetailHotelAvailable(Long id);

    HotelAvailablePaginateForUser listHotelForUser(Long destination, PageRequest pageRequest, String rating, String facilities, String types);

    void postHotelAvailable(DetailHotelForListDto detailHotelForListDto);
}
