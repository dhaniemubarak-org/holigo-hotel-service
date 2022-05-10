package id.holigo.services.holigohotelservice.repositories;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import id.holigo.services.holigohotelservice.domain.Cities;
import id.holigo.services.holigohotelservice.domain.HotelAvailable;

public interface HotelAvailableRepository
        extends JpaRepository<HotelAvailable, Long>, JpaSpecificationExecutor<HotelAvailable> {

    Page<HotelAvailable> findAllByCityId(Cities cityId, Pageable pageable);

    @Query("SELECT hv FROM HotelAvailable hv WHERE hv.name = (:name) AND hv.checkIn = (:tglCheckIn)")
    Optional<HotelAvailable> findByNameAndCheckIn(@Param("name") String name, @Param("tglCheckIn") Date checkIn);

    @Query(nativeQuery = true, value = "SELECT * FROM hotel_available hv WHERE hotel_id = (:hotelId) AND YEAR(check_in) = (:year) AND MONTH(check_in) = (:month)")
    List<HotelAvailable> findByHotelAndBetweenDate(@Param("hotelId") Long hotelId, @Param("year") String year, @Param("month") String month);
}
