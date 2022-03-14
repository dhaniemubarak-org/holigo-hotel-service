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
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

    @OneToMany
    private List<HotelImages> images;

    @ManyToMany
    @JoinTable(name = "hotel_main_facilities", joinColumns = @JoinColumn(name = "hotel_id"), inverseJoinColumns = @JoinColumn(name = "main_facility_id"))
    private Set<MainFacilities> mainFacility;

    @OneToMany
    private List<HotelDescription> description;

    @OneToMany
    private List<HotelPolicies> policies;

    @OneToMany
    private List<HotelRules> rules;
}
