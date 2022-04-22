package id.holigo.services.holigohotelservice.web.model.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomAvailableDto {
    private String category;
    private List<RoomAdditionalInformationDto> additionalInformation;
    private List<String> images;
    private String name;
    private Boolean isFreeBreakfast;
    private Boolean isRefundable;
    private Boolean isFreeRefundable;
    private Short maxOccupancy;
    private Short maxAdult;
    private Short maxChildren;
    private Boolean isSmoking;
    private BigDecimal fareAmount;
    private BigDecimal normalAmount;
    private BigDecimal hpAmount;
    private String roomDescription;
    private List<String> amenities;
    private List<RoomPolicies> roomPolicies;
}
