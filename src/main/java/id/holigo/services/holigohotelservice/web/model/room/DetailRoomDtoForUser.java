package id.holigo.services.holigohotelservice.web.model.room;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import id.holigo.services.holigohotelservice.web.model.detailHotel.hotelRooms.RoomAmenityDto;
import id.holigo.services.holigohotelservice.web.serializers.AmountSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailRoomDtoForUser {

    private UUID id;

    private String category;

    private List<RoomAdditionalInformationDto> additionalInformation;

    private List<String> images;

    private RoomTag tag;

    private String name;

    private Boolean isFreeBreakFast;

    private Boolean isRefundable;

    private Boolean isFreeRefundable;

    private Integer maxOccupancy;

    private Integer maxAdult;

    private Integer maxChildren;

    @JsonSerialize(using = AmountSerializer.class)
    private BigDecimal fareAmount;

    @JsonSerialize(using = AmountSerializer.class)
    private BigDecimal normalAmount;

    private BigDecimal hpAmount;

    private String roomDescription;

    private List<RoomAmenityDto> amenities;

    private List<RoomPolicies> roomPolicies;
}
