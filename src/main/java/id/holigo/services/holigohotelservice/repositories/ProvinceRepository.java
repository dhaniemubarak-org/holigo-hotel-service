package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.Provinces;

public interface ProvinceRepository extends JpaRepository<Provinces, Long>{
    Optional<Provinces> findByName(String name);
}
