package id.holigo.services.holigohotelservice.services;

import id.holigo.services.holigohotelservice.web.model.requests.RequestBookDto;

import javax.jms.JMSException;
import java.util.UUID;

public interface HotelBookService {

    UUID postBookHotel(Long userId, RequestBookDto requestBookDto) throws JMSException;
}
