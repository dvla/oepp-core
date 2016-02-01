package uk.gov.dvla.core.exception;

import io.dropwizard.jersey.validation.ConstraintViolationExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;

public class ValidationViolationExceptionMapper extends ConstraintViolationExceptionMapper {

    private static final Logger logger = LoggerFactory.getLogger(ConstraintViolationException.class);

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        logger.debug("The following validation constraints were violated: {}", exception.getConstraintViolations());
        return super.toResponse(exception);
    }
}
