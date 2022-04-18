package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.Hotel;
import id.holigo.services.holigohotelservice.domain.HotelNearbyPlaces;

public interface HotelNearbyPlaceRepository extends JpaRepository<HotelNearbyPlaces, Long> {
    Optional<HotelNearbyPlaces> findByNameAndCategoryAndHotel(String name, String category, Hotel hotel);
}
