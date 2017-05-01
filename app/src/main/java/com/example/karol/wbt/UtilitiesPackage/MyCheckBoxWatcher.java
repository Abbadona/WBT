package com.example.karol.wbt.UtilitiesPackage;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;

/**
 * Created by Karol on 03.03.2017.
 */

public class MyCheckBoxWatcher implements TextWatcher {

    private CheckBox myCheckBox;

    private CheckBox oppCheckBox;

    public MyCheckBoxWatcher(CheckBox myCheckBox,CheckBox opposite){

        this.myCheckBox = myCheckBox;

        this.oppCheckBox = opposite;

    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (myCheckBox.isChecked()){
            oppCheckBox.setChecked(false);
        }else{
            oppCheckBox.setChecked(true);
        }
    }
}
