package io.rhiot.quickstarts.cloudlets.mqtt;

import io.rhiot.steroids.camel.Route;
import org.apache.camel.builder.RouteBuilder;

import static io.rhiot.steroids.activemq.EmbeddedActiveMqCamelRoutes.mqttEventBus;

@Route
public class MqttVertxBridgeRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(mqttEventBus()).to("mock:consumedFromMqttBroker");
    }

}