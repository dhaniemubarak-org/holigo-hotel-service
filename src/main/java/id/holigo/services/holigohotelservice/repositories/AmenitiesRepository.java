package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.Amenities;

public interface AmenitiesRepository extends JpaRepository<Amenities, Long> {
    Optional<Amenities> findByName(String name);
}
