package io.rhiot.quickstarts.cloudlets.mqtt;

import io.rhiot.steroids.camel.CamelBootstrap;

import java.util.LinkedList;
import java.util.List;

import static io.rhiot.steroids.activemq.EmbeddedActiveMqBrokerBootInitializer.mqttJmsBridge;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * Example of the MQTT base microservice.
 */
public class ChatCloudlet extends CamelBootstrap {

    static final List<String> chat = new LinkedList<>();

    @Override
    public void configure() throws Exception {
        from(mqttJmsBridge("chat")).process(
                // Append message to the chat history.
                exchange -> chat.add(exchange.getIn().getBody(String.class))
        ).process(
                // Prepare history update for chat listeners.
                exchange -> exchange.getIn().setBody(join(chat, "\n"))
        ).to(mqttJmsBridge("chat-updates"));
    }

}