package id.holigo.services.holigohotelservice.repositories;

import id.holigo.services.holigohotelservice.domain.Hotel;
import id.holigo.services.holigohotelservice.domain.HotelRules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRuleRepository extends JpaRepository<HotelRules, Long> {
    Optional<HotelRules> findByHotelAndLabel(Hotel hotel, String label);
}
