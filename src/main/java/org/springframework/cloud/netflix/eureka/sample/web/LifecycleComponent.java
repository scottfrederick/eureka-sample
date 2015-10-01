package org.springframework.cloud.netflix.eureka.sample.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Slf4j
public class LifecycleComponent {
	@Value("${shutdown.delay}")
	private int shutdownDelaySeconds;

	@PostConstruct
	public void startup() {
		log.info("Starting");
	}

	@PreDestroy
	public void shutdown() throws InterruptedException {
		log.info("Initiating graceful shutdown");

		int countdown = shutdownDelaySeconds;

		while (countdown > 0) {
			log.info("Shutting down in " + countdown + " seconds");

			Thread.sleep(1000L);

			countdown--;
		}

		log.info("Shutting down now");
	}
}