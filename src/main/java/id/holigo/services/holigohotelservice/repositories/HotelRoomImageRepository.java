package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.HotelRoomImages;
import id.holigo.services.holigohotelservice.domain.HotelRooms;

public interface HotelRoomImageRepository extends JpaRepository<HotelRoomImages, Long> {
    Optional<HotelRoomImages> findByRoomAndImageUrl(HotelRooms room, String imageUrl);
}
