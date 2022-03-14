package id.holigo.services.holigohotelservice.web.model.detailHotel.hotelRooms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RoomImageDto {
    
    private String category;
    private String imageUrl;
    
}
