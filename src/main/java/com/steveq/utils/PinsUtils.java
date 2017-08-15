package com.steveq.utils;

import com.pi4j.io.gpio.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adam on 2017-08-09.
 */
public class PinsUtils {
    public enum Output{
        SECTION1,
        SECTION2
    }

    public static final Map<Output, Pin> pinsMapping;

    static{
        Map<Output, Pin> temp = new HashMap<>();
        temp.put(Output.SECTION1, RaspiPin.GPIO_15);
        temp.put(Output.SECTION2, RaspiPin.GPIO_16);
        pinsMapping = Collections.unmodifiableMap(temp);
    }

    public static boolean isPinProvisioned(Pin pin){
        final GpioController gpio = GpioFactory.getInstance();
        return gpio.getProvisionedPin(pin) != null;
    }
}
