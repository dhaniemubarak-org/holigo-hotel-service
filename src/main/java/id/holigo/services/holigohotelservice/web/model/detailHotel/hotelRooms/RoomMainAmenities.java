package id.holigo.services.holigohotelservice.web.model.detailHotel.hotelRooms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomMainAmenities {
    
    private String label;
    private String imageUrl;
}
