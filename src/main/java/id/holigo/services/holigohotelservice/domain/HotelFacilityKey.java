package id.holigo.services.holigohotelservice.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class HotelFacilityKey implements Serializable{
    
    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "category_id")
    private Long categoryId;
}
