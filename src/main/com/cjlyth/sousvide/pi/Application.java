package com.cjlyth.sousvide.pi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cjlyth.sousvide.pi.config.AppConfig;
import com.cjlyth.sousvide.pi.service.impl.SousvideServiceImpl;

public class Application {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static void main(String[] args) {
		Application application = new Application();
		application.run(args);
	}
	
	private void run(String[] args) {
		final String METHOD_NAME = getClass().getName() + ".run()";
		logger.info("============== Sous-vide-pi application started ====================");
		ApplicationContext context = null;
		try {
			context = new AnnotationConfigApplicationContext(AppConfig.class);
			SousvideServiceImpl  temperatureService = (SousvideServiceImpl) context.getBean(SousvideServiceImpl.class);
			temperatureService.startExecution();
		} catch (Exception ie) {
			logger.error("{} threw an InterruptedException: {}", METHOD_NAME, ie);
		}
	}
}
