package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.MainFacilities;

public interface MainFacilityRepository extends JpaRepository<MainFacilities, Short> {
    Optional<MainFacilities> findByLabel(String label);
}
