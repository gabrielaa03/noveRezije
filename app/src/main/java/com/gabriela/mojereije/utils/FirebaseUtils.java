package com.gabriela.mojereije.utils;//package com.example.gabrielaangebrandt.mojerezije.utils;
//
//import android.os.Build;
//import android.provider.SyncStateContract;
//import android.support.annotation.RequiresApi;
//
//import com.example.gabrielaangebrandt.mojerezije.App;
//import com.example.gabrielaangebrandt.mojerezije.model.data_models.Bill;
//import com.example.gabrielaangebrandt.mojerezije.model.data_models.User;
////import com.google.firebase.database.DataSnapshot;
////import com.google.firebase.database.DatabaseReference;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Objects;
//
//public class FirebaseUtils {
//
//    public static DatabaseReference getFirebaseReference() {
//        return App.getFirebaseDb().getReference();
//    }
//
//    public static List<User> getUsers(DataSnapshot dataSnapshot) {
//        List<User> listOfUser = new ArrayList<>();
//        for (DataSnapshot dataSnapshot1 : dataSnapshot.child("USERS").getChildren()) {
//            User user = dataSnapshot1.getValue(User.class);
//            if (user != null) {
//                listOfUser.add(user);
//            }
//        }
//        return listOfUser;
//    }
////
////    public static List<Bill> getBills(DataSnapshot dataSnapshot){
////        List<Bill> listOfBills = new ArrayList<>();
////        for (DataSnapshot dataSnapshot1 : dataSnapshot.child(BILL).getChildren()) {
////            Bill bill = dataSnapshot1.getValue(Bill.class);
////            if (bill != null) {
////                listOfBills.add(bill);
////            }
////        }
////        return listOfBills;
////    }
//
//    public static void writeNewUser(String username, String name, String addr, String email, String pass, E<Bill> list, String datumPlace, List<String> categoryList) {
//        User user = new User(username, name, addr, email, pass, list, datumPlace, categoryList);
//        getFirebaseReference().child("USERS").child(username).setValue(user);
//    }
//
//    public static User checkIfUserExists(String username, DataSnapshot dataSnapshot) {
//        List<User> listOfUsers = getUsers(dataSnapshot);
//        for (User user : listOfUsers) {
//            if (user.getUsername().equals(username)) {
//                return user;
//            }
//        }
//        return null;
//    }
//}
