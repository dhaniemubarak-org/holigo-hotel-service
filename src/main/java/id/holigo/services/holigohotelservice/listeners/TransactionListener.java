package id.holigo.services.holigohotelservice.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.holigo.services.common.model.DetailHotelTransactionDto;
import id.holigo.services.common.model.DetailProductTransaction;
import id.holigo.services.holigohotelservice.confg.JmsConfig;
import id.holigo.services.holigohotelservice.domain.Hotel;
import id.holigo.services.holigohotelservice.domain.HotelRooms;
import id.holigo.services.holigohotelservice.domain.HotelTransactions;
import id.holigo.services.holigohotelservice.repositories.HotelRepository;
import id.holigo.services.holigohotelservice.repositories.HotelRoomRepository;
import id.holigo.services.holigohotelservice.repositories.HotelTransactionRepository;
import id.holigo.services.holigohotelservice.web.exceptions.NotFoundException;
import id.holigo.services.holigohotelservice.web.mappers.HotelMapper;
import id.holigo.services.holigohotelservice.web.mappers.HotelRoomMapper;
import id.holigo.services.holigohotelservice.web.model.ContactPersonDto;
import id.holigo.services.holigohotelservice.web.model.GuestDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDto;
import id.holigo.services.holigohotelservice.web.model.detailHotel.hotelRooms.HotelRoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import java.sql.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionListener {
    @Autowired
    private final JmsTemplate jmsTemplate;

    @Autowired
    private final ObjectMapper objectMapper;

    @Autowired
    private HotelTransactionRepository hotelTransactionRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private HotelRoomMapper hotelRoomMapper;

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Transactional
    @JmsListener(destination = JmsConfig.DETAIL_PRODUCT_HOTEL_TRANSACTION)
    public void listenDetailProduct(@Payload DetailProductTransaction detailProductTransaction, @Headers MessageHeaders headers, Message message) throws JMSException {
        log.info("Listened Detail Hotel!");
        HotelTransactions hotelTransactions = hotelTransactionRepository.findById(detailProductTransaction.getId()).orElseThrow(NotFoundException::new);
        Hotel hotel = hotelRepository.findById(hotelTransactions.getHotelId()).orElseThrow(NotFoundException::new);
        HotelRooms hotelRooms = hotelRoomRepository.findById(hotelTransactions.getRoomId()).orElseThrow(NotFoundException::new);
        HotelRoomDto hotelRoomDto = hotelRoomMapper.hotelsToHotelRoomDto(hotelRooms);
        HotelDto hotelDto = hotelMapper.hotelsToHotelDto(hotel);

        Long totalNight = getDifferenceDays(hotelTransactions.getCheckIn(), hotelTransactions.getCheckOut());

        GuestDto guestDto = new GuestDto();
        guestDto.setName(hotelTransactions.getGuestName());
        guestDto.setTitle(hotelTransactions.getGuestTitle());
        guestDto.setNote(hotelTransactions.getGuestNote());

        ContactPersonDto contactPersonDto = new ContactPersonDto();
        contactPersonDto.setPhoneNumber(hotelTransactions.getContactPhoneNumber());
        contactPersonDto.setName(hotelTransactions.getContactName());
        contactPersonDto.setEmail(hotelTransactions.getContactEmail());

        DetailHotelTransactionDto detailHotelTransactionDto = new DetailHotelTransactionDto();
        detailHotelTransactionDto.setId(hotelTransactions.getId());
        detailHotelTransactionDto.setCheckIn(hotelTransactions.getCheckIn());
        detailHotelTransactionDto.setCheckOut(hotelTransactions.getCheckOut());
        detailHotelTransactionDto.setRoomAmount(hotelTransactions.getRoomAmount());
        detailHotelTransactionDto.setGuestAmount(hotelTransactions.getGuestAmount());
        detailHotelTransactionDto.setDurations(Short.valueOf(totalNight.toString()));
        detailHotelTransactionDto.setVoucherCode(hotelTransactions.getVoucherNumber());
        detailHotelTransactionDto.setReservationCode(hotelTransactions.getResNumber());
        detailHotelTransactionDto.setContactPerson(contactPersonDto);
        detailHotelTransactionDto.setGuest(guestDto);
        detailHotelTransactionDto.setHotel(hotelDto);
        detailHotelTransactionDto.setRoom(hotelRoomDto);

        detailProductTransaction.setDetail(detailHotelTransactionDto);

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), detailProductTransaction);
    }

    @Transactional
    @JmsListener(destination = JmsConfig.CANCEL_HOTEL_TRANSACTION)
    public void listenCancelTransaction(@Payload DetailProductTransaction detailProductTransaction, @Headers MessageHeaders headrs, Message message) throws JMSException{
        HotelTransactions hotelTransactions = hotelTransactionRepository.findById(detailProductTransaction.getId()).orElseThrow(NotFoundException::new);


    }
}
