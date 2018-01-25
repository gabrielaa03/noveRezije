package com.gabriela.mojereije.utils;

import android.support.annotation.NonNull;
import android.widget.EditText;


import com.gabriela.mojereije.model.App;
import com.gabriela.mojereije.model.data_models.Bill;
import com.gabriela.mojereije.model.data_models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmUtils {

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

    public static void setPass(User user, String novaLozz) {
        Realm realm = App.getRealmInstance();
        realm.beginTransaction();
        user.setPass(novaLozz);
        realm.commitTransaction();
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

    public static void setDatumPodsjetnika(User user, String noviDatum) {
        Realm realm = App.getRealmInstance();
        realm.beginTransaction();
        user.setDatumPodsjetnika(noviDatum);
        realm.commitTransaction();
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

    public static void saveUsersBills(List<Bill> bills) {
        Realm realm = App.getRealmInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(bills);
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

    public static HashMap<String, String> getAllMonthsAndTheirValues(String value) {
        Realm realm = App.getRealmInstance();
        realm.beginTransaction();
        List<Bill> bills = realm.copyFromRealm(realm.where(Bill.class).equalTo("user", value).findAll());
        HashMap<String, String> hashMap = new HashMap<>();
        for (Bill bill : bills) {
            String[] date = bill.getMjesec().split("/");
            String month = date[1];
            hashMap.put(month, bill.getIznos());
        }
        realm.commitTransaction();
        return hashMap;
    }

    public static void changeBill(String brojRacuna, String username) {
        Realm realm = App.getRealmInstance();
        List<Bill> bills = realm.copyFromRealm(realm.where(Bill.class).equalTo("user", username).equalTo("stanje", "rb_neplacen").findAll());
        realm.beginTransaction();
        for (Bill bill1 : bills) {
          if(bill1.getBrojRacuna().equals(brojRacuna)){
              bill1.setStanje("rb_placen");
              realm.copyToRealmOrUpdate(bills);
          }
        }
        realm.commitTransaction();
    }

    public static void deleteItem(String brojRacuna, String username) {
        Realm realm = App.getRealmInstance();
        realm.beginTransaction();
        Bill bill = realm.where(Bill.class).equalTo("user", username).equalTo("brojRacuna", brojRacuna).findFirst();
        if (bill != null) {
            bill.deleteFromRealm();
        }
        realm.commitTransaction();
    }
}
