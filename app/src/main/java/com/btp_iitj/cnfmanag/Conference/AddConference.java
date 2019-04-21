package com.btp_iitj.cnfmanag.Conference;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.btp_iitj.cnfmanag.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.btp_iitj.cnfmanag.Core.MainActivityTwo.conf;

public class AddConference extends Fragment {
    private EditText name, venue, date, description,panelists;
    private Button save;
    private FirebaseFirestore db;

    public AddConference() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_conference, container, false);
        name = view.findViewById(R.id.conf_name);
        venue = view.findViewById(R.id.venue);
        date = view.findViewById(R.id.date);
        description = view.findViewById(R.id.description);
        panelists=view.findViewById(R.id.panelists);
        save = view.findViewById(R.id.SaveConf);
        db=FirebaseFirestore.getInstance();
        db.collection("CONFERENCE").document("science")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            name.setText(documentSnapshot.getString("name"));
                            date.setText(documentSnapshot.getString("date"));
                            venue.setText(documentSnapshot.getString("venue"));
                            description.setText(documentSnapshot.getString("description"));
                            panelists.setText(documentSnapshot.getString("panelists"));

                        }
                    }
                });


        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                conf.setName(name.getText().toString());
                conf.setDate(date.getText().toString());
                conf.setVenue(venue.getText().toString());
                conf.setDescription(description.getText().toString());
                if(name.getText().toString().isEmpty()){
                    name.setError("Name is Required");
                    name.requestFocus();
                    return;
                }
                else  if(date.getText().toString().isEmpty()){
                    date.setError("Date is Required");
                    date.requestFocus();
                    return;
                }
                else if(venue.getText().toString().isEmpty()){
                    venue.setError("Venue is Required");
                    venue.requestFocus();
                    return;
                }
                else  if(description.getText().toString().isEmpty()){
                    description.setError("Description is Required");
                    description.requestFocus();
                    return;
                }

                else {

                    Map<String, Object> conference = new HashMap<>();
                    db = FirebaseFirestore.getInstance();
                    conference.put("name", conf.getName());
                    conference.put("venue", conf.getVenue());
                    conference.put("date", conf.getDate());
                    conference.put("description", conf.getDescription());
                    conference.put("panelists",panelists.getText().toString());
                    Toast.makeText(getActivity(), "Data successfully Saved", Toast.LENGTH_SHORT).show();


                    db.collection("CONFERENCE").document("science")
                            .update(conference);
                }
            }
        });

        return view;
    }

}
