package com.steveq.controllers;

import com.pi4j.gpio.extension.base.AdcGpioProvider;
import com.pi4j.gpio.extension.mcp.MCP3008GpioProvider;
import com.pi4j.gpio.extension.mcp.MCP3008Pin;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinAnalogValueChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerAnalog;
import com.pi4j.io.gpio.exception.GpioPinExistsException;
import com.pi4j.io.spi.SpiChannel;
import com.steveq.communication.JsonProcessor;
import com.steveq.communication.models.FromClientRequest;
import com.steveq.communication.models.Section;
import com.steveq.communication.models.ToClientResponse;
import com.steveq.utils.PinsUtils;
import com.steveq.weather.model.WeatherOutputModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Adam on 2017-08-03.
 */
public class ScanMethodController implements Controller {

    @Override
    public ToClientResponse processRequest(FromClientRequest request) {

        ToClientResponse response = new ToClientResponse();
        Set<Integer> sectionsNums = new HashSet<>();

        try {
            System.out.println("<--Pi4J--> GPIO Control Example ... started.");

            // create gpio controller
            final GpioController gpio = GpioFactory.getInstance();

            System.out.println("Get GPIO");
            final AdcGpioProvider provider;
            provider = new MCP3008GpioProvider(SpiChannel.CS0);

            System.out.println("GET PROVIDER");
            final GpioPinAnalogInput inputs[] = {
                    gpio.provisionAnalogInputPin(provider, MCP3008Pin.CH0, "1"),
                    gpio.provisionAnalogInputPin(provider, MCP3008Pin.CH1, "2")
            };

            provider.setEventThreshold(25, inputs);

            provider.setMonitorInterval(100);

            GpioPinListenerAnalog listenerAnalog = new GpioPinListenerAnalog() {
                @Override
                public void handleGpioPinAnalogValueChangeEvent(GpioPinAnalogValueChangeEvent event) {
                    if(event.getValue() < 1020){
                        System.out.println("PIN CONNECTED : " + event.getPin().getName());
                        sectionsNums.add(Integer.valueOf(event.getPin().getName()));
                    } else{
                        System.out.println("PIN NOT CONNECTED : " + event.getPin().getName());
                    }
                    double value = event.getValue();
                    System.out.println("<CHANGED VALUE> [" + event.getPin().getName() + "] : RAW VALUE = " + value);
                }
            };

            gpio.addListener(listenerAnalog, inputs);

            try{
                final GpioPinDigitalOutput section2Pin = gpio.provisionDigitalOutputPin(PinsUtils.outputPinsMapping.get(PinsUtils.Output.SECTION2), "2", PinState.HIGH);
                final GpioPinDigitalOutput section1Pin = gpio.provisionDigitalOutputPin(PinsUtils.outputPinsMapping.get(PinsUtils.Output.SECTION1), "1", PinState.HIGH);


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                section1Pin.setState(PinState.LOW);
                section2Pin.setState(PinState.LOW);
                gpio.unprovisionPin(section1Pin);
                gpio.unprovisionPin(section2Pin);
            } catch (GpioPinExistsException gpiee){
                gpiee.printStackTrace();
                response.setMethod(JsonProcessor.Method.SCAN.toString());

                List<Section> sections = new ArrayList<>();

                Section section = new Section();
                section.setNumber(-1);
                section.setActive(false);
                section.setTimes(new ArrayList<>());
                section.setDays(new ArrayList<>());
                sections.add(section);

                response.setSections(sections);
                return response;
            }

        } catch (IOException e) {
            System.out.println(e);
        }

        response.setMethod(JsonProcessor.Method.SCAN.toString());

        List<Section> sections = new ArrayList<>();

        for(Integer i : sectionsNums){
            Section section = new Section();
            section.setNumber(i);
            section.setActive(false);
            section.setTimes(new ArrayList<>());
            section.setDays(new ArrayList<>());
            sections.add(section);
        }

        response.setSections(sections);
        response.setWeather(new WeatherOutputModel());

        return response;
    }
}
