package uk.gov.dvla.core.exception;

import uk.gov.dvla.core.error.ErrorResult;
import uk.gov.dvla.core.error.ServiceErrors;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class WebApplicationExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    @SuppressWarnings("unchecked")
    public Response toResponse(Exception exception) {
        if (exception instanceof WebApplicationException) {
            WebApplicationException webApplicationException = (WebApplicationException) exception;
            return Response
                    .status(webApplicationException.getStatus())
                    .entity(new ErrorResult(webApplicationException.getStatus(), webApplicationException.getError()))
                    .build();
        } else {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResult(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), ServiceErrors.SERVICE_ERROR))
                    .build();
        }
    }

}
