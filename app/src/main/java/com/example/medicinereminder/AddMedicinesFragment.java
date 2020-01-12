package com.example.medicinereminder;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



/**
 * A simple {@link Fragment} subclass.
 */
public class AddMedicinesFragment extends Fragment {

    private Button confirmButton;
    private Spinner dateMedSpinner;
    private EditText medNameET, nrOfTabET, medOneTimeET, dateEditText;


    public AddMedicinesFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionBarTitle("Dodaj lek");
        View view = inflater.inflate(R.layout.fragment_add_medicines, container, false);

        medNameET = view.findViewById(R.id.medNameET);
        nrOfTabET = view.findViewById(R.id.nrOfTabET);
        medOneTimeET = view.findViewById(R.id.medOneTimeET);
        dateMedSpinner = view.findViewById(R.id.dateMedSpinner);
        confirmButton = view.findViewById(R.id.confirmButton);

        TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYYHHmm";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i <10; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 12) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));
                        int hour = Integer.parseInt(clean.substring(8, 10));
                        int min = Integer.parseInt(clean.substring(10, 12));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        hour = hour<1 ? 1 : hour > 24 ? 24 : hour;
                        cal.set(Calendar.HOUR, hour);
                        min = min<1 ? 1 : min > 60 ? 60 : min;
                        cal.set(Calendar.MINUTE, min);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d%02d%02d", day, mon, year,hour,min);
                    }

                    clean = String.format("%s/%s/%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8),
                            clean.substring(8,10),
                            clean.substring(10,12));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    dateEditText.setText(current);
                    dateEditText.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };


        nrOfTabET.setRawInputType(Configuration.KEYBOARD_12KEY);
        medOneTimeET.setRawInputType(Configuration.KEYBOARD_12KEY);

        dateEditText = (EditText)view.findViewById(R.id.dateEditText);
        dateEditText.addTextChangedListener(tw);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.spinner_numbers,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateMedSpinner.setAdapter(adapter);









        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    int dateMed = Integer.parseInt(dateMedSpinner.getSelectedItem().toString());
                    int nrOfTab = Integer.parseInt(nrOfTabET.getText().toString());
                    String medName = medNameET.getText().toString();
                    int medOneTime = Integer.parseInt(medOneTimeET.getText().toString());
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy/HH/mm");


                    Date dateOfFirstUse = null;
                    try {
                        dateOfFirstUse = format.parse(dateEditText.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    Medicine med = new Medicine(medName,nrOfTab,dateMed,medOneTime,dateOfFirstUse);
                    HomeFragment hm = new HomeFragment();
                    hm.list.add(med);

                    DataBaseController.createMedicine(hm.list);
                    //med.otherTablets(nrOfTab,dateMed,medOneTime,nrOfTab);
                    //Medicine med1 = new Medicine(medName,nrOfTab,dateMed,medOneTime,dateOfFirstUse, med.getDateOfLastUse(),med.getOtherTablets());

                    //DataBaseController.createMedicine(name,med.getNameMedicine(),med.getNrOfTablets(),med.getDateTakeMed(),med.getNrOfTabletsOneTime(),med.getFirstTakeMedicine(),med.getDateOfLastUse());

                    //DataBaseController.updateMedicine(name,med.getNameMedicine(),med.getDateOfLastUse(),med.substractTablets(nrOfTab,medOneTime));
                    Toast.makeText(getActivity(),"Poprawnie dodano lek",Toast.LENGTH_SHORT).show();
                    medNameET.getText().clear();
                    nrOfTabET.getText().clear();
                    medOneTimeET.getText().clear();
                    dateEditText.getText().clear();
                }

            }
        });


        return view;
    }

    private Boolean validate(){
        Boolean validate = false;

        String nrOfTab = nrOfTabET.getText().toString();
        String medName = medNameET.getText().toString();
        String medOneTime = medOneTimeET.getText().toString();

        if(nrOfTab.isEmpty() || medName.isEmpty() || medOneTime.isEmpty())
        {
            Toast.makeText(getActivity(), "Wypełnij wszystkie pola", Toast.LENGTH_SHORT).show();
        }else{
            validate = true;
        }

        return validate;
    }

}
