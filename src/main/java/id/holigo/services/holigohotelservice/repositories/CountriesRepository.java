package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.Countries;

public interface CountriesRepository extends JpaRepository<Countries, String> {
    Optional<Countries> findByName(String name);
}
