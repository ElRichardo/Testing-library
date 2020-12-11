package com.choicely.learn.testingapp.sulkeiset;

public class Lamppu {//tämä luokka tietää onko se päällä ja se tietää myös värin

    private int color;
    private boolean isActive;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
