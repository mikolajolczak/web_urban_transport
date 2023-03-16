package com.example.demo.Przystanek;

public class Przystanek {
    public int nrprzystanku;
    public String nazwa;
    public int nradresu;

    public Przystanek(){

    }

    public Przystanek(int nrprzystanku, String nazwa, int nradresu) {
        this.nrprzystanku = nrprzystanku;
        this.nazwa = nazwa;
        this.nradresu = nradresu;
    }

    public int getNrprzystanku() {
        return nrprzystanku;
    }

    public void setNrprzystanku(int nrprzystanku) {
        this.nrprzystanku = nrprzystanku;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getNradresu() {
        return nradresu;
    }

    public void setNradresu(int nradresu) {
        this.nradresu = nradresu;
    }
}
