package id.holigo.services.holigohotelservice.web.mappers;

import id.holigo.services.holigohotelservice.domain.HotelInquiry;
import id.holigo.services.holigohotelservice.web.model.InquiryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface HotelInquiryMapper {
    @Mapping(target = "childAge", ignore = true)
    InquiryDto hotelInquiryToInquiryDto(HotelInquiry hotelInquiry);
}
