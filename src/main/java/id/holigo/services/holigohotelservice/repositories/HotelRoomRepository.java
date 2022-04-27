package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.Hotel;
import id.holigo.services.holigohotelservice.domain.HotelRooms;

public interface HotelRoomRepository extends JpaRepository<HotelRooms, Long> {
    Optional<HotelRooms> findByNameAndHotel(String name, Hotel hotel);
    Optional<HotelRooms> findByRoomSupplierId(String supplierId);
}
