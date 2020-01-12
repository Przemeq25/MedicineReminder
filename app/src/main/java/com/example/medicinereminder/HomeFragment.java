package com.example.medicinereminder;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private Button pokButton;
    Medicine med;
    ArrayList<Medicine> list=  DataBaseController.getMedicines();
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity)getActivity()).setActionBarTitle("Home");

        
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        pokButton = view.findViewById(R.id.pokButton);
        pokButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        TableLayout stk = view.findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(getActivity());
        stk.addView(tbrow0);
        for (int i = 0; i <list.size(); i++) {
            TableRow tbrow = new TableRow(getActivity());
            TextView t1v = new TextView(getActivity());
            t1v.setText(list.get(i).getNameMedicine()+"");
            t1v.setTextColor(Color.parseColor("#673AB7"));
            t1v.setGravity(Gravity.LEFT);
            t1v.setWidth(500);
            tbrow.addView(t1v);
            TextView t2v = new TextView(getActivity());
            t2v.setText(list.get(i).getNrOfTablets()+"");
            t2v.setTextColor(Color.parseColor("#673AB7"));
            t2v.setGravity(Gravity.LEFT);
            t2v.setWidth(280);
            tbrow.addView(t2v);
            TextView t3v = new TextView(getActivity());
            t3v.setText(list.get(i).getDateOfLastUse()+"");
            t3v.setTextColor(Color.parseColor("#673AB7"));
            t3v.setGravity(Gravity.LEFT);
            t3v.setWidth(250);
            tbrow.addView(t3v);

            stk.addView(tbrow);
        }
        
        return view;
    }
    


}
