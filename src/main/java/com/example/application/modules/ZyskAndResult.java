package com.example.application.modules;

public class ZyskAndResult {
    boolean used;
    double zyskValue;
    int amountValue;
    boolean fictional;

    public ZyskAndResult(boolean used, double zyskValue, int amountValue, boolean fictional) {
        this.used = used;
        this.zyskValue = zyskValue;
        this.amountValue = amountValue;
        this.fictional = fictional;
    }

    public ZyskAndResult(boolean used, double value) {
        this.used = used;
        this.zyskValue = value;
    }

    public int getAmountValue() {
        return amountValue;
    }

    public void setAmountValue(int amountValue) {
        this.amountValue = amountValue;
    }

    public boolean isFictional() {
        return fictional;
    }

    public void setFictional(boolean fictional) {
        this.fictional = fictional;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public double getZyskValue() {
        return zyskValue;
    }

    public void setZyskValue(double zyskValue) {
        this.zyskValue = zyskValue;
    }

    public void setMaxAmountValue(Dostawca dostawca,Odbiorca odbiorca){
        if(dostawca.getActualPodaż() >= odbiorca.getActualPopyt()){
            amountValue = odbiorca.getActualPopyt();
            dostawca.setActualPodaż(dostawca.getActualPodaż() - odbiorca.getActualPopyt());
            odbiorca.setActualPopyt(0);
        }else{
            amountValue = dostawca.getActualPodaż();
            dostawca.setActualPodaż(0);
            odbiorca.setActualPopyt(odbiorca.getActualPopyt() - amountValue);
        }
    }

    @Override
    public String toString() {
        return amountValue + ", ";
    }
}
