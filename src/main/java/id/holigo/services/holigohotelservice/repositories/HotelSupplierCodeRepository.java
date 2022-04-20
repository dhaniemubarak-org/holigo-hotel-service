package id.holigo.services.holigohotelservice.repositories;

import id.holigo.services.holigohotelservice.domain.Hotel;
import id.holigo.services.holigohotelservice.domain.HotelSupplierCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelSupplierCodeRepository extends JpaRepository<HotelSupplierCode, Long> {
    Optional<HotelSupplierCode> findByHotel(Hotel hotel);
}
