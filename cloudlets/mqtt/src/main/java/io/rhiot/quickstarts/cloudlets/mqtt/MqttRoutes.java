package io.rhiot.quickstarts.cloudlets.mqtt;

import io.rhiot.steroids.camel.Route;
import org.apache.camel.builder.RouteBuilder;

import static io.rhiot.steroids.activemq.EmbeddedActiveMqBrokerBootInitializer.mqtt;

@Route
public class MqttRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Every second send message to the MQTT topic using MQTT client.
        from("timer:sendToMqtt").
                setBody().constant("dummy message").
                to(mqtt("testTopic"));

        // Read messages from the MQTT broker using MQTT client.
        from(mqtt("testTopic")).to("mock:consumedFromMqttBroker");
    }

}