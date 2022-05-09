package id.holigo.services.holigohotelservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class GuestDto {
    private String title;

    private String name;

    private String phoneNumber;

    private String note;
}
