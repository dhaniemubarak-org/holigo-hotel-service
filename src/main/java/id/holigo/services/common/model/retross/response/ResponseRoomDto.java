package id.holigo.services.common.model.retross.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseRoomDto {
    private String characteristic;
    private String bed;
    private String board;
    private BigDecimal price;
    private BigDecimal nta;
    private String selectedIDroom;
    private String room_id;
}
