package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.Hotel;
import id.holigo.services.holigohotelservice.domain.HotelDescription;

public interface HotelDescriptionRepository extends JpaRepository<HotelDescription, Long>{
    Optional<HotelDescription> findByHotel(Hotel hotel);
}
