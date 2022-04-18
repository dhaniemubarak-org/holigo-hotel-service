package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.Hotel;
import id.holigo.services.holigohotelservice.domain.HotelMainFacilities;
import id.holigo.services.holigohotelservice.domain.MainFacilities;

public interface HotelMainFacilityRepository extends JpaRepository<HotelMainFacilities, Long> {
    Optional<HotelMainFacilities> findByHotelAndMainFacility(Hotel hotel, MainFacilities mainFacilities);
}
