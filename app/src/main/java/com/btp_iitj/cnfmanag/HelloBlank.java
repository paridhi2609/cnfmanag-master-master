package com.btp_iitj.cnfmanag;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.btp_iitj.cnfmanag.Core.MainActivityTwo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelloBlank extends Fragment {
    public TextView textView;
    private FirebaseFirestore db;
    ProgressBar progressBar;


    public HelloBlank() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_hello_blank, container, false);
        textView=view.findViewById(R.id.fstatus);
        FirebaseAuth kAuth;
        progressBar=view.findViewById(R.id.progressbarblankFragment);
        kAuth=FirebaseAuth.getInstance();
        final String userId=kAuth.getCurrentUser().getUid();
        db=FirebaseFirestore.getInstance();
        progressBar.setVisibility(View.VISIBLE);
        db.collection("RegisteredUser").document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String checking=document.getString("RequestStatus");
                                // Toast.makeText(MainActivityTwo.this, "step1", Toast.LENGTH_SHORT).show();
                                if("Y".equals(checking))
                                { textView.setText("Your Application is Acceptetd!");}
                                    //Toast.makeText(MainActivityTwo.this, "Your Applicataion is accepted!", Toast.LENGTH_SHORT).show();}
                                else if("R".equals(checking))
                                {
                                    textView.setText("Your have applied for a Seat and Your application is in Process!");
                                }

                                else if("rejected".equals(checking)) {
                                    textView.setText("Your applicataion is rejected!");
                                }
                                    //oast.makeText(MainActivityTwo.this, "Your application is Rejected!", Toast.LENGTH_SHORT).show();}
                                else if("N".equals(checking)){
                                    textView.setText("You have not yet applied for a Seat in Conference");
                                    //Toast.makeText(MainActivityTwo.this, "You have not applied for a Seat in Conference", Toast.LENGTH_SHORT).show();
                                }
                                  progressBar.setVisibility(View.GONE);

                                // Log.d("pass", document.getString("userId"));
                                Log.d("pass",document.getString("RequestStatus"));
                            } else {
                                textView.setText("You have Not Applied for a seat in conference Yet!");

                                Log.d("fail", "Document does not exist!");
                            }
                        } else {
                            Log.d("ultra", "Failed with: ", task.getException());
                        }
                    }
                });
        return view;
    }

}
