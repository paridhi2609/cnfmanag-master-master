package com.btp_iitj.cnfmanag.Conference;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.btp_iitj.cnfmanag.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.btp_iitj.cnfmanag.Domain_Classes.Conference;


public class allConferencesFragment extends Fragment{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("CONFERENCE");
    private Conference_Adapter adapter;
    private static final String TAG = "Suppport";
    public static FragmentManager fragmentmanager;
    public allConferencesFragment() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_all_conferences, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Query query = db.collection("CONFERENCE");
        FirestoreRecyclerOptions<Conference> options = new FirestoreRecyclerOptions.Builder<Conference>()
                .setQuery(query, Conference.class).build();
        adapter = new Conference_Adapter(options);
        adapter.startListening();
        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
        adapter.setOnItemCLickLIstener(new Conference_Adapter.onItemCLickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Conference conferenceItem= documentSnapshot.toObject(Conference.class);
                String id= documentSnapshot.getId();
                Toast.makeText(getActivity(), "position"+position+
                        "ID:"+id, Toast.LENGTH_SHORT).show();
                fragmentmanager = getActivity().getSupportFragmentManager();
                ConferenceDetailsFragment ldf= new ConferenceDetailsFragment();
                Bundle args= new Bundle();
                args.putString("documentId",id);
                ldf.setArguments(args);
                fragmentmanager.beginTransaction().replace(R.id.fragment_container,ldf).addToBackStack("ldf").commit();
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
