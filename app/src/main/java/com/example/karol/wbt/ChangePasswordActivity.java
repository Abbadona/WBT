package com.example.karol.wbt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import java.util.HashMap;
import java.io.InputStream;
import com.example.karol.wbt.ConnectionPackage.ClientConnection;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity{
    private EditText oldPassword;
    private EditText newPassword;
    private EditText repeatedPassword;
    private HashMap<String,String> passwords = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        oldPassword = (EditText)this.findViewById(R.id.old_password_text);
        newPassword = (EditText)this.findViewById(R.id.new_password_text);
        repeatedPassword = (EditText)this.findViewById(R.id.repeat_password_text);
        passwords = new HashMap<>();
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, OptionActivity.class));
        finish();
    }

    public void onButtonClick(View v){
        switch(v.getId()){
            case R.id.accept_button:
                if(!isEmpty(oldPassword) && isCorrect(newPassword) && areTheSame(newPassword, repeatedPassword)){
                    this.passwords.put("old_password", oldPassword.getText().toString());
                    this.passwords.put("new_password", newPassword.getText().toString());
                    //sendPasswords();
                }
                break;

            case R.id.cancel_button:
                startActivity(new Intent(this, OptionActivity.class));
                finish();
                break;
        }
    }

    public boolean isEmpty(EditText password){
        if(password.getText().toString().isEmpty()){
            password.setError("Podaj hasło");
            return false;
        }
        return true;
    }

    public boolean isCorrect(EditText password){
        if(isEmpty(password))
            return false;
        else if(!password.getText().toString().matches("^[a-zA-Z0-9]*$")){
            password.setError("Wpisz tylko litery i cyfry");
            return false;
        }
        return true;
    }

    public boolean areTheSame(EditText firstPassword, EditText secondPassword){
        if(firstPassword.getText().toString().equals(secondPassword.getText().toString()))
            return true;
        else
            secondPassword.setError(getString(R.string.pass_different));
        return false;
    }

    public Intent sendPasswords(){ // TODO: do dokonczenia
        InputStream keyin = getResources().openRawResource(R.raw.testkeysore);
        ClientConnection clientConnection = new ClientConnection(keyin, "ChangePassword", passwords);
        String result = clientConnection.runConnection();

        if(result.contains("true"))
            Toast.makeText(this, "Zmieniono hasło", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Wystąpił błąd", Toast.LENGTH_LONG).show();
        return new Intent(this, OptionActivity.class);
    }
}
