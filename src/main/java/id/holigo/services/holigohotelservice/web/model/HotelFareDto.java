package id.holigo.services.holigohotelservice.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelFareDto {

    private UUID id;
    private BigDecimal fareAmount;
    @JsonProperty("normalPrice")
    private BigDecimal estimationPrice;
    private BigDecimal hpAmount;
    private BigDecimal ntaAmount;
    private AccomodationDto accomodation;

}
