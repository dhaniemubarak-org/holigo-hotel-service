package id.holigo.services.holigohotelservice.domain;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "hotel_rooms")
public class HotelRooms {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    private String roomSupplierId;

    private String name;

    private String size;

    @Column(columnDefinition = "TINYINT")
    private Short maxOccupancy;

    @Column(columnDefinition = "TINYINT")
    private Short maxAdults;

    @Column(columnDefinition = "TINYINT")
    private Short maxChilds;

    @Lob
    private String description;

    @OneToMany(mappedBy = "room")
    private List<HotelRoomImages> images;

    @ManyToMany
    @JoinTable(name = "hotel_room_main_amenities", joinColumns = @JoinColumn(name = "hotel_room_id"), inverseJoinColumns = @JoinColumn(name = "main_amenity_id"))
    private Set<MainAmenities> mainAmenities;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}

