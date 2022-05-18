package id.holigo.services.holigohotelservice.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("city")
    private String name;

    private String type;

    private String province;

    private String country;

    private Integer hotelAmount;
}
