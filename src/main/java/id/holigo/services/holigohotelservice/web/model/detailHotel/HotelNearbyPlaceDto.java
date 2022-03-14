package id.holigo.services.holigohotelservice.web.model.detailHotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelNearbyPlaceDto {
    
    private String name;
    private String category;
    private String distance;
    private String icon;
    
}
