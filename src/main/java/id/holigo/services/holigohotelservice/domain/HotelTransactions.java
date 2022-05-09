package id.holigo.services.holigohotelservice.domain;

import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentStatusEnum;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hotel_transactions")
public class HotelTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    private Timestamp deletedAt;

    private Long userId;

    private Integer serviceId;

    private Long hotelId;

    private Long roomId;

    private BigDecimal fareAmount;

    private BigDecimal discountAmount;

    private BigDecimal nraAmount;

    private BigDecimal ntaAmount;

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

    private String selectedID;

    private String selectedIDroom;

    private String supplierTransactionId;

    private String voucherNumber;

    private String resNumber;

    private String guestTitle;

    private String guestName;

    private String guestPhoneNumber;

    private String guestNote;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum paymentStatus;

    @Builder.Default
    private Short flag = 0;

    private Date checkIn;

    private Date checkOut;

    private Short roomAmount;

    private Short guestAmount;

}
