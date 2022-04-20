package id.holigo.services.holigohotelservice.repositories;

import java.util.List;
import java.util.Optional;

import id.holigo.services.holigohotelservice.domain.Countries;
import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.Cities;
import id.holigo.services.holigohotelservice.domain.Provinces;

public interface CitiesRepository extends JpaRepository<Cities, Long> {
    Optional<Cities> findByNameEnAndProvince(String name, Provinces province);
    List<Cities> findAllByCountry(Countries country);
}
