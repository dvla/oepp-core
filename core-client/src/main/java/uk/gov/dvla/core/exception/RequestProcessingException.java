package uk.gov.dvla.core.exception;

public class RequestProcessingException extends RuntimeException {

    public RequestProcessingException(Throwable cause) {
        super(cause);
    }
}
