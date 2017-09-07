package com.example.karol.wbt;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.EditText;
import com.example.karol.wbt.SurveyActivities.ContactSurvey;
import com.example.karol.wbt.UtilitiesPackage.LearnGesture;
import com.example.karol.wbt.UtilitiesPackage.MySurveyPageActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SignUpActivity extends MySurveyPageActivity {

    private HashMap<String,String> parameters = null;
    private EditText loginEditText;
    private EditText passEditText;
    private EditText retryPassEditText;
    private EditText fNameEditText;
    private EditText lNameEditText;
    private LearnGesture learnGesture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        learnGesture = new LearnGesture(this,null,new Intent(this, ContactSurvey.class));
        super.gestureObject = new GestureDetectorCompat(this,learnGesture);
        parameters = new HashMap<>();
        loginEditText = (EditText)this.findViewById(R.id.login_up_sur);
        passEditText = (EditText)this.findViewById(R.id.password_up_sur);
        retryPassEditText = (EditText) this.findViewById(R.id.retry_pass_sur);
        fNameEditText = (EditText)this.findViewById(R.id.first_name_sur);
        lNameEditText = (EditText)this.findViewById(R.id.last_name_sur);
        loadStatesOfEditText();
    }

    private void loadStatesOfEditText(){
        try {
            ArrayList<String> arrayList = new ArrayList<>();
            Collections.addAll(arrayList, "login", "password", "name", "lastname");
            parameters = loadStringPreferences(arrayList);
            loginEditText.setText(parameters.get("login"));
            passEditText.setText(parameters.get("password"));
            fNameEditText.setText(parameters.get("name"));
            lNameEditText.setText(parameters.get("lastname"));
        }catch (Exception ex){

        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        loadStatesOfEditText();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        addParameter(loginEditText,"login",true);
        addParameter(passEditText,"password",true);
        addParameter(fNameEditText,"name",false);
        addParameter(lNameEditText,"lastname",false);

        if (isEditTextFilled(loginEditText) && isPasswordsCorrects(passEditText,retryPassEditText) && isEditTextFilled(fNameEditText) && isEditTextFilled(lNameEditText) ) {
            addStringPreferences(parameters);
            return super.onTouchEvent(event);
        }
        return false;
    }

    private boolean isPasswordsCorrects(EditText firstPass,EditText secondPass){

        if ( addParameter(firstPass,"password",true)){
            String first = firstPass.getText().toString();
            String second = secondPass.getText().toString();
            if (first.equals(second)){
                return true;
            }else{
                secondPass.setError(getString(R.string.pass_different));
                return false;
            }
        }
        return false;
    }

    public boolean addParameter( EditText editText, String type, boolean isRequired){
        //// TODO: 18.08.2017
        // Polskie znaki nie wchodzą!!
        String text = editText.getText().toString();
        if ( isRequired && text.trim().equals("")){
            editText.setError(getString(R.string.fill_field));
            return false;
        }else{
            //
            if ( !text.matches("^[a-zA-Z0-9]*$")){
                editText.setError(getString(R.string.only_numbers_and_letter));
                text = "";
                return false;
            }else{
                this.parameters.put(type,text);
                return true;
            }
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,SignInUpActivity.class);
        startActivity(intent);
        finish();
    }
}
