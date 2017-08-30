package com.example.karol.wbt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Intent;
import android.view.View;
import org.json.JSONException;
import java.util.HashMap;
import org.json.JSONObject;
import com.example.karol.wbt.ConnectionPackage.ClientConnection;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class ChangeTrainingDataActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText age, height, weight;
    private Spinner frequency, advancementLevel, goal;
    private Button accept, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_training_data);
        String frequencies[] = {getResources().getString(R.string.once_twice), getString(R.string.three_four),
                                getString(R.string.five_six), getString(R.string.seven_or_more)};
        String advancement_levels[] = {getString(R.string.basic), getString(R.string.intermediate), getString(R.string.advanced)};
        String goals[] = {getString(R.string.strength), getString(R.string.stamina), getString(R.string.reduction)};

        age = (EditText)findViewById(R.id.age_edit);
        height = (EditText)findViewById(R.id.height_edit);
        weight = (EditText)findViewById(R.id.weight_edit);
        frequency = (Spinner)findViewById(R.id.frequency_edit);
        advancementLevel = (Spinner)findViewById(R.id.advancement_level_edit);
        goal = (Spinner)findViewById(R.id.goal_edit);
        accept = (Button)findViewById(R.id.accept_button);
        cancel = (Button)findViewById(R.id.cancel_button);

        setChoices(frequency, frequencies);
        setChoices(advancementLevel, advancement_levels);
        setChoices(goal, goals);
        accept.setOnClickListener(this);
        cancel.setOnClickListener(this);
        getCurrentData();
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, OptionsActivity.class));
        finish();
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.accept_button:
                if(isFilled(age) && isFilled(height) && isFilled(weight)){
                    HashMap<String, String> trainingData = new HashMap<>();
                    trainingData.put("age", age.getText().toString());
                    trainingData.put("height", height.getText().toString());
                    trainingData.put("weight", weight.getText().toString());
                    trainingData.put("frequency", frequency.getSelectedItem().toString());
                    trainingData.put("advancment_level", advancementLevel.getSelectedItem().toString());  // TODO: poprawic literowke jak Mikolaj wprowadzi zmiany
                    trainingData.put("goal", goal.getSelectedItem().toString());

                    try{
                        JSONObject sendNewData = new JSONObject((new ClientConnection(this, "InsertParameters", trainingData)).runConnection());
                        if(sendNewData.getString("message_type").equals("Error"))
                            Toast.makeText(this, "Wystąpił błąd", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(this, "Zapisano zmiany", Toast.LENGTH_SHORT).show();
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                    startActivity(new Intent(this, OptionsActivity.class));
                    finish();
                }
                break;

            case R.id.cancel_button:
                startActivity(new Intent(this, OptionsActivity.class));
                finish();
                break;
        }
    }

    public void getCurrentData(){
        try{
            JSONObject dataRequest = new JSONObject((new ClientConnection(this, "GetParameters")).runConnection());
            age.setText(dataRequest.getString("age"));
            height.setText(dataRequest.getString("height"));
            weight.setText(dataRequest.getString("weight"));
            loadChoice(frequency, dataRequest.getString("frequency"));
            loadChoice(advancementLevel, dataRequest.getString("advancement_level"));
            loadChoice(goal, dataRequest.getString("goal"));
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void setChoices(Spinner choiceList, String array[]){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, array);
        choiceList.setAdapter(adapter);
    }

    public void loadChoice(Spinner choiceList, String choice){
        ArrayAdapter adapter = (ArrayAdapter)choiceList.getAdapter();
        if(choice.contains("7"))
            choiceList.setSelection(3);
        else if(choice.contains("ednio") || choice.contains("Wytrz"))
            choiceList.setSelection(1);
        else
            choiceList.setSelection(adapter.getPosition(choice));
    }

    boolean isFilled(EditText editText){
        if(editText.getText().toString().isEmpty()){
            editText.setError("Wypełnij pole");
            return false;
        }
        return true;
    }
}
