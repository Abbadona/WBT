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

public class ChangePasswordActivity extends AppCompatActivity{
    private EditText oldPassword;
    private EditText newPassword;
    private EditText repeatedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        oldPassword = (EditText)this.findViewById(R.id.old_password_text);
        newPassword = (EditText)this.findViewById(R.id.new_password_text);
        repeatedPassword = (EditText)this.findViewById(R.id.repeat_password_text);
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, OptionActivity.class));
        finish();
    }

    public void onButtonClick(View v) throws JSONException{

        switch(v.getId()){
            case R.id.accept_button:
                if(isOldPasswordCorrect(oldPassword) && isNewPasswordCorrect(newPassword) && areTheSame(newPassword, repeatedPassword)){
                    JSONObject sendNewPassword = new JSONObject((new ClientConnection(this, "ChangePassword", "new_password",
                                                                 newPassword.getText().toString())).runConnection());
                    if(sendNewPassword.has("error"))
                        Toast.makeText(this, "Wystąpił błąd", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.cancel_button:
                startActivity(new Intent(this, OptionActivity.class));
                finish();
                break;
        }
    }

    public boolean isOldPasswordCorrect(EditText oldPassword){
        if(isEmpty(oldPassword))
            return false;
        if(!oldPassword.getText().toString().equals(getCurrentPassword())){
            oldPassword.setError("Niepoprawne hasło");
            return false;
        }
        return true;
    }

    public boolean isNewPasswordCorrect(EditText password){
        if(isEmpty(password))
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

    public boolean isEmpty(EditText password){
        if(password.getText().toString().isEmpty()){
            password.setError("Podaj hasło");
            return false;
        }
        return true;
    }

    public String getCurrentPassword(){
        String password = "";
        try{
            JSONObject passwordRequest = new JSONObject((new ClientConnection(this, "GetPassword")).runConnection());
            password = passwordRequest.getString("password");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return password;
    }
}
