package uk.gov.dvla.core.error;

/**
 * Basic interface for application errors.
 */
public interface ApplicationError {

    /**
     * Returns error code
     * @return error code
     */
    String getCode();

    /**
     * Returns error message
     * @return error message
     */
    String getMessage();

}
