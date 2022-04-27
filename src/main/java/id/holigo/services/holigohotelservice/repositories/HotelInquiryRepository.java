package id.holigo.services.holigohotelservice.repositories;

import id.holigo.services.holigohotelservice.domain.HotelInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

public interface HotelInquiryRepository extends JpaRepository<HotelInquiry, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM hotel_inquiries hi WHERE hi.user_id = :userId AND hi.check_in = :checkIn AND hi.room_amount = :roomAmount AND hi.destination_id = :destinationId AND hi.adult_amount = :adultAmount AND hi.child_amount = :childAmount")
    Optional<HotelInquiry> findByCustom(@Param("userId") Long userId, @Param("checkIn") Date checkIn, @Param("roomAmount") Short roomAmount, @Param("destinationId") Long destinationId, @Param("adultAmount") Short adultAmount, @Param("childAmount") Short childAmount);
}
