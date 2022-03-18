package id.holigo.services.holigohotelservice.domain;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

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
@Table(name = "hotels")
public class Hotels {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Double rating;

    @ManyToOne
    @JoinColumn(name = "hotel_type_id")
    private HotelTypes hotelType;

    private Short priority;

    private Short lockUpdate;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    private Timestamp deletedAt;

    @OneToOne(mappedBy = "hotel")
    private HotelAddresses location;

    @OneToMany(mappedBy = "hotel")
    private List<HotelImages> images;

    @ManyToMany
    @JoinTable(name = "hotel_main_facilities", joinColumns = @JoinColumn(name = "hotel_id"), inverseJoinColumns = @JoinColumn(name = "main_facility_id"))
    private Set<MainFacilities> mainFacility;

    @OneToMany(mappedBy = "hotel")
    private List<HotelDescription> descriptions;

    @OneToMany(mappedBy = "hotel")
    private List<HotelPolicies> policies;

    @OneToMany(mappedBy = "hotel")
    private List<HotelRules> rules;

    @OneToMany(mappedBy = "hotel")
    private List<HotelNearbyPlaces> nearbyPlaces;

    @OneToMany(mappedBy = "hotel")
    private List<HotelPopularAreas> popularAreas;

    @OneToMany(mappedBy = "hotel")
    @Where(clause = "is_show = 1")
    private List<HotelAdditionalInformations> additionalInformations;

    @OneToMany(mappedBy = "hotel")
    @Where(clause = "is_show = 1")
    private List<HotelStories> stories;

    @OneToMany(mappedBy = "hotels")
    private Set<HotelFacilities> facilities;

    @OneToMany(mappedBy = "hotel")
    private List<HotelRooms> rooms;

}
