package com.example.ridesync;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.Objects;

public class _3_2_TicketsScreen extends AppCompatActivity {

    RecyclerView recyclerView;
    _9_2_3_TicketRecyclerViewCustomAdapter myAdapter;
    ArrayList<_9_ModelBusData> list;
    Spinner _spinner;
    Button btnLogin,btnHistory;
    EditText edtbusnumber;

    private DatabaseReference ActivityRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_32_tickets_screen);
        Viewsetup();
    }
    private void ListView() {
        ActivityRef.child("Bus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Inspect the DataSnapshot
                    Log.d("DataSnapshot", dataSnapshot.toString());

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
    private void Viewsetup() {

        recyclerView = findViewById(R.id.datalist);
        ActivityRef = FirebaseDatabase.getInstance().getReference() ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnLogin= findViewById(R.id.btnLogin);
        edtbusnumber = findViewById(R.id.edtemail);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterSearch();
            }
        });
        btnHistory = findViewById(R.id.btnhistory);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_3_2_TicketsScreen.this,_3_2_1_TicketHistoryScreen.class);
                startActivity(intent);
            }
        });
        list = new ArrayList<>();
        myAdapter = new _9_2_3_TicketRecyclerViewCustomAdapter(this,list);
        recyclerView.setAdapter(myAdapter);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.FilterTickets,
                R.layout.activity_11_signup_screen_dropdown_layout
        );
        adapter.setDropDownViewResource(R.layout.activity_11_signup_screen_dropdown_item);
        _spinner = findViewById(R.id.edtnatureofwork);
        _spinner.setAdapter(adapter);
    }

    private void FilterSearch() {
        switch (_spinner.getSelectedItemPosition()){
            case 0:
                list.clear();
                ListView();
                break;
            case 1:
                Search();
                break;
            case 2:
                Search("From","Please enter source","No bus with this Source found" );
                break;
            case 3:
                Search("To","Please enter destination","No bus with this Destination found");
                break;
        }
    }

    private void Search()
    {
        String _edtBusNumber = edtbusnumber.getText().toString();
        if(_edtBusNumber.isEmpty())
        {
            edtbusnumber.setError("Please enter Bus Number");
            edtbusnumber.requestFocus();
            return;
        }
        list.clear();
        ActivityRef.child("Bus").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(_edtBusNumber).exists())
                {
                    _9_ModelBusData newdata = new _9_ModelBusData();
                    newdata.setBusFair(snapshot.child(_edtBusNumber).child("BusFair").getValue().toString());
                    newdata.setBusNumber(snapshot.child(_edtBusNumber).child("BusNumber").getValue().toString());
                    newdata.setBookedSeats(snapshot.child(_edtBusNumber).child("BookedSeats").getValue().toString());
                    newdata.setBusSeats(snapshot.child(_edtBusNumber).child("TotalSeats").getValue().toString());
                    newdata.setFrom(snapshot.child(_edtBusNumber).child("From").getValue().toString());
                    newdata.setTo(snapshot.child(_edtBusNumber).child("To").getValue().toString());
                    newdata.setBusTime(snapshot.child(_edtBusNumber).child("BusTime").getValue().toString());
                    list.add(newdata);
                    myAdapter.notifyDataSetChanged();
                }
                else{
                    edtbusnumber.setError("Bus does not exists");
                    edtbusnumber.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(_3_2_TicketsScreen.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Search(String _parameter,String _firstmeessage, String _secondmessage)
    {
        String _edtBusNumber = edtbusnumber.getText().toString();
        if(_edtBusNumber.isEmpty())
        {
            edtbusnumber.setError(_firstmeessage);
            edtbusnumber.requestFocus();
            return;
        }
        list.clear();
        ActivityRef.child("Bus").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int Counter = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(dataSnapshot.child(_parameter).getValue().toString().equals(_edtBusNumber))
                    {
                        _9_ModelBusData newdata = new _9_ModelBusData();
                        newdata.setBusFair(dataSnapshot.child("BusFair").getValue().toString());
                        newdata.setBusNumber(dataSnapshot.child("BusNumber").getValue().toString());
                        newdata.setBookedSeats(dataSnapshot.child("BookedSeats").getValue().toString());
                        newdata.setBusSeats(dataSnapshot.child("TotalSeats").getValue().toString());
                        newdata.setFrom(dataSnapshot.child("From").getValue().toString());
                        newdata.setTo(dataSnapshot.child("To").getValue().toString());
                        newdata.setBusTime(dataSnapshot.child("BusTime").getValue().toString());
                        list.add(newdata);
                        Counter++;
                        myAdapter.notifyDataSetChanged();
                    }
                }
                if(Counter>0)
                {
                    Toast.makeText(_3_2_TicketsScreen.this, "Total "+Counter+" buses found", Toast.LENGTH_SHORT).show();
                }
                else{
                    edtbusnumber.setError(_secondmessage);
                    edtbusnumber.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(_3_2_TicketsScreen.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, _3_PassengerHomeScreen.class);
        startActivity(intent);

    }

}

