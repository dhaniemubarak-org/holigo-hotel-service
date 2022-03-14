package id.holigo.services.holigohotelservice.web.model.detailHotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelLocationDto {
    
    private String country;
    private String province;
    private String city;
    private String district;
    private String address;
    private Double latitude;
    private Double longitude;
}
