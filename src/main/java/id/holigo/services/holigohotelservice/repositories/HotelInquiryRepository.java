package id.holigo.services.holigohotelservice.repositories;

import id.holigo.services.holigohotelservice.domain.HotelInquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HotelInquiryRepository extends JpaRepository<HotelInquiry, UUID> {

}
