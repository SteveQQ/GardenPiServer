package com.steveq.bt_server;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;

/**
 * Created by Adam on 2017-07-18.
 */
public class BluetoothWaitThread implements Runnable {

    public BluetoothWaitThread(){}

    @Override
    public void run() {
        waitForConnection();
    }

    private void waitForConnection(){
        LocalDevice local = null;

        StreamConnectionNotifier notifier;
        StreamConnection connection = null;

        try {
            local = LocalDevice.getLocalDevice();
            System.out.println("Local Device : " + local);
            boolean discoverable = local.setDiscoverable(DiscoveryAgent.GIAC);
            System.out.println("Discoverable : " + discoverable);

            UUID uuid = new UUID("4df91da744c64b86afce0deb1edf324e", false);
            System.out.println(uuid.toString());

            String url = "btspp://localhost:" + uuid.toString() + ";name=RemoteBluetooth";
            notifier = (StreamConnectionNotifier) Connector.open(url);
        } catch (BluetoothStateException e) {
            System.out.println("Bluetooth is not turned on.");
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while(true){
            System.out.println("waiting for connection...");
            try {
                System.out.println("connection : " + connection);
                connection = notifier.acceptAndOpen();
                System.out.println("connection : " + connection);
                System.out.println("creating thread...");
                Thread processThread = new Thread(new ProcessConnectionThread(connection));
                processThread.start();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

    }
}
