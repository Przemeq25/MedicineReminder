package com.example.medicinereminder;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    ListView list;



    ArrayList<Medicine> listmed=  DataBaseController.getMedicines();
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionBarTitle("Home");

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        list = view.findViewById(R.id.lista1);
        MyAdapter adapter = new MyAdapter(getActivity(),listmed);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //do zrobienia bedzie przechodzic do fragmentu OtherMedicinesFragment i tam beda wypisane wszystkie informacje o kliknietym leku
            }

        });

        return view;
    }
    public static class MedicineView{
        TextView titleTextView;
        TextView nrOfTabTextView;
        TextView dateTextView;
        Button getButton;
    }
    public class  MyAdapter extends ArrayAdapter<Medicine>{
        private ArrayList<Medicine> Medicines;
        Context context;


        public MyAdapter(Context x, ArrayList<Medicine>Medicines){
            super(x,R.layout.row, R.id.titleTextView, Medicines);
            this.context = x;
            this.Medicines= Medicines;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            MedicineView medicineView;
            if(convertView == null)
            {
                medicineView = new MedicineView();
                convertView = inflater.inflate(R.layout.row,null,true);

                medicineView.titleTextView = convertView.findViewById(R.id.titleTextView);
                medicineView.nrOfTabTextView = convertView.findViewById(R.id.nrOfTabTextView);
                medicineView.dateTextView = convertView.findViewById(R.id.dateTextView);
                medicineView.getButton = convertView.findViewById(R.id.getButton);
                convertView.setTag(medicineView);
            }else{
                medicineView = (MedicineView) convertView.getTag();
            }

            Medicine med = Medicines.get(position);
            medicineView.titleTextView.setText(med.getNameMedicine());
            medicineView.titleTextView.setTextColor(Color.BLUE);
            medicineView.nrOfTabTextView.setText(med.getNrOfTablets()+"");
            medicineView.dateTextView.setText(med.getDateOfLastUse().toString());
            medicineView.getButton.setVisibility(View.INVISIBLE);
            if(med.getNrOfTablets()< 20){
                medicineView.nrOfTabTextView.setTextColor(Color.RED);
                medicineView.getButton.setVisibility(View.VISIBLE);
            }

            return convertView;
        }

    }

}

