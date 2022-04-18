package id.holigo.services.holigohotelservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import id.holigo.services.holigohotelservice.domain.Facilities;
import id.holigo.services.holigohotelservice.domain.FacilityCategories;
import id.holigo.services.holigohotelservice.domain.HotelFacilities;
import id.holigo.services.holigohotelservice.domain.Hotel;

public interface HotelFacilitiesRepository extends JpaRepository<HotelFacilities, Long> {

    @Query("SELECT hf.category FROM HotelFacilities hf WHERE hf.hotels = (:hotelId) GROUP BY hf.category")
    List<FacilityCategories> findAllByHotelId(@Param("hotelId") Hotel hotelId);

    List<HotelFacilities> findByCategoryAndHotels(FacilityCategories category, Hotel hotel);

    Optional<HotelFacilities> findByHotelsAndFacilities(Hotel hotel, Facilities facilities);
}
