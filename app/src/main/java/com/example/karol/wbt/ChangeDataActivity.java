package com.example.karol.wbt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import com.example.karol.wbt.ConnectionPackage.ClientConnection;
import org.json.JSONObject;
import org.json.JSONException;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.view.View;
import android.widget.Toast;
import java.util.HashMap;

public class ChangeDataActivity extends AppCompatActivity{
    private EditText name, lastName, phone, email, age, height, weight;
    private Spinner frequency, advancementLevel, goal;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_data);
        String frequencies[] = {getResources().getString(R.string.once_twice), getString(R.string.three_four),
                                getString(R.string.five_six), getString(R.string.seven_or_more)};
        String advancement_levels[] = {getString(R.string.basic), getString(R.string.intermediate), getString(R.string.advanced)};
        String goals[] = {getString(R.string.strength), getString(R.string.stamina), getString(R.string.reduction)};

        name = (EditText)findViewById(R.id.name_edit);
        lastName = (EditText)findViewById(R.id.lastname_edit);
        phone = (EditText)findViewById(R.id.phone_edit);
        email = (EditText)findViewById(R.id.email_edit);
        age = (EditText)findViewById(R.id.age_edit);
        height = (EditText)findViewById(R.id.height_edit);
        weight = (EditText)findViewById(R.id.weight_edit);
        frequency = (Spinner)findViewById(R.id.frequency_edit);
        advancementLevel = (Spinner)findViewById(R.id.advancement_level_edit);
        goal = (Spinner)findViewById(R.id.goal_edit);
        setChoices(frequency, frequencies);
        setChoices(advancementLevel, advancement_levels);
        setChoices(goal, goals);
        getCurrentData();
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, OptionActivity.class));
        finish();
    }

    public void onButtonClick(View v) throws JSONException{
        switch(v.getId()){
            case R.id.accept_button:
                if(isFilled(name) && isFilled(lastName) && isFilled(phone) && isFilled(email)
                   && isFilled(age) && isFilled(height) && isFilled(weight)){
                    HashMap<String, String> userData = new HashMap<>();
                    userData.put("name", name.getText().toString());
                    userData.put("lastname", lastName.getText().toString());
                    userData.put("PHONE", phone.getText().toString());
                    userData.put("EMAIL", email.getText().toString());
                    userData.put("age", age.getText().toString());
                    userData.put("height", height.getText().toString());
                    userData.put("weight", weight.getText().toString());
                    userData.put("frequency", frequency.getSelectedItem().toString());
                    userData.put("advancement_level", advancementLevel.getSelectedItem().toString());
                    userData.put("goal", goal.getSelectedItem().toString());

                    JSONObject sendNewData = new JSONObject((new ClientConnection(this, "ChangeData", userData)).runConnection());
                    if(sendNewData.has("error"))
                        Toast.makeText(this, "Wystąpił błąd", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.cancel_button:
                startActivity(new Intent(this, OptionActivity.class));
                finish();
                break;
        }
    }

    public void setChoices(Spinner choiceList, String array[]){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, array);
        choiceList.setAdapter(adapter);
    }

    public void loadChoice(Spinner choiceList, String choice){
        ArrayAdapter adapter = (ArrayAdapter)choiceList.getAdapter();
        choiceList.setSelection(adapter.getPosition(choice));
    }

    public void getCurrentData(){
        try{
            JSONObject dataRequest = new JSONObject((new ClientConnection(this, "GetData")).runConnection());
            name.setText(dataRequest.getString("name"));
            lastName.setText(dataRequest.getString("lastname"));
            phone.setText(dataRequest.getString("PHONE"));
            email.setText(dataRequest.getString("EMAIL"));
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

    boolean isFilled(EditText editText){
        if(editText.getText().toString().isEmpty()){
            editText.setError("Wypełnij pole");
            return false;
        }
        return true;
    }
}

// linia testowa XD