package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.Hotel;
import id.holigo.services.holigohotelservice.domain.HotelPolicies;

public interface HotelPolicyRepository extends JpaRepository<HotelPolicies, Long> {
    Optional<HotelPolicies> findByHotel(Hotel hotel);
}
