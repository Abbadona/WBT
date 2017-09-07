package com.example.karol.wbt.TrainingPackage;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.example.karol.wbt.ConnectionPackage.ClientConnection;
import com.example.karol.wbt.MenuActivity;
import com.example.karol.wbt.R;
import com.example.karol.wbt.UtilitiesPackage.ConfigYouTube;
import com.google.android.youtube.player.YouTubeBaseActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayerView;

public class WorkoutActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private YouTubePlayer youTubePlayer;
    private float x1=0,x2=0;
    private int siteCounter = 0,siteNumber = 5;
    private TextView exerciseTitle;
    private final String PREFERENCES_NAME = "myPreferences";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private JSONArray jsonArray;
    private JSONArray replacementArray;
    private JSONObject replacementExercise;
    private TextView description_textView;

    private void saveTrainingToDatabase(){
        //// TODO: 16.08.2017
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_create_plan);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        exerciseTitle = (TextView) findViewById(R.id.exercise_title_textView);
        description_textView = (TextView) findViewById(R.id.description_textView);
        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        editor = preferences.edit();
        jsonArray = getDataFromServer(getIntent().getExtras().getString("training_id"));
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(ConfigYouTube.DEVELOPER_KEY,this);
        loadSide();
    }

    private JSONArray getDataFromServer(String training_id){

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("training_id",training_id);
        JSONArray dataFromServer = null;
        try{
            JSONObject jsonAns = new JSONObject( new ClientConnection(this,"GetTraining",hashMap).runConnection());
            dataFromServer = new JSONArray(jsonAns.getString("exercises"));
            siteNumber = dataFromServer.length();
            saveTrainingToDatabase();
        }catch (Exception e){
            Log.d("TAG_WORKOUT","ERROR");
            siteNumber = 0;
        }
        return dataFromServer;
    }

    @Override
    public void onBackPressed() {

        final AlertDialog alertDialog = new AlertDialog.Builder(WorkoutActivity.this).create();
        alertDialog.setTitle(getString(R.string.workout));
        alertDialog.setMessage(getString(R.string.end_workout_question));

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.exit_lable), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getBaseContext(),MenuActivity.class);
                startActivity(intent);
                finish();
            }

        });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,getString(R.string.cancel),new DialogInterface.OnClickListener() {

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
                if (deltaX < 0) {
                    if (siteCounter + 1 < siteNumber){
                        this.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        siteCounter++;
                        loadSide();
                        Log.d("TAG_SLIDE", "right");
                    }
                } else {
                    if (siteCounter - 1 >= 0){
                        this.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                        siteCounter--;
                        loadSide();
                        Log.d("TAG_SLIDE", "left");
                    }
                }
                editor.putInt("siteCounter",siteCounter);
                editor.apply();
                editor.commit();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void loadSide(){

        String title = "" ,description = " ";
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(siteCounter);
            title = jsonObject.getString("exercise_name");
            ConfigYouTube.YOUTUBE_VIDEO_CODE = jsonObject.getString("video_link").replace("https://www.youtube.com/watch?v=","");
            description = jsonObject.getString("description");
        } catch (Exception e) {
            e.printStackTrace();
            title = getString(R.string.connection_error);
        } finally {
            if (this.youTubePlayer != null) this.youTubePlayer.loadVideo(ConfigYouTube.YOUTUBE_VIDEO_CODE);
            exerciseTitle.setText(title);
            description_textView.setText(description);
            editor.putString("description",description);
            editor.putString("title",title);
            editor.apply();
            editor.commit();
        }
    }

    private String[] getExercisesReplacement(){
        HashMap<String,String> parameters = new HashMap<>();
        String[] array = null;
        try {
            parameters.put("exercise_id",jsonArray.getJSONObject(siteCounter).getString("exercise_id"));
            parameters.put("id_replacment_group",jsonArray.getJSONObject(siteCounter).getString("id_replacment_group"));
            JSONObject jsonAnswer = new JSONObject(new ClientConnection(this,"ExerciseReplacement",parameters).runConnection());
            replacementArray = new JSONArray(jsonAnswer.getString("exercises "));
            array = new String[replacementArray.length()];
            for ( int i = 0 ; i < replacementArray.length(); ++i){
                array[i] = replacementArray.getJSONObject(i).getString("exercise_name");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    public void ExerciseReplacementJSON(View view) {

        String[] exercises = getExercisesReplacement();
        AlertDialog.Builder builder = new AlertDialog.Builder(WorkoutActivity.this);
        builder.setTitle(getString(R.string.replacement_exer))
                .setItems(exercises,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            replacementExercise = replacementArray.getJSONObject(which);
                            Context context = WorkoutActivity.this;
                            JSONObject newExercise = new JSONObject(new ClientConnection(context,"GetExercise", "exercise_id",
                                    replacementExercise.getString("exercise_id")).runConnection());
                            jsonArray.getJSONObject(siteCounter).put("exercise_id",replacementExercise.getString("exercise_id"));
                            jsonArray.getJSONObject(siteCounter).put("description",newExercise.getString("description"));
                            jsonArray.getJSONObject(siteCounter).put("video_link",newExercise.getString("video_link"));
                            jsonArray.getJSONObject(siteCounter).put("exercise_name",newExercise.getString("exercise_name"));
                            loadSide();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(WorkoutActivity.this,getString(R.string.invalid_replaceExercise),Toast.LENGTH_LONG).show();
                        }
                    }});
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //Youtubowskie funkcje
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        this.youTubePlayer = player;
        // loadVideo() will auto play video
        // Use cueVideo() method, if you don't want to play it automatically
        //this.youTubePlayer.loadVideo(ConfigYouTube.YOUTUBE_VIDEO_CODE);
        this.youTubePlayer.cueVideo(ConfigYouTube.YOUTUBE_VIDEO_CODE);
        // Hiding player controls
        this.youTubePlayer.setPlayerStyle(PlayerStyle.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(ConfigYouTube.DEVELOPER_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = errorReason.toString();
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}