package io.rhiot.quickstarts.kura.camel;

import io.rhiot.component.kura.router.RhiotKuraRouter;

/**
 * Example of the Kura Camel application.
 */
public class GatewayRouter extends RhiotKuraRouter {

	@Override
	public void configure() throws Exception {
		
		from("timer://io.rhiot.kura.timer?period={{metatype:camel.kura.gpio.ouput.period}}")
				.to("log:io.rhiot.kura.heartbeat") 
				.to("kura-gpio://{{metatype:camel.kura.gpio.ouput.id}}?action={{metatype:camel.kura.gpio.ouput.action}}");
	}

}