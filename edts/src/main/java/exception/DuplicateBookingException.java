package exception;

public class DuplicateBookingException extends BookingException {

    public DuplicateBookingException() {
        super("User already booked this concert");
    }

}
