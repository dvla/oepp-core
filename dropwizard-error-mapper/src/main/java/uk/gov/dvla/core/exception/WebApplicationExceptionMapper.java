package uk.gov.dvla.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dvla.core.error.ApplicationError;
import uk.gov.dvla.core.error.ErrorResult;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.concurrent.ThreadLocalRandom;

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
        String logID = String.format("%016x", ThreadLocalRandom.current().nextLong());

        if (exception instanceof WebApplicationException) {
            WebApplicationException webApplicationException = (WebApplicationException) exception;
            return Response
                    .status(webApplicationException.getStatus())
                    .entity(new ErrorResult(webApplicationException.getStatus(), webApplicationException.getError(), logID))
                    .build();
        } else {
            logger.error("Unexpected error has been handled (ID: {})", logID, exception);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResult(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), unexpectedError, logID))
                    .build();
        }
    }

}
