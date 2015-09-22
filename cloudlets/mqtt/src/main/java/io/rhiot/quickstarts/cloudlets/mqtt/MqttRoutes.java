package io.rhiot.quickstarts.cloudlets.mqtt;

import org.apache.camel.builder.RouteBuilder;

public class MqttRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("paho:test?brokerUrl=tcp://localhost:1883").to("vertx:mqtt");

        from("vertx:mqtt").to("mock:mqttConsumer");
    }

}