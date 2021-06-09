package com.example.basiccalculator;

import androidx.lifecycle.ViewModel;

public class baseCalculatorViewModel extends ViewModel {
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    String text="";

    public int getIbase() {
        return ibase;
    }

    public void setIbase(int ibase) {
        this.ibase = ibase;
    }

    int ibase=0;
}
