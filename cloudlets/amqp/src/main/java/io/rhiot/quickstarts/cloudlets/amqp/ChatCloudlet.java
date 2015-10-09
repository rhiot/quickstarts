package io.rhiot.quickstarts.cloudlets.amqp;

import io.rhiot.steroids.camel.CamelBootstrap;

import java.util.LinkedList;
import java.util.List;

import static io.rhiot.steroids.activemq.EmbeddedActiveMqBrokerBootInitializer.amqpJmsBridge;
import static io.rhiot.steroids.activemq.EmbeddedActiveMqBrokerBootInitializer.mqttJmsBridge;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * Example of the AMQP base microservice.
 */
public class ChatCloudlet extends CamelBootstrap {

    static final List<String> chat = new LinkedList<>();

    @Override
    public void configure() throws Exception {
        from(amqpJmsBridge("chat")).process(
                exchange -> chat.add(exchange.getIn().getBody(String.class))
        ).process(
                exchange -> exchange.getIn().setBody(join(chat, "\n"))
        ).to(amqpJmsBridge("chat-updates"));
    }

}