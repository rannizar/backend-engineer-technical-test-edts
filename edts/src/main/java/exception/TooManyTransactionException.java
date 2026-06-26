package exception;

public class TooManyTransactionException extends BookingException {

    public TooManyTransactionException() {
        super("Too many transaction in a short time, please try again.");
    }

}
