package id.holigo.services.holigohotelservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import id.holigo.services.holigohotelservice.domain.HotelRoomAmenities;
import id.holigo.services.holigohotelservice.domain.HotelRooms;
import id.holigo.services.holigohotelservice.domain.AmenityCategories;

public interface HotelRoomAmenitiesRepository extends JpaRepository<HotelRoomAmenities, Long> {

    @Query("SELECT a.category FROM HotelRoomAmenities a WHERE a.rooms = (:room) GROUP BY a.category")
    List<AmenityCategories> findAllByHotelRoom(@Param("room") HotelRooms hotelRooms);

    List<HotelRoomAmenities> findByCategory(AmenityCategories category);
}
