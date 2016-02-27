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
        from("kura-gpio://{{metatype:camel.kura.gpio.input.id}}?direction=INPUT&mode=INPUT_PULL_DOWN&trigger={{metatype:camel.kura.gpio.input.trigger}}")
            .to("deviceio-i2c:1/0x77?driver=bmp180")
            .to("log:io.rhiot.kura.heartbeat")
            .wireTap("kura-gpio://{{metatype:camel.kura.gpio.output.id}}?action={{metatype:camel.kura.gpio.output.action}}")
            .to("kura-cloud:demoAppId/tempMetric");

    }
}