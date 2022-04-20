package id.holigo.services.holigohotelservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long>{
    
    Optional<Hotel> findByName(String name);
    List<Hotel> findAllByIdIn(List<Long> hotelIdList);
}
