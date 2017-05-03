package com.example.karol.wbt.SurveyActivities;
import com.example.karol.wbt.R;
import com.example.karol.wbt.UtilitiesPackage.LearnGesture;
import com.example.karol.wbt.UtilitiesPackage.MySurveyPageActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.view.MotionEvent;

public class AdvancementLevelSurvey extends MySurveyPageActivity{
    private RadioGroup options;
    private RadioButton optBasic;
    private RadioButton optIntermediate;
    private RadioButton optAdvanced;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advancement_level_survey);
        super.gestureObject = new GestureDetectorCompat(this, new LearnGesture(
                                                        this, new Intent(this, FrequencySurvey.class),
                                                        new Intent(this, GoalSurvey.class)));
        options = (RadioGroup)findViewById(R.id.advancement_choice);
        optBasic = (RadioButton)findViewById(R.id.opt_basic);
        optIntermediate = (RadioButton)findViewById(R.id.opt_intermediate);
        optAdvanced = (RadioButton)findViewById(R.id.opt_advanced);
        loadSavedOption();
    }

    private void loadSavedOption(){
        String savedText = loadSingleString("advancement_level");
        if(optBasic.getText().equals(savedText))
            optBasic.setChecked(true);
        else if(optIntermediate.getText().equals(savedText))
            optIntermediate.setChecked(true);
        else if(optAdvanced.getText().equals(savedText))
            optAdvanced.setChecked(true);
    }

    public boolean onTouchEvent(MotionEvent event){
        int selectedId = options.getCheckedRadioButtonId();
        if(selectedId != -1){
            RadioButton selectedButton = (RadioButton)findViewById(selectedId);
            String selectedOption = selectedButton.getText().toString();
            addSingleString("advancement_level", selectedOption);
            return super.onTouchEvent(event);
        }
        return false;
    }
}
