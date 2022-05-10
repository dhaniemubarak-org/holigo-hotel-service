package id.holigo.services.holigohotelservice.services;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import id.holigo.services.holigohotelservice.web.model.InquiryRoomDto;
import org.springframework.data.domain.PageRequest;

import id.holigo.services.holigohotelservice.web.model.DetailHotelForListDto;
import id.holigo.services.holigohotelservice.web.model.HotelAvailablePaginateForUser;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDto;

import javax.jms.JMSException;

public interface HotelAvailableService {
    HotelDto getDetailHotelAvailable(Long id);

    HotelAvailablePaginateForUser listHotelForUser(Long destination, PageRequest pageRequest, String rating,
                                                   String facilities, String types, Date checkIn, Date checkOut);

    void postHotelAvailable(DetailHotelForListDto detailHotelForListDto, Integer cityId);

    public List<DetailHotelForListDto> generateAvailableHotel(Date checkIn, Integer cityId);

    InquiryRoomDto inquiryRoom(Long destinationId, Long userId, Date checkIn, Date checkOut, Short adultAmount, Short childAmount, String childAge, Short roomAmount) throws JMSException, JsonMappingException, JsonProcessingException;

    void generateAvailableFromCms(Integer cityId, Date startDate, Date endDate);

}
