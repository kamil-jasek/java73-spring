package pl.sda.customers.exception;

public abstract class BusinessServiceException extends RuntimeException {

    public BusinessServiceException(String message) {
        super(message);
    }

    public BusinessServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
