package com.example.ridesync;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class _2_2_1_BusInsertFragment extends Fragment {

    private DatabaseReference ActivityRef;
    private EditText edtTo , edtFrom, edtBusNumber, edtBusSeats, edtBusFair, edtBusTime, edtBusPassengers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_221_bus_insert_fragment, container, false);
        SetupViews(view);
        return view;
    }

    void SetupViews(View view)
    {
        ActivityRef = FirebaseDatabase.getInstance().getReference();
        edtTo = view.findViewById(R.id.edtto);
        edtFrom = view.findViewById(R.id.edtFrom);
        edtBusNumber = view.findViewById(R.id.edtbusnumber);
        edtBusSeats = view.findViewById(R.id.edtseats);
        edtBusFair = view.findViewById(R.id.edtfair);
        edtBusTime = view.findViewById(R.id.edtTime);
        edtBusPassengers = view.findViewById(R.id.edtcurrentseats);

        Button btnRegister=view.findViewById(R.id.btnregister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerBus(v);
            }
        });
    }
    private void FirebaseRegister(String _edtbusnumber, String _edtto, String _edtfrom, String _edtbusseats, String _edtbusfair, String _edtbustime, String _edtbuspassengers) {
        ActivityRef.child("Bus").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(_edtbusnumber).exists())
                {
                    edtBusNumber.setError("Bus already exists");
                    edtBusNumber.requestFocus();
                }
                else
                {
                    ActivityRef.child("Bus").child(_edtbusnumber).child("BusNumber").setValue(_edtbusnumber);
                    ActivityRef.child("Bus").child(_edtbusnumber).child("To").setValue(_edtto);
                    ActivityRef.child("Bus").child(_edtbusnumber).child("From").setValue(_edtfrom);
                    ActivityRef.child("Bus").child(_edtbusnumber).child("TotalSeats").setValue(_edtbusseats);
                    ActivityRef.child("Bus").child(_edtbusnumber).child("BusFair").setValue(_edtbusfair);
                    ActivityRef.child("Bus").child(_edtbusnumber).child("BusTime").setValue(_edtbustime);
                    ActivityRef.child("Bus").child(_edtbusnumber).child("BookedSeats").setValue(_edtbuspassengers);


                    Toast.makeText(getView().getContext(), "Bus Registered Successfully", Toast.LENGTH_SHORT).show();

                    edtBusNumber.setText("");
                    edtTo.setText("");
                    edtFrom.setText("");
                    edtBusSeats.setText("");
                    edtBusFair.setText("");
                    edtBusTime.setText("");
                    edtBusPassengers.setText("");

                    Intent intent = new Intent(getView().getContext(), _2_2_AdminBusesScreen.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getView().getContext(), "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void registerBus(View view) {
        String _edtbusnumber = edtBusNumber.getText().toString();
        String _edtto = edtTo.getText().toString();
        String _edtfrom = edtFrom.getText().toString();
        String _edtbusseats = edtBusSeats.getText().toString();
        String _edtbusfair = edtBusFair.getText().toString();
        String _edtbustime = edtBusTime.getText().toString();
        String _edtbuspassengers = edtBusPassengers.getText().toString();


        if(!_edtbusnumber.isEmpty())
        {
            if(!_edtfrom.isEmpty())
            {
                if(!_edtto.isEmpty())
                {
                    if(!_edtbusseats.isEmpty())
                    {
                        if(!_edtbuspassengers.isEmpty())
                        {
                            if(!(Integer.parseInt(_edtbuspassengers)>Integer.parseInt(_edtbusseats)))
                            {
                                if(!_edtbusfair.isEmpty())
                                {
                                    if(!_edtbustime.isEmpty())
                                    {
                                        FirebaseRegister(_edtbusnumber,_edtto, _edtfrom,_edtbusseats,_edtbusfair,_edtbustime, _edtbuspassengers);
                                    }
                                    else
                                    {
                                        edtBusTime.setError("Please enter bus time");
                                        edtBusTime.requestFocus();
                                    }
                                }
                                else
                                {
                                    edtBusFair.setError("Please enter bus fair");
                                    edtBusFair.requestFocus();
                                }
                            }
                            else
                            {
                                edtBusPassengers.setError("Passengers cannot be greater than seats");
                                edtBusPassengers.requestFocus();
                            }
                        }
                        else
                        {
                            edtBusPassengers.setError("Please enter bus passengers");
                            edtBusPassengers.requestFocus();
                        }
                    }
                    else
                    {
                        edtBusSeats.setError("Please enter bus seats");
                        edtBusSeats.requestFocus();
                    }

                }
                else
                {
                    edtTo.setError("Please enter Destination");
                    edtTo.requestFocus();
                }
            }
            else
            {
                edtFrom.setError("Please enter Source");
                edtFrom.requestFocus();
            }
        }
        else
        {
            edtBusNumber.setError("Please enter bus number");
            edtBusNumber.requestFocus();
        }
    }
}
