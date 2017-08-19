package com.example.karol.wbt.SurveyActivities;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.karol.wbt.ConnectionPackage.ClientConnection;
import com.example.karol.wbt.MenuActivity;
import com.example.karol.wbt.R;
import com.example.karol.wbt.SignInUpActivity;
import com.example.karol.wbt.SignUpActivity;
import com.example.karol.wbt.UtilitiesPackage.LearnGesture;
import com.example.karol.wbt.UtilitiesPackage.MySurveyPageActivity;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class EndSurvey extends MySurveyPageActivity implements View.OnClickListener {

    Button endButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_survey);
        endButton = (Button) findViewById(R.id.end_button);
        endButton.setOnClickListener(this);
    }

    private Intent sendPreferencesToServer(){

        ArrayList<String> keyList = new ArrayList<>();
        /*Collections.addAll(keyList, "login", "password", "name", "last_name","phone","email",
                "verify_way","age","height","weight","frequency","advancement_level","goal");*/
        Collections.addAll(keyList,"login", "password", "name", "lastname","PHONE","EMAIL", "verify_way");
        HashMap<String,String> parameters = loadStringPreferences(keyList);
        ClientConnection clientConnection = new ClientConnection(this,"RegisterNewClient",parameters);
        String result = clientConnection.runConnection();

        if ( result == "RegisterNewClient" ){
            Toast.makeText(this,getString(R.string.registration_positive),Toast.LENGTH_LONG);
            this.clearPreferences(keyList);
            preferences = getSharedPreferences("myPreferences", Activity.MODE_PRIVATE);
            preferencesEditor = preferences.edit();
            preferencesEditor.putBoolean("islogged",true);
            preferencesEditor.apply();
            preferencesEditor.commit();
            Toast.makeText(this,getString(R.string.registration_positive),Toast.LENGTH_LONG);
            return new Intent(this, MenuActivity.class);
        }else
            Toast.makeText(this,getString(R.string.registration_negative),Toast.LENGTH_LONG); {
            return new Intent(this, SignInUpActivity.class);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == endButton.getId()){
            Intent intent = sendPreferencesToServer();
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return false;
    }
}
