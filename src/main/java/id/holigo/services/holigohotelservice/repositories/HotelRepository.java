package id.holigo.services.holigohotelservice.repositories;

import java.util.List;
import java.util.Optional;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import id.holigo.services.holigohotelservice.domain.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long>{
    
    Optional<Hotel> findByName(String name);
    List<Hotel> findAllByIdIn(List<Long> hotelIdList);

//    @Query("SELECT htl FROM Hotel as htl WHERE htl.id > 583 ORDER BY htl.id ASC")
    @Query(nativeQuery = true, value = "SELECT * FROM hotel htl WHERE htl.id > :lastId ORDER BY htl.id LIMIT 50000")
    List<Hotel> findAllByIdGreaterThan(@Param("lastId") Integer lastId);
}
