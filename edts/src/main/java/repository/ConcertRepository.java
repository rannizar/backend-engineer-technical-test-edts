@Repository
public interface ConcertRepository extends JpaRepository<Concert, UUID> {

    List<Concert> findByBookingStartBeforeAndBookingEndAfter(LocalDateTime now, LocalDateTime now);

}