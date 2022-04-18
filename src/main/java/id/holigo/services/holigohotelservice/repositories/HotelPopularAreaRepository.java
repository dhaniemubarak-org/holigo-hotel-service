package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.Hotel;
import id.holigo.services.holigohotelservice.domain.HotelPopularAreas;

public interface HotelPopularAreaRepository extends JpaRepository<HotelPopularAreas, Long>{
    Optional<HotelPopularAreas> findByNameAndCategoryAndHotel(String name, String category, Hotel hotel);
}
