package id.holigo.services.holigohotelservice.web.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RequestBookDto {
    private UUID fareId;
    private RequestGuestDto guest;
}
