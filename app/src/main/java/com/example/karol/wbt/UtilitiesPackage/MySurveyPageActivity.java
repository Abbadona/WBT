package com.example.karol.wbt.UtilitiesPackage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.widget.EditText;

import com.example.karol.wbt.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import android.widget.RadioButton;

/**
 * Created by Karol on 14.03.2017.
 */

public abstract class MySurveyPageActivity extends AppCompatActivity {

    private String PREFERENCES_NAME = "survey_preferences";
    protected GestureDetectorCompat gestureObject;
    private float x1=0,x2=0;
    protected SharedPreferences preferences;
    protected SharedPreferences.Editor preferencesEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
    }
    protected void addStringPreferences(HashMap<String,String> hashMap){
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            preferencesEditor.putString((String)pair.getKey(),(String)pair.getValue());
            it.remove();
        }
        preferencesEditor.apply();
        preferencesEditor.commit();
    }
    protected HashMap<String,String> loadStringPreferences(ArrayList<String> parametersList){
        HashMap<String,String> data = new HashMap<>();
        for (String parameter:parametersList){
            data.put(parameter,preferences.getString(parameter,""));
        }
        return data;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        this.gestureObject.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                Log.d("TAG_deltaX", deltaX + "");
                if (deltaX < 0) {
                    this.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    Log.d("TAG_SLIDE", "right to left");
                } else {
                    this.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                    Log.d("TAG_SLIDE", "left to right");
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    protected void addSingleString(String key, String value){
        preferencesEditor.putString(key, value).apply();
        preferencesEditor.commit();
    }

    protected String loadSingleString(String key){
        return preferences.getString(key, "");
    }

    public boolean addChoice(int selectedId, String type){
        if(selectedId != -1){
            RadioButton selectedButton = (RadioButton)findViewById(selectedId);
            String selectedOption = selectedButton.getText().toString();
            addSingleString(type, selectedOption);
            return true;
        }
        return false;
    }

    protected boolean isEditTextFilled(EditText editText){
        if (editText.getText().toString().trim().equals("")){
            editText.setError("Wypełnij pole");
            return false;
        }
        return true;
    }

    public void cleanPreferences(){
        preferencesEditor.clear();
        preferencesEditor.commit();

    }
    @Override
    public void onBackPressed() {
        //Ta klasa ma być pusta aby nie móc się cofnąć!
    }
}
