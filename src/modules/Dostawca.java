package com.company.modules;

public class Dostawca {
    private double cenaZakupu;
    private int podaż;
    private int actualPodaż;


    public Dostawca() {
    }

    public Dostawca(double cenaZakupu, int podaż) {
        this.cenaZakupu = cenaZakupu;
        this.podaż = podaż;
        this.actualPodaż = podaż;
    }

    public double getCenaZakupu() {
        return cenaZakupu;
    }

    public void setCenaZakupu(double cenaZakupu) {
        this.cenaZakupu = cenaZakupu;
    }

    public int getPodaż() {
        return podaż;
    }

    public void setPodaż(int podaż) {
        this.podaż = podaż;
    }

    public int getActualPodaż() {
        return actualPodaż;
    }

    public void setActualPodaż(int actualPodaż) {
        this.actualPodaż = actualPodaż;
    }
}
