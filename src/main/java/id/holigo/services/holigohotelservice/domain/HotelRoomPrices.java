package id.holigo.services.holigohotelservice.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hotel_room_prices")
public class HotelRoomPrices {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private HotelRooms room;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    private String selectedId;

    private String selectedRoomId;

    private String board;

    private String bedType;

    private String characteristic;

    private Date checkIn;

    private Date checkOut;

    @Column(columnDefinition = "decimal(12,2) default 0")
    private BigDecimal nraAmount;

    @Column(columnDefinition = "decimal(12,2) default 0")
    private BigDecimal ntaAmount;

    @Column(columnDefinition = "decimal(12,2) default 0")
    private BigDecimal priceAmount;

    @Column(columnDefinition = "decimal(12,2) default 0")
    private BigDecimal fareAmount;

    private Boolean isFreeBreakFast;

    @Column(columnDefinition = "decimal(10,2) default 0")
    private BigDecimal cpAmount;

    @Column(columnDefinition = "decimal(10,2) default 0")
    private BigDecimal mpAmount;

    @Column(columnDefinition = "decimal(10,2) default 0")
    private BigDecimal ipAmount;

    @Column(columnDefinition = "decimal(10,2) default 0")
    private BigDecimal hpAmount;

    @Column(columnDefinition = "decimal(10,2) default 0")
    private BigDecimal hvAmount;

    @Column(columnDefinition = "decimal(10,2) default 0")
    private BigDecimal prAmount;

    @Column(columnDefinition = "decimal(10,2) default 0")
    private BigDecimal ipcAmount;

    @Column(columnDefinition = "decimal(10,2) default 0")
    private BigDecimal hpcAmount;

    @Column(columnDefinition = "decimal(10,2) default 0")
    private BigDecimal prcAmount;

    @Column(columnDefinition = "decimal(10,2) default 0")
    private BigDecimal lossAmount;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;


}
