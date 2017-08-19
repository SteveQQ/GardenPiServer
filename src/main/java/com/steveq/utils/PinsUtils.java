package com.steveq.utils;

import com.pi4j.gpio.extension.mcp.MCP3008Pin;
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

    public enum Input{
        POT1,
        POT2,
        MOIS1,
        MOIS2,
        RAIN
    }

    public static final Map<Output, Pin> outputPinsMapping;
    public static final Map<Input, Pin> inputPinsMapping;

    static{
        Map<Output, Pin> temp = new HashMap<>();
        temp.put(Output.SECTION1, RaspiPin.GPIO_15);
        temp.put(Output.SECTION2, RaspiPin.GPIO_16);
        outputPinsMapping = Collections.unmodifiableMap(temp);

        Map<Input, Pin> temp2 = new HashMap<>();
        temp2.put(Input.POT1, MCP3008Pin.CH2);
        temp2.put(Input.POT2, MCP3008Pin.CH3);
        temp2.put(Input.MOIS1, MCP3008Pin.CH4);
        temp2.put(Input.MOIS2, MCP3008Pin.CH5);
        temp2.put(Input.RAIN, MCP3008Pin.CH6);
        inputPinsMapping = Collections.unmodifiableMap(temp2);
    }

    public static boolean isPinProvisioned(Pin pin){
        final GpioController gpio = GpioFactory.getInstance();
        return gpio.getProvisionedPin(pin) != null;
    }
}
