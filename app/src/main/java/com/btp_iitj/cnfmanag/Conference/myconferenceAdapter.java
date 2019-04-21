package com.btp_iitj.cnfmanag.Conference;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.btp_iitj.cnfmanag.Domain_Classes.Conference;
import com.btp_iitj.cnfmanag.Domain_Classes.Registration;
import com.btp_iitj.cnfmanag.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class myconferenceAdapter extends FirestoreRecyclerAdapter<Registration,myconferenceAdapter.myconference_holder> {


    private myconferenceAdapter.onItemCLickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public myconferenceAdapter(@NonNull FirestoreRecyclerOptions<Registration> options) {
        super(options);
    }
    //private static TextView tt;



    @Override
    protected void onBindViewHolder(@NonNull myconferenceAdapter.myconference_holder holder, final int position, @NonNull Registration model) {
        holder.cname.setText(model.getName());


    }

    @NonNull
    @Override
    public myconferenceAdapter.myconference_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_text_view,viewGroup,false);
        return new myconference_holder(v);
    }
    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class myconference_holder extends RecyclerView.ViewHolder{
        TextView cname, cdate, cvenue,parid;
        public myconference_holder(@NonNull View itemView) {
            super(itemView);
            cname=itemView.findViewById(R.id.tv1);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listener!=null)
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);

                }
            });

        }

    }
    public interface onItemCLickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemCLickLIstener(myconferenceAdapter.onItemCLickListener listener){
        this.listener=listener;
    }
}
