package id.holigo.services.holigohotelservice.web.model.detailHotel.hotelRooms;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RoomAmenityDto {
    
    private String category;
    private List<String> list;
}
