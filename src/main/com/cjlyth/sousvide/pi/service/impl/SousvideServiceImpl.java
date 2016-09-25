package com.cjlyth.sousvide.pi.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cjlyth.sousvide.pi.entity.Configuration;
import com.cjlyth.sousvide.pi.service.SousvideService;
import com.cjlyth.sousvide.pi.util.HttpClient;
import com.cjlyth.sousvide.pi.util.MCP3008Gpio;
import com.fasterxml.jackson.databind.ObjectMapper;

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
			if (result == null || StringUtils.isEmpty(result)) {
				logger.error("{} ERROR!!! Could not retrieve the configuration from endpoint: {}", METHOD_NAME, serviceUrl + "/" + endpointConfiguration);	
			}
			logger.info("{} The result was: {}", METHOD_NAME, result);
			ObjectMapper mapper = new ObjectMapper();

			//JSON from URL to Object
			Configuration configuration = mapper.readValue(result, Configuration.class);
			
			logger.info("{} Configuration obtained successfully: {}", METHOD_NAME, configuration.toString());
			
		} catch (IOException e) {
			logger.error(METHOD_NAME + " throw exception: " + e.getMessage());
			e.printStackTrace();
		}
		
		//Read temperature
		MCP3008Gpio temperatureReader = new MCP3008Gpio(); 
		
		try {
			temperatureReader.execute();
		} catch (IOException | InterruptedException e) {
			logger.error("{} throw exception: " + e.getMessage());
			e.printStackTrace();
		}
		
		//Write temperature
		
		//Control heater
		
	}

}
