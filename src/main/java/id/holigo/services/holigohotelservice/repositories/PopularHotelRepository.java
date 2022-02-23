package id.holigo.services.holigohotelservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigohotelservice.domain.PopularHotel;

public interface PopularHotelRepository extends JpaRepository<PopularHotel, Integer> {
    List<PopularHotel> getAllByCityId(Integer cityId);
}
