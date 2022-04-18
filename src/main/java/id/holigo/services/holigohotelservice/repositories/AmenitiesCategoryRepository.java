package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.AmenityCategories;

public interface AmenitiesCategoryRepository extends JpaRepository<AmenityCategories, Long> {
    Optional<AmenityCategories> findByName(String name);

}
