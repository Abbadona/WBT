package com.example.karol.wbt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ShowPlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plan);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MenuActivity.class));
        finish();
    }
}
