package id.holigo.services.holigohotelservice.domain;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hotel_available")
public class HotelAvailable {
    
    @Id
    private Long id;

    private String name;

    private String imageUrl;

    private String type;

    private String tag;

    private Double rating;

    private String ratingReviews;

    private String location;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private Cities cityId;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Provinces provinceId;

    private String facilities;

    private String amenities;

    private Double latitude;

    private Double longitude;

    private Date checkIn;

    private Date checkOut;
    
    private String city;

    private String fareType;

    private BigDecimal fareAmount;

    private BigDecimal normalFare;

    private BigDecimal point;

    private String price;

    @Column(columnDefinition = "TINYINT")
    private Short freeBreakfast;

    @Column(columnDefinition = "TINYINT")
    private Short refundable;

    @Column(columnDefinition = "TINYINT")
    private Short freeRefundable;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    private Timestamp deletedAt;
}
