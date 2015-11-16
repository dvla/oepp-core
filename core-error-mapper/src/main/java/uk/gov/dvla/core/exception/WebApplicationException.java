package uk.gov.dvla.core.exception;

import uk.gov.dvla.core.error.ApplicationError;

/**
 * Basic web application exception which is mapped to {@link uk.gov.dvla.core.error.ErrorResult}.
 */
public class WebApplicationException extends RuntimeException {

    private int status;
    private ApplicationError error;

    public WebApplicationException(int status, ApplicationError error) {
        this.status = status;
        this.error = error;
    }

    /**
     * Returns response status code.
     * <p>
     * Should be valid HTTP response code as this field is mapped by exception mapper directly
     * to response status code (see: {@link uk.gov.dvla.core.error.ErrorResult#status}).
     * @return response status code
     */
    public int getStatus() {
        return status;
    }

    /**
     * Returns error instance.
     * <p>
     * Should be serializable to JSON / deserializable from JSON as this field is mapped by exception
     * mapper directly to response body (see: {@link uk.gov.dvla.core.error.ErrorResult#error}).
     * @return error instance
     */
    public ApplicationError getError() {
        return error;
    }

}
