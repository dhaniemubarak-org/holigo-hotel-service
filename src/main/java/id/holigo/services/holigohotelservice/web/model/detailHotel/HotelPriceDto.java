package id.holigo.services.holigohotelservice.web.model.detailHotel;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelPriceDto {
    
    private String type;
    private BigDecimal fareAmount;
    private BigDecimal normalFare;
    private BigDecimal point;
}
