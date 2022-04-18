package id.holigo.services.holigohotelservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.Hotel;
import id.holigo.services.holigohotelservice.domain.HotelAddresses;

public interface HotelAddressRepository extends JpaRepository<HotelAddresses, Long> {
    Optional<HotelAddresses> findByHotel(Hotel hotel);
}
