package com.gabriela.mojereije.utils;

import android.widget.EditText;
import android.widget.Spinner;

public class Credentials {
    public static boolean checkCredentials(EditText et){
        return et.getText().toString().isEmpty();
    }

    public static boolean checkCredentialsSpinner(Spinner spinner){
        return spinner.getSelectedItem().toString().isEmpty();
    }
}
