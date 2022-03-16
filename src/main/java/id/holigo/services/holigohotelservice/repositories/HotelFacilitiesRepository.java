package id.holigo.services.holigohotelservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import id.holigo.services.holigohotelservice.domain.FacilityCategories;
import id.holigo.services.holigohotelservice.domain.HotelFacilities;
import id.holigo.services.holigohotelservice.domain.Hotels;

public interface HotelFacilitiesRepository extends JpaRepository<HotelFacilities, Long> {

    @Query("SELECT hf.category FROM HotelFacilities hf WHERE hf.hotels = (:hotelId) GROUP BY hf.category")
    List<FacilityCategories> findAllByHotelId(@Param("hotelId") Hotels hotelId);

    List<HotelFacilities> findByCategory(FacilityCategories category);
}
