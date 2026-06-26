package exception;

public class BookingClosedException extends BookingException {

    public BookingClosedException() {
        super("Booking period has ended");
    }

}
