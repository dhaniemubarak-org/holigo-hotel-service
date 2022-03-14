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
public class HotelStoryDto {

    private Long id;
    private String videoUrl;
    private String name;
    private Double rating;
    private BigDecimal fareAmount;
    private String tag;
}
