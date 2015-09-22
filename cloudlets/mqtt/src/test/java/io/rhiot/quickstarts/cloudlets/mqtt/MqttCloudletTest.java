package io.rhiot.quickstarts.cloudlets.mqtt;

import com.github.camellabs.iot.vertx.camel.CamelContextFactories;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.BeforeClass;
import org.junit.Test;

public class MqttCloudletTest {

    static MqttCloudlet mqttCloudlet = new MqttCloudlet();

    @BeforeClass
    public static void xxx() {
        mqttCloudlet.start();
    }

    @Test
    public void should() throws InterruptedException {
        MockEndpoint mockEndpoint = CamelContextFactories.mockEndpoint("mock:mqttConsumer");
        mockEndpoint.setMinimumExpectedMessageCount(1);
        CamelContextFactories.camelContext().createProducerTemplate().sendBody("paho:test?brokerUrl=tcp://localhost:1883", "foo");
        mockEndpoint.assertIsSatisfied();
    }

}