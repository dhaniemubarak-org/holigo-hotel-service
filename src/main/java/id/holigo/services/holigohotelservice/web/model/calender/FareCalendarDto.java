package id.holigo.services.holigohotelservice.web.model.calender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FareCalendarDto {

    private Date date;
    private BigDecimal fare;
}
