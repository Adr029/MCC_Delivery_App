package com.example.mcc_deliveryapp.User;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mcc_deliveryapp.R;
import com.example.mcc_deliveryapp.Rider.model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
public class record_adapter2 extends FirebaseRecyclerAdapter<
        model, record_adapter2.recordViewholder> {

    String userNum, userName;

    public record_adapter2(
            @NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }
    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull recordViewholder holder,
                     int position, @NonNull model model)
    {


        holder.receivercontact.setText(model.getReceivercontact());
        holder.receivername.setText(model.getReceivername());
        holder.receiverlocation.setText(model.getReceiverlocation());
        holder.sendercontact.setText(model.getSendercontact());
        holder.sendername.setText(model.getSendername());
        holder.senderlocation.setText(model.getSenderlocation());
//		holder.vehicletype.setText(model.getVehicletype());
        holder.fee.setText(model.getFee());
        holder.orderID.setText(model.getOrderID());
//		holder.customernotes.setText("Notes:" + model.getCustomerNotes());
    }

    // Function to tell the class about the Card view
    // which the data will be shown
    @NonNull
    @Override
    public recordViewholder onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userrecordlayoutdesign, parent, false);
        return new recordViewholder(view);
    }

    public String getUserNum(String userNum){
        this.userNum = userNum;
        return userNum;
    }

    public String getUserName(String userName){
        this.userName = userName;
        return userName;
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class recordViewholder
            extends RecyclerView.ViewHolder {
        TextView receivercontact,receiverlocation,receivername,sendercontact,senderlocation,
                sendername, vehicletype, customernotes,fee, orderID;
        Context context;
        public recordViewholder(@NonNull View itemView)
        {
            super(itemView);

            receivercontact = itemView.findViewById(R.id.txt_receiver_contact);
            receiverlocation = itemView.findViewById(R.id.txt_receiver_loc);
            receivername = itemView.findViewById(R.id.txt_receiver_name);
//			customernotes = itemView.findViewById(R.id.txt_note);
            sendercontact = itemView.findViewById(R.id.txt_sender_contact);
            senderlocation = itemView.findViewById(R.id.txt_sender_loc);
            sendername = itemView.findViewById(R.id.txt_sender_name);
//			vehicletype = itemView.findViewById(R.id.txt_vehicletype);
            fee = itemView.findViewById(R.id.txt_price);
            orderID = itemView.findViewById(R.id.user_home_orderID);
            CardView cv = (CardView) itemView.findViewById(R.id.user_record_card);

            context = itemView.getContext();

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Card clicked!");
                    Intent intent = new Intent(context, user_completed_order_details.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("phonenum", userNum);
                    intent.putExtra("username", userName);
                    intent.putExtra("orderID", orderID.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }
}


