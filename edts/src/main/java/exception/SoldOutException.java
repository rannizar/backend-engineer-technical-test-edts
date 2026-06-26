package exception;

public class SoldOutException extends BookingException {

    public SoldOutException() {
        super("Ticket sold out");
    }

}
