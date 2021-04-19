package com.example.application.modules;

public class Odbiorca {
    private double sprzedaż;
    private int popyt;
    private int actualPopyt;

    public Odbiorca() {
    }


    public Odbiorca(double sprzedaż, int popyt) {
        this.sprzedaż = sprzedaż;
        this.popyt = popyt;
        this.actualPopyt = popyt;
    }

    public double getSprzedaż() {
        return sprzedaż;
    }

    public void setSprzedaż(double sprzedaż) {
        this.sprzedaż = sprzedaż;
    }

    public int getPopyt() {
        return popyt;
    }

    public void setPopyt(int popyt) {
        this.popyt = popyt;
    }

    public int getActualPopyt() {
        return actualPopyt;
    }

    public void setActualPopyt(int actualPopyt) {
        this.actualPopyt = actualPopyt;
    }
}
