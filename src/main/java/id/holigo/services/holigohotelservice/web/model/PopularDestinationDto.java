package id.holigo.services.holigohotelservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PopularDestinationDto {
    
    private Integer targetId;

    private String city;

    private String province;

    private String country;

    private Integer hotelAmount;
}
