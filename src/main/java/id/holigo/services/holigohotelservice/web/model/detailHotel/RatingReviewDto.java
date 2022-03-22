package id.holigo.services.holigohotelservice.web.model.detailHotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingReviewDto {
    
    private String provider;
    private Double rating;
    private String icon;
}
