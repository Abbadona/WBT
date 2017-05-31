package com.example.karol.wbt.UtilitiesPackage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.karol.wbt.R;

public class InfoExerciseActivity extends AppCompatActivity {

    private int counter = 0;
    private TextView textView;
    private final String PREFERENCES_NAME = "myPreferences";
    private SharedPreferences preferences;
    private int info_id[]={R.string.exc1_info,R.string.exc2_info,R.string.exc3_info,R.string.exc4_info,R.string.exc5_info,R.string.exc6_info,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_exercise);
        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        textView = (TextView) findViewById(R.id.info_exer_textView);
        counter = preferences.getInt("siteCounter",0);
        textView.setText(getString(info_id[counter]));
    }

}
