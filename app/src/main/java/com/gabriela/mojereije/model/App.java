package com.gabriela.mojereije.model;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

//import com.google.firebase.database.FirebaseDatabase;

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
//    public static FirebaseDatabase getFirebaseDb() {
//        return FirebaseDatabase.getInstance();
//    }

}
