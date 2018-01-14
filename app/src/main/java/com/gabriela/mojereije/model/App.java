package com.gabriela.mojereije.model;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration defaultConfig = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(defaultConfig);
    }

    public static Realm getRealmInstance() {
        return Realm.getDefaultInstance();
    }
}
