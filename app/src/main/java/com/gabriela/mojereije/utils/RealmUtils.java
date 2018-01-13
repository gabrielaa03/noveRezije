package com.gabriela.mojereije.utils;

import android.widget.EditText;


import com.gabriela.mojereije.model.App;
import com.gabriela.mojereije.model.data_models.Bill;
import com.gabriela.mojereije.model.data_models.User;

import java.util.HashMap;
import java.util.List;

import io.realm.Realm;

//import com.google.firebase.database.DataSnapshot;

public class RealmUtils {

    public static List<User> getUsers() {
        Realm realm = App.getRealmInstance();
        return realm.where(User.class).findAll();
    }

    public static User checkIfUserExists(String databaseElement, String value) {
        Realm realm = App.getRealmInstance();
        User user = realm.where(User.class).equalTo(databaseElement, value).findFirst();
        if (user != null) {
            return user;
        }
        return null;
    }

    public static String getPass(User user) {
        return user.getPass();
    }

    public static String getEmail(User user) {
        return user.getEmail();
    }

    public static String getName(User user) {
        return user.getName();
    }

    public static String getDatumPodsjetnika(User user) {
        return user.getDatumPodsjetnika();
    }

    public static User createUser(EditText username, EditText imeIPrezime, EditText adresa, EditText email, EditText pass, EditText placa) {
        return new User(username.getText().toString(), imeIPrezime.getText().toString(), adresa.getText().toString(),
                email.getText().toString(), pass.getText().toString(), placa.getText().toString());
    }

    public static void saveUser(User user) {
        Realm realm = App.getRealmInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }

    public static void saveUsersBills(Bill bill, String username) {
        Realm realm = App.getRealmInstance();
        realm.beginTransaction();
        List<Bill> listOfBills = realm.copyFromRealm(realm.where(Bill.class).equalTo("user", username).findAll());
        if (listOfBills != null) {
            listOfBills.add(bill);
            realm.copyToRealmOrUpdate(listOfBills);
        }
        realm.commitTransaction();
    }

    public static List<Bill> getUsersBills(String value) {
        Realm realm = App.getRealmInstance();
        realm.beginTransaction();
        List<Bill> bills = realm.copyFromRealm(realm.where(Bill.class).equalTo("user", value).findAll());
        realm.commitTransaction();
        return bills;
    }

    public static List<Bill> getUsersPaidBills(String value) {
        Realm realm = App.getRealmInstance();
        realm.beginTransaction();
        List<Bill> bills = realm.copyFromRealm(realm.where(Bill.class).equalTo("user", value).equalTo("stanje", "rb_placen").findAll());
        realm.commitTransaction();
        return bills;
    }
    public static List<Bill> getUsersUnPaidBills(String value) {
        Realm realm = App.getRealmInstance();
        realm.beginTransaction();
        List<Bill> bills = realm.copyFromRealm(realm.where(Bill.class).equalTo("user", value).equalTo("stanje", "rb_neplacen").findAll());
        realm.commitTransaction();
        return bills;
    }

    public static HashMap<String, String> getAllMonthsAndTheirValues(String value){
        Realm realm = App.getRealmInstance();
        realm.beginTransaction();
        List<Bill> bills = realm.copyFromRealm(realm.where(Bill.class).equalTo("user", value).findAll());
        HashMap<String, String> hashMap = new HashMap<>();
        for(Bill bill : bills){
            String[] date = bill.getMjesec().split("/");
            String month = date[1];
            hashMap.put(month, bill.getIznos());
        }
        realm.commitTransaction();
        return hashMap;
    }

}
