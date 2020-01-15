package com.example.medicinereminder;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    ListView list;


    public static ArrayList<Medicine>listmed = DataBaseController.getMedicines();


    public HomeFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionBarTitle("Home");

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        list = view.findViewById(R.id.lista1);
        MyAdapter adapter = new MyAdapter(getActivity(),listmed);
        asd(adapter);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Wybierz")
                        .setMessage("Chec dodać lek czy go zażyć?")
                        .setPositiveButton("Dodać", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                OtherMedicinesFragment fragment = new OtherMedicinesFragment();
                                FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                Bundle bundle=new Bundle();

                                bundle.putString("name",listmed.get(position).getNameMedicine());
                                bundle.putInt("position",position);
                                fragment.setArguments(bundle);
                                fragmentTransaction.replace(R.id.flMain, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        })


                        .setNegativeButton("Zażyć", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(listmed.get(position).getNrOfTablets()>=listmed.get(position).getNrOfTabletsOneTime()){
                                    DataMedicinesFragment fragment = new DataMedicinesFragment();
                                    FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    Bundle bundle=new Bundle();
                                    bundle.putString("name", listmed.get(position).getNameMedicine());
                                    bundle.putInt("tab",listmed.get(position).getNrOfTabletsOneTime());
                                    bundle.putInt("position",position);
                                    fragment.setArguments(bundle);
                                    fragmentTransaction.replace(R.id.flMain, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();



                                }else{
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Brak tabletek!")
                                            .setMessage("Dokup tabletki")
                                            .setPositiveButton("Anuluj", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                }


                            }

                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }

        });

        return view;
    }

    public void asd(final MyAdapter adapter) {
        final Handler handler = new Handler();
        handler.postDelayed( new Runnable() {

            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                handler.postDelayed( this, 60 * 1000 );
            }
        }, 60 * 1000 );
    }
    public static class MedicineView{
        TextView titleTextView;
        TextView nrOfTabTextView;
        TextView dateTextView;
        Button getButton;
    }
    public class  MyAdapter extends ArrayAdapter<Medicine>{
        private ArrayList<Medicine> Medi;
        Context context;




        public MyAdapter(Context x, ArrayList<Medicine>Medi){
            super(x,R.layout.row, R.id.titleTextView, Medi);
            this.context = x;
            this.Medi= Medi;
        }



        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            final MedicineView medicineView;
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

            final Medicine med = Medi.get(position);
            medicineView.titleTextView.setText(med.getNameMedicine());
            medicineView.titleTextView.setTextColor(Color.BLUE);
            medicineView.nrOfTabTextView.setText(med.getNrOfTablets()+"");
            medicineView.dateTextView.setText(med.getTimeToNextTake(position,listmed)+"");
            medicineView.getButton.setVisibility(View.INVISIBLE);

            /*if(med.getNrOfTablets()<med.getNrOfTabletsOneTime())
            {
                medicineView.getButton.setVisibility(View.INVISIBLE);
            }else
            {
                medicineView.getButton.setVisibility(View.VISIBLE);
            }*/
            /*medicineView.getButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(med.getNrOfTablets()>=med.getNrOfTabletsOneTime()){
                        DataMedicinesFragment fragment = new DataMedicinesFragment();
                        FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle=new Bundle();
                        bundle.putString("name", med.getNameMedicine());
                        bundle.putInt("tab",med.getNrOfTabletsOneTime());
                        bundle.putInt("position",position);
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.flMain, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        Date cr = new Date();
                        med.setDateOFLastUse(cr);
                        medicineView.getButton.setVisibility(View.INVISIBLE);
                    }else{
                        medicineView.getButton.setVisibility(View.INVISIBLE);
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Brak tabletek!")
                                .setMessage("Dokup tabletki")
                                .setPositiveButton("Anuluj", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }


                }
            });*/

            return convertView;
        }

    }

}

