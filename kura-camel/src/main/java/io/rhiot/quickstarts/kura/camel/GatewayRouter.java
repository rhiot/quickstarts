package io.rhiot.quickstarts.kura.camel;

import org.eclipse.kura.camel.router.CamelRouter;

/**
 * Example of the Kura Camel application.
 */
public class GatewayRouter extends CamelRouter {

    @Override
    public void configure() throws Exception {
        from("timer://heartbeat").
                setBody().simple("random(1,20)").
                choice().
                  when(simple("${body} < 10"))
                .to("log:lessThanTen")
                  .when(simple("${body} == 10"))
                  .to("log:equalToTen")
                .otherwise()
                .to("log:greaterThanTen");
    }

}