package com.steveq.alarms;

import com.pi4j.io.gpio.*;
import com.steveq.communication.models.Section;
import com.steveq.utils.FileUtils;
import com.steveq.utils.PinsUtils;

import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by Adam on 2017-08-09.
 */
public class SectionTurnOnTask extends TimerTask {
    private Pin pin;
    private Section section;
    private int duration;

    public SectionTurnOnTask(int duration){
        this.duration = duration;
    }

    public SectionTurnOnTask(Section section){
        switch(section.getNumber()){
            case 1:
                pin = PinsUtils.pinsMapping.get(PinsUtils.Output.SECTION1);
                break;
            case 2:
                pin = PinsUtils.pinsMapping.get(PinsUtils.Output.SECTION2);
                break;
            default:
                break;
        }
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    @Override
    public void run() {
        switch(section.getNumber()){
            case 1:
                pin = PinsUtils.pinsMapping.get(PinsUtils.Output.SECTION1);
                break;
            case 2:
                pin = PinsUtils.pinsMapping.get(PinsUtils.Output.SECTION2);
                break;
            default:
                break;
        }
        System.out.println("RUN TASK!!!");
        System.out.println("SECTION DAYS : " + section.getDays());
        if(section.getDays().size()==0){
            final GpioController gpio = GpioFactory.getInstance();
            final GpioPinDigitalOutput section1Pin = gpio.provisionDigitalOutputPin(pin, "section", PinState.HIGH);
            section1Pin.setShutdownOptions(true, PinState.LOW);
            try {
                Thread.sleep(duration * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            section1Pin.low();
            gpio.unprovisionPin(section1Pin);
        } else if (currentDayMatch(section.getDays())){
            final GpioController gpio = GpioFactory.getInstance();
            final GpioPinDigitalOutput section1Pin = gpio.provisionDigitalOutputPin(pin, "section", PinState.HIGH);
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            section1Pin.low();
            gpio.unprovisionPin(section1Pin);
        }
    }

    private boolean currentDayMatch(List<String> daysRepeat) {
        Calendar calendar = Calendar.getInstance();
        int dayNum = calendar.get(Calendar.DAY_OF_WEEK);
        for(String day : daysRepeat){
            if(getDayNum(day) == dayNum){
                return true;
            }
        }
        return false;
    }


    private int getDayNum(String dayString){
        List<String> configuredDatys = FileUtils.getDaysProperties();
        int counter = 1;
        for(String day : configuredDatys){
            if(dayString.equals(day)){
                break;
            }
            counter++;
        }
        counter++;
        if(counter==8) counter = 1;
        return counter;
    }
}
