package com.example.demo.Adres;

import java.util.Objects;

public class Adres {
    public int nradresu;
    public String miasto;
    public String ulica;
    public String nrbudynku;
    public String nrlokalu;
    public String kodpocztowy;
    public Adres(){

    }

    public Adres(int nradresu, String miasto, String ulica, String nrbudynku, String nrlokalu,String kodpocztowy) {
        this.nradresu = nradresu;
        this.miasto = miasto;
        this.ulica = ulica;
        this.nrbudynku = nrbudynku;
        this.nrlokalu = nrlokalu;
        this.kodpocztowy = kodpocztowy;
    }

    public int getNradresu() {
        return nradresu;
    }

    public void setNradresu(int nradresu) {
        this.nradresu = nradresu;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getNrbudynku() {
        return nrbudynku;
    }

    public void setNrbudynku(String nrbudynku) {
        this.nrbudynku = nrbudynku;
    }

    public String getNrlokalu() {
        if(Objects.isNull(this.nrlokalu))
            return "";
        return nrlokalu;
    }

    public void setNrlokalu(String nrlokalu) {
        this.nrlokalu = nrlokalu;
    }

    public String getKodpocztowy() {
        if(Objects.isNull(this.kodpocztowy))
            return "";
        return kodpocztowy;
    }

    public void setKodpocztowy(String kodpocztowy) {
        this.kodpocztowy = kodpocztowy;
    }
}
