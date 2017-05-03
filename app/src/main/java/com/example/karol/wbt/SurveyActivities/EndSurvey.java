package com.example.karol.wbt.SurveyActivities;
import android.os.Bundle;
import com.example.karol.wbt.R;
import com.example.karol.wbt.UtilitiesPackage.LearnGesture;
import com.example.karol.wbt.UtilitiesPackage.MySurveyPageActivity;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;

public class EndSurvey extends MySurveyPageActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_survey);
        super.gestureObject = new GestureDetectorCompat(this, new LearnGesture(
                                                        this, new Intent(this, SummarySurvey.class),
                                                        new Intent(this, EndSurvey.class)));
    }
}
