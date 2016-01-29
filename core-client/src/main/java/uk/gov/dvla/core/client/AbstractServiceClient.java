package uk.gov.dvla.core.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dvla.core.exception.RequestProcessingException;
import uk.gov.dvla.core.exception.UnexpectedResponseException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.Optional;

public abstract class AbstractServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(AbstractServiceClient.class);

    protected static final String HEALTH_CHECK_URI = "/healthcheck";

    protected final Client client;

    protected AbstractServiceClient(Client client) {
        this.client = client;
    }

    public abstract boolean isHealthy();

    protected String appendPath(URL baseURL, String path) {
        return (baseURL + "/" + path).replaceAll("([^:]\\/)\\/+", "$1");
    }

    protected <Req, Res> Optional<Res> callServerForOptionalResource(ServerCommand<Req, Res> serverCommand, Req request) {
        try {
            return Optional.of(callServerForMandatoryResource(serverCommand, request));
        } catch (UnexpectedResponseException ex) {
            if (ex.getResponseStatus() == 404) {
                logger.warn("Received 404 response for request {}, response body was {}", request, ex.getResponseBody());
                return Optional.empty();
            }

            throw ex;
        }
    }

    protected <Req, Res> Res callServerForMandatoryResource(ServerCommand<Req, Res> serverCommand, Req request) {
        try {
            Res response = serverCommand.makeServerCall(request);
            logger.debug("Received successful response {}, for request {}", response, request);
            return response;
        } catch (WebApplicationException ex) {
            Response errorResponse = ex.getResponse();
            logger.error("Received {} response for request {}, response body was {}", errorResponse.getStatus(), request, errorResponse, ex);
            throw new UnexpectedResponseException(errorResponse.getStatus(), errorResponse.readEntity(String.class), ex);
        } catch (Exception ex) {
            logger.error("Unexpected error occurred while calling underlying service for request: {}", request, ex);
            throw new RequestProcessingException(ex);
        }
    }

    protected interface ServerCommand<Req, Res> {

        Res makeServerCall(Req req);
    }
}
