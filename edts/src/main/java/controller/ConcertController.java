@RestController

@RequestMapping("/concerts")

@RequiredArgsConstructor

public class ConcertController {

    private final ConcertService service;

    @GetMapping

    public List<Concert> search(){

        return service.search();

    }

}