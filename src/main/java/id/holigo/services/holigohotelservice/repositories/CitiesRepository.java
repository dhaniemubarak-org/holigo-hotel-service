package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.Cities;
import id.holigo.services.holigohotelservice.domain.Provinces;

public interface CitiesRepository extends JpaRepository<Cities, Long> {
    Optional<Cities> findByNameEnAndProvince(String name, Provinces province);
}
