package com.example.karol.wbt.SurveyActivities;
import com.example.karol.wbt.R;
import com.example.karol.wbt.UtilitiesPackage.LearnGesture;
import com.example.karol.wbt.UtilitiesPackage.MySurveyPageActivity;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.view.MotionEvent;

public class GoalSurvey extends MySurveyPageActivity{
    private RadioGroup options;
    private RadioButton optStrength;
    private RadioButton optStamina;
    private RadioButton optReduction;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_survey);
        super.gestureObject = new GestureDetectorCompat(this, new LearnGesture(
                                                        this, new Intent(this, AdvancementLevelSurvey.class),
                                                        new Intent(this, SummarySurvey.class)));
        options = (RadioGroup)findViewById(R.id.advancement_choice);
        optStrength = (RadioButton)findViewById(R.id.opt_strength);
        optStamina = (RadioButton)findViewById(R.id.opt_stamina);
        optReduction = (RadioButton)findViewById(R.id.opt_redutcion);
        loadSavedOption();
    }

    private void loadSavedOption(){
        String savedText = loadSingleString("goal");
        if(optStrength.getText().equals(savedText))
            optStrength.setChecked(true);
        else if(optStamina.getText().equals(savedText))
            optStamina.setChecked(true);
        else if(optReduction.getText().equals(savedText))
            optReduction.setChecked(true);
    }

    public boolean onTouchEvent(MotionEvent event){
        return addChoice(options.getCheckedRadioButtonId(), "goal") && super.onTouchEvent(event);
    }
}
