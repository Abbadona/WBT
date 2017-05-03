package com.example.karol.wbt.SurveyActivities;
import com.example.karol.wbt.R;
import com.example.karol.wbt.UtilitiesPackage.LearnGesture;
import com.example.karol.wbt.UtilitiesPackage.MySurveyPageActivity;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import android.view.MotionEvent;

public class UserParametersSurvey extends MySurveyPageActivity{
    private EditText age;
    private EditText height;
    private EditText weight;
    private HashMap<String,String> parameters;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_parameters_survey);
        super.gestureObject = new GestureDetectorCompat(this, new LearnGesture(
                                                        this, new Intent(this, ContactSurvey.class),
                                                        new Intent(this, FrequencySurvey.class)));
        age = (EditText)findViewById(R.id.age_text);
        height = (EditText)findViewById(R.id.height_text);
        weight = (EditText)findViewById(R.id.weight_text);
        loadSavedData();
    }

    private void loadSavedData(){
        ArrayList<String> data = new ArrayList<>();
        Collections.addAll(data, "age", "height", "weight");
        parameters = loadStringPreferences(data);
        age.setText(parameters.get("age"));
        height.setText(parameters.get("height"));
        weight.setText(parameters.get("weight"));
    }

    public boolean addParameter(EditText field, String type){
        String text = field.getText().toString();
        if(text.isEmpty()){
            field.setError("Wypełnij pole");
            return false;
        }
        else{
            this.parameters.put(type, text);
            return true;
        }
    }

    public boolean onTouchEvent(MotionEvent event){
        addParameter(age, "age");
        addParameter(height, "height");
        addParameter(weight, "weight");

        if(isEditTextFilled(age) && isEditTextFilled(height) && isEditTextFilled(weight)){
            addStringPreferences(parameters);
            return super.onTouchEvent(event);
        }
        return false;
    }
}
