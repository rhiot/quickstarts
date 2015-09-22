package io.rhiot.quickstarts.cloudlets.mqtt;

import com.github.camellabs.iot.vertx.camel.CamelContextFactories;
import io.rhiot.steroids.bootstrap.Bootstrap;
import io.vertx.core.Vertx;
import org.apache.camel.builder.RouteBuilder;

public class MqttCloudlet extends Bootstrap {

    public static void main(String... args) {
        new MqttCloudlet().start();
    }

}