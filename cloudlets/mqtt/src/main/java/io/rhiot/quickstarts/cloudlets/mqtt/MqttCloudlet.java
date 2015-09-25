package io.rhiot.quickstarts.cloudlets.mqtt;

import io.rhiot.steroids.camel.CamelBootstrap;

import static io.rhiot.steroids.activemq.EmbeddedActiveMqBrokerBootInitializer.mqtt;

/**
 * Example of the MQTT base microservice.
 */
public class MqttCloudlet extends CamelBootstrap {

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