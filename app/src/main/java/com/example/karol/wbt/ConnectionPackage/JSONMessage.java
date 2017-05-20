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

    static String jsonLogin( HashMap<String,String> parameters ){

        Map<String,String> data = new LinkedHashMap<>();
        data.put("message_type", "LoginRequest");
        data.put("login", parameters.get("login"));
        data.put("password", parameters.get("password"));
        data.put("device_id",parameters.get("device_id"));
        return new JSONObject(data).toString();

    }

    static String jsonRegister( HashMap<String,String> parameters  ){

        Map<String,String> data = new LinkedHashMap<>();
        data.put("message_type", "RegisterNewClient");
        data.put("login", parameters.get("login"));
        data.put("password", parameters.get("password"));
        data.put("lastname", parameters.get("lastname"));
        data.put("name", parameters.get("name"));
        data.put("PHONE",parameters.get("PHONE"));
        data.put("EMAIL",parameters.get("EMAIL"));
        data.put("verify_way",parameters.get("verify_way"));
        return new JSONObject(data).toString();

    }
    static String jsonGetData(){

        Map<String,String> data = new LinkedHashMap<>();
        data.put("message_type", "getData");
        return new JSONObject(data).toString();

    }
    static String jsonAddDevice( HashMap<String,String> parameters  ){

        Map<String,String> data = new LinkedHashMap<>();
        data.put("message_type","AddDevice");
        data.put("login", parameters.get("login"));
        data.put("password", parameters.get("password"));
        data.put("device_id", parameters.get("device_id"));
        data.put("verify_code",parameters.get("verify_code"));
        return new JSONObject(data).toString();

    }
}

