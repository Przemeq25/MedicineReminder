package com.example.medicinereminder;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class OtherMedicinesFragment extends Fragment {
    TextView nameMedTextView;
    TextView nrOfTabET2;
    Button updateMedButton;

    public OtherMedicinesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).setActionBarTitle("Pozostałe leki");
         View view = inflater.inflate(R.layout.fragment_other_medicines, container, false);
        String nameMedicine =getArguments().getString("name");
        final int pos =getArguments().getInt("position");




        nameMedTextView = view.findViewById(R.id.nameMedTextView);
        nrOfTabET2 = view.findViewById(R.id.nrOfTabET2);
        updateMedButton  = view.findViewById(R.id.updateMedButton);
        nameMedTextView.setText(nameMedicine);

        updateMedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nrOfTabET2.getText().toString().isEmpty() || Integer.parseInt(nrOfTabET2.getText().toString())<0){
                    Toast.makeText(getActivity(),"Błąd",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Dodano pomyślnie",Toast.LENGTH_SHORT).show();
                    HomeFragment.listmed.get(pos).setTablets(Integer.parseInt(nrOfTabET2.getText().toString()));
                    HomeFragment fragment = new HomeFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.flMain, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }

            }
        });
        return view;
    }

}
