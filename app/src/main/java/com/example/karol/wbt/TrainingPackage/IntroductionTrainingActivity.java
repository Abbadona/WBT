package com.example.karol.wbt.TrainingPackage;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.karol.wbt.ConnectionPackage.ClientConnection;
import com.example.karol.wbt.MenuActivity;
import com.example.karol.wbt.R;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class IntroductionTrainingActivity extends AppCompatActivity {

    private List<Integer> id_list;
    private List<Integer> desc_list;
    private String[] order = {"one","two","three"};

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

    private void loadContent(){

        HashMap<String,String> mapOfTraining = getTrainingMap();
        int i = 0;
        while (i < 3 ){
            TextView id_textView = (TextView) findViewById(id_list.get(i));
            TextView desc_textView = (TextView) findViewById(desc_list.get(i));
            id_textView.setText(mapOfTraining.get("training_"+order[i]));
            desc_textView.setText(mapOfTraining.get("description_"+order[i]));
            i++;
        }
    }

    private HashMap<String,String> getTrainingMap()  {

        HashMap<String,String>  map = new HashMap<>();
        ClientConnection clientConnection = new ClientConnection(this,"TrainingProposition");
        String result = clientConnection.runConnection();

        try{
            JSONObject jsonAns = new JSONObject(result);
            JSONArray jsonArray = jsonAns.getJSONArray("TrainingProposition");
            for ( int i = 0 ; i < jsonArray.length(); ++i ){
                JSONObject jsonTmp = jsonArray.getJSONObject(i);
                map.put("training_"+order[i],jsonTmp.getString("id"));
                map.put("description_"+order[i],jsonTmp.getString("description"));
            }
            if (map.size() == 0 ) throw new Exception("Error");

        }catch (Exception e){

            AlertDialog alertDialog = new AlertDialog.Builder(IntroductionTrainingActivity.this).create();
            alertDialog.setTitle("Połączenie");
            alertDialog.setMessage("Błąd połączenia lub wczytania danych!");
            alertDialog.setCancelable(false);
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Wyjście", new DialogInterface.OnClickListener() {

                //
                //  Kliknąłeś w Zaloguj, więc wyświetla się alertdialog związany z logowaniem
                //

                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getBaseContext(),MenuActivity.class));
                    finish();
                }

            });
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"Połącz",new DialogInterface.OnClickListener() {

                //
                //  Kliknąłeś w Rejestracje, więc tworzymy alterDialog dla Rejestracji
                //
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getBaseContext(), IntroductionTrainingActivity.class));
                    finish();
                }

            });

            alertDialog.show();
        }
        return map;
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
