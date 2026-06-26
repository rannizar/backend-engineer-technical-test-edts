package repository;

import entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {

    boolean existsByConcertIdAndBookingCode(UUID concertId, String bookingCode);

}