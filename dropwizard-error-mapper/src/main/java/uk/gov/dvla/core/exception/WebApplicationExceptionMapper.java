package uk.gov.dvla.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dvla.core.error.ApplicationError;
import uk.gov.dvla.core.error.ErrorResult;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.concurrent.ThreadLocalRandom;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

public class WebApplicationExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger logger = LoggerFactory.getLogger(WebApplicationExceptionMapper.class);

    /**
     * The type of error returned for any unhandled exceptions.
     */
    private ApplicationError unexpectedError;

    public WebApplicationExceptionMapper(ApplicationError unexpectedError) {
        this.unexpectedError = unexpectedError;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Response toResponse(Exception exception) {
        int responseStatus;
        ApplicationError applicationError;
        String incidentID;

        if (exception instanceof WebApplicationException) {
            WebApplicationException webApplicationException = (WebApplicationException) exception;

            responseStatus = webApplicationException.getStatus();
            applicationError = webApplicationException.getError();
        } else {
            responseStatus = INTERNAL_SERVER_ERROR.getStatusCode();
            applicationError = unexpectedError;
        }

        if (Family.familyOf(responseStatus) == Family.SERVER_ERROR) {
            incidentID = String.format("%016x", ThreadLocalRandom.current().nextLong());
            logger.error("Unexpected error has been handled (incident ID: {})", incidentID, exception);
        } else {
            incidentID = null;
        }

        return Response
                .status(responseStatus)
                .entity(new ErrorResult(responseStatus, applicationError, incidentID))
                .build();
    }

}
