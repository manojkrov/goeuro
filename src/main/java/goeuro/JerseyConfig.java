package goeuro;

import goeuro.service.DirectBusRouteService;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;


/**
 * Jersey Config class to register service components.
 * Created by manoj on 19/09/16.
 */
@Component
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        registerEndpoints();
    }

    private void registerEndpoints() {
        register(DirectBusRouteService.class);

    }
}
