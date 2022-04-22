package id.holigo.services.holigohotelservice.repositories;

import java.util.List;
import java.util.Optional;

import feign.Param;
import id.holigo.services.holigohotelservice.domain.Countries;
import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.Cities;
import id.holigo.services.holigohotelservice.domain.Provinces;
import org.springframework.data.jpa.repository.Query;

public interface CitiesRepository extends JpaRepository<Cities, Long> {
    Optional<Cities> findByNameEnAndProvince(String name, Provinces province);
    List<Cities> findAllByCountry(Countries country);

    @Override
    @Query(nativeQuery = true, value = "SELECT * FROM cities c WHERE c.id = :id")
    Optional<Cities> findById(@Param("id") Long id);
}
