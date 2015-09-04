package uk.gov.dvla.core.health;

import com.codahale.metrics.health.HealthCheck;
import uk.gov.dvla.core.client.AbstractServiceClient;

public class ServiceHealthCheck extends HealthCheck {

    private AbstractServiceClient client;

    public ServiceHealthCheck(AbstractServiceClient client) {
        this.client = client;
    }

    @Override
    protected Result check() throws Exception {
        if (client.isHealthy()) {
            return Result.healthy("Health check of underlying service hasn't reported any problems");
        } else {
            return Result.unhealthy("Health check of underlying service has reported some problems");
        }
    }
}
