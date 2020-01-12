package com.example.medicinereminder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Medicine {

    private String nameMedicine;
    private int nrOfTablets;
    private int dateTakeMed;
    private int nrOfTabletsOneTime;
    private Date firstTakeMedicine;
    private Date dateOfLastUse;
    public static String name= null;



    public Medicine(String nameMedicine, int nrOfTablets, int dateTakeMed, int nrOfTabletsOneTime, Date firstTakeMedicine){
        this.nameMedicine = nameMedicine;
        this.nrOfTablets = nrOfTablets;
        this.dateTakeMed = dateTakeMed;
        this.nrOfTabletsOneTime = nrOfTabletsOneTime;
        this.firstTakeMedicine = firstTakeMedicine;
        this.dateOfLastUse = countMedicineDate(nrOfTablets,dateTakeMed,nrOfTabletsOneTime,firstTakeMedicine);
    }
    public Medicine(String nameMedicine, int nrOfTablets, int dateTakeMed, int nrOfTabletsOneTime, Date firstTakeMedicine, Date dateOfLastUse){
        this.nameMedicine = nameMedicine;
        this.nrOfTablets = nrOfTablets;
        this.dateTakeMed = dateTakeMed;
        this.nrOfTabletsOneTime = nrOfTabletsOneTime;
        this.firstTakeMedicine = firstTakeMedicine;
        this.dateOfLastUse = dateOfLastUse;
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

    public int substractTablets(int nrOfTablets,int nrOfTabletsOneTime){
        nrOfTablets -=nrOfTabletsOneTime;
        return nrOfTablets;
    }

    public Date countMedicineDate(int nrOfTablets,int dateTakeMed,int nrOfTabletsOneTime,Date firstTakeMedicine){
        int number = nrOfTablets;
        Calendar cal = Calendar.getInstance();
        cal.setTime(firstTakeMedicine);

        for(int i = nrOfTablets; i>=0; i-=nrOfTabletsOneTime)
        {
            cal.add(Calendar.HOUR_OF_DAY,dateTakeMed*(nrOfTablets/nrOfTabletsOneTime)+1);
            dateOfLastUse = cal.getTime();
            return dateOfLastUse;

        }

        return dateOfLastUse;

    }

    public Date getDateOfLastUse() {
        return dateOfLastUse;
    }

}
