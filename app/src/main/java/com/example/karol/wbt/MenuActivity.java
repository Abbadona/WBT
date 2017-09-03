package com.example.karol.wbt;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.karol.wbt.TrainingPackage.DbOpenHelper;
import com.example.karol.wbt.TrainingPackage.IntroductionTrainingActivity;

public class MenuActivity extends AppCompatActivity {
    DbOpenHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        database = new DbOpenHelper(this);
        // database.getReadableDatabase();
        // database.close();
    }

    public void onButtonClick(View v){
        Intent intent = null;
        switch(v.getId()){
            case R.id.show_plan_button:
                intent = new Intent(this,ShowPlanActivity.class);
                break;
            case R.id.options_button:
                intent = new Intent(this, OptionsActivity.class);
                break;
            case R.id.training_button:
                intent = new Intent(this,IntroductionTrainingActivity.class);
                break;
            case R.id.unknown_button:
                Toast.makeText(this,"Wykup płatne DLC",Toast.LENGTH_SHORT).show();
                break;
        }
        if (intent != null){
            startActivity(intent);
            finish();
        }
    }
}
