package com.btp_iitj.cnfmanag.Registration;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.btp_iitj.cnfmanag.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CheckedInputStream;

import static com.btp_iitj.cnfmanag.Core.MainActivityTwo.registration;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationStep1Fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    //private String str;
    public String money;
    public TextView moneyam;
    public RegistrationStep2Fragment ldf=new RegistrationStep2Fragment();
    public static EditText secEmail,secMob;
    public static Button nex, prev;
    public static Map<String,Object> myuser = new HashMap<>();
    private static final String TAG = "Suppport";
    private FirebaseFirestore db,dbx;
    public DocumentReference docref;
    Calendar c;
    public static FragmentManager fragmentManager;

    public RegistrationStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_page1, container, false);
        FirebaseAuth kAuth;
        kAuth=FirebaseAuth.getInstance();
       final String userId=kAuth.getCurrentUser().getUid();
        //Toast.makeText(getActivity(), userId, Toast.LENGTH_SHORT).show();
        nex=view.findViewById(R.id.save_page1);
        moneyam=view.findViewById(R.id.amountDisplay);
        secMob=view.findViewById(R.id.sec_contact);
       // prev=view.findViewById(R.id.prev);
        secEmail=view.findViewById(R.id.sec_email);
        final Spinner spinner = (Spinner) view.findViewById(R.id.salutation_spinner);
        spinner.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.salutation_adapter, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        final Spinner spinner2 = (Spinner) view.findViewById(R.id.package_selection_spinner);
        spinner2.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.package_adapter, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        registration.setSecemail(secEmail.getText().toString());
        registration.setSecmob(secMob.getText().toString());

        db=FirebaseFirestore.getInstance();
        db.collection("RegisteredUser").document(userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){

                            secEmail.setText(documentSnapshot.getString("secEmail"));
                            secMob.setText(documentSnapshot.getString("secMob"));
                            spinner.setSelection(1);
                            spinner2.setSelection(0);

                        }
                    }
                });




        ///handle next and previous
        nex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> myuser = new HashMap<>();
                myuser.put("RequestStatus", "N");
                ///N ka matlab application bhar raha h but not completed

                //ar String value=getArguments().getString("username");
                db.collection("RegisteredUser").document(userId)
                        .update(myuser);



                registration.setSecemail(secEmail.getText().toString());
                registration.setSecmob(secMob.getText().toString());
                db=FirebaseFirestore.getInstance();


                myuser.put("secEmail",secEmail.getText().toString());
                myuser.put("secMob",secMob.getText().toString());
                myuser.put("registrationPackage",registration.getRegPackage());
                myuser.put("salutation",registration.getSalutation());


                 //String value=getArguments().getString("username");
                docref=db.collection("RegisteredUser").document(userId);
                docref.update(myuser);
                //myuser.put("conferenceRegisteresId", conf.getName());



                fragmentManager=getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, ldf).commit() ;


            }
        });




        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String str="0 Rs";
        str=parent.getItemAtPosition(position).toString();
        money="0";
        //textView.setText(str);
        Spinner spin = (Spinner)parent;
        Spinner spin2 = (Spinner)parent;
        if(spin.getId() == R.id.salutation_spinner)

        {
            registration.setSalutation(str);

           // Toast.makeText(getActivity(), "Your choose :1",Toast.LENGTH_SHORT).show();
        }



        if(spin2.getId() == R.id.package_selection_spinner) {
            registration.setRegPackage(str);
            str=parent.getItemAtPosition(position).toString();
            if("Full Registration".equals(str))
                money="10000";
            else if("Student".equals(str))
                money="1000";
            else if("Industry Delegate".equals(str)) {
                money="15000";
            }
           // money="your cost for registration"+money;
            Bundle args= new Bundle();

            //String id=value;
            args.putString("cost",money);
            ldf.setArguments(args);





            String temp=money+" Rs.";
            moneyam.setText(temp);

            //Toast.makeText(getActivity(), "Your choose : 2", Toast.LENGTH_SHORT).show();
        }

        }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
