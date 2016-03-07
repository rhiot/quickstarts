package io.rhiot.quickstarts.kura.camel;

import io.rhiot.component.kura.router.RhiotKuraRouter;

/**
 * Example of the Kura Camel application.
 */
public class GatewayRouter extends RhiotKuraRouter {

    @Override
    public void configure() throws Exception {
        from("kura-gpio://{{metatype:camel.kura.gpio.input.id}}?direction=INPUT&mode=INPUT_PULL_DOWN&trigger={{metatype:camel.kura.gpio.input.trigger}}")
                .id("[Magnetic-Route]").to("direct:mainRoute");

        from("timer:tempRoute?period=30000&delay=1000").id("[Timer-Route]").to("direct:mainRoute");

        from("direct:mainRoute").id("[Main-Route]")
                .to("deviceio-i2c://{{metatype:camel.kura.i2c.bus.id}}/{{metatype:camel.kura.i2c.device.id}}?driver={{metatype:camel.kura.i2c.device.driver}}")
                .to("log:io.rhiot.kura.heartbeat")
                .wireTap(
                        "kura-gpio://{{metatype:camel.kura.gpio.output.id}}?action={{metatype:camel.kura.gpio.output.action}}")
                .to("kura-cloud:demoAppId/tempMetric");

    }
}