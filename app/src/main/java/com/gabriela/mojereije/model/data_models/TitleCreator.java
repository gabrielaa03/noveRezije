package com.gabriela.mojereije.model.data_models;

import android.content.Context;


import com.gabriela.mojereije.utils.RealmUtils;
import com.gabriela.mojereije.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

public class TitleCreator {
    private static TitleCreator titleCreator;
    private List<TitleParent> titleParentsPaid, titleParentsNonPaid;

    public TitleCreator(Context context){
        titleParentsNonPaid = new ArrayList<>();
        titleParentsPaid = new ArrayList<>();
        List<String> placeniRacuni = new ArrayList<>();
        List<String> neplaceniRacuni = new ArrayList<>();
        List<Bill> unpaidBills = RealmUtils.getUsersUnPaidBills(SharedPrefs.getSharedPrefs("username", context));
        List<Bill> paidBills = RealmUtils.getUsersPaidBills(SharedPrefs.getSharedPrefs("username", context));
        for(Bill bill : unpaidBills){
            neplaceniRacuni.add(bill.getNaziv() + ", " + bill.getMjesec());
        }
        for(Bill bill : paidBills){
            placeniRacuni.add(bill.getNaziv() + ", " + bill.getMjesec());
        }

        for(int i = 0; i<placeniRacuni.size(); i++){
            TitleParent title = new TitleParent(placeniRacuni.get(i));
            titleParentsPaid.add(title);
        }
        for(int i = 0; i<neplaceniRacuni.size(); i++){
            TitleParent title = new TitleParent(neplaceniRacuni.get(i));

            titleParentsNonPaid.add(title);
        }

    }
    public static TitleCreator get(Context context) {
        if (titleCreator == null) {
            titleCreator = new TitleCreator(context);
            return titleCreator;
        }
        return null;
    }

    public List<TitleParent> getAllPaid() {
        return titleParentsPaid;
    }
    public List<TitleParent> getAllUnPaid() {
        return titleParentsNonPaid;
    }
}
