package io.rhiot.quickstarts.kura.camel;

import org.apache.camel.component.kura.KuraRouter;

/**
 * Example of the Kura Camel application.
 */
public class GatewayRouter extends KuraRouter {

    @Override
    public void configure() throws Exception {
        from("timer://heartbeat").
                to("log:heartbeat");
    }

}