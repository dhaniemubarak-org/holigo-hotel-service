package id.holigo.services.holigohotelservice.web.model.calender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListCalendarDto {
    private List<FareCalendarDto> data;
}
