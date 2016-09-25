package com.cjlyth.sousvide.pi.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.gpio.extension.base.AdcGpioProvider;
import com.pi4j.gpio.extension.mcp.MCP3008GpioProvider;
import com.pi4j.gpio.extension.mcp.MCP3008Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.event.GpioPinAnalogValueChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerAnalog;
import com.pi4j.io.spi.SpiChannel;

public class MCP3008Gpio {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public void execute() throws IOException, InterruptedException {
		final String METHOD_NAME = getClass().getName() + ".execute()";
        // Create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // Create custom MCP3008 analog gpio provider
        // we must specify which chip select (CS) that that ADC chip is physically connected to.
        final AdcGpioProvider provider = new MCP3008GpioProvider(SpiChannel.CS0);

        // Provision gpio analog input pins for all channels of the MCP3008.
        // (you don't have to define them all if you only use a subset in your project)
        final GpioPinAnalogInput inputs[] = {
                gpio.provisionAnalogInputPin(provider, MCP3008Pin.CH0, "MyAnalogInput-CH0"),
                gpio.provisionAnalogInputPin(provider, MCP3008Pin.CH1, "MyAnalogInput-CH1")
        };
        
        // Define the amount that the ADC input conversion value must change before
        // a 'GpioPinAnalogValueChangeEvent' is raised.  This is used to prevent unnecessary
        // event dispatching for an analog input that may have an acceptable or expected
        // range of value drift.
        provider.setEventThreshold(100, inputs); // all inputs; alternatively you can set thresholds on each input discretely
        
        // Set the background monitoring interval timer for the underlying framework to
        // interrogate the ADC chip for input conversion values.  The acceptable monitoring
        // interval will be highly dependant on your specific project.  The lower this value
        // is set, the more CPU time will be spend collecting analog input conversion values
        // on a regular basis.  The higher this value the slower your application will get
        // analog input value change events/notifications.  Try to find a reasonable balance
        // for your project needs.
        provider.setMonitorInterval(5000); // milliseconds   
        
        // Print current analog input conversion values from each input channel
        for(GpioPinAnalogInput input : inputs){
            logger.info("{} <INITIAL VALUE> [" + input.getName() + "] : RAW VALUE = " + input.getValue(), METHOD_NAME);
        }
        
        // Create an analog pin value change listener
        GpioPinListenerAnalog listener = new GpioPinListenerAnalog()
        {
            @Override
            public void handleGpioPinAnalogValueChangeEvent(GpioPinAnalogValueChangeEvent event)
            {
                // get RAW value
                double value = event.getValue();

                // display output
                System.out.println("<CHANGED VALUE> [" + event.getPin().getName() + "] : RAW VALUE = " + value);
            }
        };
        
        // Register the gpio analog input listener for all input pins
        gpio.addListener(listener, inputs);

        // Keep this sample program running for 10 minutes
        for (int count = 0; count < 600; count++) {
            Thread.sleep(1000);
        }
	}
}
