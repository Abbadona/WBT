package com.example.karol.wbt.ConnectionPackage;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.util.HashMap;
import java.util.List;

public class ClientConnection {

    //UWAGA!! Zmienna volatile jest po to aby ułatwić
    //        synchronizację procesów ( funkcji runConnectionoraz doInBackground)
    //
    //Pytamy czy udało się logowanie - czy klient faktycznie jest w bazie i czy połączenie jest ok
    private static volatile boolean isLogged=false;
    //Flaga czy udało nam się odpowienio połąćzyć z serverm
    private static volatile boolean isConnected = false;
    //Jak ja żałuje że nia ma plików nagłówkowych...cały ten syf był by uporządkowany...

    private static volatile boolean isVerified = false;

    private static volatile String messageResult = "ERROR";

    private SSLConnector sslConnector;

    private InputStream keyin;

    private String MessageType;

    private String messageToSend;

    private HashMap<String,String> parameters;
    

    //KONSTRUKTOR
    public ClientConnection(InputStream keyin, String messageType, HashMap<String,String> parameters){
        this.keyin = keyin;
        this.MessageType = messageType;
        this.parameters = parameters;
    }

    public static boolean IsLogged(){
        return isLogged;
    }
    public static boolean IsConnected(){
        return  isConnected;
    }
    public static void closeConnection() {
            isConnected = false;
            isLogged = false;
            //messageResult = "ERROR";

    }
    public String runConnection( ){

        new ConnectionTask().execute();

        while ( !IsConnected() ){
            //Czekamy aż się połączy! - czekamy aż falaga isConnected będzie zwrócona na true
        }

        return messageResult;
    }

    public String verifyClient(HashMap<String,String> parameters){

        this.MessageType = "AddDevice";
        this.parameters = parameters;
        this.isConnected = false;
        return this.runConnection();
    }
    private class ConnectionTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {

                sslConnector = SSLConnector.getInstance(keyin);

                switch(MessageType){

                    case ("LoginRequest"):
                        messageToSend = JSONMessage.jsonLogin( parameters);
                        break;
                    case ("RegisterNewClient"):
                        messageToSend = JSONMessage.jsonRegister(parameters);
                        break;
                    case ("AddDevice"):
                        messageToSend = JSONMessage.jsonAddDevice(parameters);
                        break;
                    default:
                        messageToSend = JSONMessage.jsonGetData();
                        break;
                }
                sslConnector.sendMessageToServer(messageToSend);
                JSONObject JSONanswer = sslConnector.getMessageFromServer();
                Log.d ("TAG_CONN",JSONanswer.toString());

                switch(JSONanswer.getString("message_type")){//error

                    case ("LoginRequest"):
                        Log.d("TAG_LogReq",JSONanswer.getString("message_type"));
                        isLogged = JSONanswer.getBoolean("islogged");
                        messageResult = "LoginRequest";
                        if ( !isLogged  ){
                            try {
                                if (JSONanswer.getString("text").equals("enter verify code")) {
                                    messageResult = "enter verify code";
                                }
                            }catch(Exception ex){
                                messageResult = "ERROR";
                            }
                        }

                        break;
                    case ("RegisterNewClient"):
                        isLogged = false;
                        messageResult = "RegisterNewClient";
                        break;
                    case ("AddDevice"):
                        isVerified = JSONanswer.getBoolean("added");
                        if ( isVerified ) {
                            messageResult = "AddDevice";
                            isLogged = true;
                        }
                        else {
                            messageResult = JSONanswer.getString("text");
                            isLogged = false;
                        }
                        break;
                    case("GetBasicData"):
                        messageResult = "GetBasicData";
                        break;
                    default://Mamy Error!
                        isLogged = false;
                        messageResult ="ERRORDEFAULT";
                        break;
                }

                //Zwalniamy semafor, możemy przejść dalej
                isConnected = true;

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}