package id.holigo.services.holigohotelservice.services;

import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigohotelservice.domain.HotelFares;
import id.holigo.services.holigohotelservice.domain.HotelTransactions;
import id.holigo.services.holigohotelservice.repositories.HotelFareRepository;
import id.holigo.services.holigohotelservice.repositories.HotelTransactionRepository;
import id.holigo.services.holigohotelservice.services.transactions.TransactionService;
import id.holigo.services.holigohotelservice.web.exceptions.NotFoundException;
import id.holigo.services.holigohotelservice.web.mappers.HotelBookMapper;
import id.holigo.services.holigohotelservice.web.model.requests.RequestBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class HotelBookServiceImpl implements HotelBookService {

    @Autowired
    private HotelTransactionRepository hotelTransactionRepository;

    @Autowired
    private HotelFareRepository hotelFareRepository;

    @Autowired
    private HotelBookMapper hotelBookMapper;

    @Autowired
    private TransactionService transactionService;

    @Override
    @Transactional
    public UUID postBookHotel(Long userId, RequestBookDto requestBookDto) throws JMSException {

        HotelFares hotelFares = hotelFareRepository.findByIdAndStatus(requestBookDto.getFareId(), Short.valueOf("0")).orElseThrow(NotFoundException::new);
        HotelTransactions hotelTransactions = hotelBookMapper.mappingHotelTransaction(requestBookDto, hotelFares);
        HotelTransactions savedHotelTransactions = hotelTransactionRepository.save(hotelTransactions);
        TransactionDto transactionDto = hotelBookMapper.hotelTransactionToTransactionDto(savedHotelTransactions, userId, hotelFares);
        TransactionDto savedTransactionDto = transactionService.sendCreateTransaction(transactionDto);

        hotelFares.setStatus(Short.valueOf("1"));
        hotelFareRepository.save(hotelFares);

        return savedTransactionDto.getId();
    }
}
