package com.gabriela.mojereije.utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DateUtils {

    public static boolean isTodaysDate(Context context) {
        DateFormat formater = new SimpleDateFormat("dd", Locale.getDefault());
        String currentDate = formater.format(new Date());
        String date = RealmUtils.getDatumPodsjetnika(RealmUtils.checkIfUserExists("username", SharedPrefs.getSharedPrefs("username", context)));
        return Objects.equals(currentDate, date);
    }
}
