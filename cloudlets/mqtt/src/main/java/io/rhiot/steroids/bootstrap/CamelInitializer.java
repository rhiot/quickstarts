package io.rhiot.steroids.bootstrap;

import com.github.camellabs.iot.vertx.camel.CamelContextFactories;
import io.rhiot.steroids.Steroids;
import io.vertx.core.Vertx;
import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.util.List;
import java.util.Set;

public class CamelInitializer implements BootInitializer {

    CamelContext camelContext = CamelContextFactories.camelContext();

    @Override
    public void start() {
        Vertx vertx = Vertx.vertx();
        CamelContextFactories.connect(vertx);
        Set<Class<? extends RouteBuilder>> routes = new Reflections(new ConfigurationBuilder().forPackages("io.rhiot.quickstarts.cloudlets.mqtt")).getSubTypesOf(RouteBuilder.class);
        for(Class<? extends RoutesBuilder> routesBuilder : routes) {
            try {
                camelContext.addRoutes(routesBuilder.newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void stop() {
        try {
            camelContext.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int order() {
        return 1100;
    }

}