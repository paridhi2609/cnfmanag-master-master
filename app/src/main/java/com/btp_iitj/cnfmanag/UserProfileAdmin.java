package com.btp_iitj.cnfmanag;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileAdmin extends Fragment {

    private FirebaseFirestore db;
    public TextView mdepart,mname,mdob,memail,mmobile,msecemail,msecmobile,msalutation,mpackage,mpaymentmode, mtransid,mtransdate, mbankname,mifsccodem,mmodeoftrans,marrivaldate,maccomodation;
    public String str;
    Button bl,br;


    public UserProfileAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_profile_admin, container, false);
        bl=view.findViewById(R.id.adminReject);
        br=view.findViewById(R.id.adminConfirm);
        mname=view.findViewById(R.id.adname);
        mdepart=view.findViewById(R.id.addepartur);
        mdob=view.findViewById(R.id.addob);
        memail=view.findViewById(R.id.ademail);
        mmobile=view.findViewById(R.id.admobile);
        msecemail=view.findViewById(R.id.adsecemail);
        msecmobile=view.findViewById(R.id.adsecmob);
        msalutation=view.findViewById(R.id.adsalutation);
        mpackage=view.findViewById(R.id.adpackage);
        mpaymentmode=view.findViewById(R.id.adpaymentmode);
        mtransid=view.findViewById(R.id.adtransid);
        mtransdate=view.findViewById(R.id.adtransdate);
        mbankname=view.findViewById(R.id.adbnkname);
        mifsccodem=view.findViewById(R.id.adifsccode);
        mmodeoftrans=view.findViewById(R.id.admodeoftrans);
        marrivaldate=view.findViewById(R.id.adarrival);
        maccomodation=view.findViewById(R.id.adaccomreq);

        db=FirebaseFirestore.getInstance();
        //FirebaseAuth kAuth;a
        //kAuth=FirebaseAuth.getInstance();
        String userId=getArguments().getString("userId");
        DocumentReference docRef = db.collection("RegisteredUser").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        str=document.getString("name");
                        mname.setText(str);
                        mdob.setText(document.getString("dob"));
                        memail.setText(document.getString("email"));
                        mmobile.setText(document.getString("phone"));
                        msecemail.setText(document.getString("secEmail"));
                        msecmobile.setText(document.getString("secMob"));
                        msalutation.setText(document.getString("salutation"));
                        mpaymentmode.setText(document.getString("paymentMode"));
                        mtransid.setText(document.getString("TransId"));
                        mtransdate.setText(document.getString("transDate"));
                        mbankname.setText(document.getString("BankName"));
                        mifsccodem.setText(document.getString("IfscCode"));
                        mmodeoftrans.setText(document.getString("modeOFtransport"));
                        marrivaldate.setText(document.getString("ArrivalDate"));
                        maccomodation.setText(document.getString("accomodation"));
                        mdepart.setText(document.getString("DepartureDate"));


                        Log.d("gehloti",str);


                    } else {
                        // Log.d(TAG, "No such document");
                    }
                } else {
                    // Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String str=text.getText().toString();
                String str=getArguments().getString("userId");
                Map<String,Object> myuser = new HashMap<>();
                myuser.put("RequestStatus","rejected");
                db.collection("RegisteredUser").document(str).update(myuser);
                Toast.makeText(getActivity(), "Rejected!", Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                fragmentManager.popBackStackImmediate();
            }
        });
        br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=getArguments().getString("userId");
                Map<String,Object> myuser = new HashMap<>();
                myuser.put("RequestStatus","Y");
                db.collection("RegisteredUser").document(str).update(myuser);
                Toast.makeText(getActivity(), "Accepted!", Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                fragmentManager.popBackStackImmediate();


            }
        });
        return view;
    }


}
