package id.holigo.services.holigohotelservice.web.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListPopularHotelDto {

    @Builder.Default
    private String title = "Hotel Popular";

    @Builder.Default
    private String subtitle = "Jelajahi pilihan hotel kami yang sedang populer saat ini.";

    private List<PopularHotelByCityDto> cities;
}
