package com.example.karol.wbt.ConnectionPackage;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.example.karol.wbt.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.util.HashMap;
import java.util.List;

public class ClientConnection {

    private static volatile boolean isConnected = false;

    private static volatile String messageResult = "ERROR";

    private SSLConnector sslConnector;

    private InputStream keyin;

    private HashMap<String,String> parameters;
    

    //KONSTRUKTOR
    public ClientConnection(Context context, String messageType, HashMap<String,String> parameters){
        isConnected = false;
        this.keyin = context.getResources().openRawResource(R.raw.testkeysore);
        this.parameters = parameters;
        this.parameters.put("message_type",messageType);
    }
    public ClientConnection(Context context,String messageType, String key, String value){
        isConnected = false;
        this.keyin = context.getResources().openRawResource(R.raw.testkeysore);
        if ( this.parameters == null ) parameters = new HashMap<>();
        this.parameters.put(key,value);
        this.parameters.put("message_type",messageType);
    }
    public ClientConnection(Context context, String messageType){
        isConnected = false;
        this.keyin = context.getResources().openRawResource(R.raw.testkeysore);
        if ( this.parameters == null ) parameters = new HashMap<>();
        this.parameters.put("message_type",messageType);
    }
    public String runConnection( ){

        new ConnectionTask().execute();

        while ( !this.isConnected ){
            //Czekamy aż się połączy! - czekamy aż falaga isConnected będzie zwrócona na true
        }
        return messageResult;
    }

    public String verifyClient(HashMap<String,String> parameters){

        this.parameters = parameters;
        this.parameters.put("message_type","AddDevice");
        this.isConnected = false;
        return this.runConnection();
    }

    private class ConnectionTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                sslConnector = SSLConnector.getInstance(keyin);
                sslConnector.sendMessageToServer(new JSONObject(parameters).toString());
                messageResult = sslConnector.getMessageFromServer();
                isConnected = true;
            }  catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}