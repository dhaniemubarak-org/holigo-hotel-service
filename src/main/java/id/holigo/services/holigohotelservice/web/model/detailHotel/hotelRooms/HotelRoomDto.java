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
public class HotelRoomDto {

    private String name;
    private String size;
    private Integer maxOccupancy;
    private String description;
    private List<RoomImageDto> images;
    private List<RoomMainAmenities> mainAmenities;
    private List<RoomAmenityDto> amenities;
}
