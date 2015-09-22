package io.rhiot.steroids.bootstrap;

import org.apache.activemq.broker.BrokerService;

public class EmbeddedActiveMQBrokerInitializer implements Initializer {

    private final BrokerService brokerService;

    public EmbeddedActiveMQBrokerInitializer() {
        try {
            brokerService = new BrokerService();
            brokerService.addConnector("mqtt://0.0.0.0:1883");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start() {
        try {
            brokerService.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int order() {
        return 1000;
    }

}