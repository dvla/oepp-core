package uk.gov.dvla.core.exception;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.jersey.errors.EarlyEofExceptionMapper;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import uk.gov.dvla.core.error.ApplicationError;

public class CustomExceptionBundle<T extends Configuration> implements ConfiguredBundle<T> {

    private final ApplicationError applicationError;

    public CustomExceptionBundle(ApplicationError applicationError) {
        this.applicationError = applicationError;
    }

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        environment.jersey().register(new WebApplicationExceptionMapper(applicationError));
        environment.jersey().register(new ValidationViolationExceptionMapper());
        environment.jersey().register(new EarlyEofExceptionMapper());
        environment.jersey().register(new JsonProcessingExceptionMapper());
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }
}
