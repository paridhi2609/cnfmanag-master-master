package com.btp_iitj.cnfmanag.Conference;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.btp_iitj.cnfmanag.R;

import static com.btp_iitj.cnfmanag.Core.MainActivityTwo.conf;

public class aboutConferenceFragment extends Fragment {

    private static final String TAG = "Suppport";
    public static TextView a,b,c,d;


    public aboutConferenceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about_conference, container, false);
        a=view.findViewById(R.id.Aname);
        b=view.findViewById(R.id.Adate);
        c=view.findViewById(R.id.Avenue);
       // d=view.findViewsWithText(Adesc);
        a.setText(conf.getName());
        b.setText(conf.getDate());
        c.setText(conf.getVenue());
        d.setText(conf.getDescription());
        return view;
    }

}
