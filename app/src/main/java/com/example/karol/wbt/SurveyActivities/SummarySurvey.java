package com.example.karol.wbt.SurveyActivities;
import android.os.Bundle;
import android.widget.TextView;
import com.example.karol.wbt.R;
import com.example.karol.wbt.UtilitiesPackage.MySurveyPageActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import com.example.karol.wbt.UtilitiesPackage.LearnGesture;

public class SummarySurvey extends MySurveyPageActivity {

    HashMap<String,String> parameters;
    TextView login,password,name,last_name,phone,email,verify_way,age,height,weight,frequency,advancement_level,goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_survey);
        super.gestureObject = new GestureDetectorCompat(this, new LearnGesture(
                                                        this, new Intent(this, GoalSurvey.class),
                                                        new Intent(this, EndSurvey.class)));
        login = (TextView) findViewById(R.id.login_summary_textview);
        password = (TextView) findViewById(R.id.name_summary_textview);
        name = (TextView) findViewById(R.id.name_summary_textview);
        last_name = (TextView) findViewById(R.id.lastname_summary_textview);
        phone = (TextView) findViewById(R.id.phone_summary_textview);
        email = (TextView) findViewById(R.id.email_summary_textview);
        verify_way = (TextView) findViewById(R.id.verify_summary_textview);
        age = (TextView) findViewById(R.id.age_summary_textview);
        height = (TextView) findViewById(R.id.height_summary_textview);
        weight = (TextView) findViewById(R.id.weight_summary_textview);
        frequency = (TextView) findViewById(R.id.frequency_summary_textview);
        advancement_level = (TextView) findViewById(R.id.advancement_level_summary_textview);
        goal = (TextView) findViewById(R.id.goal_summary_textview);
        loadStatesOfTextView();
    }

    private void loadStatesOfTextView(){
        ArrayList<String> keyList = new ArrayList<>();
        // TODO: poprawic literowke jak Mikolaj wprowadzi zmiany
        Collections.addAll(keyList, "login", "password", "name", "lastname","PHONE","EMAIL",
                "verify_way","age","height","weight","frequency","advancment_level","goal");
        parameters = loadStringPreferences(keyList);
        login.setText(parameters.get("login"));
        password.setText(parameters.get("password"));
        name.setText(parameters.get("name"));
        last_name.setText(parameters.get("lastname"));
        phone.setText(parameters.get("PHONE"));
        email.setText(parameters.get("EMAIL"));
        verify_way.setText(parameters.get("verify_way"));
        age.setText(parameters.get("age"));
        height.setText(parameters.get("height"));
        weight.setText(parameters.get("weight"));
        frequency.setText(parameters.get("frequency") +" tygodniowo");
        advancement_level.setText(parameters.get("advancment_level")); // TODO: poprawic literowke jak Mikolaj wprowadzi zmiany
        goal.setText(parameters.get("goal"));
    }

}
