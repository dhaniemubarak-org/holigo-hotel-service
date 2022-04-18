package id.holigo.services.holigohotelservice.web.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelLocationDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelPriceDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelTagDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.RatingReviewDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class DetailHotelForListDto {
    
    private String id;
    private String name;
    private String type;
    private Double rating;
    private List<String> imageUrl;
    private HotelTagDto tag;
    private List<RatingReviewDto> ratingReviews;
    private HotelLocationDto location;
    private List<String> facilities;
    private List<String> amenities;
    private Double latitude;
    private Double longitude;
    private String city;
    private String fareType;
    private BigDecimal fareAmount;
    private BigDecimal normalFare;
    private BigDecimal point;
    private HotelPriceDto price;
    private Boolean freeBreakfast;
    private Boolean refundable;
    private Boolean freeRefundable;

    private Date checkIn;

    private Date checkOut;
}
