package id.holigo.services.common.model.retross.request;

import id.holigo.services.holigohotelservice.confg.RetrossConfig;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class DetailHotelRequestDto extends RetrossConfig{
    private String checkin;
    private String checkout;
    private String room;
    private String adt;
    private String chd;
    private String app;
    private String action;
    private String h_id;
    private String room_type;
}
