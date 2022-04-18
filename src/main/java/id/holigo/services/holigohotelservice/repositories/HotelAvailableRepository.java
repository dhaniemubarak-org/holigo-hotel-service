package id.holigo.services.holigohotelservice.repositories;

import java.sql.Date;
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
}
