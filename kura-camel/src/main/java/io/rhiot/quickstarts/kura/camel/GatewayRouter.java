package io.rhiot.quickstarts.kura.camel;

import org.apache.camel.component.kura.KuraRouter;

public class GatewayRouter extends KuraRouter {

    @Override
    public void configure() throws Exception {
        from("timer://gateway-trigger").
                to("log:gateway");
    }

}