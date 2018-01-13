package com.gabriela.mojereije.utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DateUtils {

    private static final String TAG = "Luka";

    public static boolean isTodaysDate(Context context) {
        DateFormat format = new SimpleDateFormat("dd", Locale.getDefault());
        Date currentDate = new Date();
        String currDate = format.format(currentDate);
        String date = RealmUtils.getDatumPodsjetnika(RealmUtils.checkIfUserExists("username", SharedPrefs.getSharedPrefs("username", context)));
        if (Objects.equals(currDate, date)) {
            return true;
        }
        return false;
    }
}
