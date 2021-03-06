package uk.gov.dvla.core.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Basic error result returned by RESTful services.
 * <p>
 * Class is intended to be serialized to JSON / deserialised from JSON so
 * generic type must be configured properly using Jackson annotations.
 *
 * @param <T> implementation of application error interface
 */
public class ErrorResult<T extends ApplicationError> {

    private final int status;
    private final T error;
    @JsonInclude(Include.NON_NULL)
    private final String incidentID;

    @JsonCreator
    public ErrorResult(@JsonProperty("status") int status,
                       @JsonProperty("error") T error,
                       @JsonProperty("incidentID") String incidentID) {
        this.incidentID = incidentID;
        this.status = status;
        this.error = error;
    }

    /**
     * Returns response status code and should be valid HTTP status code.
     * @return response status code
     */
    public int getStatus() {
        return status;
    }

    /**
     * Returns error instance which implements {@link ApplicationError} interface.
     * @return error instance
     */
    public T getError() {
        return error;
    }

    /**
     * Returns an incident ID which matches the message that the error is linked to in the logs
     * @return unique error response ID
     */
    public String getIncidentID() {
        return incidentID;
    }
}
