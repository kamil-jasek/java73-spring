package pl.sda.customers.exception;

public final class VatAlreadyExistsException extends BusinessServiceException {

    public VatAlreadyExistsException(String message) {
        super(message);
    }

    public VatAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
