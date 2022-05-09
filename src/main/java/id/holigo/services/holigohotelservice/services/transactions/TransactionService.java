package id.holigo.services.holigohotelservice.services.transactions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigohotelservice.confg.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
    @Autowired
    private final JmsTemplate jmsTemplate;

    @Autowired
    private final ObjectMapper objectMapper;

    public TransactionDto sendCreateTransaction(TransactionDto transactionDto) throws JMSException {
        log.info("Sending To Transaction Service -> {}", transactionDto);
        Message message = jmsTemplate.sendAndReceive(JmsConfig.CREATE_NEW_TRANSACTION, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message message = null;
                try {
                    message = session.createTextMessage(objectMapper.writeValueAsString(transactionDto));
                    message.setStringProperty("_type", "id.holigo.services.common.model.TransactionDto");
                    return message;
                } catch (JsonProcessingException e) {
                    throw new JMSException("Error Sending Transaction Service!");
                }
            }
        });

        TransactionDto responseTransaction = new TransactionDto();

        try {
            assert message != null;
            responseTransaction = objectMapper.readValue(message.getBody(String.class), TransactionDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseTransaction;
    }
}
