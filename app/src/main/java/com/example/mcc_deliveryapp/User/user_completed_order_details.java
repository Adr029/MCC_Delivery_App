package com.example.mcc_deliveryapp.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mcc_deliveryapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class user_completed_order_details extends AppCompatActivity {

    String name, phonenum, orderID, riderName, riderVehicle, senderName, senderLocation, senderContact,
            receiverName, receiverLocation, receiverContact, vehicleType, senderNote, orderPrice;
    TextView senderloc, sendername, sendercontact, receiverloc, receivername, receivercontact,
            order_id, rider_name, vehicletype, usernote, parcelprice;
    Button btn_cancelOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_completed_order_details);

        Intent intent = getIntent();
        name = intent.getStringExtra("username");
        phonenum = intent.getStringExtra("phonenum");
        orderID = intent.getStringExtra("orderID");

        senderloc = findViewById(R.id.sender_loc2);
        sendername = findViewById(R.id.sender_name2);
        sendercontact = findViewById(R.id.sender_contact2);
        receiverloc = findViewById(R.id.receiver_loc2);
        receivername = findViewById(R.id.receiver_name2);
        receivercontact = findViewById(R.id.receiver_contact2);
        order_id = findViewById(R.id.parcelorder_ID2);
        rider_name = findViewById(R.id.rider_name);
        vehicletype = findViewById(R.id.vehicle_details);
        usernote = findViewById(R.id.note_rider2);
        parcelprice = findViewById(R.id.txt_price2);
        btn_cancelOrder = findViewById(R.id.btn_cancelOrder);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dr = database.getReference().child("userparcel");
        Query query = dr.orderByChild("OrderID").equalTo(orderID);
        System.out.println(orderID);

        query.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        senderName = dataSnapshot.child("sendername").getValue(String.class);
                        senderLocation = dataSnapshot.child("senderlocation").getValue(String.class);
                        senderContact = dataSnapshot.child("sendercontact").getValue(String.class);
                        receiverName = dataSnapshot.child("receivername").getValue(String.class);
                        receiverLocation = dataSnapshot.child("receiverlocation").getValue(String.class);
                        receiverContact = dataSnapshot.child("receivercontact").getValue(String.class);
                        riderName = dataSnapshot.child("ridername").getValue(String.class);
                        vehicleType = dataSnapshot.child("vehicletype").getValue(String.class);
                        senderNote = dataSnapshot.child("customernotes").getValue(String.class);
                        orderPrice = dataSnapshot.child("fee").getValue(String.class);
                        sendername.setText(senderName);
                        senderloc.setText(senderLocation);
                        sendercontact.setText(senderContact);
                        receivername.setText(receiverName);
                        receiverloc.setText(receiverLocation);
                        receivercontact.setText(receiverContact);
                        order_id.setText(orderID);
                        rider_name.setText(riderName);
                        vehicletype.setText(vehicleType);
                        usernote.setText(senderNote);
                        parcelprice.setText(orderPrice);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }
                });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(user_completed_order_details.this, user_navigation.class);
        intent.putExtra("phonenum", phonenum);
        intent.putExtra("username", name);
        intent.putExtra("orderID", orderID);
        startActivity(intent);
    }
}