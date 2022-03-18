package id.holigo.services.holigohotelservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

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
@Table(name = "hotel_room_amenities")
public class HotelRoomAmenities {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "hotel_room_id")
    private HotelRooms rooms;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private AmenityCategories category;

    @ManyToOne
    @JoinColumn(name = "amenity_id")
    private Amenities amenities;
}
