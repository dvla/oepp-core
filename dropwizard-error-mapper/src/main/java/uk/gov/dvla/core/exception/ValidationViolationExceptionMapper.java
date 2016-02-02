package uk.gov.dvla.core.exception;

import io.dropwizard.jersey.validation.ConstraintViolationExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;

/**
 * A {@link ConstraintViolationException} mapper which logs validation constraints violations before
 * handling it in the same way as the {@link ConstraintViolationExceptionMapper} would
 */
public class ValidationViolationExceptionMapper extends ConstraintViolationExceptionMapper {

    private static final Logger logger = LoggerFactory.getLogger(ValidationViolationExceptionMapper.class);

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        logger.warn("The following validation constraints were violated: {}", exception.getConstraintViolations());
        return super.toResponse(exception);
    }
}
