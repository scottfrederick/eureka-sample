package org.springframework.cloud.netflix.eureka.sample.web;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private EurekaInstanceConfigBean instanceConfigBean;

	@RequestMapping("/status")
	public String takeDown(@RequestParam("status") String newStatus) {
		InstanceInfo.InstanceStatus status = InstanceInfo.InstanceStatus.valueOf(newStatus);

		logger.info("Setting status of application {} to {}", instanceConfigBean.getAppname(), status);
		ApplicationInfoManager.getInstance().setInstanceStatus(status);

		return "Status of " + instanceConfigBean.getAppname() + " changed to " + status;
	}
}
