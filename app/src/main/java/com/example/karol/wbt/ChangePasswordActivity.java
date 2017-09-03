package com.example.karol.wbt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import com.example.karol.wbt.ConnectionPackage.ClientConnection;
import org.json.JSONObject;
import org.json.JSONException;
import android.widget.Toast;
import java.util.HashMap;
import android.widget.Button;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText oldPassword, newPassword, repeatedPassword;
    private Button accept, cancel;
    private ClientConnection client;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        oldPassword = (EditText)this.findViewById(R.id.old_password_text);
        newPassword = (EditText)this.findViewById(R.id.new_password_text);
        repeatedPassword = (EditText)this.findViewById(R.id.repeat_password_text);
        accept = (Button)findViewById(R.id.accept_button);
        cancel = (Button)findViewById(R.id.cancel_button);

        accept.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, OptionsActivity.class));
        finish();
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.accept_button:
                if(isFilled(oldPassword) && isCorrect(newPassword) && areTheSame(newPassword, repeatedPassword)){
                    HashMap<String, String> passwords = new HashMap<>();
                    passwords.put("old_password", oldPassword.getText().toString());
                    passwords.put("new_password", newPassword.getText().toString());

                    try{
                        JSONObject sendNewPassword = new JSONObject((new ClientConnection(this, "ChangePassword", passwords))
                                                     .runConnection());
                        if(sendNewPassword.getString("message_type").equals("Error"))
                            Toast.makeText(this, "Wystąpił błąd", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(this, "Zapisano zmiany", Toast.LENGTH_SHORT).show();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    startActivity(new Intent(this, OptionsActivity.class));
                    finish();
                }
                break;

            case R.id.cancel_button:
                startActivity(new Intent(this, OptionsActivity.class));
                finish();
                break;
        }
    }

    public boolean isCorrect(EditText password){
        if(!isFilled(password))
            return false;
        else if(!password.getText().toString().matches("^[a-zA-Z0-9]*$")){
            password.setError("Wpisz tylko litery i cyfry");
            return false;
        }
        return true;
    }

    public boolean areTheSame(EditText firstPassword, EditText secondPassword){
        if(!firstPassword.getText().toString().equals(secondPassword.getText().toString())){
            secondPassword.setError(getString(R.string.pass_different));
            return false;
        }
        return true;
    }

    boolean isFilled(EditText editText){
        if(editText.getText().toString().isEmpty()){
            editText.setError("Wypełnij pole");
            return false;
        }
        return true;
    }
}
