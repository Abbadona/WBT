package com.example.karol.wbt;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OptionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MenuActivity.class));
        finish();
    }

    public void onButtonClick(View v){
        switch(v.getId()){
            case R.id.change_password_button:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                finish();
                break;

            case R.id.change_data_button:
                startActivity(new Intent(this, ChangeDataActivity.class));
                finish();
                break;
        }
    }
}
