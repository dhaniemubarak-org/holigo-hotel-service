package id.holigo.services.holigohotelservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class InquiryDto {

    private UUID id;
    private Date checkIn;
    private Date checkOut;
    private Integer adultAmount;
    private Integer childAmount;
    private List<Integer> childAge;
    private Integer roomAmount;
}
