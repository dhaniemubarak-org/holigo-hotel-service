package id.holigo.services.holigohotelservice.repositories;

import id.holigo.services.holigohotelservice.domain.HotelTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelTransactionRepository extends JpaRepository<HotelTransactions, Long> {
}
