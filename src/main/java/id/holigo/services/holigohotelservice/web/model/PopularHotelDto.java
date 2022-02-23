package id.holigo.services.holigohotelservice.web.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PopularHotelDto {
    
    private Integer hotelId;
    
    private String name;

    private String imageUrl;

    private Double rating;

    private BigDecimal fareAmount;

    private BigDecimal normalAmount;
}
