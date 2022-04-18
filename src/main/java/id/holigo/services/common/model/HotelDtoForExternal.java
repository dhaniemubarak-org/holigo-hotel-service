package id.holigo.services.common.model;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class HotelDtoForExternal {

    private int cityId;

    private JsonNode data;

    private int number;

    private int size;

    private int totalElement;

    private JsonNode pageable;

    private Boolean last;

    private int totalPages;

    private JsonNode sort;

    private Boolean first;

    private int numberOfElements;
}
