package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.HotelTypes;

public interface HotelTypeRepository extends JpaRepository<HotelTypes, Integer> {
    Optional<HotelTypes> findByName(String name);
}
