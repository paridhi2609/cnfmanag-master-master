package com.btp_iitj.cnfmanag.Registration;


import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.btp_iitj.cnfmanag.HelloBlank;
import com.btp_iitj.cnfmanag.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.btp_iitj.cnfmanag.Conference.ConferenceDetailsFragment.v;
import static com.btp_iitj.cnfmanag.Core.MainActivityTwo.registration;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationStep3Fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    public static Button finalsave,finalback;
    private FirebaseFirestore db;
    public TextView datethree;
    public static FragmentManager fragmentManager;
    public static EditText bankNam, ifsccod, transid,modofpayment;
    public DatePickerDialog dpd;
    public static ImageView datePIcker;
    public Calendar c;

    public RegistrationStep3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_page3, container, false);
        finalback=view.findViewById(R.id.finalBack);
        finalsave=view.findViewById(R.id.finalSubmit);
        FirebaseAuth kAuth;
        kAuth=FirebaseAuth.getInstance();
        final String userId=kAuth.getCurrentUser().getUid();
        bankNam=view.findViewById(R.id.bankname);
        transid=view.findViewById(R.id.transacIdEDit);
        ifsccod=view.findViewById(R.id.ifscCode);
        datePIcker=view.findViewById(R.id.datePicker);
        datethree=view.findViewById(R.id.datethree);

        registration.setIfscCode(ifsccod.getText().toString());
        registration.setBankName(bankNam.getText().toString());
        Spinner spinner = (Spinner) view.findViewById(R.id.payOptSpinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.paymentInforItems, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        finalsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transid.getText().toString().isEmpty()) {
                    transid.setError("Transaction Id is Required");
                    transid.requestFocus();
                    return;
                } else if (bankNam.getText().toString().isEmpty()) {
                    bankNam.setError("Bank Name is Required");
                    bankNam.requestFocus();
                    return;
                } else if (ifsccod.getText().toString().isEmpty()) {
                    ifsccod.setError("IFSC code is Required");
                    ifsccod.requestFocus();
                    return;
                } else {
                    db = FirebaseFirestore.getInstance();
                    registration.setTransId(transid.getText().toString());
                    //Toast.makeText(getActivity(), registration.getTransId(), Toast.LENGTH_SHORT).show();
                    //registration.setTransDate(registration.getTransDate());
                    registration.setBankName(bankNam.getText().toString());
                    registration.setIfscCode(ifsccod.getText().toString());
                    Map<String, Object> myuser = new HashMap<>();
                    myuser.put("paymentMode", registration.getPaymentMode());
                    myuser.put("TransId", registration.getTransId());
                    myuser.put("BankName", registration.getBankName());
                    myuser.put("IfscCode", registration.getIfscCode());
                    myuser.put("RequestStatus","R");
                    myuser.put("transDate", registration.getTransDate());
                    db.collection("RegisteredUser").document(userId)
                            .update(myuser);
                    Toast.makeText(getActivity(), "Request made for a seat!", Toast.LENGTH_LONG).show();

                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, new HelloBlank()).commit();
                }
            }
        });
        finalback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getActivity().getSupportFragmentManager();
               fragmentManager.beginTransaction().replace(R.id.fragment_container,new RegistrationStep2Fragment()).commit();
                //fragmentManager.popBackStackImmediate();
            }
        });
        datePIcker.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    c=Calendar.getInstance();
                    int date=c.get(Calendar.DAY_OF_MONTH);
                    int month=c.get(Calendar.MONTH);
                    int year=c.get(Calendar.YEAR);
                    dpd=new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            registration.setTransDate(dayOfMonth+"/"+month+"/"+year);
                            datethree.setText(registration.getTransDate());

                        }
                    },date,month,year);

                    dpd.show();
                }
            });



        return view;
    }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String str= parent.getItemAtPosition(position).toString();
            registration.setPaymentMode(str);
            //Toast.makeText(getActivity(), "Item selected for payment Option", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }



}
