package com.example.ridesync;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class _3_2_1_TicketHistoryScreen extends AppCompatActivity {

    RecyclerView recyclerView;
    _9_3_SessionManager sessionManager;
    _9_2_3_TicketRecyclerViewCustomAdapter myAdapter;
    ArrayList<_9_ModelBusData> list;
    private DatabaseReference ActivityRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_321_ticket_history_screen);
        Viewsetup();
        ListView();
    }
    private void Viewsetup() {
        sessionManager=new _9_3_SessionManager(getApplicationContext());
        recyclerView = findViewById(R.id.datalist);
        recyclerView.setClickable(false);
        ActivityRef = FirebaseDatabase.getInstance().getReference() ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter = new _9_2_3_TicketRecyclerViewCustomAdapter(this,list);
        recyclerView.setAdapter(myAdapter);
    }
    private void ListView() {
        ActivityRef.child("Ticket").child(sessionManager.getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    _9_ModelBusData newdata = new _9_ModelBusData();
                    newdata.setBusFair(dataSnapshot.child("BusFair").getValue().toString());
                    newdata.setBusNumber(dataSnapshot.child("BusNumber").getValue().toString());
                    newdata.setBookedSeats(dataSnapshot.child("BookedSeats").getValue().toString());
                    newdata.setBusSeats(dataSnapshot.child("TotalSeats").getValue().toString());
                    newdata.setFrom(dataSnapshot.child("From").getValue().toString());
                    newdata.setTo(dataSnapshot.child("To").getValue().toString());
                    newdata.setBusTime(dataSnapshot.child("BusTime").getValue().toString());
                    list.add(newdata);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ListView", "Error: " + error.getMessage());
            }
        });
    }
}

