package com.example.karol.wbt.ConnectionPackage;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JSONMessage {

    static String jsonCreator( HashMap<String,String> parameters, String message_type){
        //  Tak naprawdę to wystarczy, reszta to przepisy (tak jak przepis kucharski na json'a
        //  bo wystarczy zamiast tworzyć Map<String,String>data można odrazu załadować
        //  HashMap parameters
        parameters.put("message_type",message_type);
        return new JSONObject(parameters).toString();
    }
    static String jsonExerciseReplacement(HashMap<String,String> parameters){
        parameters.put("message_type","ExerciseReplacement");
        return new JSONObject(parameters).toString();
    }
    static String jsonLogin( HashMap<String,String> parameters ){

        parameters.put("message_type", "LoginRequest");
        return new JSONObject(parameters).toString();
    }
    static String jsonShowExercise (HashMap<String,String> parameters){
        parameters.put("message_type", "ShowExercise");
        return new JSONObject(parameters).toString();
    }
    static String jsonRegister( HashMap<String,String> parameters  ){

        parameters.put("message_type", "RegisterNewClient");
        return new JSONObject(parameters).toString();

    }
    static String jsonGetData(){

        Map<String,String> data = new LinkedHashMap<>();
        data.put("message_type", "getData");
        return new JSONObject(data).toString();

    }
    static String jsonAddDevice( HashMap<String,String> parameters  ){
        parameters.put("message_type","AddDevice");
        return new JSONObject(parameters).toString();

    }
    static String jsonUpdateClientData( HashMap<String,String> parameters){
        parameters.put("message_type","UpdateClientData");
        return new JSONObject(parameters).toString();
    }
    static String jsonProposition( HashMap<String,String> parameters){
        parameters.put("message_type","TrainingProposition");
        return new JSONObject(parameters).toString();
    }
    static String jsonGetTraining( HashMap<String,String> parameters){
        parameters.put("message_type","GetTraining").toString();
        return new JSONObject(parameters).toString();
    }
}

