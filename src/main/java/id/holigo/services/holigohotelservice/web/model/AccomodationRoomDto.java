package id.holigo.services.holigohotelservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccomodationRoomDto {

    private UUID id;
    private String name;
    private String board;
    private String bedType;
    private BigDecimal price;
}
