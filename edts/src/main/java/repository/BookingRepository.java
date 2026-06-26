@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    boolean existsByConcertIdAndUserId(UUID concertId, UUID userId);

}