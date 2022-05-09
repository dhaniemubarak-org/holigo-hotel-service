package id.holigo.services.holigohotelservice.web.mappers;

import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigohotelservice.domain.HotelFares;
import id.holigo.services.holigohotelservice.domain.HotelTransactions;
import id.holigo.services.holigohotelservice.web.model.requests.RequestBookDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.UUID;

@Mapper
@DecoratedWith(HotelBookMapperDecorator.class)
public interface HotelBookMapper {

    @Mapping(target = "id", ignore = true)
    HotelTransactions mappingHotelTransaction(RequestBookDto requestBookDto, HotelFares hotelFares);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "userId", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "fareAmount", ignore = true),
            @Mapping(target = "ntaAmount", ignore = true),
            @Mapping(target = "nraAmount", ignore = true),
            @Mapping(target = "cpAmount", ignore = true),
            @Mapping(target = "mpAmount", ignore = true),
            @Mapping(target = "ipAmount", ignore = true),
            @Mapping(target = "hpAmount", ignore = true),
            @Mapping(target = "hvAmount", ignore = true),
            @Mapping(target = "prAmount", ignore = true),
            @Mapping(target = "ipcAmount", ignore = true),
            @Mapping(target = "hpcAmount", ignore = true),
            @Mapping(target = "prcAmount", ignore = true),
            @Mapping(target = "lossAmount", ignore = true),
    })
    TransactionDto hotelTransactionToTransactionDto(HotelTransactions hotelTransactions, Long userId, HotelFares hotelFares);
}
