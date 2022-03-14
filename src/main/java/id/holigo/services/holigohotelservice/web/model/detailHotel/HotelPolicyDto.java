package id.holigo.services.holigohotelservice.web.model.detailHotel;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelPolicyDto {

    @JsonProperty("short")
    private String shortPolicy;
    @JsonProperty("long")
    private String longPolicy;
}
