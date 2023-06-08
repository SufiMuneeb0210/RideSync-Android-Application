package com.example.ridesync;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class _2_3_AdminPassesScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    _9_3_SessionManager sessionManager;
    RecyclerView recyclerView;
    _9_2_4_PassRecyclerViewCusstomAdapter myAdapter;
    ArrayList<_9_ModelPassData> list;
    Spinner _spinner;
    private DatabaseReference ActivityRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_331_pass_status_screen);
        sessionManager=new _9_3_SessionManager(getApplicationContext());
        Viewsetup();
    }

    private void ListView() {
        ActivityRef.child("Pass").child(sessionManager.getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Log.d("Snap",dataSnapshot.toString());
                    _9_ModelPassData newdata = new _9_ModelPassData();
                    newdata.setStatus(dataSnapshot.child("Status").getValue().toString());
                    newdata.set_passHoldername(sessionManager.getName());
                    newdata.set_passHolderEmail(sessionManager.getEmail());
                    newdata.set_busNumber(dataSnapshot.child("BusNumber").getValue().toString());
                    newdata.set_passDays(Integer.parseInt(dataSnapshot.child("Month").getValue().toString()));
                    newdata.set_passPayment(Integer.parseInt(dataSnapshot.child("TotalPayment").getValue().toString()));
                    newdata.set_passDate(dataSnapshot.child("Datad").getValue().toString());

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
        list = new ArrayList<>();
        myAdapter = new _9_2_4_PassRecyclerViewCusstomAdapter(this,list);
        recyclerView.setAdapter(myAdapter);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.FilterStatus,
                R.layout.activity_11_signup_screen_dropdown_layout
        );
        adapter.setDropDownViewResource(R.layout.activity_11_signup_screen_dropdown_item);
        _spinner = findViewById(R.id.edtnatureofwork);
        _spinner.setAdapter(adapter);
        _spinner.setOnItemSelectedListener(this);
    }
    private void Search(String _parameter, String _secondmessage)
    {
        list.clear();
        ActivityRef.child("Pass").child(sessionManager.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int Counter = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(dataSnapshot.child("Status").getValue().toString().equals(_parameter))
                    {
                        Log.d("Snap",dataSnapshot.toString());
                        _9_ModelPassData newdata = new _9_ModelPassData();
                        newdata.setStatus(dataSnapshot.child("Status").getValue().toString());
                        newdata.set_passHoldername(sessionManager.getName());
                        newdata.set_passHolderEmail(sessionManager.getEmail());
                        newdata.set_busNumber(dataSnapshot.child("BusNumber").getValue().toString());
                        newdata.set_passDays(Integer.parseInt(dataSnapshot.child("Month").getValue().toString()));
                        newdata.set_passPayment(Integer.parseInt(dataSnapshot.child("TotalPayment").getValue().toString()));
                        newdata.set_passDate(dataSnapshot.child("Datad").getValue().toString());

                        list.add(newdata);
                        Counter++;
                        myAdapter.notifyDataSetChanged();
                    }
                }
                if(Counter>0)
                {
                    Toast.makeText(_2_3_AdminPassesScreen.this, "Total "+Counter+" Passes found", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(_2_3_AdminPassesScreen.this, "Nothing found!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(_2_3_AdminPassesScreen.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0)
        {
            list.clear();
            ListView();
        }
        else {
            list.clear();
            Search(_spinner.getSelectedItem().toString(),"No such Passes exist");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}