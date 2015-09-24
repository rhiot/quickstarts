package io.rhiot.quickstarts.cloudlets.mqtt;

import org.apache.camel.component.mock.MockEndpoint;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.rhiot.steroids.camel.CamelBootInitializer.camelContext;

public class MqttCloudletTest {

    // Fixtures

    static MqttCloudlet mqttCloudlet = new MqttCloudlet();

    @BeforeClass
    public static void beforeClass() {
        mqttCloudlet.start();
    }

    @AfterClass
    public static void afterClass() {
        mqttCloudlet.stop();
    }

    // Tests

    @Test
    public void shouldReadMessageFromVertxMqttBridge() throws InterruptedException {
        MockEndpoint mockEndpoint = camelContext().getEndpoint("mock:consumedFromMqttBroker", MockEndpoint.class);
        mockEndpoint.expectedMinimumMessageCount(1);
        mockEndpoint.assertIsSatisfied();
    }

    @Test
    public void shouldReadMessageFromJmsMqttBridge() throws InterruptedException {
        MockEndpoint mockEndpoint = camelContext().getEndpoint("mock:mqttJmsBridgeTest", MockEndpoint.class);
        mockEndpoint.expectedMinimumMessageCount(1);
        mockEndpoint.assertIsSatisfied();
    }

}