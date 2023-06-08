package com.example.ridesync;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class _2_2_3_BusShowActivty extends AppCompatActivity {

    private DatabaseReference ActivityRef;
    private EditText edtTo , edtFrom, edtBusSeats, edtBusFair, edtBusTime, edtBusPassengers;
    private TextView edtBusNumber;

    Button btnupdate, btndelete;

    _9_ModelBusData myObject;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_223_bus_show_activty);
        Intent intent = getIntent();
        String json = intent.getStringExtra("data");
        ActivityRef = FirebaseDatabase.getInstance().getReference();
        Gson gson = new Gson();
        myObject = gson.fromJson(json, _9_ModelBusData.class);

        edtTo=findViewById(R.id.edtto);
        edtFrom=findViewById(R.id.edtFrom);
        edtBusSeats=findViewById(R.id.edtseats);
        edtBusFair=findViewById(R.id.edtfair);
        edtBusTime=findViewById(R.id.edtTime);
        edtBusPassengers=findViewById(R.id.edtcurrentseats);
        edtBusNumber=findViewById(R.id.edtbusnumber);

        edtTo.setText(myObject.getTo());
        edtFrom.setText(myObject.getFrom());
        edtBusSeats.setText(myObject.getBusSeats());
        edtBusFair.setText(myObject.getBusFair());
        edtBusTime.setText(myObject.getBusTime());
        edtBusPassengers.setText(myObject.getBookedSeats());
        edtBusNumber.setText(myObject.getBusNumber());

        btnupdate=findViewById(R.id.btnupdate);
        btndelete=findViewById(R.id.btndelete);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update(v);
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete(v);
            }
        });

    }

    private void FirebaseRegister(String _edtbusnumber, String _edtto, String _edtfrom, String _edtbusseats, String _edtbusfair, String _edtbustime, String _edtbuspassengers) {
        ActivityRef.child("Bus").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ActivityRef.child("Bus").child(_edtbusnumber).child("BusNumber").setValue(_edtbusnumber);
                ActivityRef.child("Bus").child(_edtbusnumber).child("To").setValue(_edtto);
                ActivityRef.child("Bus").child(_edtbusnumber).child("From").setValue(_edtfrom);
                ActivityRef.child("Bus").child(_edtbusnumber).child("TotalSeats").setValue(_edtbusseats);
                ActivityRef.child("Bus").child(_edtbusnumber).child("BusFair").setValue(_edtbusfair);
                ActivityRef.child("Bus").child(_edtbusnumber).child("BusTime").setValue(_edtbustime);
                ActivityRef.child("Bus").child(_edtbusnumber).child("BookedSeats").setValue(_edtbuspassengers);


                Toast.makeText(getApplicationContext(), "Bus Updated Successfully", Toast.LENGTH_SHORT).show();

                edtBusNumber.setText("");
                edtTo.setText("");
                edtFrom.setText("");
                edtBusSeats.setText("");
                edtBusFair.setText("");
                edtBusTime.setText("");
                edtBusPassengers.setText("");

                Intent intent = new Intent(getApplicationContext(), _2_2_AdminBusesScreen.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void Update(View view) {
        String _edtbusnumber = edtBusNumber.getText().toString();
        String _edtto = edtTo.getText().toString();
        String _edtfrom = edtFrom.getText().toString();
        String _edtbusseats = edtBusSeats.getText().toString();
        String _edtbusfair = edtBusFair.getText().toString();
        String _edtbustime = edtBusTime.getText().toString();
        String _edtbuspassengers = edtBusPassengers.getText().toString();

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

    public void Delete(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Bus");
        builder.setMessage("Are you sure you want to delete this Bus?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityRef.child("Bus").child(myObject.getBusNumber()).removeValue();
                Toast.makeText(getApplicationContext(), "Bus Deleted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), _2_2_AdminBusesScreen.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
