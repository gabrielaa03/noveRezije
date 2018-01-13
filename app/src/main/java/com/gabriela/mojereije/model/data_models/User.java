package com.gabriela.mojereije.model.data_models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    @PrimaryKey
    private String username;
    private String name, addr, email, pass;
    private String datumPodsjetnika;

    public User(String username, String name, String addr, String email, String pass, String datumPlace) {
        this.username = username;
        this.name = name;
        this.addr = addr;
        this.email = email;
        this.pass = pass;
        this.datumPodsjetnika = datumPlace;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDatumPodsjetnika() {
        return datumPodsjetnika;
    }

    public void setDatumPodsjetnika(String datumPodsjetnika) {
        this.datumPodsjetnika = datumPodsjetnika;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                //", listOfBillsCategory=" + listOfBillsCategory +
                ", datumPodsjetnika=" + datumPodsjetnika +
                '}';
    }
}
