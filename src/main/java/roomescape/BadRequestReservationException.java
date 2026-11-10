package roomescape;

public class BadRequestReservationException extends RuntimeException {
    public BadRequestReservationException(String message) {
        super(message);
    }
}
