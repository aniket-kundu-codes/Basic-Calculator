package com.example.basiccalculator;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.lifecycle.ViewModel;

public class investmentViewModel extends ViewModel {
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    String text="";

    boolean si=false;
    public boolean isSi() {
        return si;
    }

    public void setSi(boolean si) {
        this.si =si;
        this.sip=false;
        this.ci=false;
        this.cisip=false;
    }
    boolean sip=false;
    public boolean isSip() {
        return sip;
    }

    public void setSip(boolean sip) {
        this.si =false;
        this.sip=sip;
        this.ci=false;
        this.cisip=false;
    }


    boolean ci=false;
    public boolean isCi() {
        return ci;
    }

    public void setCi(boolean ci) {
        this.si =false;
        this.sip=false;
        this.ci=ci;
        this.cisip=false;
    }


    boolean cisip=false;
    public boolean isCisip() {
        return cisip;
    }

    public void setCisip(boolean cisip) {
        this.si =false;
        this.sip=false;
        this.ci=false;
        this.cisip=cisip;
    }



}
