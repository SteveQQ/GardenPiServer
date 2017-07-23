package com.steveq;

import com.pi4j.io.gpio.*;
import com.steveq.bt_server.BluetoothWaitThread;

/**
 * Created by Adam on 2017-07-18.
 */
public class MainClass {
    public static void main(String[] args) {
        Thread waitThread = new Thread(new BluetoothWaitThread());
        waitThread.start();
//        System.out.println("<--Pi4J--> GPIO Control Example ... started.");
//
//        // create gpio controller
//        final GpioController gpio = GpioFactory.getInstance();
//
//        // provision gpio pin #01 as an output pin and turn on
//        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_15, "MyLED", PinState.HIGH);
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        pin.setState(PinState.LOW);
    }
}
