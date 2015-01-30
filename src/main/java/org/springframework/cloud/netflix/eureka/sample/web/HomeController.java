package org.springframework.cloud.netflix.eureka.sample.web;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.discovery.EurekaClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private EurekaClientConfig clientConfig;

	@Autowired
	private EurekaInstanceConfigBean instanceConfigBean;

	@Autowired(required = false)
	private HealthCheckHandler healthCheckHandler;

	@RequestMapping("/")
	public String home() {
		return "Hello world";
	}

	@RequestMapping("/down")
	public String takeDown() {
		return changeAppStatus(InstanceInfo.InstanceStatus.DOWN);
	}

	@RequestMapping("/out")
	public String takeOut() {
		return changeAppStatus(InstanceInfo.InstanceStatus.OUT_OF_SERVICE);
	}

	@RequestMapping("/up")
	public String bringUp() {
		return changeAppStatus(InstanceInfo.InstanceStatus.UP);
	}

	@RequestMapping("/register")
	public String register() {
		DummyEurekaInstanceConfig instanceConfig = new DummyEurekaInstanceConfig("test-app");

		logger.info("Registering application {} with eureka with status {}",
				instanceConfig.getAppname(), instanceConfig.getInitialStatus());

		DiscoveryManager.getInstance().initComponent(instanceConfig, clientConfig);

		ApplicationInfoManager.getInstance().setInstanceStatus(instanceConfig.getInitialStatus());

		if (healthCheckHandler != null) {
			DiscoveryManager.getInstance().getDiscoveryClient().registerHealthCheck(healthCheckHandler);
		}

		return "Registered application " + instanceConfig.getAppname() +
				" with status " + instanceConfig.getInitialStatus();
	}

	private String changeAppStatus(InstanceInfo.InstanceStatus status) {
		logger.info("Setting status of application {} to {}", instanceConfigBean.getAppname(), status);
		ApplicationInfoManager.getInstance().setInstanceStatus(status);

		return "Status of " + instanceConfigBean.getAppname() + " changed to " + status;
	}

	class DummyEurekaInstanceConfig extends EurekaInstanceConfigBean {

		private String appName;

		public DummyEurekaInstanceConfig(String appName) {
			this.appName = appName;
		}

		@Override
		public String getAppname() {
			return appName;
		}
	}
}
