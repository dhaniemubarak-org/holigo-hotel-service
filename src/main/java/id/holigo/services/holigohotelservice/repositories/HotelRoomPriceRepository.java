package id.holigo.services.holigohotelservice.repositories;

import id.holigo.services.holigohotelservice.domain.Hotel;
import id.holigo.services.holigohotelservice.domain.HotelRoomPrices;
import id.holigo.services.holigohotelservice.domain.HotelRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

public interface HotelRoomPriceRepository extends JpaRepository<HotelRoomPrices, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM hotel_room_prices hrp WHERE hrp.room_id = :room AND hrp.check_in = :checkIn")
    Optional<HotelRoomPrices> findByRoomAndDateCheckIn(@Param("room") HotelRooms room, @Param("checkIn") Date checkIn);

    @Query(nativeQuery = true, value = "SELECT * FROM hotel_room_prices hrp WHERE hrp.bed_type = :bedType AND hrp.board = :board AND hrp.characteristic = :characteristic AND hrp.check_in = :checkIn AND hrp.hotel_id = :hotel AND hrp.room_id = :hotelRoom AND hrp.selected_id = :selectId AND hrp.selected_room_id = :selectRoomId")
    Optional<HotelRoomPrices> firstOrFail(@Param("bedType") String bedType, @Param("board") String board, @Param("characteristic") String characteristic, @Param("checkIn") Date checkIn, @Param("hotel") Hotel hotel, @Param("hotelRoom") HotelRooms hotelRooms, @Param("selectId") String selectId, @Param("selectRoomId") String selectRoomId);
}
