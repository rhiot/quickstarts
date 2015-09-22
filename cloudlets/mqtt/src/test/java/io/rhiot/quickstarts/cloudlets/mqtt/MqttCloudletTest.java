package io.rhiot.quickstarts.cloudlets.mqtt;

import org.apache.camel.component.mock.MockEndpoint;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.github.camellabs.iot.vertx.camel.CamelContextFactories.camelContext;
import static com.github.camellabs.iot.vertx.camel.CamelContextFactories.mockEndpoint;

public class MqttCloudletTest {

    // Fixtures

    static MqttCloudlet mqttCloudlet = new MqttCloudlet();

    @BeforeClass
    public static void beforeClass() {
        mqttCloudlet.start();
    }

    // Tests

    @Test
    public void shouldReadMessageFromEmbeddedMqttBroker() throws InterruptedException {
        MockEndpoint mockEndpoint = mockEndpoint("mock:mqttConsumer");
        mockEndpoint.setMinimumExpectedMessageCount(1);
        camelContext().createProducerTemplate().sendBody("paho:test?brokerUrl=tcp://localhost:1883", "foo");
        mockEndpoint.assertIsSatisfied();
    }

}