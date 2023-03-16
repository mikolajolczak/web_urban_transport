package com.example.demo.Pracownik;


import java.time.LocalDate;

public class Pracownik {
    public int nrpracownika;
    public String imie;
    public String nazwisko;
    public char plec;
    public int nrbiura;
    public int nradresu;
    public int nrwynagrodzenia;
    public int nrstanowiska;
    public String nrkonta;
    public String nrtelefonu;
    public String pesel;
    public LocalDate datazatrudnienia;

    public Pracownik(){

    }

    public Pracownik(int nrpracownika, String imie, String nazwisko, char plec, int nrbiura, int nradresu, int nrwynagrodzenia, int nrstanowiska, String nrkonta, String nrtelefonu, String pesel, LocalDate datazatrudnienia) {
        this.nrpracownika = nrpracownika;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.plec = plec;
        this.nrbiura = nrbiura;
        this.nradresu = nradresu;
        this.nrwynagrodzenia = nrwynagrodzenia;
        this.nrstanowiska = nrstanowiska;
        this.nrkonta = nrkonta;
        this.nrtelefonu = nrtelefonu;
        this.pesel = pesel;
        this.datazatrudnienia = datazatrudnienia;
    }

    public int getNrpracownika() {
        return nrpracownika;
    }

    public void setNrpracownika(int nrpracownika) {
        this.nrpracownika = nrpracownika;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public char getPlec() {
        return plec;
    }

    public void setPlec(char plec) {
        this.plec = plec;
    }

    public int getNrbiura() {
        return nrbiura;
    }

    public void setNrbiura(int nrbiura) {
        this.nrbiura = nrbiura;
    }

    public int getNradresu() {
        return nradresu;
    }

    public void setNradresu(int nradresu) {
        this.nradresu = nradresu;
    }

    public int getNrwynagrodzenia() {
        return nrwynagrodzenia;
    }

    public void setNrwynagrodzenia(int nrwynagrodzenia) {
        this.nrwynagrodzenia = nrwynagrodzenia;
    }

    public int getNrstanowiska() {
        return nrstanowiska;
    }

    public void setNrstanowiska(int nrstanowiska) {
        this.nrstanowiska = nrstanowiska;
    }

    public String getNrkonta() {
        return nrkonta;
    }

    public void setNrkonta(String nrkonta) {
        this.nrkonta = nrkonta;
    }

    public String getNrtelefonu() {
        return nrtelefonu;
    }

    public void setNrtelefonu(String nrtelefonu) {
        this.nrtelefonu = nrtelefonu;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public LocalDate getDatazatrudnienia() {
        return datazatrudnienia;
    }

    public void setDatazatrudnienia(LocalDate datazatrudnienia) {
        this.datazatrudnienia = datazatrudnienia;
    }
}
