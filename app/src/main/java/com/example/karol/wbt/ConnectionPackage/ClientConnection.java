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

    //UWAGA!! Zmienna volatile jest po to aby ułatwić
    //        synchronizację procesów ( funkcji runConnectionoraz doInBackground)
    //
    //Flaga czy udało nam się odpowienio połąćzyć z serverm
    private static volatile boolean isConnected = false;
    //Jak ja żałuje że nia ma plików nagłówkowych...cały ten syf był by uporządkowany...

    private static volatile String messageResult = "ERROR";

    private SSLConnector sslConnector;

    private InputStream keyin;

    private HashMap<String,String> parameters;
    

    //KONSTRUKTOR
    public ClientConnection(Context context, String messageType, HashMap<String,String> parameters){
        this.keyin = context.getResources().openRawResource(R.raw.testkeysore);
        this.parameters = parameters;
        this.parameters.put("message_type",messageType);
    }
    public ClientConnection(Context context,String messageType, String key, String value){
        this.keyin = context.getResources().openRawResource(R.raw.testkeysore);
        if ( this.parameters == null ) parameters = new HashMap<>();
        this.parameters.put(key,value);
        this.parameters.put("message_type",messageType);
    }
    public ClientConnection(Context context, String messageType){
        this.keyin = context.getResources().openRawResource(R.raw.testkeysore);
        if ( this.parameters == null ) parameters = new HashMap<>();
        this.parameters.put("message_type",messageType);
    }
    public static boolean IsConnected(){
        return  isConnected;
    }

    public String runConnection( ){

        new ConnectionTask().execute();

        while ( !IsConnected() ){
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