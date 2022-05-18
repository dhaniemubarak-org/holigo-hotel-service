package id.holigo.services.holigohotelservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import id.holigo.services.holigohotelservice.domain.PopularDestination;

public interface PopularDestinationRepository extends JpaRepository<PopularDestination, Integer> {
    
    @Query("SELECT e FROM PopularDestination e WHERE e.name IN (:names)")
    List<PopularDestination> findAllByCity(@Param("names") List<String> names);
}
