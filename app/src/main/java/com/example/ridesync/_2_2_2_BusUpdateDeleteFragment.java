package com.example.ridesync;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class _2_2_2_BusUpdateDeleteFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    RecyclerView recyclerView;
    _9_2_2_BusRecyclerViewCustomAdapter BusAdapter;
    ArrayList<_9_ModelBusData> list;
    Button btnLogin;
    EditText edtbusnumber;
    private DatabaseReference ActivityRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_222_bus_update_delete_fragment, container, false);
        ViewSetup(view);
        ListView();
        return view;
    }


    private void ListView() {
        ActivityRef.child("Bus").addValueEventListener(new ValueEventListener() {
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
                BusAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ListView", "Error: " + error.getMessage());
            }
        });
    }


    private void ViewSetup(View view) {
        recyclerView = view.findViewById(R.id.datalist);
        ActivityRef = FirebaseDatabase.getInstance().getReference();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btnLogin= view.findViewById(R.id.btnsearch);
        edtbusnumber = view.findViewById(R.id.edtbusnumber);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });

        list = new ArrayList<>();
        BusAdapter = new _9_2_2_BusRecyclerViewCustomAdapter(getContext(),list);
        recyclerView.setAdapter(BusAdapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        list.clear();
        ListView();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void Search()
    {
        String _edtBusNumber = edtbusnumber.getText().toString().trim();
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
                    BusAdapter.notifyDataSetChanged();
                }
                else{
                    edtbusnumber.setError("Bus does not exists");
                    edtbusnumber.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
