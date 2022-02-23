package id.holigo.services.holigohotelservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import id.holigo.services.holigohotelservice.domain.Cities;

public interface CityRepository extends JpaRepository<Cities, Integer>{
    
    @Query("SELECT e FROM Cities e WHERE e.name IN (:names)")
    List<Cities> findByCitiesNames(@Param("names") List<String> names);
}
