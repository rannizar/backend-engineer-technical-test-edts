package exception;

public class ConcertNotFoundException extends BookingException {

    public ConcertNotFoundException() {
        super("Concert not found");
    }

}
