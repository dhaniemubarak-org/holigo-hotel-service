package id.holigo.services.holigohotelservice.web.mappers;

import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.common.model.UserDto;
import id.holigo.services.holigohotelservice.domain.HotelFares;
import id.holigo.services.holigohotelservice.domain.HotelTransactions;
import id.holigo.services.holigohotelservice.services.transactions.TransactionService;
import id.holigo.services.holigohotelservice.services.user.UserService;
import id.holigo.services.holigohotelservice.web.model.requests.RequestBookDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class HotelBookMapperDecorator implements HotelBookMapper {

    @Autowired
    private HotelBookMapper hotelBookMapper;

    @Autowired
    private UserService userService;

    @Override
    public HotelTransactions mappingHotelTransaction(RequestBookDto requestBookDto, HotelFares hotelFares) {
        HotelTransactions hotelTransactions = hotelBookMapper.mappingHotelTransaction(requestBookDto, hotelFares);
        hotelTransactions.setUserId(hotelFares.getUserId());
        hotelTransactions.setServiceId(28);
        hotelTransactions.setHotelId(hotelFares.getHotel().getId());
        hotelTransactions.setRoomId(hotelFares.getRoom().getId());
        hotelTransactions.setFareAmount(hotelFares.getFareAmount());
        hotelTransactions.setDiscountAmount(BigDecimal.valueOf(0));
        hotelTransactions.setNraAmount(hotelFares.getNraAmount());
        hotelTransactions.setNtaAmount(hotelFares.getNtaAmount());
        hotelTransactions.setTotalEstimationPrice(hotelFares.getTotalEstimationPrice());
        hotelTransactions.setCpAmount(hotelFares.getCpAmount());
        hotelTransactions.setMpAmount(hotelFares.getMpAmount());
        hotelTransactions.setIpAmount(hotelFares.getIpAmount());
        hotelTransactions.setHpAmount(hotelFares.getHpAmount());
        hotelTransactions.setHvAmount(hotelFares.getHvAmount());
        hotelTransactions.setPrAmount(hotelFares.getPrAmount());
        hotelTransactions.setIpcAmount(hotelFares.getIpcAmount());
        hotelTransactions.setHpcAmount(hotelFares.getHpcAmount());
        hotelTransactions.setPrcAmount(hotelFares.getPrcAmount());
        hotelTransactions.setLossAmount(hotelFares.getLossAmount());
        hotelTransactions.setSelectedID(hotelFares.getSelectedId());
        hotelTransactions.setSelectedIDRoom(hotelFares.getSelectedRoomId());
        hotelTransactions.setContactName(requestBookDto.getContactPerson().getName());
        hotelTransactions.setContactEmail(requestBookDto.getContactPerson().getEmail());
        hotelTransactions.setContactPhoneNumber(requestBookDto.getContactPerson().getPhoneNumber());
        hotelTransactions.setIsForSelf(requestBookDto.getIsForSelf());
        hotelTransactions.setGuestTitle(requestBookDto.getGuest().getTitle());
        hotelTransactions.setGuestName(requestBookDto.getGuest().getName());
        hotelTransactions.setGuestNote(requestBookDto.getGuest().getNote());
        hotelTransactions.setOrderStatus(OrderStatusEnum.BOOKED);
        hotelTransactions.setPaymentStatus(PaymentStatusEnum.SELECTING_PAYMENT);
        hotelTransactions.setCheckIn(hotelFares.getCheckIn());
        hotelTransactions.setCheckOut(hotelFares.getCheckOut());
        hotelTransactions.setRoomAmount(hotelFares.getInquiry().getRoomAmount());
        hotelTransactions.setGuestAmount(hotelFares.getInquiry().getAdultAmount());
        return hotelTransactions;
    }

    @Override
    public TransactionDto hotelTransactionToTransactionDto(HotelTransactions hotelTransactions, Long userId, HotelFares hotelFares) {
        TransactionDto transactionDto = hotelBookMapper.hotelTransactionToTransactionDto(hotelTransactions, userId, hotelFares);

        UserDto retrieveUser = UserDto.builder().id(userId).build();
        UserDto user = userService.sendToGetUser(retrieveUser);
        String indexUser = user.getName() + "|" + user.getPhoneNumber() + "|" + user.getEmail();

        String indexProduct = hotelFares.getHotel().getName() + ", " + hotelFares.getRoom().getName() + "|" + hotelFares.getCheckIn().toString() + "|" + hotelFares.getCheckOut() + "|" + hotelTransactions.getGuestName();
        transactionDto.setUserId(userId);
        transactionDto.setFareAmount(hotelTransactions.getFareAmount());
        transactionDto.setAdminAmount(BigDecimal.valueOf(0));
        transactionDto.setDiscountAmount(hotelTransactions.getDiscountAmount());
        transactionDto.setPaymentStatus(hotelTransactions.getPaymentStatus());
        transactionDto.setOrderStatus(hotelTransactions.getOrderStatus());
        transactionDto.setTransactionId(hotelTransactions.getId().toString());
        transactionDto.setTransactionType("HTL");
        transactionDto.setServiceId(hotelTransactions.getServiceId());
        transactionDto.setProductId(hotelTransactions.getServiceId());
        transactionDto.setNtaAmount(hotelTransactions.getNtaAmount());
        transactionDto.setNraAmount(hotelTransactions.getNraAmount());
        transactionDto.setIndexProduct(indexProduct);
        transactionDto.setIndexUser(indexUser);
        transactionDto.setNote(hotelTransactions.getGuestNote());
        transactionDto.setCpAmount(hotelTransactions.getCpAmount());
        transactionDto.setMpAmount(hotelTransactions.getMpAmount());
        transactionDto.setIpAmount(hotelTransactions.getIpAmount());
        transactionDto.setIpcAmount(hotelTransactions.getIpcAmount());
        transactionDto.setHpAmount(hotelTransactions.getHpAmount());
        transactionDto.setHpcAmount(hotelTransactions.getHpcAmount());
        transactionDto.setHvAmount(hotelTransactions.getHvAmount());
        transactionDto.setPrAmount(hotelTransactions.getPrAmount());
        transactionDto.setPrcAmount(hotelTransactions.getPrcAmount());
        transactionDto.setLossAmount(hotelTransactions.getLossAmount());
        transactionDto.setPointAmount(BigDecimal.valueOf(0));

        return transactionDto;
    }


}
