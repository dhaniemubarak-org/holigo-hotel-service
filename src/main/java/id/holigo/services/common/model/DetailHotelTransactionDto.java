package id.holigo.services.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import id.holigo.services.holigohotelservice.domain.HotelRooms;
import id.holigo.services.holigohotelservice.web.model.ContactPersonDto;
import id.holigo.services.holigohotelservice.web.model.GuestDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.hotelRooms.HotelRoomDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class DetailHotelTransactionDto {

    private Long id;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date checkIn;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date checkOut;

    private Short roomAmount;

    private Short guestAmount;

    private Short durations;

    private String voucherCode;

    private String reservationCode;

    @Builder.Default
    private String iconUrl = "https://ik.imagekit.io/holigo/Icon_produk_homepage/hotel_M8iRX4bsTP.png";

    private ContactPersonDto contactPerson;

    private GuestDto guest;

    private HotelDto hotel;

    private HotelRoomDto room;


}
