package com.example.karol.wbt.SurveyActivities;
import android.os.Bundle;
import com.example.karol.wbt.ConnectionPackage.ClientConnection;
import com.example.karol.wbt.MenuActivity;
import com.example.karol.wbt.R;
import com.example.karol.wbt.SignInUpActivity;
import com.example.karol.wbt.UtilitiesPackage.MySurveyPageActivity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class EndSurvey extends MySurveyPageActivity implements View.OnClickListener {

    Button endButton;
    MenuActivity menuActivity;
    ClientConnection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_survey);
        endButton = (Button) findViewById(R.id.end_button);
        endButton.setOnClickListener(this);
    }

    private Intent sendPreferencesToServer(){
        ArrayList<String> keyList = new ArrayList<>();
        // TODO: poprawic literowke jak Mikolaj wprowadzi zmiany
        Collections.addAll(keyList, "login", "password", "name", "lastname","PHONE","EMAIL",
                           "verify_way", "age","height","weight","frequency","advancment_level","goal");
        HashMap<String,String> parameters = loadStringPreferences(keyList);
        ClientConnection clientConnection = new ClientConnection(this,"RegisterNewClient",parameters);
        String result = null;
        try {
            result = new JSONObject(clientConnection.runConnection()).getString("message_type");
            if ( result == "RegisterNewClient" ){
                Toast.makeText(this,getString(R.string.registration_positive),Toast.LENGTH_LONG);
                this.clearPreferences(keyList);
                Toast.makeText(this,getString(R.string.registration_positive),Toast.LENGTH_LONG);
            }else throw new IOException("Error");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,getString(R.string.registration_negative),Toast.LENGTH_LONG);
        }
        return new Intent(this, SignInUpActivity.class);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == endButton.getId()){
            Intent intent = sendPreferencesToServer();
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return false;
    }
}
