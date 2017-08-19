package com.steveq.sensors;

import com.pi4j.gpio.extension.base.AdcGpioProvider;
import com.pi4j.gpio.extension.mcp.MCP3008GpioProvider;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.spi.SpiChannel;
import com.steveq.utils.PinsUtils;

import java.io.IOException;

/**
 * Created by Adam on 2017-08-19.
 */
public class MoistureSensor implements Sensor {
    private final String NAME;
    private double data;
    private Pin pin;
    private static final String DRY = "Dry";
    private static final String MEDIUM = "Medium";
    private static final String WET = "Wet";

    public MoistureSensor(PinsUtils.Input input){
        String sensorTag = input.toString();
        pin = PinsUtils.inputPinsMapping.get(input);
        NAME = "Moisture Sensor - Section " + sensorTag.substring(sensorTag.length()-1);
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
                    gpio.provisionAnalogInputPin(provider, pin)
            };

            provider.setEventThreshold(25, inputs);

            provider.setMonitorInterval(100);

            do {
                for(GpioPinAnalogInput input : inputs){
                    data = input.getValue();
                    System.out.println("MOISTURE SENSOR : " + input.getPin().getName() + "] : RAW VALUE = " + input.getValue());
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
        if(data > 0 && data < 180){
            return DRY;
        } else if (data >= 180 && data < 360){
            return MEDIUM;
        } else if (data >= 360 && data < 530){
            return WET;
        }
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
