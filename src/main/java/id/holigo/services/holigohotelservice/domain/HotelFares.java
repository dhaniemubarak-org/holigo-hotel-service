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
@Table(name = "hotel_fares")
public class HotelFares {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private HotelRooms room;

    private String selectedId;

    private String selectedRoomId;

    private Date checkIn;

    private Date checkOut;

    @Column(columnDefinition = "decimal(12,2) default 0")
    private BigDecimal ntaAmount;

    @Column(columnDefinition = "decimal(12,2) default 0")
    private BigDecimal nraAmount;

    @Column(columnDefinition = "decimal(12,2) default 0")
    private BigDecimal fareAmount;

    @Column(columnDefinition = "decimal(12,2) default 0")
    private BigDecimal totalEstimationPrice;

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

    @ManyToOne
    @JoinColumn(name = "hotel_inquiry_id")
    private HotelInquiry inquiry;

    @Builder.Default
    private Short status = 0;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;



}
