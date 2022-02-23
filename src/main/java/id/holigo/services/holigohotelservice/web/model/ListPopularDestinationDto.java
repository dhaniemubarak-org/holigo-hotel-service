package id.holigo.services.holigohotelservice.web.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListPopularDestinationDto {
    
    private List<PopularDestinationDto> data;
}
