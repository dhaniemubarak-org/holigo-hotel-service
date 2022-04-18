package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.Cities;
import id.holigo.services.holigohotelservice.domain.Districts;

public interface DistrictsRepository extends JpaRepository<Districts, Long> {
    Optional<Districts> findByNameAndCity(String name, Cities city);
}
