package com.btp_iitj.cnfmanag;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.btp_iitj.cnfmanag.Conference.Conference_Adapter;
import com.btp_iitj.cnfmanag.Domain_Classes.Conference;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class adminApplicationAdapter extends FirestoreRecyclerAdapter<AdminApplications,adminApplicationAdapter.adminApplication_holder> {

    public String temp;
    private onItemCLickListener listener;
    public adminApplicationAdapter(@NonNull FirestoreRecyclerOptions<AdminApplications> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final adminApplication_holder holder, int position, @NonNull AdminApplications model) {

        FirebaseFirestore db;
        db=FirebaseFirestore.getInstance();
        db.collection("RegisteredUser").document(model.getUserId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        temp=documentSnapshot.getString("name");
                        Log.d("paridhi gehlot is back",temp);
                        holder.cname.setText(temp);
                    }
                });

        holder.cextra.setText(model.getUserId());
        Log.d("suthar", "Model: " + model.toString());
    }

    @NonNull
    @Override
    public adminApplication_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_dashboard_cardview,viewGroup,false);
        return new adminApplication_holder(v);
    }

    class adminApplication_holder extends RecyclerView.ViewHolder{
        TextView cname,cextra;
        Button acept,rejct;
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        TextView text;

        public adminApplication_holder(@NonNull View itemView) {
            super(itemView);
            cname=itemView.findViewById(R.id.adminapplicationname);
            cextra=itemView.findViewById(R.id.extra);
           // acept=(Button)itemView.findViewById(R.id.adminConfirm);
           // rejct=(Button)itemView.findViewById(R.id.adminReject);
            text=itemView.findViewById(R.id.adminapplicationname);


            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position );
                    }

                }
            });

        }

    }
    public interface onItemCLickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemCLickLIstener(onItemCLickListener listener){
        this.listener=listener;
    }


}
