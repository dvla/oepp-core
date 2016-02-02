package uk.gov.dvla.core.exception;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.jersey.errors.EarlyEofExceptionMapper;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.server.ServerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dvla.core.error.ApplicationError;

/**
 * This bundle registers our exception mappers, it disables the default dropwizard exception mappers if your
 * configuration is using the DefaultServerFactory implementation.
 * <p>
 * It registers the following custom exception mappers:
 * <ul>
 *   <li>{@link WebApplicationExceptionMapper}</li>
 *   <li>{@link ValidationViolationExceptionMapper}</li>
 * </ul>
 * <p>
 * And the standard dropwizard exception mappers:
 * <ul>
 *   <li>{@link EarlyEofExceptionMapper}</li>
 *   <li>{@link JsonProcessingExceptionMapper}</li>
 * </ul>
 *
 * @param <T>
 */
public class ExceptionMappersBundle<T extends Configuration> implements ConfiguredBundle<T> {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionMappersBundle.class);

    private final ApplicationError applicationError;

    /**
     * @param applicationError The service specific general application error to be returned by the
     *                         {@link WebApplicationExceptionMapper} when the exception caught is
     *                         not a {@link WebApplicationException}
     */
    public ExceptionMappersBundle(ApplicationError applicationError) {
        this.applicationError = applicationError;
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) { }

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        ServerFactory serverFactory = configuration.getServerFactory();
        if (serverFactory instanceof DefaultServerFactory) {
            ((DefaultServerFactory) serverFactory).setRegisterDefaultExceptionMappers(false);
            logger.info("Default exceptions mappers has been disabled - custom ones will be installed instead");
        }

        environment.jersey().register(new WebApplicationExceptionMapper(applicationError));
        environment.jersey().register(new ValidationViolationExceptionMapper());
        environment.jersey().register(new EarlyEofExceptionMapper());
        environment.jersey().register(new JsonProcessingExceptionMapper());
    }
}
