package com.example.karol.wbt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import com.example.karol.wbt.ConnectionPackage.ClientConnection;

import org.json.JSONObject;

public class ChangeDataActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_data);
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, OptionActivity.class));
        finish();
    }

    // TODO: jeszcze nie zaczete
}
