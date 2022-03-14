package id.holigo.services.holigohotelservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import id.holigo.services.holigohotelservice.domain.PopularCities;

public interface PopularCityRepository extends JpaRepository<PopularCities, Integer>{
    
    @Query("SELECT e FROM PopularCities e WHERE e.name IN (:names)")
    List<PopularCities> findByCitiesNames(@Param("names") List<String> names);
}
