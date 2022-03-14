package id.holigo.services.holigohotelservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.Hotels;

public interface HotelRepository extends JpaRepository<Hotels, Long>{
    
}
