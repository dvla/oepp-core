package uk.gov.dvla.core.exception;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.jersey.errors.EarlyEofExceptionMapper;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.server.ServerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import uk.gov.dvla.core.error.ApplicationError;

/**
 * This bundle registers our exception mappers, it disables the default dropwizard exception mappers if your
 * configuration is using the DefaultServerFactory implementation.
 *
 * It registers the following custom exception mappers:
 * WebApplicationExceptionMapper
 * ValidationViolationExceptionMapper
 *
 * And the standard dropwizard exception mappers:
 * EarlyEofExceptionMapper
 * JsonProcessingExceptionMapper
 * @param <T>
 */
public class CustomExceptionBundle<T extends Configuration> implements ConfiguredBundle<T> {

    private final ApplicationError applicationError;

    /**
     * @param applicationError The service specific general application error to be returned by the
     *                         WebApplicationExceptionMapper when the exception caught is not a WebApplicationException
     */
    public CustomExceptionBundle(ApplicationError applicationError) {
        this.applicationError = applicationError;
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) { }

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        ServerFactory serverFactory = configuration.getServerFactory();
        if (serverFactory instanceof DefaultServerFactory) {
            ((DefaultServerFactory) serverFactory).setRegisterDefaultExceptionMappers(false);
        }

        environment.jersey().register(new WebApplicationExceptionMapper(applicationError));
        environment.jersey().register(new ValidationViolationExceptionMapper());
        environment.jersey().register(new EarlyEofExceptionMapper());
        environment.jersey().register(new JsonProcessingExceptionMapper());
    }
}
