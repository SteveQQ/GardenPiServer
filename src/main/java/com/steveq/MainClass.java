package com.steveq;

import com.pi4j.io.gpio.*;
import com.steveq.alarms.AlarmsCreator;
import com.steveq.bt_server.BluetoothWaitThread;
import com.steveq.communication.models.Section;
import com.steveq.database.Repository;
import com.steveq.database.SectionsRepository;
import com.steveq.database.SectionsSQLiteHelper;
import com.steveq.utils.FileUtils;
import com.steveq.weather.WeatherController;

import java.util.Map;

/**
 * Created by Adam on 2017-07-18.
 */
public class MainClass {
    private static AlarmsCreator alarmsCreator;

    public static void main(String[] args) {
        alarmsCreator = new AlarmsCreator();



        alarmsCreator.registerAlarmForSection(Integer.valueOf(FileUtils.getDurationProperty()));
        Thread waitThread = new Thread(new BluetoothWaitThread());
        waitThread.start();
    }
}
