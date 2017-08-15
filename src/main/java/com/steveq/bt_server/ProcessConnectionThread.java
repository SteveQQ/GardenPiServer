package com.steveq.bt_server;

import com.steveq.communication.JsonProcessor;
import com.steveq.communication.models.FromClientRequest;
import com.steveq.communication.models.ToClientResponse;
import com.steveq.controllers.*;

import javax.microedition.io.StreamConnection;
import java.io.*;

/**
 * Created by Adam on 2017-07-18.
 */
public class ProcessConnectionThread implements Runnable{
    private StreamConnection streamConnection;
    public Boolean isConnected = false;
    public Boolean isRunning = false;
    private FromClientRequest request;
    private Controller processController;
    private OutputStream outputStream;

    public ProcessConnectionThread(StreamConnection connection){
        streamConnection = connection;
    }

    @Override
    public void run() {
        try{
            System.out.println("waiting for input");
            isConnected = true;
            InputStream inputStream = streamConnection.openInputStream();
            outputStream = streamConnection.openDataOutputStream();
            byte[] buffer = new byte[8000];

            System.out.println("waiting ... ");
            System.out.println("Input Stream : " + inputStream);
            int counter = 0;
            while(true){
                isRunning = true;
                inputStream.read(buffer);
                System.out.println("COMMAND STRING : " + new String(buffer));

                processCommand(buffer);
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    private void processCommand(byte[] data) throws IOException {
        String payload = new String(data);
        System.out.println("RECEIVED REQUEST : " + payload);
        request = JsonProcessor.getInstance().parseClientRequest(payload);

        JsonProcessor.Method method = JsonProcessor.Method.valueOf(request.getMethod());

        System.out.println("METHOD : " + method);
        switch(method){
            case SCAN: {
                processController = new ScanMethodController();
                break;
            }
            case UPLOAD: {
                processController = new UploadSectionsDataController();
                break;
            }
            case DOWNLOAD : {
                processController = new DownloadSectionsDataController();
                break;
            }
            case WEATHER : {
                processController = new WeatherMethodController();
                break;
            }
            default: {
                break;
            }
        }

        ToClientResponse response = processController.processRequest(request);
        String responseString = JsonProcessor.getInstance().getResponseString(response);
        System.out.println("RESPONSE STRING : " + responseString);
        outputStream.write(responseString.getBytes());
    }
}
