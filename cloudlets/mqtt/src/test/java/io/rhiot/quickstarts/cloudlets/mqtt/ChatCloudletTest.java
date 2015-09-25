package io.rhiot.quickstarts.cloudlets.mqtt;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.commons.io.IOUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static io.rhiot.quickstarts.cloudlets.mqtt.ChatCloudlet.chat;
import static io.rhiot.steroids.activemq.EmbeddedActiveMqBrokerBootInitializer.mqtt;
import static io.rhiot.steroids.camel.CamelBootInitializer.camelContext;

public class ChatCloudletTest extends Assert {

    // Fixtures

    static ChatCloudlet chatCloudlet = new ChatCloudlet();

    @BeforeClass
    public static void beforeClass() {
        chatCloudlet.start();
    }

    @AfterClass
    public static void afterClass() {
        chatCloudlet.stop();
    }

    // Tests

    @Test
    public void shouldReadChatUsingREST() throws InterruptedException, IOException {
        // Given
        chat.clear();
        camelContext().createProducerTemplate().sendBody(mqtt("chat"), "Hello I'm the IoT device!");
        camelContext().createProducerTemplate().sendBody(mqtt("chat"), "Just wanted to say hello!");

        // When
        String chat = IOUtils.toString(new URL("http://localhost:8181/chat"));

        // Then
        assertEquals("Hello I'm the IoT device!\nJust wanted to say hello!", chat);
    }

    @Test
    public void shouldReadChatUsingMqtt() throws Exception {
        // Given
        chat.clear();
        camelContext().addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(mqtt("chat-updates")).to("mock:chat");
            }
        });

        // When
        MockEndpoint mockEndpoint = camelContext().getEndpoint("mock:chat", MockEndpoint.class);
        mockEndpoint.expectedBodiesReceived("Hello I'm the IoT device!", "Hello I'm the IoT device!\nJust wanted to say hello!");
        camelContext().createProducerTemplate().sendBody(mqtt("chat"), "Hello I'm the IoT device!");
        camelContext().createProducerTemplate().sendBody(mqtt("chat"), "Just wanted to say hello!");

        // Then
        mockEndpoint.assertIsSatisfied();
    }

}