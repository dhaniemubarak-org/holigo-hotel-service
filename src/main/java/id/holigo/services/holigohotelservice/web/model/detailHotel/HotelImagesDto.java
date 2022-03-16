package id.holigo.services.holigohotelservice.web.model.detailHotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelImagesDto {
    
    private String category;
    @Builder.Default
    private String uploadedBy = "HOTEL";
    private String imageUrl;
}
