package com.example.karol.wbt.TrainingPackage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.karol.wbt.ConnectionPackage.ClientConnection;
import com.example.karol.wbt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExerciseReplacementActivity extends AppCompatActivity {

    private final String PREFERENCES_NAME = "myPreferences";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private List<Integer> textViewId;
    private HashMap<String,String> parameters;
    private JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_replacement);
        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        editor = preferences.edit();
        textViewId = new ArrayList<>();
        parameters = new HashMap<>();
        textViewId.add(R.id.repl_exer_1);
        textViewId.add(R.id.repl_exer_2);
        textViewId.add(R.id.repl_exer_3);
        textViewId.add(R.id.repl_exer_4);
        textViewId.add(R.id.repl_exer_5);
        getDataFromServer();
    }
    private void getDataFromServer(){
        //// TODO: 17.08.2017  
        parameters.put("exercise_id",preferences.getString("exercise_id","error"));
        parameters.put("id_replacment_group",preferences.getString("id_replacment_group","error"));
        try {
            JSONObject jsonAnswer = new JSONObject(new ClientConnection(this,"ExerciseReplacement",parameters).runConnection());
            jsonArray = jsonAnswer.getJSONArray("ExerciseReplacement");
            for ( int i = 0 ; i < jsonArray.length(); ++i){
                TextView textView = (TextView) findViewById(textViewId.get(i));
                textView.setText(jsonArray.getJSONObject(i).getString("exercise_name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void onTitleTouchRepl(View view){

        int i = 0 ;
        while ( i < jsonArray.length()){
            if ( view.getId() == textViewId.get(i) && !((TextView)findViewById(textViewId.get(i))).getText().toString().equals("")){
                try {
                    parameters.put("repleced_exercise_id",jsonArray.getJSONObject(i).getString("exercise_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    parameters.put("repleced_exercise_id","error");
                }finally {
                    i = jsonArray.length();
                }
            }
        }
        finish();
    }
}
