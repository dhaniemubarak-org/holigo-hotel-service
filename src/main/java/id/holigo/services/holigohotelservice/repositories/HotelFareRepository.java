package id.holigo.services.holigohotelservice.repositories;

import id.holigo.services.holigohotelservice.domain.HotelFares;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HotelFareRepository extends JpaRepository<HotelFares, UUID> {

}
