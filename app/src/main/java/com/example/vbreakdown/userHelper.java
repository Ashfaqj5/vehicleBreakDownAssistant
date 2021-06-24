package com.example.vbreakdown;

public class userHelper {
    int wheels;
    String ph;
    String model;
    String reg;
    String ad;
    String fuel;
    int quantity;
    public userHelper() {
    }
    public userHelper(int wheels,String ph,String model,String reg,String ad){
        this.wheels=wheels;
        this.ph=ph;
        this.model=model;
        this.reg=reg;
        this.ad=ad;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public userHelper(String ph, String reg, String fuel, int quantity) {
        this.ph = ph;
        this.reg = reg;
        this.fuel = fuel;
        this.quantity = quantity;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public int getWheels() {
        return wheels;
    }

    public String getPh() {
        return ph;
    }

    public void setWheels(int wheels) {
        this.wheels = wheels;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }
}
