package id.holigo.services.common.model.retross.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDataDto {
    private String name;
    private Double rating;
    private String rooms;
    private String address;
    private String email;
    private String website;
}
