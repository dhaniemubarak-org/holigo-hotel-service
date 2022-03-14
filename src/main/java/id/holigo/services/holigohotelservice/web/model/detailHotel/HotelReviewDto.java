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
public class HotelReviewDto {
    
    private String title;
    private String subtitle;
    private String icon;
    private List<HotelReviewDto> content;
}
