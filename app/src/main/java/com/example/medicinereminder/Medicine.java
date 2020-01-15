package com.example.medicinereminder;

import android.util.Log;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Medicine {

    private String nameMedicine;
    private int nrOfTablets;
    private int dateTakeMed;
    private int nrOfTabletsOneTime;
    private Date firstTakeMedicine;
    private Date dateOfLastUse;



    public Medicine(String nameMedicine, int nrOfTablets, int dateTakeMed, int nrOfTabletsOneTime, Date firstTakeMedicine){
        this.nameMedicine = nameMedicine;
        this.nrOfTablets = nrOfTablets;
        this.dateTakeMed = dateTakeMed;
        this.nrOfTabletsOneTime = nrOfTabletsOneTime;
        this.firstTakeMedicine = firstTakeMedicine;
        this.dateOfLastUse = firstTakeMedicine;
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

    public int substractTablets(){
        nrOfTablets -=nrOfTabletsOneTime;
        return nrOfTablets;
    }

    public String getTimeToNextTake(int i, ArrayList<Medicine>arr){
        int timeToTakeMed = arr.get(i).getDateTakeMed();
        Date lastDateTakeMed =arr.get(i).getDateOfLastUse();
        Date currentDate = new Date();


            Calendar cal = Calendar.getInstance();
            cal.setTime(lastDateTakeMed);
            cal.add(Calendar.HOUR, timeToTakeMed);
            Duration diff = Duration.between(currentDate.toInstant(), cal.getTime().toInstant());

            secToTime(diff.getSeconds());



        return secToTime(diff.getSeconds());


    }
    String secToTime(long sec) {
        long seconds = sec % 60;
        long minutes = sec / 60;
        if (minutes >= 60) {
            long hours = minutes / 60;
            minutes %= 60;
            if( hours >= 24) {
                long days = hours / 24;
                return String.format("%d days %02d:%02d", days,hours%24, minutes );
            }
            return String.format("%02d:%02d", hours, minutes );
        }
        return String.format("00:%02d", minutes );
    }

    public Date countMedicineDate(int nrOfTablets,int dateTakeMed,int nrOfTabletsOneTime,Date firstTakeMedicine){
        Calendar cal = Calendar.getInstance();
        cal.setTime(firstTakeMedicine);
        Date predictableDateOfLastUse = null;

        cal.add(Calendar.HOUR_OF_DAY,dateTakeMed*(nrOfTablets/nrOfTabletsOneTime));
        predictableDateOfLastUse = cal.getTime();

        return predictableDateOfLastUse;

    }


    public Date getDateOfLastUse() {
        return dateOfLastUse;
    }
    public void setDateOFLastUse(Date dateOfLastUse){
        this.dateOfLastUse = dateOfLastUse;
    }
    public void setTablets(int nrOfTablets){
        this.nrOfTablets += nrOfTablets;
    }


}
