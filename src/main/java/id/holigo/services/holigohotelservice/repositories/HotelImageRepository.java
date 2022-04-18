package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.Hotel;
import id.holigo.services.holigohotelservice.domain.HotelImages;

public interface HotelImageRepository extends JpaRepository<HotelImages, Long> {
    Optional<HotelImages> findByHotelAndCategoryAndImageUrl(Hotel hotel, String category, String imageUrl);
}
