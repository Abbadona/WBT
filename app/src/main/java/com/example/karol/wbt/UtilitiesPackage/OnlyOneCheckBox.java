package com.example.karol.wbt.UtilitiesPackage;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karol on 07.03.2017.
 */

public class OnlyOneCheckBox implements CheckBox.OnCheckedChangeListener {

    List<CheckBox> checkBoxList;

    public OnlyOneCheckBox(CheckBox checkBox){

        checkBoxList = new ArrayList<>();
        checkBoxList.add(checkBox);
    }

    public OnlyOneCheckBox(CheckBox checkBox1,CheckBox checkBox2,CheckBox checkBox3){

        checkBoxList = new ArrayList<>();
        checkBoxList.add(checkBox1);
        checkBoxList.add(checkBox2);
        checkBoxList.add(checkBox3);


    }
    public OnlyOneCheckBox(List<CheckBox> list){
        this.checkBoxList = list;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            for (CheckBox checkBox : checkBoxList){
                checkBox.setChecked(false);
                checkBox.animate();
            }


        }
    }
}
