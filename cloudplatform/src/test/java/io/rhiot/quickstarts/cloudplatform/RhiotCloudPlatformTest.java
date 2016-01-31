package io.rhiot.quickstarts.cloudplatform;

import java.util.Map;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.common.truth.Truth;

import io.rhiot.cloudplatform.connector.Header;
import io.rhiot.cloudplatform.encoding.spi.PayloadEncoding;
import io.rhiot.cloudplatform.runtime.spring.test.CloudPlatformTest;
import io.rhiot.cloudplatform.service.binding.ServiceBinding;

@Configuration
public class RhiotCloudPlatformTest extends CloudPlatformTest {

	private final double TEMP_MIN = 25;
	private final double TEMP_MAX = 35;
	
	private final String DEVICE_ID = "1234";
		
	private RestTemplate rest = new RestTemplate();
	
	// to simulate temperature values
	private Random random = new Random();
	
	private static final Logger LOG = LoggerFactory.getLogger(RhiotCloudPlatformTest.class);
	
	@Test
	public void httpRestStoreTemperatureHeader() {
		
		double temperature = getTemperature(); 
		
		// HTTP REST URL format : http://localhost:8080/[service]/[operation]
		String url = String.format("http://localhost:8080/%s/store", TemperatureService.CHANNEL);
		
		// put parameters (device ID and temperature value) inside RHIOT prefixed HTTP headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("RHIOT_ARG0", DEVICE_ID);
		headers.set("RHIOT_ARG1", String.valueOf(temperature));
		
		ResponseEntity<Map> response = rest.exchange(url, HttpMethod.POST, new HttpEntity<byte[]>(headers), Map.class);
		
		boolean stored = (Boolean)response.getBody().get("payload");
		LOG.info("Temperature stored " + stored);
		
		Truth.assertThat(stored).isTrue();
	}
	
	@Test
	public void httpRestStoreTemperatureBody() {
		
		double temperature = getTemperature();
		
		// HTTP REST URL format : http://localhost:8080/[service]/[operation]/[parameter]
		// put device ID in the URL path
		String url = String.format("http://localhost:8080/%s/store/%s", TemperatureService.CHANNEL, DEVICE_ID);
		
		// encode temperature ...
		byte[] request = payloadEncoding.encode(temperature);
		// ... and put its value inside the HTTP request body
		Map response = rest.postForObject(url, request, Map.class);
		
		boolean stored = (Boolean)response.get("payload");
		LOG.info("Temperature stored " + stored);
		
		Truth.assertThat(stored).isTrue();
	}
	
	@Test
	public void httpRestTemperatureThreshold() {
		
		// put parameter device ID inside RHIOT prefixed HTTP headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("RHIOT_ARG0", DEVICE_ID);
		
		// HTTP REST URL format : http://localhost:8080/[service]/[operation]
		String url = String.format("http://localhost:8080/%s/threshold", TemperatureService.CHANNEL);
		
		ResponseEntity<Map> response = rest.exchange(url, HttpMethod.GET, new HttpEntity<byte[]>(headers), Map.class);
		
		double threshold = (Double)response.getBody().get("payload");
		LOG.info("Temperature threshold " + threshold);
		
		Truth.assertThat(threshold).isNotNull();
	}
	
	@Test
	public void iotConnectorStoreTemperature() {
		
		double temperature = getTemperature();
		
		// IoT connector channel format [service].[operation]
		String channel = String.format("%s.%s", TemperatureService.CHANNEL, "store");
		
		// put parameters (device ID and temperature value) as headers
		boolean stored = connector.fromBus(channel, boolean.class, Header.arguments(DEVICE_ID, temperature));
		LOG.info("Temperature stored " + stored);
		
		Truth.assertThat(stored).isTrue();
	}
	
	@Test
	public void iotConnectorTemperatureThreshold() {
		
		// IoT connector channel format [service].[operation]
		String channel = String.format("%s.%s", TemperatureService.CHANNEL, "threshold");
		
		double threshold = connector.fromBus(channel, double.class, Header.arguments(DEVICE_ID));
		LOG.info("Temperature threshold " + threshold);
		
		Truth.assertThat(threshold).isNotNull();
	}
	
	private double getTemperature() {
		
		// to simulate temperature reading
		return TEMP_MIN + (TEMP_MAX - TEMP_MIN) * random.nextDouble();
	}
	
	public static interface TemperatureService {
		
		static final String CHANNEL = "temperature";
		
		Boolean store(String deviceId, double temperature);
		
		double threshold(String deviceId);
	}
	
	@Component(TemperatureService.CHANNEL)
	public static class TemperatureServiceImpl implements TemperatureService {
		
		public Boolean store(String deviceId, double temperature) {
			LOG.info("Storing temperature " + temperature + " for device " +  deviceId);
			return true;
		}

		public double threshold(String deviceId) {
			LOG.info("Temperature threshold for device " + deviceId);
			return 25.0;
		}
	}
	
	@Bean
	ServiceBinding temperatureServiceBinding(PayloadEncoding payloadEncoding) {
		return new ServiceBinding(payloadEncoding, TemperatureService.CHANNEL);
	}
}
