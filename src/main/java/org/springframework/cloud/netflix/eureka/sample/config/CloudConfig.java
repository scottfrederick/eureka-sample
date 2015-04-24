package org.springframework.cloud.netflix.eureka.sample.config;

import com.netflix.discovery.EurekaClientConfig;
import org.springframework.cloud.pivotal.config.java.CloudConnectorsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cloud")
public class CloudConfig extends CloudConnectorsConfig {
	@Bean
	public EurekaClientConfig eurekaClientConfig() {
		return connectionFactory().eurekaClientConfig();
	}
}
