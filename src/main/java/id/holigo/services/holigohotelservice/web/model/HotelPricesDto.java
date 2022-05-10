package id.holigo.services.holigohotelservice.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelPricesDto {

    private Long id;
    private Long hotel_id;
    private Double star_rating;
    private Date date;
    private Integer priority;
    private Double latitude;
    private Double longitude;
    private BigDecimal price;
    private BigDecimal nta;
    private Integer status;
    private String city_id;
    private String province_id;

}
