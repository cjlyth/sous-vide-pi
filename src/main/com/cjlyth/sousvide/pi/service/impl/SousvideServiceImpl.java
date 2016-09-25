package com.cjlyth.sousvide.pi.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cjlyth.sousvide.pi.service.SousvideService;
import com.cjlyth.sousvide.pi.util.HttpClient;

@Service
public class SousvideServiceImpl implements SousvideService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	HttpClient httpClient;
	
	@Value("${service.url}")
	private String serviceUrl;
	
	@Value("${endpoint.configuration}")
	private String endpointConfiguration;
	
	private static final String EMPTY = "";
	private static final String GET = "GET";
	private static final String POST = "POST";
	
	@Override
	public void startExecution() {
		final String METHOD_NAME = getClass().getName() + ".startExecution()";
		logger.info("{} started", METHOD_NAME);
		
		//Get configuration
		try {
			String result = httpClient.doHttpRequest(serviceUrl, endpointConfiguration, EMPTY, GET);
			StringUtils.isEmpty(result);
			logger.info("{} The result was: {}", METHOD_NAME, result);
		} catch (IOException e) {
			logger.error(METHOD_NAME + " throw exception: " + e.getMessage());
			e.printStackTrace();
		}
		
		//Read temperature
		
		//Write temperature
		
		//Control heater
		
	}

}
