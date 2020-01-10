package com.example.medicinereminder;

import java.util.Date;

public class Medicine {

    private String nameMedicine;
    private int nrOfTablets;
    private int dateTakeMed;
    private int nrOfTabletsOneTime;
    private Date firstTakeMedicine;

    public Medicine(String nameMedicine, int nrOfTablets, int dateTakeMed, int nrOfTabletsOneTime, Date firstTakeMedicine){
        this.nameMedicine = nameMedicine;
        this.nrOfTablets = nrOfTablets;
        this.dateTakeMed = dateTakeMed;
        this.nrOfTabletsOneTime = nrOfTabletsOneTime;
        this.firstTakeMedicine = firstTakeMedicine;
    }

    public String getNameMedicine() {
        return nameMedicine;
    }

    public int getNrOfTablets() {
        return nrOfTablets;
    }

    public int getDateTakeMed() {
        return dateTakeMed;
    }

    public int getNrOfTabletsOneTime() {
        return nrOfTabletsOneTime;
    }

    public Date getFirstTakeMedicine() {
        return firstTakeMedicine;
    }

}
