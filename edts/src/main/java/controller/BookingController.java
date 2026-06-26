@RestController

@RequestMapping("/booking")

@RequiredArgsConstructor

public class BookingController {

    private final BookingService service;

    @PostMapping

    public BookingResponse booking(
            @RequestBody BookingRequest request){

        return service.book(request);

    }

}