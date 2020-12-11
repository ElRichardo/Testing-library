package com.choicely.learn.testingapp.sulkeiset;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class LiikenneValot {    //tietää vaan monta valoa on (valoja on viisi näytöllä)

    List<Lamppu> list = new ArrayList<>();

    public void createLights() {
        Lamppu lamppu1 = new Lamppu();
        lamppu1.setColor(Color.GREEN);
        lamppu1.setActive(true);

        list.add(lamppu1);

        Lamppu lamppu2 = new Lamppu();
        lamppu2.setColor(Color.YELLOW);
        lamppu2.setActive(false);

        list.add(lamppu2);

        Lamppu lamppu3 = new Lamppu();
        lamppu3.setColor(Color.RED);
        lamppu3.setActive(false);

        list.add(lamppu3);

        Lamppu lamppu4 = new Lamppu();
        lamppu4.setColor(Color.BLUE);
        lamppu4.setActive(false);

        list.add(lamppu4);

        Lamppu lamppu5 = new Lamppu();
        lamppu5.setColor(Color.BLACK);
        lamppu5.setActive(false);

        list.add(lamppu5);
    }

    private StatusChange statusListener;

    public void updateStatus() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isActive()) {
                //if the last lamppu is active, set the first to active
                if (i + 1 == list.size()) {
                    list.get(i).setActive(false);
                    list.get(0).setActive(true);
                } else {
                    //set the current inactive and the next one active
                    list.get(i).setActive(false);
                    list.get(i + 1).setActive(true);
                }
                //break so that it would stop going through the list if it finds an active lamp
                break;
            }
        }
        statusListener.onStatusChanged();
    }

    public void setStatusChange(StatusChange statusChange) {
        statusListener = statusChange;
    }

    public interface StatusChange {

        void onStatusChanged();
    }
}
