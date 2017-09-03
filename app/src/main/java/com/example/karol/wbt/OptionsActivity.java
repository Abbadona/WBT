package com.example.karol.wbt;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private void logout(){
        String PREFERENCES_NAME = "myPreferences";
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("islogged",false);
        editor.putString("login","");
        editor.putString("password","");
        editor.commit();
        startActivity(new Intent(this,SignInUpActivity.class));
        finish();
    };

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
            case R.id.logout_button:
                logout();
                break;
        }
    }
}
