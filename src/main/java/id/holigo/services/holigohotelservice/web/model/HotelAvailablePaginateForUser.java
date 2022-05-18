package id.holigo.services.holigohotelservice.web.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.databind.JsonNode;

import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelStoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
public class HotelAvailablePaginateForUser extends PageImpl<DetailHotelForListDto> {

    @JsonCreator(mode = Mode.PROPERTIES)
    public HotelAvailablePaginateForUser(@JsonProperty("data") List<DetailHotelForListDto> content,
                                         @JsonProperty("number") int number, @JsonProperty("totalElement") int totalElements,
                                         @JsonProperty("size") int size, @JsonProperty("last") boolean last,
                                         @JsonProperty("totalPages") int totalPages, @JsonProperty("Sort") JsonNode sort,
                                         @JsonProperty("first") boolean first, @JsonProperty("numberOfElement") int numberOfElement) {
        super(content, PageRequest.of(number, size), totalElements);
    }

    public HotelAvailablePaginateForUser(List<DetailHotelForListDto> data, Pageable pageable, long total) {
        super(data, pageable, total);
    }

    public HotelAvailablePaginateForUser(List<DetailHotelForListDto> data) {
        super(data);
    }
}
