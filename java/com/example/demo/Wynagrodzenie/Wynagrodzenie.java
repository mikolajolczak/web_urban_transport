package com.example.demo.Wynagrodzenie;

public class Wynagrodzenie {
    public int nrwynagrodzenia;
    public float wysokoscwynagrodzenia;
    public Wynagrodzenie(){

    }

    public Wynagrodzenie(int nrwynagrodzenia, float wysokoscwynagrodzenia) {
        this.nrwynagrodzenia = nrwynagrodzenia;
        this.wysokoscwynagrodzenia = wysokoscwynagrodzenia;
    }

    public int getNrWynagrodzenia() {
        return nrwynagrodzenia;
    }

    public void setNrWynagrodzenia(int nrwynagrodzenia) {
        this.nrwynagrodzenia = nrwynagrodzenia;
    }

    public float setWysokoscWynagrodzenia() {
        return wysokoscwynagrodzenia;
    }

    public void setWysokoscWynagrodzenia(float wysokoscwynagrodzenia) {
        this.wysokoscwynagrodzenia = wysokoscwynagrodzenia;
    }
}
