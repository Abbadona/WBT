package com.example.karol.wbt;
import android.provider.Settings.Secure;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.karol.wbt.ConnectionPackage.ClientConnection;
import com.example.karol.wbt.UtilitiesPackage.MyTextWatcher;
import org.json.JSONObject;
import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    private CheckBox remeberLogin;
    private Button signInButton;

    private EditText loginEditText;
    private EditText passEditText;
    private MyTextWatcher myTextWatcher;
    private MenuActivity menu;
    private SignInUpActivity signInUpActivity;
    private ClientConnection clientConnection;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(),SignInUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        remeberLogin = (CheckBox)findViewById(R.id.remember_checkBox);
        loginEditText= (EditText) this.findViewById(R.id.login_in);
        passEditText = (EditText) this.findViewById(R.id.password_in);
    }

    private void goToMenu(){
        if (remeberLogin.isChecked()) rememberLogin();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        Toast.makeText(this, getString(R.string.signed_in), Toast.LENGTH_SHORT).show();
    }

    private String hashFunction(String macAdress){
        int intMacAddress = 0;
        for ( int i = 0 ; i < macAdress.length(); ++i){
            intMacAddress =+ (int)macAdress.charAt(i);
        }
        return new Integer(intMacAddress).toString();
    }

    public void onLoginButtonClick(View v){

        final HashMap<String, String> parameters = new HashMap<>();
        //Nasłuchiwanie na działanie
        MyTextWatcher loginWatcher = new MyTextWatcher(loginEditText);
        loginEditText.addTextChangedListener(loginWatcher);

        MyTextWatcher passWatcher = new MyTextWatcher(passEditText);
        passEditText.addTextChangedListener(passWatcher);

        String password = passWatcher.getText();
        String login = loginWatcher.getText();
        final ClientConnection client = new ClientConnection(this, "LoginRequest", parameters);

        if (!password.equals("") || !login.equals("")) {
            parameters.put("login", login);
            parameters.put("password", password);
            parameters.put("device_id",hashFunction(Secure.getString(this.getContentResolver(),Secure.ANDROID_ID)));
            try {
                JSONObject jsonObject = new JSONObject(client.runConnection());
                if( !jsonObject.getString("message_type").equals("LoginRequest"))throw new Exception("Error");
                if (jsonObject.getBoolean("islogged"))  goToMenu();
                else {
                    final AlertDialog alertVerifyDialog = new AlertDialog.Builder(this).create();
                    LayoutInflater inflaterVerify = LayoutInflater.from(getApplicationContext());
                    alertVerifyDialog.setView(inflaterVerify.inflate(R.layout.verify_alert, null));
                    alertVerifyDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.send), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editTextVerify = (EditText) alertVerifyDialog.findViewById(R.id.verify_edit_text);
                            parameters.put("verify_code", editTextVerify.getText().toString());
                            try {
                                JSONObject answer = new JSONObject(client.verifyClient(parameters));
                                if (answer.getString("message_type").equals("AddDevice")) goToMenu();
                                else throw new Exception("Error");
                            } catch (Exception e) {
                                e.printStackTrace();
                                startActivity(new Intent(SignInActivity.this.getBaseContext(),SignInUpActivity.class));
                                Toast.makeText(SignInActivity.this, getString(R.string.verify_error), Toast.LENGTH_SHORT).show(
                                );
                            }
                            finally {finish();}
                        }
                    });
                    alertVerifyDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    alertVerifyDialog.show();
                }
            } catch (Exception e) {
                Toast.makeText(this,getString(R.string.sign_in_error),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void rememberLogin(){

        SharedPreferences.Editor preferencesEditor;
        SharedPreferences preferences;

        preferences = getSharedPreferences("myPreferences", Activity.MODE_PRIVATE);
        preferencesEditor = preferences.edit();

        preferencesEditor.putBoolean("islogged",true);
        preferencesEditor.putString("login",loginEditText.getText().toString());
        preferencesEditor.putString("password",passEditText.getText().toString());
        preferencesEditor.commit();

    }
}
