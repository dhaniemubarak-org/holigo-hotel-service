package id.holigo.services.holigohotelservice.repositories;

import id.holigo.services.holigohotelservice.domain.Hotel;
import id.holigo.services.holigohotelservice.domain.HotelContacts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelContactRepository extends JpaRepository<HotelContacts, Long> {
    Optional<HotelContacts> findByTypeAndDetail(String type, String detail);
    Optional<HotelContacts> findByHotelAndDetail(Hotel hotel, String Detail);
}
