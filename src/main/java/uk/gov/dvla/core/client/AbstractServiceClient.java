package uk.gov.dvla.core.client;

import javax.ws.rs.client.Client;
import java.net.URL;

public abstract class AbstractServiceClient {

    protected static final String HEALTH_CHECK_URI = "/healthcheck";

    protected final Client client;

    protected AbstractServiceClient(Client client) {
        this.client = client;
    }

    public abstract boolean isHealthy();

    protected String appendPath(URL baseURL, String path) {
        return (baseURL + "/" + path).replaceAll("([^:]\\/)\\/+", "$1");
    }

}
