package com.btp_iitj.cnfmanag;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class acceptedRequests extends Fragment {
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference docref=db.collection("RegisteredUser");
    private adminApplicationAdapter adapter;
    Button b1,b2;

    public acceptedRequests() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_accepted_requests, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CollectionReference collectionReference=db.collection("RegisteredUser");
        Query query=collectionReference.whereEqualTo("RequestStatus","Y");
        FirestoreRecyclerOptions<AdminApplications> options = new FirestoreRecyclerOptions.Builder<AdminApplications>()
                .setQuery(query, AdminApplications.class).build();
        adapter = new adminApplicationAdapter(options);
        adapter.startListening();
        RecyclerView recyclerView = view.findViewById(R.id.acceptedRequestsFragment);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemCLickLIstener(new adminApplicationAdapter.onItemCLickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String id=documentSnapshot.getId();
                Bundle args=new Bundle();
                UserProfileAdmin ldf=new UserProfileAdmin();
                args.putString("userId",id);
                ldf.setArguments(args);
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container,ldf).addToBackStack(null).commit();
                //Toast.makeText(getActivity(), "this is paridhi gehlot", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
