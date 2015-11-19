package io.rhiot.quickstarts.kura.camel;

import io.rhiot.component.kura.router.RhiotKuraRouter;
import org.apache.camel.CamelContext;
import org.apache.camel.component.log.LogComponent;
import org.apache.camel.component.timer.TimerComponent;

/**
 * Example of the Kura Camel application.
 */
public class GatewayRouter extends RhiotKuraRouter {

    @Override
    public void configure() throws Exception {
        from("timer://heartbeat").
                to("log:heartbeat");
    }

    @Override
    protected void beforeStart(CamelContext camelContext) {
        camelContext.addComponent("timer", new TimerComponent());
        camelContext.addComponent("log", new LogComponent());
    }

}