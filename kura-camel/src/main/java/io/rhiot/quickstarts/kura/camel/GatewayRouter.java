package io.rhiot.quickstarts.kura.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.component.kura.KuraRouter;
import org.apache.camel.component.log.LogComponent;
import org.apache.camel.component.timer.TimerComponent;
import org.osgi.framework.BundleContext;

/**
 * Example of the Kura Camel application.
 */
public class GatewayRouter extends KuraRouter {

    @Override
    public void configure() throws Exception {
        from("timer://heartbeat").
                to("log:heartbeat");
    }

    // TODO Remove this overriden method as soon as Camel 2.17 is out (see CAMEL-9314)
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        try {
            super.start(bundleContext);
        } catch (Throwable e) {
            String errorMessage = "Problem when starting Kura module " + getClass().getName() + ":";
            log.warn(errorMessage, e);

            // Print error to the Kura console.
            System.err.println(errorMessage);
            e.printStackTrace();

            throw e;
        }
    }

    @Override
    protected CamelContext createCamelContext() {
        CamelContext camelContext = super.createCamelContext();
        camelContext.addComponent("timer", new TimerComponent());
        camelContext.addComponent("log", new LogComponent());
        return camelContext;
    }

}