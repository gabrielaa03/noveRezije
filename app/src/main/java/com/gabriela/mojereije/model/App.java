package com.gabriela.mojereije.model;

import android.app.Application;

import com.gabriela.mojereije.utils.DateUtils;
import com.gabriela.mojereije.utils.NotificationUtils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration defaultConfig = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(defaultConfig);

        if (DateUtils.isTodaysDate(this)) {
            NotificationUtils.issueNotification(this);
        }
    }

    public static Realm getRealmInstance() {
        return Realm.getDefaultInstance();
    }
}
