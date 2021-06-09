package com.example.basiccalculator;

import androidx.lifecycle.ViewModel;

public class dateViewModel extends ViewModel {
    String anstext="";
    public String getText()
    {
        return anstext;
    }
    public void setText(String setValue)
    {
        anstext=setValue;
    }
    String topText="Enter Date";
    public String getTopText()
    {
        return topText;
    }
    public void setTopText(String setValue)
    {
        topText=setValue;
    }

    boolean agecal=false;
    public boolean isAgecal() {
        return agecal;
    }

    public void setAgecal(boolean agecal) {
        this.agecal = agecal;
    }

    boolean datecal=false;
    public boolean isDatecal() {
        return datecal;
    }

    public void setDatecal(boolean datecal) {
        this.datecal = datecal;
    }


    boolean after=false;
    public boolean isAfter() {
        return after;
    }

    public void setAfter(boolean after) {
        this.after = after;
    }

    boolean before=false;
    public boolean isBefore() {
        return before;
    }

    public void setBefore(boolean before) {
        this.before = before;
    }



}
