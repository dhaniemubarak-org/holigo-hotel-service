package id.holigo.services.holigohotelservice.web.model;

import id.holigo.services.holigohotelservice.web.model.room.DetailRoomDtoForUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class InquiryRoomDto
{
    private InquiryDto inquiry;

    private String name;
    private Double starRating;
    private List<DetailRoomDtoForUser> rooms;
}
