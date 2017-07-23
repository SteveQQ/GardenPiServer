package com.steveq.bt_server;

import com.pi4j.io.gpio.*;

import javax.microedition.io.StreamConnection;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 2017-07-18.
 */
public class ProcessConnectionThread implements Runnable{
    private StreamConnection streamConnection;

    public ProcessConnectionThread(StreamConnection connection){
        streamConnection = connection;
    }

    @Override
    public void run() {
        try{
            System.out.println("waiting for input");
            InputStream inputStream = streamConnection.openInputStream();
            byte[] buffer = new byte[1024];

            System.out.println("waiting ... ");
            System.out.println("Input Stream : " + inputStream);
            int counter = 0;
            while(true){
                inputStream.read(buffer);
                System.out.println("COMMAND STRING : " + new String(buffer));
                processCommand(buffer);
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    private void processCommand(byte[] data){
        String key = new String(data);
        System.out.println("KEY : " + key);
        if("hello world".equals(key.trim())){
            System.out.println("<--Pi4J--> GPIO Control Example ... started.");

            // create gpio controller
            final GpioController gpio = GpioFactory.getInstance();

            // provision gpio pin #01 as an output pin and turn on
            final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_15, "MyLED", PinState.HIGH);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            pin.setState(PinState.LOW);
        }
    }
}
