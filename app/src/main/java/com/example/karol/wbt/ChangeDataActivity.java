package com.example.karol.wbt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import android.widget.AutoCompleteTextView;
import com.example.karol.wbt.ConnectionPackage.ClientConnection;
import org.json.JSONObject;
import org.json.JSONException;
import android.widget.ArrayAdapter;

public class ChangeDataActivity extends AppCompatActivity{
    private EditText name, lastName, phone, email, age, height, weight;
    private AutoCompleteTextView frequency, advancementLevel, goal;

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
        frequency = (AutoCompleteTextView)findViewById(R.id.frequency_edit);
        advancementLevel = (AutoCompleteTextView)findViewById(R.id.advancement_level_edit);
        goal = (AutoCompleteTextView)findViewById(R.id.goal_edit);

        setChoices(frequency, frequencies);
        setChoices(advancementLevel, advancement_levels);
        setChoices(goal, goals);

        // getCurrentData();
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, OptionActivity.class));
        finish();
    }

    public void setChoices(AutoCompleteTextView view, String array[]){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_singlechoice, array);
        // view.setThreshold(0);
        view.setAdapter(adapter);
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
            // frequency.setText(dataRequest.getString("frequency"));
            // advancementLevel.setText(dataRequest.getString("advancement_level"));
            // goal.setText(dataRequest.getString("goal"));
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
