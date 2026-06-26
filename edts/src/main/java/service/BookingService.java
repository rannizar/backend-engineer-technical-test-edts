@Service
@RequiredArgsConstructor
public class BookingService {

    private final ConcertRepository concertRepository;

    private final BookingRepository bookingRepository;

    @Transactional
    public BookingResponse book(BookingRequest request){

        Concert concert = concertRepository.findById(request.getConcertId())
                .orElseThrow();

        LocalDateTime now = LocalDateTime.now();

        if(now.isAfter(concert.getBookingEnd())){

            throw new RuntimeException("Booking closed");
        }

        if(bookingRepository.existsByConcertIdAndUserId(
                request.getConcertId(),
                request.getUserId())){

            throw new RuntimeException("Already booked");
        }

        if(concert.getAvailableTicket() < request.getQuantity()){

            throw new RuntimeException("Ticket sold out");
        }

        concert.setAvailableTicket(
                concert.getAvailableTicket()-request.getQuantity());

        Booking booking = new Booking();

        booking.setConcert(concert);
        booking.setUserId(request.getUserId());
        booking.setQuantity(request.getQuantity());
        booking.setBookingTime(now);
        booking.setStatus(BookingStatus.SUCCESS);

        bookingRepository.save(booking);

        concertRepository.save(concert);

        return new BookingResponse("Success");

    }