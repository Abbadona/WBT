package com.example.karol.wbt.TrainingPackage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.karol.wbt.R;

public class InfoExerciseActivity extends AppCompatActivity {

    private TextView descTextView,titleTextView;
    private final String PREFERENCES_NAME = "myPreferences";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_exercise);
        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        descTextView = (TextView) findViewById(R.id.info_exer_textView);
        titleTextView = (TextView) findViewById(R.id.title_exer_textView);
        descTextView.setText(preferences.getString("description",getString(R.string.connection_error)));
        titleTextView.setText(preferences.getString("title",getString(R.string.connection_error)));
    }

    @Override
    public void onBackPressed(){
        finish();
    }

}
