package com.btp_iitj.cnfmanag.Conference;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.btp_iitj.cnfmanag.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.btp_iitj.cnfmanag.Domain_Classes.Conference;

public class Conference_Adapter extends FirestoreRecyclerAdapter<Conference,Conference_Adapter.conference_holder> {

    private onItemCLickListener listener;
    //private static TextView tt;

    public Conference_Adapter(@NonNull FirestoreRecyclerOptions<Conference> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull conference_holder holder, final int position, @NonNull Conference model) {
        holder.cname.setText(model.getName());
        holder.cdate.setText("Date: "+model.getDate());
        holder.cvenue.setText("Venue:  "+model.getVenue());
        holder.parid.setText(String.valueOf(position+1));




    }

    @NonNull
    @Override
    public conference_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_text_view,viewGroup,false);
        return new conference_holder(v);
    }
    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class conference_holder extends RecyclerView.ViewHolder{
    TextView cname, cdate, cvenue,parid;
        public conference_holder(@NonNull View itemView) {
            super(itemView);
            cname=itemView.findViewById(R.id.tv1);
            cdate=itemView.findViewById(R.id.tv2);
            cvenue=itemView.findViewById(R.id.tv3);
            parid=itemView.findViewById(R.id.myImageViewText);

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
    public void setOnItemCLickLIstener(onItemCLickListener listener){
        this.listener=listener;
    }
}
