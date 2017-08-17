package com.example.karol.wbt.TrainingPackage;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.example.karol.wbt.ConnectionPackage.ClientConnection;
import com.example.karol.wbt.MenuActivity;
import com.example.karol.wbt.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class WorkoutActivity extends AppCompatActivity {

    private float x1=0,x2=0;
    private int siteCounter = 0,siteNumber = 5;
    private VideoView videoView;
    private TextView exerciseTitle;
    private final String PREFERENCES_NAME = "myPreferences";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private JSONArray jsonArray;
    private JSONArray replacementArray;
    private JSONObject replacementExercise;
    private void saveTrainingToDatabase(){
        //// TODO: 16.08.2017
    }

    private void getDataFromServer(String training_id){

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id",training_id);
        ClientConnection clientConnection = new ClientConnection(this,"GetTraining",hashMap);
        String result = clientConnection.runConnection();
        try{
            JSONObject jsonAns = new JSONObject(result);
            jsonArray = jsonAns.getJSONArray("GetTraining");
            siteNumber = jsonArray.length();
            saveTrainingToDatabase();
        }catch (Exception e){
            jsonArray = null;
            siteNumber = 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);
        videoView = (VideoView) findViewById(R.id.videoView);
        exerciseTitle = (TextView) findViewById(R.id.exercise_title_textView);
        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        editor = preferences.edit();
        Toast.makeText(this,getIntent().getExtras().getString("training_id"),Toast.LENGTH_SHORT).show();
        getDataFromServer(getIntent().getExtras().getString("training_id"));
        loadSide();
        registerForContextMenu(videoView);

    }

    @Override
    public void onBackPressed() {
        final AlertDialog alertDialog = new AlertDialog.Builder(WorkoutActivity.this).create();
        alertDialog.setTitle("Trening");
        alertDialog.setMessage("Czy chcesz zakończyć trening?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Wyjście", new DialogInterface.OnClickListener() {

            //
            //  Kliknąłeś w Zaloguj, więc wyświetla się alertdialog związany z logowaniem
            //

            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getBaseContext(),MenuActivity.class);
                startActivity(intent);
                finish();
            }

        });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"Anuluj",new DialogInterface.OnClickListener() {

            //
            //  Kliknąłeś w Rejestracje, więc tworzymy alterDialog dla Rejestracji
            //
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
            }

        });
        alertDialog.show();

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
                editor.putInt("siteCounter",siteCounter);
                editor.apply();
                editor.commit();
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
            startActivity(new Intent(this, InfoExerciseActivity.class));
        }
        else if(item.getTitle()== getString(R.string.change_exercises)){
            try {
                ExerciseReplacementJSON();
                loadSide();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this,"Nie udało się zmienić ćwiczenia",Toast.LENGTH_SHORT).show();
            }
        }else{
            return false;
        }
        return true;
    }

    private void loadSide(){
        String title = "",link = " " ,description = " ";
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(siteCounter);
            title = jsonObject.getString("exercise_name");
            link = jsonObject.getString("video_link");
            description = jsonObject.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
            title = getString(R.string.connection_error);
            description = title;
            link = description;
        } finally {
            exerciseTitle.setText(title);
            videoView.setVideoURI(Uri.parse(link));
            videoView.setMediaController(new MediaController(this));
            videoView.requestFocus();
            videoView.start();
            editor.putString("description",description);
            editor.putString("title",title);
            editor.apply();
            editor.commit();
        }
    }

    private String[] getExercisesReplacement() throws JSONException {
        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("exercise_id",jsonArray.getJSONObject(siteCounter).getString("exercise_id"));
        parameters.put("id_replacment_group",jsonArray.getJSONObject(siteCounter).getString("id_replacment_group"));
        JSONObject jsonAnswer = new JSONObject(new ClientConnection(this,"ExerciseReplacement",parameters).runConnection());
        replacementArray = jsonAnswer.getJSONArray("ExerciseReplacement");
        String[] array = new String[replacementArray.length()];
        for ( int i = 0 ; i < replacementArray.length(); ++i){
            array[i] = replacementArray.getJSONObject(i).getString("exercise_name");
        }
        return array;
    }

    private void ExerciseReplacementJSON() throws JSONException {
        //// TODO: 17.08.2017
        String[] exercises = getExercisesReplacement();
        AlertDialog.Builder builder = new AlertDialog.Builder(WorkoutActivity.this);
        builder.setTitle(getString(R.string.replacement_exer))
                .setItems(exercises,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            replacementExercise = replacementArray.getJSONObject(which);
                            Context context = WorkoutActivity.this;
                            JSONObject newExercise = new JSONObject(new ClientConnection(context,"ShowExercise", "exercise_id ",
                                    replacementExercise.getString("exercise_id ")).runConnection());
                            jsonArray.getJSONObject(siteCounter).put("exercise_id",newExercise.getString("exercise_id"));
                            jsonArray.getJSONObject(siteCounter).put("description",newExercise.getString("description"));
                            jsonArray.getJSONObject(siteCounter).put("video_link ",newExercise.getString("video_link "));
                            jsonArray.getJSONObject(siteCounter).put("exercise_name",newExercise.getString("exercise_name"));
                            loadSide();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }});
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
