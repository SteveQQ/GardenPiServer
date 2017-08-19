package com.steveq.sensors;

import com.pi4j.gpio.extension.base.AdcGpioProvider;
import com.pi4j.gpio.extension.mcp.MCP3008GpioProvider;
import com.pi4j.gpio.extension.mcp.MCP3008Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.event.GpioPinAnalogValueChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerAnalog;
import com.pi4j.io.spi.SpiChannel;
import com.steveq.utils.PinsUtils;

import java.io.IOException;

/**
 * Created by Adam on 2017-08-19.
 */
public class RaindropSensor implements Sensor {
    private static final String NAME = "Raindrop Sensor";
    private static final String RAINING = "Raining";
    private static final String NOT_RAINING = "Not Raining";

    private double data;

    public RaindropSensor(){
        takeMeasurements();
    }

    @Override
    public void takeMeasurements() {
        try {
            System.out.println("<--Pi4J--> GPIO Control Example ... started.");

            // create gpio controller
            final GpioController gpio = GpioFactory.getInstance();

            final AdcGpioProvider provider;
            provider = new MCP3008GpioProvider(SpiChannel.CS0);

            final GpioPinAnalogInput inputs[] = {
                    gpio.provisionAnalogInputPin(provider, PinsUtils.inputPinsMapping.get(PinsUtils.Input.RAIN))
            };

            provider.setEventThreshold(25, inputs);

            provider.setMonitorInterval(100);

            do {
                for(GpioPinAnalogInput input : inputs){
                    data = input.getValue();
                    System.out.println("RAINDROP SENSOR : " + input.getPin().getName() + "] : RAW VALUE = " + input.getValue());
                }
                Thread.sleep(1000);
            } while(data <= 0);
            gpio.unprovisionPin(inputs);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double getRawData() {
        return data;
    }

    @Override
    public String getReadableData() {
        if(data >= 0 && data < 1000){
            return RAINING;
        } else if (data >= 1000 && data <1024){
            return NOT_RAINING;
        }
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
