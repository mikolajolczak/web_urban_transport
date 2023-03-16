package com.example.demo.Biuro;

public class Biuro {
    public int nrbiura;
    public String nazwa;
    public int nradresu;
    public Biuro(){

    }

    public Biuro(int nrbiura, String nazwa, int nradresu) {
        this.nrbiura = nrbiura;
        this.nazwa = nazwa;
        this.nradresu = nradresu;
    }

    public int getNrbiura() {
        return nrbiura;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getNradresu() {
        return nradresu;
    }

    public void setNrbiura(int nrbiura) {
        this.nrbiura = nrbiura;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setNradresu(int nradresu) {
        this.nradresu = nradresu;
    }

    @Override
    public String toString() {
        return "Biuro{" +
                "nrbiura=" + nrbiura +
                ", nazwa='" + nazwa + '\'' +
                ", nradresu=" + nradresu +
                '}';
    }
}
