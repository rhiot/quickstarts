package io.rhiot.quickstarts.cloudlets.mqtt;

import io.rhiot.steroids.camel.Route;
import org.apache.camel.builder.RouteBuilder;

import static io.rhiot.steroids.activemq.EmbeddedActiveMqBrokerBootInitializer.mqttJmsBridge;

@Route
public class MqttJmsBridgeRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Every second send message to the MQTT topic using JMS client.
        from("timer:sendToMqtt").
                setBody().constant("dummy message").
                to(mqttJmsBridge("mqttJmsBridgeTopic"));

        // Read messages from the MQTT broker using JMS client.
        from(mqttJmsBridge("mqttJmsBridgeTopic")).to("mock:mqttJmsBridgeTest");
    }

}