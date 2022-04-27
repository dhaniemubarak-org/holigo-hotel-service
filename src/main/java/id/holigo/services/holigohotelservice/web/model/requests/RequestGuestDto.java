package id.holigo.services.holigohotelservice.web.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestGuestDto {
    private String title;
    private String type;
    private String name;
    private String phoneNumber;
    private String note;
}
