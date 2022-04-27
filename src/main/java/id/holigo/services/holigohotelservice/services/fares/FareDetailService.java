package id.holigo.services.holigohotelservice.services.fares;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.holigo.services.common.model.FareDetailDto;
import id.holigo.services.common.model.FareDto;
import id.holigo.services.holigohotelservice.confg.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service
@RequiredArgsConstructor
@Slf4j
public class FareDetailService {
    private final JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper;

    public FareDto getDetailProduct(FareDetailDto fareDetailDto) throws JMSException, JsonMappingException, JsonProcessingException {
        log.info("Trying Sending Fare Detail -> {}", fareDetailDto);
        Message message = jmsTemplate.sendAndReceive(JmsConfig.GET_DETAIL_FARE_PRODUCT, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message detailProductMessage = null;
                try {
                    log.info("Parsing message!");
                    detailProductMessage = session.createTextMessage(objectMapper.writeValueAsString(fareDetailDto));
                    detailProductMessage.setStringProperty("_type", "id.holigo.services.common.model.FareDetailDto");
                    log.info("Sending DetailProduct!");
                } catch (JsonProcessingException e) {
                    log.info("Error Message -> {}", e.getMessage());
                    throw new JMSException(e.getMessage());
                }
                return detailProductMessage;
            }
        });
        log.info("Getting Fare Detail -> {}", message.getBody(String.class));
        FareDto fareDto = objectMapper.readValue(message.getBody(String.class), FareDto.class);

        return fareDto;
    }
}
