package com.example.karol.wbt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import com.example.karol.wbt.ConnectionPackage.ClientConnection;
import org.json.JSONObject;
import org.json.JSONException;
import android.view.View;
import android.widget.Toast;
import java.util.HashMap;

public class ChangeUserDataActivity extends AppCompatActivity{
    private EditText name, lastName, phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_data);

        name = (EditText)findViewById(R.id.name_edit);
        lastName = (EditText)findViewById(R.id.lastname_edit);
        phone = (EditText)findViewById(R.id.phone_edit);
        email = (EditText)findViewById(R.id.email_edit);

        getCurrentData();
        if(!isFilled(name)){
            finish();
            startActivity(getIntent());
        }
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, OptionsActivity.class));
        finish();
    }

    public void onButtonClick(View v) throws JSONException{
        switch(v.getId()){
            case R.id.accept_button:
                if(isFilled(name) && isFilled(lastName) && isFilled(phone) && isFilled(email)){
                    HashMap<String, String> userData = new HashMap<>();
                    userData.put("name", name.getText().toString());
                    userData.put("last_name", lastName.getText().toString());
                    userData.put("phone", phone.getText().toString());
                    userData.put("email", email.getText().toString());

                    JSONObject sendNewData = new JSONObject((new ClientConnection(this, "ChangeData", userData)).runConnection());
                    if(sendNewData.getString("message_type").equals("Error"))
                        Toast.makeText(this, "Wystąpił błąd", Toast.LENGTH_SHORT).show();
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
            JSONObject dataRequest = new JSONObject((new ClientConnection(this, "GetBasicData")).runConnection());
            name.setText(dataRequest.getString("name"));
            lastName.setText(dataRequest.getString("last_name"));
            phone.setText(dataRequest.getString("phone"));
            email.setText(dataRequest.getString("email"));
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
