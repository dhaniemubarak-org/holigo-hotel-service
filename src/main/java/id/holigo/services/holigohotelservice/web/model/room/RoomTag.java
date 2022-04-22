package id.holigo.services.holigohotelservice.web.model.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomTag {
    private String name;
    private String color;
    private String background;
}
