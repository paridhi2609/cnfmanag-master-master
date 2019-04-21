package com.btp_iitj.cnfmanag.Registration;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
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

import com.btp_iitj.cnfmanag.Core.MainActivityTwo;
import com.btp_iitj.cnfmanag.MainActivity;
import com.btp_iitj.cnfmanag.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.btp_iitj.cnfmanag.Core.MainActivityTwo.registration;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationStep2Fragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public Calendar c;
    public String accomRe;
    public  Calendar c2;
    public String userId;
    String primaryAmount;
    public TextView t,n;
    public EditText d;
    public String info;
    public String numdays;
    public Spinner spinner2;
    private FirebaseFirestore db;
    public static ImageView datePIcker,departDate;
    public static FragmentManager fragmentManager;
    public DatePickerDialog dpd,dpd2;
    public static Button nxt,pre;
    public TextView date1,date2;
    public TextView amnt;

    public RegistrationStep2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_page2, container, false);
        nxt =view.findViewById(R.id.nextpage2);
        pre=view.findViewById(R.id.prevpage2);
        date1=view.findViewById(R.id.dateone);
        date2=view.findViewById(R.id.datetwo);
        t=view.findViewById(R.id.accomTypetext);
        amnt=view.findViewById(R.id.twoamountDisplay);
        n=view.findViewById(R.id.numda);
        d=view.findViewById(R.id.numdays);
        numdays=d.getText().toString();
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);


        FirebaseAuth kAuth;
        kAuth=FirebaseAuth.getInstance();
         userId=kAuth.getCurrentUser().getUid();
        final Spinner spinner = (Spinner) view.findViewById(R.id.transportSpinner);
        spinner.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.transport_adapter, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
         spinner2 = (Spinner) view.findViewById(R.id.accomodationTypeSpinner);
        spinner2.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.accomadapter, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);
        db=FirebaseFirestore.getInstance();
        db.collection("RegisteredUser").document(userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            date1.setText(documentSnapshot.getString("ArrivalDate"));
                            date2.setText(documentSnapshot.getString("DepartureDate"));
                            spinner.setSelection(1);
                            spinner2.setSelection(1);
                            radioGroup.check(R.id.no);

                        }
                    }
                });
        t.setVisibility(View.GONE);
        spinner2.setVisibility(View.GONE);
        n.setVisibility(View.GONE);
        d.setVisibility(View.GONE);
        datePIcker=view.findViewById(R.id.datePickr);
        departDate=view.findViewById(R.id.departureDate);
        datePIcker.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                c=Calendar.getInstance();
                int date=c.get(Calendar.DAY_OF_MONTH);
                int month=c.get(Calendar.MONTH)+1;
                int year=c.get(Calendar.YEAR);
                dpd=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        registration.setArrDate(dayOfMonth+"/"+month+1+"/"+year);
                        date1.setText(registration.getArrDate());

                    }
                },date,month,year);

                dpd.show();
            }
        });
        departDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                c2=Calendar.getInstance();
                int date=c2.get(Calendar.DAY_OF_MONTH);
                int month=c2.get(Calendar.MONTH)+1;
                int year=c2.get(Calendar.YEAR);
                dpd2=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        registration.setDepartDate(dayOfMonth+"/"+month+"/"+year);
                        date2.setText(registration.getDepartDate());

                    }
                },date,month,year);

                dpd2.show();
            }
        });

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=FirebaseFirestore.getInstance();

                Map<String,Object> myuser = new HashMap<>();
                myuser.put("modeOFtransport",registration.getModeOfTrans());
                myuser.put("accomodation",registration.getAccomodation());
                myuser.put("ArrivalDate",registration.getArrDate());
                myuser.put("DepartureDate",registration.getDepartDate());
                myuser.put("accomType",info);

                RegistrationStep3Fragment ldf = new RegistrationStep3Fragment();

                db.collection("RegisteredUser").document(userId)
                            .update(myuser);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, ldf).commit();

            }
        });
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager=getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new RegistrationStep1Fragment()).commit();
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
                dpd=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        registration.setArrDate(dayOfMonth+"/"+month+"/"+year);
                        date1.setText(registration.getArrDate());

                    }
                },date,month,year);

                dpd.show();
            }
        });
        departDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                c2=Calendar.getInstance();
                int date=c2.get(Calendar.DAY_OF_MONTH);
                int month=c2.get(Calendar.MONTH);
                int year=c2.get(Calendar.YEAR);
                dpd2=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        registration.setDepartDate(dayOfMonth+"/"+month+"/"+year);
                        date2.setText(registration.getDepartDate());

                    }
                },date,month,year);

                dpd2.show();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.yes:
                        // switch to fragment 1
                        registration.setAccomodation("Y");
                        t.setVisibility(View.VISIBLE);
                        spinner2.setVisibility(View.VISIBLE);
                        n.setVisibility(View.VISIBLE);
                        d.setVisibility(View.VISIBLE);
                        accomRe="Y";
                        //view.findViewById(R.id.accomTypetext);
                        //Toast.makeText(getActivity(), "Yes selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.no:
                        // Fragment 2
                        t.setVisibility(View.GONE);
                        spinner2.setVisibility(View.GONE);
                        n.setVisibility(View.GONE);
                        accomRe="N";
                        d.setVisibility(View.GONE);
                        registration.setAccomodation("N");
                        //Toast.makeText(getActivity(), "NO selected", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        return view;

    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String str= parent.getItemAtPosition(position).toString();
        //String amount = "";
        Spinner spin = (Spinner)parent;
        Spinner spin2 = (Spinner)parent;
        if(spin.getId() == R.id.accomodationTypeSpinner)

        {
            info=str;
            ////define categories
            if("N".equals(accomRe)){
                //pichhle wale frament se data uthana hai


            }
            else if("R".equals(accomRe)) {
                if ("Bussiness".equals(info)) {
                    //amount="6000";
                    //int temp=6000*Integer.parseInt(numdays)+8000;
                    // amnt.setText(String.valueOf(temp));
                    amnt.setText("1");
                } else if ("Luxury".equals(info)) {
                    //amount="4000";
                    amnt.setText("2");
                    //int temp=4000*Integer.parseInt(numdays)+8000;
                    //amnt.setText(String.valueOf(temp));
                } else if ("Economy".equals(info)) {
                    // amount="2000";
                    amnt.setText("3");
                    //int temp=6000*Integer.parseInt(numdays)+8000;
                    //amnt.setText(String.valueOf(temp));
                } else {
                    //amnt.setText("0");
                }
            }








            // Toast.makeText(getActivity(), "Your choose :1",Toast.LENGTH_SHORT).show();
        }


        if(spin2.getId() == R.id.transportSpinner) {
            registration.setModeOfTrans(str);

            //Toast.makeText(getActivity(), "Your choose : 2", Toast.LENGTH_SHORT).show();
        }


        //Toast.makeText(getActivity(), "Item selected for payment Option", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
