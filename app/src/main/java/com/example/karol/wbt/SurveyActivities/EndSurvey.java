package com.example.karol.wbt.SurveyActivities;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.karol.wbt.MenuActivity;
import com.example.karol.wbt.R;
import com.example.karol.wbt.SignUpActivity;
import com.example.karol.wbt.UtilitiesPackage.LearnGesture;
import com.example.karol.wbt.UtilitiesPackage.MySurveyPageActivity;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class EndSurvey extends AppCompatActivity implements View.OnClickListener {

    Button endButton;
    protected SharedPreferences preferences;
    protected SharedPreferences.Editor preferencesEditor;
    private String PREFERENCES_NAME = "survey_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_survey);
        endButton = (Button) findViewById(R.id.end_button);
        endButton.setOnClickListener(this);
        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
    }
    private void clearPreferences(){
        preferencesEditor.clear();
        preferencesEditor.apply();
        preferencesEditor.commit();
    }
    private void sendPreferencesToServer(){
        //// TODO: 06.05.2017
        //Tu trzeba umieścić kod wysyłający
        //      dane z ankiety na serwer
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == endButton.getId()){
            sendPreferencesToServer();
            clearPreferences();
            preferences = getSharedPreferences("myPreferences", Activity.MODE_PRIVATE);
            preferencesEditor = preferences.edit();
            preferencesEditor.putBoolean("islogged",true);
            preferencesEditor.apply();
            preferencesEditor.commit();
            startActivity(new Intent(new Intent(this, MenuActivity.class)));
            finish();
        }
    }
}
