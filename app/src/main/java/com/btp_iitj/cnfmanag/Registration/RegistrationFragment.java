package com.btp_iitj.cnfmanag.Registration;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.btp_iitj.cnfmanag.Core.MainActivityTwo;
import com.btp_iitj.cnfmanag.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.btp_iitj.cnfmanag.Conference.allConferencesFragment.fragmentmanager;
import static com.btp_iitj.cnfmanag.Core.MainActivityTwo.registration;

public class RegistrationFragment extends Fragment {
    public static EditText name, dob, mobile, email;
    private FirebaseAuth mAuth;
    public TextView textView;
    public static Button save;
    private static final String TAG = "Suppport";
    public DocumentReference docref;
    public static FragmentManager fragmentManager;
    private FirebaseFirestore db,dbx;
    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_profile, container, false);
        FirebaseAuth kAuth;
        textView=view.findViewById(R.id.registerednam);
        kAuth=FirebaseAuth.getInstance();
        final String userId=kAuth.getCurrentUser().getUid();

        name=view.findViewById(R.id.uname);
        textView.setText(name.getText().toString());

        dob=view.findViewById(R.id.udob);
        mobile= view.findViewById(R.id.uphone);
        email=view.findViewById(R.id.uemail);
        save=view.findViewById(R.id.save_user);

        db=FirebaseFirestore.getInstance();
        db.collection("RegisteredUser").document(userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            name.setText(documentSnapshot.getString("name"));
                            dob.setText(documentSnapshot.getString("dob"));
                            email.setText(documentSnapshot.getString("email"));
                            mobile.setText(documentSnapshot.getString("phone"));
                        }
                    }
                });







        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(name.getText().toString().isEmpty()){
                    name.setError("Name is Required");
                    name.requestFocus();
                    return;
                }
                else  if(dob.getText().toString().isEmpty()){
                    dob.setError("DOB is Required");
                    dob.requestFocus();
                    return;
                }
                else  if(mobile.getText().toString().isEmpty()){
                    mobile.setError("Mobile is Required");
                    mobile.requestFocus();
                    return;
                }
                else  if(email.getText().toString().isEmpty()){
                    email.setError("Email is Required");
                    email.requestFocus();
                    return;
                }
                else
                {

                    registration.setName(name.getText().toString());
                    registration.setDob(dob.getText().toString());
                    registration.setEmail(email.getText().toString());
                    registration.setPhone(mobile.getText().toString());
                    db = FirebaseFirestore.getInstance();
                    Map<String, Object> myuser = new HashMap<>();
                    myuser.put("name", registration.getName());
                    myuser.put("userId", userId);
                    myuser.put("phone", registration.getPhone());
                    myuser.put("dob", registration.getDob());
                    myuser.put("email", registration.getEmail());
                    myuser.put("RequestStatus", "N");
                    //String dalna;
                    //dalna = mAuth.getCurrentUser().getUid();
                    db.collection("RegisteredUser").document(userId)
                            .update(myuser);
                    //myuser.put("conferenceRegisteresId", conf.getName());

                    //final String id=docref.getId();
                    Intent intent = new Intent(getActivity(), MainActivityTwo.class);
                    //intent.putExtra("documentId", id);
                    intent.putExtra("username", name.getText().toString());
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }


            }
        });


        return view;
    }

}
