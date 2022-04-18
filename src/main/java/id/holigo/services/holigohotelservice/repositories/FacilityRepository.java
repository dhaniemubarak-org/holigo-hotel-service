package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.Facilities;

public interface FacilityRepository extends JpaRepository<Facilities, Long> {
    Optional<Facilities> findByName(String name);
}
