package id.holigo.services.holigohotelservice.web.model.requests;

import id.holigo.services.holigohotelservice.web.model.ContactPersonDto;
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

    private Boolean isForSelf;

    private ContactPersonDto contactPerson;

    private RequestGuestDto guest;
}
