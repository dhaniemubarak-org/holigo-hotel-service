package id.holigo.services.holigohotelservice.web.model.detailHotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class HotelTagDto {
    
    private String name;
    private String color;
    private String background;
}
