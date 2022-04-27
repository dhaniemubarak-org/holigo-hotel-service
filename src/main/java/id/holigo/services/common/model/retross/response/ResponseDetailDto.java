package id.holigo.services.common.model.retross.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDetailDto {
    private ResponseDataDto data;
    private List<String> fasilitas;
    private List<ResponseRoomDto> rooms;
    private List<String> policy;
    private List<String> images;
    private JsonNode promo;
}
