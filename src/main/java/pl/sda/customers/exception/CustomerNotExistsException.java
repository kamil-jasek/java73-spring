package pl.sda.customers.exception;

public final class CustomerNotExistsException extends BusinessServiceException {

    public CustomerNotExistsException(String message) {
        super(message);
    }

    public CustomerNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
