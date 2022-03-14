package id.holigo.services.holigohotelservice.web.model.detailHotel;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class HotelReviewContentDto {

    private HotelReviewUserDto user;
    private String country;
    private Timestamp createdAt;
    private Double rating;
    private String body;
    private List<String> images;
    private Boolean isShow;
    private Boolean isLike;
}