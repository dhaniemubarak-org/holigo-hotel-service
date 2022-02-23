package id.holigo.services.holigohotelservice.web.model;

import java.util.List;

import id.holigo.services.holigohotelservice.domain.PopularHotel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PopularHotelByCityDto {
    
    private String name;

    private List<PopularHotel> hotels;
}
