package io.rhiot.quickstarts.kura.camel;

import io.rhiot.component.kura.router.RhiotKuraRouter;
import org.apache.camel.CamelContext;
import org.apache.camel.component.log.LogComponent;
import org.apache.camel.component.mock.MockComponent;
import org.apache.camel.component.timer.TimerComponent;

/**
 * Example of the Kura Camel application.
 */
public class GatewayRouter extends RhiotKuraRouter {

    @Override
    public void configure() throws Exception {
        from("timer://heartbeat").
                to("log:heartbeat").
                to("mock:test");
    }

    @Override
    protected void beforeStart(CamelContext camelContext) {
        super.beforeStart(camelContext);
        camelContext.addComponent("timer", new TimerComponent());
        camelContext.addComponent("log", new LogComponent());
        camelContext.addComponent("mock", new MockComponent());
    }

}