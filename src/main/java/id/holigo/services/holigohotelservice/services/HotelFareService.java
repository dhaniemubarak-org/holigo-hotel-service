package id.holigo.services.holigohotelservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import id.holigo.services.holigohotelservice.web.model.HotelFareDto;
import id.holigo.services.holigohotelservice.web.model.requests.RequestFareDto;

import javax.jms.JMSException;

public interface HotelFareService {

    HotelFareDto submitFareHotel(Long userId, RequestFareDto requestFareDto)throws JMSException, JsonMappingException, JsonProcessingException;
}
