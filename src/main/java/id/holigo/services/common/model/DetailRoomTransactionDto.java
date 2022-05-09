package id.holigo.services.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DetailRoomTransactionDto {

    private String board;

    private String type;

    private String name;
}
