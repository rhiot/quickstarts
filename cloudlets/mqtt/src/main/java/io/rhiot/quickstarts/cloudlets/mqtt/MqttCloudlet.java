package io.rhiot.quickstarts.cloudlets.mqtt;

import io.rhiot.steroids.bootstrap.Bootstrap;

/**
 * Example of the MQTT base microservice.
 */
public class MqttCloudlet extends Bootstrap {

    public static void main(String... args) {
        new MqttCloudlet().start();
    }

}