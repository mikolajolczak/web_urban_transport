package com.example.demo.ListaPrzystankow;

import java.time.*;

public class ListaPrzystankow {
    public int nrtrasy;
    public int nrprzystanku;
    public LocalTime planowyczasodjazdu;

    public ListaPrzystankow(){

    }

    public ListaPrzystankow(int nrtrasy, int nrprzystanku, LocalTime planowyczasodjazdu) {
        this.nrtrasy = nrtrasy;
        this.nrprzystanku = nrprzystanku;
        this.planowyczasodjazdu = planowyczasodjazdu;
    }

    public int getNrtrasy() {
        return nrtrasy;
    }

    public void setNrtrasy(int nrtrasy) {
        this.nrtrasy = nrtrasy;
    }

    public int getNrprzystanku() {
        return nrprzystanku;
    }

    public void setNrprzystanku(int nrprzystanku) {
        this.nrprzystanku = nrprzystanku;
    }

    public LocalTime getPlanowyczasodjazdu() {
        return planowyczasodjazdu;
    }

    public void setPlanowyczasodjazdu(LocalTime planowyczasodjazdu) {
        this.planowyczasodjazdu = planowyczasodjazdu;
    }
}
