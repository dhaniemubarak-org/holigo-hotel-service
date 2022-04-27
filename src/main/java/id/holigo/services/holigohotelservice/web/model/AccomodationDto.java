package id.holigo.services.holigohotelservice.web.model;

import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccomodationDto {
    private UUID inquiryId;

    private HotelDetailFareDto hotel;

    private AccomodationRoomDto room;
}
