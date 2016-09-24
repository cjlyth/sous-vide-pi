package com.cjlyth.sousvide.pi.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cjlyth.sousvide.pi.service.SousvideService;

@Service
public class SousvideServiceImpl implements SousvideService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void startExecution() {
		final String METHOD_NAME = getClass().getName() + ".startExecution()";
		logger.info(METHOD_NAME + "started");
		
		//Get configuration
		
		//Read temperature
		
		//Write temperature
		
		//Control heater
		
	}

}
