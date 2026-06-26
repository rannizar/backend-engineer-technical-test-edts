@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository repository;

    public List<Concert> search() {

        return repository.findByBookingStartBeforeAndBookingEndAfter(LocalDateTime.now(), LocalDateTime.now());

    }

}