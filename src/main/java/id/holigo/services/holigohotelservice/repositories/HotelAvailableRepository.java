package id.holigo.services.holigohotelservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import id.holigo.services.holigohotelservice.domain.Cities;
import id.holigo.services.holigohotelservice.domain.HotelAvailable;

public interface HotelAvailableRepository
        extends JpaRepository<HotelAvailable, Long>, JpaSpecificationExecutor<HotelAvailable> {

    Page<HotelAvailable> findAllByCityId(Cities cityId, Pageable pageable);
}
