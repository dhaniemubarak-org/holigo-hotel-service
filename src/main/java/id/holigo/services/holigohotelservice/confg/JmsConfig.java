package id.holigo.services.holigohotelservice.confg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {
    public static final String GET_HOTEL_TRAVELOKA_EXTERNAL = "get-hotel-traveloka-external";
    public static final String GET_DETAIL_FARE_PRODUCT = "get-detail-fare-product";

    public static final String GET_USER_DATA_BY_ID_QUEUE = "get-user-data-by-id-queue";

    public static final String CREATE_NEW_TRANSACTION = "create-new-transaction";

    public static final String DETAIL_PRODUCT_HOTEL_TRANSACTION = "detail-product-hotel-transaction";

    public static final String CANCEL_HOTEL_TRANSACTION = "cancel-hotel-transaction";

    @Bean
    public MessageConverter jacksonJmsMessageConverter(){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
