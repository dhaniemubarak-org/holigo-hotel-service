package id.holigo.services.common.model.retross.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.sql.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailHotelResponseDto {

    private String error_code;
    private String error_msg;
    private String mmid;
    private String selectedID;
    private Date checkin;
    private Date checkout;
    private String des;
    private String room;
    private String adt;
    private String chd;
    private ResponseDetailDto details;
}
