package com.example.karol.wbt;
import android.content.Context;
import android.provider.Settings.Secure;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private CheckBox remeberLogin;
    private Button signInButton;
    private SharedPreferences.Editor preferencesEditor;
    private SharedPreferences preferences;

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

        preferences = getSharedPreferences("myPreferences", Activity.MODE_PRIVATE);
        preferencesEditor = preferences.edit();

        signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(this);

        remeberLogin = (CheckBox)findViewById(R.id.remember_checkBox);
        remeberLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            //Przyciskamy Zaloguj
            case R.id.remember_checkBox:

                if (remeberLogin.isChecked()) {
                    preferencesEditor.putString("rememberLogged", "true");

                } else {
                    preferencesEditor.putString("rememberLogged", "false");
                }
                preferencesEditor.commit();

                break;

            case R.id.signInButton:

                Log.d("TAG_SIGNINBUTT", "Button SignIn");

                final HashMap<String, String> parameters = new HashMap<>();
                final EditText loginEditText = (EditText) this.findViewById(R.id.login_in);
                final EditText passEditText = (EditText) this.findViewById(R.id.password_in);

                //Nasłuchiwanie na działanie
                MyTextWatcher loginWatcher = new MyTextWatcher(loginEditText);
                loginEditText.addTextChangedListener(loginWatcher);

                MyTextWatcher passWatcher = new MyTextWatcher(passEditText);
                passEditText.addTextChangedListener(passWatcher);

                String password = passWatcher.getText();
                String login = loginWatcher.getText();
                final ClientConnection client = new ClientConnection(this, "LoginRequest", parameters);
                Log.d("TAG_SIGNINBUTT", password+" "+login);

                if (!password.equals("") || !login.equals("")) {
                    Log.d("TAG_SIGNINBUTT", "Inside");
                    parameters.put("login", login);
                    parameters.put("password", password);
                    Log.d("TAG_Device_Id",Secure.getString(this.getContentResolver(),Secure.ANDROID_ID));
                    parameters.put("device_id","123");

                    try {

                        JSONObject jsonObject = new JSONObject(client.runConnection());
                        if( !jsonObject.getString("message_type").equals("LoginRequest")){
                            Log.d("TAG_Login","ThrowExcep");
                            throw new Exception("Error");
                        }

                        if (jsonObject.getBoolean("islogged")) {

                            Intent intent = new Intent(this, MenuActivity.class);
                            startActivity(intent);
                            Toast.makeText(this, "Zalogowano", Toast.LENGTH_SHORT).show();

                        } else {//error ok

                                Log.d("TAG_Verify","Inside");
                                final AlertDialog alertVerifyDialog = new AlertDialog.Builder(this).create();
                                String macAddress = "123";
                                LayoutInflater inflaterVerify = LayoutInflater.from(getApplicationContext());
                                alertVerifyDialog.setView(inflaterVerify.inflate(R.layout.verify_alert, null));
                                final HashMap<String, String> verifyParametrs = new HashMap<>();
                                verifyParametrs.put("login", parameters.get("login"));
                                verifyParametrs.put("password", parameters.get("password"));
                                verifyParametrs.put("device_id", macAddress);


                                alertVerifyDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Wyślij", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        EditText editTextVerify = (EditText) alertVerifyDialog.findViewById(R.id.verify_edit_text);
                                        verifyParametrs.put("verify_code", editTextVerify.getText().toString());

                                        try {
                                            JSONObject answer = new JSONObject(client.verifyClient(verifyParametrs));
                                            if (answer.getString("message_type").equals("AddDevice")) {
                                                Toast.makeText(SignInActivity.this, "Zalogowano", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SignInActivity.this.getBaseContext(),MenuActivity.class));
                                            } else throw new Exception("Error");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            startActivity(new Intent(SignInActivity.this.getBaseContext(),SignInUpActivity.class));
                                            Toast.makeText(SignInActivity.this, "Błędna weryfikacja", Toast.LENGTH_SHORT).show(
                                            );
                                        }
                                        finally {finish();}
                                    }
                                });
                                alertVerifyDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Anuluj", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                alertVerifyDialog.show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(this,"Błąd logowania - złe hasło lub login",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
