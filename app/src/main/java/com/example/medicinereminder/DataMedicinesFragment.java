package com.example.medicinereminder;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class DataMedicinesFragment extends Fragment {
    TextView nameTextView;
    TextView nrTabTextView;
    Button confirmDataMedButton;

    public DataMedicinesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).setActionBarTitle("Termin zażycia leku");
        View view = inflater.inflate(R.layout.fragment_data_medicines, container, false);

        String nameMedicine =getArguments().getString("name");
        int nrTabOneTime =getArguments().getInt("tab");
        final int pos =getArguments().getInt("position");

        nameTextView = view.findViewById(R.id.nameTextView);
        nrTabTextView = view.findViewById(R.id.nrTabTextView);
        confirmDataMedButton  = view.findViewById(R.id.confirmDataMedButton);

        nameTextView.setText(nameMedicine);
        nrTabTextView.setText(String.valueOf(nrTabOneTime));

        confirmDataMedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Potwierdzenie")
                        .setMessage("Czy na pewno zażyłeś lek?")
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Date current = new Date();
                                HomeFragment.listmed.get(pos).substractTablets();
                                HomeFragment fragment = new HomeFragment();
                                HomeFragment.listmed.get(pos).setDateOFLastUse(current);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.flMain, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        })


                        .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Anulowanie")
                                        .setMessage("Czy na pewno chcesz anulować?")
                                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                HomeFragment fragment = new HomeFragment();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.flMain, fragment);
                                                fragmentTransaction.addToBackStack(null);
                                                fragmentTransaction.commit();
                                            }
                                        })


                                        .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        return view;
    }

}
