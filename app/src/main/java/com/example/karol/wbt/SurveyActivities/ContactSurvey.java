package com.example.karol.wbt.SurveyActivities;
import com.example.karol.wbt.R;
import com.example.karol.wbt.SignUpActivity;
import com.example.karol.wbt.UtilitiesPackage.LearnGesture;
import com.example.karol.wbt.UtilitiesPackage.MySurveyPageActivity;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.widget.EditText;
import android.view.MotionEvent;
import java.util.HashMap;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.util.ArrayList;
import java.util.Collections;

public class ContactSurvey extends MySurveyPageActivity{
    private EditText phone;
    private EditText email;
    private RadioGroup options;
    private RadioButton optPhone;
    private RadioButton optMail;
    private HashMap<String,String> parameters;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_survey);
        super.gestureObject = new GestureDetectorCompat(this, new LearnGesture(
                                                        this, new Intent(this, SignUpActivity.class),
                                                        new Intent(this, UserParametersSurvey.class)));
        phone = (EditText)findViewById(R.id.number_text);
        email = (EditText)findViewById(R.id.email_text);
        options = (RadioGroup)findViewById(R.id.verification_choice);
        optMail = (RadioButton)findViewById(R.id.email_opt);
        optPhone = (RadioButton)findViewById(R.id.phone_opt);
        loadSavedData();
    }

    private void loadSavedData(){
        ArrayList<String> data = new ArrayList<>();
        Collections.addAll(data, "phone", "email", "verify_way");
        String savedOption = loadSingleString("verify_way");
        parameters = loadStringPreferences(data);
        phone.setText(parameters.get("phone"));
        email.setText(parameters.get("email"));
        if(optPhone.getText().equals(savedOption))
            optPhone.setChecked(true);
        else if(optMail.getText().equals(savedOption))
            optMail.setChecked(true);
    }

    public boolean addParameter(EditText field, String type){
        String text = field.getText().toString();
        if(text.isEmpty()){
            field.setError("Wypełnij pole");
            return false;
        }
        else{
            this.parameters.put(type, text);
            return true;
        }
    }

    public boolean onTouchEvent(MotionEvent event){
        addParameter(phone, "phone");
        addParameter(email, "email");
        int selectedId = options.getCheckedRadioButtonId();

        if(isEditTextFilled(phone) && selectedId != -1 &&
           android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
            RadioButton selectedButton = (RadioButton)findViewById(selectedId);
            String selectedOption = selectedButton.getText().toString();
            this.parameters.put("verify_way", selectedOption);
            addStringPreferences(parameters);
            return super.onTouchEvent(event);
        }
        return false;
    }
}
