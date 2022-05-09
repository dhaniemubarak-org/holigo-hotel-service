package id.holigo.services.holigohotelservice.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "cities")
public class Cities {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameEn;

    private String nameId;

    private String code;

    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @org.hibernate.annotations.ForeignKey(name = "none")
    @JsonBackReference
    private Countries country;

    @ManyToOne
    @JoinColumn(name = "province_id")
    @JsonBackReference
    private Provinces province;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
    
    @OneToMany(mappedBy = "city",
            cascade = CascadeType.ALL)
    @JsonBackReference
    private List<HotelAddresses> hotel;
}
