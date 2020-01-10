package com.example.medicinereminder;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    Medicine med;
    public HomeFragment() {
        // Required empty public constructor
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity)getActivity()).setActionBarTitle("Home");

        
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TableLayout stk =(TableLayout)view.findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(getActivity());
        /*TextView tv0 = new TextView(getActivity());
        tv0.setText(" Lek ");
        tv0.setTextColor(Color.WHITE);
        tv0.setWidth(400);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(getActivity());
        tv1.setText(" Pozostałe \n tabletki ");
        tv1.setTextColor(Color.WHITE);
        tv1.setWidth(200);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getActivity());
        tv2.setText(" Data \n wyczerpania ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);*/

        stk.addView(tbrow0);
        for (int i = 0; i < 3; i++) {
            TableRow tbrow = new TableRow(getActivity());
            TextView t1v = new TextView(getActivity());
            t1v.setText(""+i);
            t1v.setTextColor(Color.GREEN);
            t1v.setGravity(Gravity.LEFT);
            t1v.setWidth(500);
            tbrow.addView(t1v);
            TextView t2v = new TextView(getActivity());
            t2v.setText("" + i);
            t2v.setTextColor(Color.GREEN);
            t2v.setGravity(Gravity.LEFT);
            t2v.setWidth(280);
            tbrow.addView(t2v);
            TextView t3v = new TextView(getActivity());
            t3v.setText("Rs." + i);
            t3v.setTextColor(Color.GREEN);
            t3v.setGravity(Gravity.LEFT);
            t3v.setWidth(250);
            tbrow.addView(t3v);

            stk.addView(tbrow);
        }
        
        return view;
    }
    


}
