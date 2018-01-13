package com.gabriela.mojereije.utils;

import android.widget.EditText;
import android.widget.Spinner;

public class Credentials {
    public static boolean checkCredentials(EditText et){
        if(et.getText().toString().isEmpty()){
            return true;
        }return false;
    }

    public static boolean checkCredentialsSpinner(Spinner spinner){
        if(spinner.getSelectedItem().toString().isEmpty()){
            return true;
        }return false;
    }
}
