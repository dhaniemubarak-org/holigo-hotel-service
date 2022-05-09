package id.holigo.services.holigohotelservice.services.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.holigo.services.common.model.UserDto;
import id.holigo.services.holigohotelservice.confg.JmsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Component
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final JmsTemplate jmsTemplate;

    @Autowired
    private final ObjectMapper objectMapper;

    public UserDto sendToGetUser(UserDto userDto) throws JmsException {
        Message message = jmsTemplate.sendAndReceive(JmsConfig.GET_USER_DATA_BY_ID_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message message = null;
                try {
                    message = session.createTextMessage(objectMapper.writeValueAsString(userDto));
                    message.setStringProperty("_type", "id.holigo.services.common.model.UserDto");
                    return message;
                } catch (JsonProcessingException e) {
                    throw new JMSException("Error Sending User Service!");
                }
            }
        });

        UserDto responseUserDto = new UserDto();
        try {
            responseUserDto = objectMapper.readValue(message.getBody(String.class), UserDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseUserDto;
    }
}
