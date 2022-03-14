package id.holigo.services.holigohotelservice.web.model.detailHotel;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class HotelFacilityDto {
    
    private String icon;
    private String category;
    private List<String> items;
}
