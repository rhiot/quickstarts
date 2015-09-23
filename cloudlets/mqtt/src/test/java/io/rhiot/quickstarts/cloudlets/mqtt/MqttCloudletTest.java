package io.rhiot.quickstarts.cloudlets.mqtt;

import io.rhiot.steroids.camel.CamelBootInitializer;
import org.apache.camel.CamelContext;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.BeforeClass;
import org.junit.Test;

public class MqttCloudletTest {

    // Fixtures

    static MqttCloudlet mqttCloudlet = new MqttCloudlet();

    static CamelContext camelContext;

    @BeforeClass
    public static void beforeClass() {
        mqttCloudlet.start();
        camelContext = (CamelContext) CamelBootInitializer.camelContext();
    }

    // Tests

    @Test
    public void shouldReadMessageFromVertxMqttBridge() throws InterruptedException {
        MockEndpoint mockEndpoint = camelContext.getEndpoint("mock:consumedFromMqttBroker", MockEndpoint.class);
        mockEndpoint.setMinimumExpectedMessageCount(1);
        camelContext.createProducerTemplate().sendBody("paho:test?brokerUrl=tcp://localhost:1883", "foo");
        mockEndpoint.assertIsSatisfied();
    }

}