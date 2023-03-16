package com.example.demo.Stanowisko;

public class Stanowisko {
    public int nrstanowiska;
    public String nazwa;
    public Stanowisko(){

    }

    public Stanowisko(int nrstanowiska, String nazwa) {
        this.nrstanowiska = nrstanowiska;
        this.nazwa = nazwa;
    }

    public int getNrstanowiska() {
        return nrstanowiska;
    }

    public void setNrstanowiska(int nrstanowiska) {
        this.nrstanowiska = nrstanowiska;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}
