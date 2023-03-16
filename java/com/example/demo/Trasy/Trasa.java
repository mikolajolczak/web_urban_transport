package com.example.demo.Trasy;

public class Trasa {
    public int nrtrasy;
    public String linia;
    public Trasa(){

    }

    public Trasa(int nrtrasy, String linia) {
        this.nrtrasy = nrtrasy;
        this.linia = linia;
    }

    public int getNrtrasy() {
        return nrtrasy;
    }

    public void setNrtrasy(int nrtrasy) {
        this.nrtrasy = nrtrasy;
    }

    public String getLinia() {
        return this.linia;
    }

    public void setLinia(String linia) {
        this.linia = linia;
    }
}
