package com.steveq;

import com.steveq.alarms.AlarmsCreator;
import com.steveq.bt_server.BluetoothWaitThread;
import com.steveq.sensors.*;
import com.steveq.utils.FileUtils;
import com.steveq.utils.PinsUtils;

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
