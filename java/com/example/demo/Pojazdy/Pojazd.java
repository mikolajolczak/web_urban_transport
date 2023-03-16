package com.example.demo.Pojazdy;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Pojazd {
    public int nrpojazdu;
    public String marka;
    public Integer rokprodukcji;
    public String model;
    public String nrrejestracyjny;
    public LocalDate datazakupu;
    public LocalDate dataubezpieczenia;
    public int nrbiura;
    public Pojazd(){

    }

    public Pojazd(int nrpojazdu, String marka, int rokprodukcji, String model, String nrrejestracyjny, LocalDate datazakupu, LocalDate dataubezpieczenia, int nrbiura) {
        this.nrpojazdu = nrpojazdu;
        this.marka = marka;
        this.rokprodukcji = rokprodukcji;
        this.model = model;
        this.nrrejestracyjny = nrrejestracyjny;
        this.datazakupu = datazakupu;
        this.dataubezpieczenia = dataubezpieczenia;
        this.nrbiura = nrbiura;
    }

    public int getNrpojazdu() {
        return nrpojazdu;
    }

    public void setNrpojazdu(int nrpojazdu) {
        this.nrpojazdu = nrpojazdu;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public Integer getRokprodukcji() {
        return rokprodukcji;
    }

    public void setRokprodukcji(int rokprodukcji) {
        this.rokprodukcji = rokprodukcji;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNrrejestracyjny() {
        return nrrejestracyjny;
    }

    public void setNrrejestracyjny(String nrrejestracyjny) {
        this.nrrejestracyjny = nrrejestracyjny;
    }

    public LocalDate getDatazakupu() {
        return datazakupu;
    }

    public void setDatazakupu(LocalDate datazakupu) {
        this.datazakupu = datazakupu;
    }

    public LocalDate getDataubezpieczenia() {
        return dataubezpieczenia;
    }

    public void setDataubezpieczenia(LocalDate dataubezpieczenia) {
        this.dataubezpieczenia = dataubezpieczenia;
    }

    public int getNrbiura() {
        return nrbiura;
    }

    public void setNrbiura(int nrbiura) {
        this.nrbiura = nrbiura;
    }
}
