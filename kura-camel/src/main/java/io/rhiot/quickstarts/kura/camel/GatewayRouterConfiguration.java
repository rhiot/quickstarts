package io.rhiot.quickstarts.kura.camel;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import java.util.Dictionary;
import java.util.Properties;

public class GatewayRouterConfiguration implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        // Initialize PID configuration before starting SCR service
        ServiceReference adminServiceReference = bundleContext.getServiceReference(ConfigurationAdmin.class);
        ConfigurationAdmin configurationAdmin = (ConfigurationAdmin) bundleContext.getService(adminServiceReference);
        Configuration config = configurationAdmin.createFactoryConfiguration(GatewayRouter.class.getName());
        Dictionary props = new Properties();
        config.update(props);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
    }

}
