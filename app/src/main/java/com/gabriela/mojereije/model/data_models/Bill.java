package com.gabriela.mojereije.model.data_models;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Bill extends RealmObject {
    @PrimaryKey
    private String brojRacuna;
    private String tvrtka, user, stanje, iznos, naziv, mjesec;

    public Bill(String user, String mjesec, String brojRacuna, String naziv, String tvrtka, String iznos, String stanje) {
        this.user = user;
        this.tvrtka = tvrtka;
        this.brojRacuna = brojRacuna;
        this.stanje = stanje;
        this.iznos = iznos;
        this.mjesec = mjesec;
        this.naziv = naziv;
    }

    public Bill() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getTvrtka() {
        return tvrtka;
    }

    public void setTvrtka(String tvrtka) {
        this.tvrtka = tvrtka;
    }

    public String getBrojRacuna() {
        return brojRacuna;
    }

    public void setBrojRacuna(String brojRacuna) {
        this.brojRacuna = brojRacuna;
    }

    public String getStanje() {
        return stanje;
    }

    public void setStanje(String stanje) {
        this.stanje = stanje;
    }

    public String getIznos() {
        return iznos;
    }

    public void setIznos(String iznos) {
        this.iznos = iznos;
    }

    public String getMjesec() {
        return mjesec;
    }

    public void setMjesec(String mjesec) {
        this.mjesec = mjesec;
    }
}
