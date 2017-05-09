package com.example.karol.wbt;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {
    //
    //Test push'a
    //


    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void onButtonClick(View v){
        Intent intent = null;
        switch(v.getId()){
            case R.id.show_plan_button:
                intent = new Intent(this,ShowPlanActivity.class);
                break;
            case R.id.option_button:
                intent = new Intent(this, OptionActivity.class);
                break;
            case R.id.create_plan_button:
                intent = new Intent(this,CreatePlanActivity.class);
                break;
            case R.id.unknown_button:
                intent = null;
                try {
                    final MediaPlayer mp = MediaPlayer.create(this, R.raw.openkey);
                    mp.start();
                }catch (Exception e ) {

                }
                break;
        }
        if (intent != null){
            startActivity(intent);
            finish();
        }
    }
}
