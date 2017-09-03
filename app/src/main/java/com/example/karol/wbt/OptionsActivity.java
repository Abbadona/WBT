package com.example.karol.wbt;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OptionsActivity extends AppCompatActivity {
    private ChangePasswordActivity changePasswordActivity;
    private ChangeTrainingDataActivity changeTrainingDataActivity;
    private ChangeUserDataActivity changeUserDataActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
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

            case R.id.change_user_data_button:
                startActivity(new Intent(this, ChangeUserDataActivity.class));
                finish();
                break;

            case R.id.change_training_data_button:
                startActivity(new Intent(this, ChangeTrainingDataActivity.class));
                finish();
                break;
        }
    }
}
