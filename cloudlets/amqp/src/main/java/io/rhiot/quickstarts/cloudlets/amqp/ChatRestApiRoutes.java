package io.rhiot.quickstarts.cloudlets.amqp;

import io.rhiot.steroids.camel.Route;
import org.apache.camel.builder.RouteBuilder;

import static org.apache.commons.lang3.StringUtils.join;

@Route
public class ChatRestApiRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().component("netty4-http").host("0.0.0.0").port(8180);

        rest("/chat").get().route().process(
                exchange -> exchange.getIn().setBody(join(ChatCloudlet.chat, "\n"))
        );
    }

}