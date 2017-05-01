package com.example.karol.wbt.SurveyActivities;
import com.example.karol.wbt.R;
import com.example.karol.wbt.SignUpActivity;
import com.example.karol.wbt.UtilitiesPackage.LearnGesture;
import com.example.karol.wbt.UtilitiesPackage.MySurveyPageActivity;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FrequencySurvey extends MySurveyPageActivity{
    private int selectedId = -1;
    private RadioButton optOnceTwice;
    private RadioButton optThreeFour;
    private RadioButton optFiveSix;
    private RadioButton optSevenMore;
    private RadioGroup options;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequency_survey);
        super.gestureObject = new GestureDetectorCompat(this, new LearnGesture(
                                                        this, new Intent(this, SignUpActivity.class),
                                                        new Intent(this, UserParametersSurvey.class)));
        optOnceTwice = (RadioButton)findViewById(R.id.opt_1_2);
        optThreeFour = (RadioButton)findViewById(R.id.opt_3_4);
        optFiveSix = (RadioButton)findViewById(R.id.opt_5_6);
        optSevenMore = (RadioButton)findViewById(R.id.opt_7_more);
        options = (RadioGroup)findViewById(R.id.frequency_choice);
        loadSavedOption();
    }

    private void loadSavedOption(){
        String savedText = loadSingleString("frequency");
        if(optOnceTwice.getText().equals(savedText))
            optOnceTwice.setChecked(true);
        else if(optThreeFour.getText().equals(savedText))
            optThreeFour.setChecked(true);
        else if(optFiveSix.getText().equals(savedText))
            optFiveSix.setChecked(true);
        else if(optSevenMore.getText().equals(savedText))
            optSevenMore.setChecked(true);
    }

    public boolean onTouchEvent(MotionEvent event){
        selectedId = options.getCheckedRadioButtonId();
        if(selectedId != -1){
            RadioButton selectedButton = (RadioButton)findViewById(selectedId);
            String selectedOption = selectedButton.getText().toString();
            addSingleString("frequency", selectedOption);
            return super.onTouchEvent(event);
        }
        return false;
    }
}
