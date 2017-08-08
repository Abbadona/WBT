package com.example.karol.wbt.TrainingPackage;

import android.content.Intent;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karol.wbt.MenuActivity;
import com.example.karol.wbt.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class IntroductionTrainingActivity extends AppCompatActivity {

    private List<Integer> id_list;
    private List<Integer> desc_list;

    private List<Pair<String,String>> getTrainingList(){
        //// TODO: 08.08.2017
        // Komunikacja z serwerem - zdobycie treningów
        List<Pair<String,String>> list = new ArrayList<>();
        Pair<String,String> Pair;

        for ( int i = 1 ; i < 4; i++){
            Pair = new Pair<>("training_"+ i,"description_"+ i);
            list.add(Pair);
        }
        return list;
    }
    private void loadContent(){
        List<Pair<String,String>> listOfTraining = getTrainingList();
        Iterator<Pair<String,String>> it = listOfTraining.iterator();
        int i = 0;
        while (it.hasNext()){
            TextView id_textView = (TextView) findViewById(id_list.get(i));
            TextView desc_textView = (TextView) findViewById(desc_list.get(i));
            Pair<String,String> pair = it.next();
            id_textView.setText(pair.first);
            desc_textView.setText(pair.second);
            i++;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        id_list = new ArrayList<>();
        desc_list = new ArrayList<>();
        id_list.add(R.id.title_1);
        id_list.add(R.id.title_2);
        id_list.add(R.id.title_3);
        desc_list.add(R.id.description_1);
        desc_list.add(R.id.description_2);
        desc_list.add(R.id.description_3);
        loadContent();
    }
    public void onTitleTouch(View view) {

        Intent intent = new Intent(this,WorkoutActivity.class);
        String training_id = "";
        if (view.getId() == R.id.title_1 || view.getId() == R.id.description_1) {
            training_id = ((TextView) findViewById(R.id.description_1)).getText().toString();
        } else {
            if (view.getId() == R.id.title_2 || view.getId() == R.id.description_2) {
                training_id = ((TextView) findViewById(R.id.description_3)).getText().toString();
            } else {
                training_id = ((TextView) findViewById(R.id.description_3)).getText().toString();
            }
        }
        //Toast.makeText(this,training_id,Toast.LENGTH_SHORT).show();
        intent.putExtra("training_id",training_id);
        startActivity(intent);
        finish();

    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this,MenuActivity.class));
    }
}
