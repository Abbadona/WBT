package com.example.karol.wbt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karol.wbt.ConnectionPackage.ClientConnection;
import com.example.karol.wbt.UtilitiesPackage.InfoExerciseActivity;

public class CreatePlanActivity extends AppCompatActivity {

    private float x1=0,x2=0;
    private int siteCounter = 0,siteNumber = 5;
    private ImageView exerciseImage;
    private TextView exerciseTitle;
    private final String PREFERENCES_NAME = "myPreferences";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ClientConnection connection;
    private int image_id[]={
            R.drawable.exc1,R.drawable.exc2,R.drawable.exc3,R.drawable.exc4,R.drawable.exc5,R.drawable.exc6};
    private int title_id[]={
            R.string.exc1_title,R.string.exc2_title,R.string.exc3_title,R.string.exc4_title,R.string.exc5_title,R.string.exc6_title
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);
        exerciseImage = (ImageView) findViewById(R.id.exercise_imageView);
        exerciseTitle = (TextView) findViewById(R.id.exercise_title_textView);
        connection = new ClientConnection(this.getResources().openRawResource(R.raw.testkeysore),"getTraining");
        loadSide();
        registerForContextMenu(exerciseImage);
        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        editor = preferences.edit();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MenuActivity.class));
        finish();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                Log.d("TAG_deltaX", deltaX + "");
                if (deltaX < 0) {
                    if (siteCounter < siteNumber){
                        this.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        siteCounter++;
                        Toast.makeText(this,"Counter:"+siteCounter,Toast.LENGTH_SHORT).show();
                        loadSide();
                        Log.d("TAG_SLIDE", "right to left");
                    }
                } else {
                    if (siteCounter > 0){
                        this.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                        siteCounter--;
                        Toast.makeText(this,"Counter:"+siteCounter,Toast.LENGTH_SHORT).show();
                        loadSide();
                        Log.d("TAG_SLIDE", "left to right");
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //menu.setHeaderTitle("Menu");
        menu.add(0, v.getId(), 0, getString(R.string.info_menu));
        menu.add(0, v.getId(), 0, getString(R.string.change_exercises));

    }
    @Override
    public boolean onContextItemSelected(MenuItem item){

        if(item.getTitle()==getString(R.string.info_menu)){

            editor.putInt("siteCounter",siteCounter);
            editor.apply();
            editor.commit();
            startActivity(new Intent(this, InfoExerciseActivity.class));
        }
        else if(item.getTitle()== getString(R.string.change_exercises)){

        }else{
            return false;
        }
        return true;
    }
    private void loadSide(){
        exerciseImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(),image_id[siteCounter], null));
        exerciseTitle.setText(getString(title_id[siteCounter]));
    }
}
