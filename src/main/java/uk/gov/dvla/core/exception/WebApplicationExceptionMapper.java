package uk.gov.dvla.core.exception;

import uk.gov.dvla.core.error.ErrorResult;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    @SuppressWarnings("unchecked")
    public Response toResponse(WebApplicationException exception) {
        return Response
                .status(exception.getStatus())
                .entity(new ErrorResult(exception.getStatus(), exception.getError()))
                .build();
    }

}
