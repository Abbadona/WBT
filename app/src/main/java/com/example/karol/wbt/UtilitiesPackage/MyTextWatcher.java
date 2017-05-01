package com.example.karol.wbt.UtilitiesPackage;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Karol on 02.03.2017.
 */

public class MyTextWatcher implements TextWatcher {

    private EditText myEditText;
    private String pattern= "^[a-zA-Z0-9]*$";
    private String text ="";

    public MyTextWatcher(EditText editText){
        this.myEditText = editText;
    }

    public void setEditText (EditText editText){
        this.myEditText = editText;
    }

    public void setPattern (String pattern){
        this.pattern = pattern;
    }

    public String getPattern(){
        return this.pattern;
    }

    public String getText(){
        return text;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if ( !myEditText.getText().toString().matches(pattern)){
            myEditText.setError("Wprowadz jedynie cyfry i liczby");
        }else{
            if ( myEditText.toString().trim().equals("")){
                myEditText.setError("Wypełnij to pole");
            }else{
                text = myEditText.getText().toString();
            }
        }

    }
}
