package com.example.ridesync;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class _3_3_PassScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private DatabaseReference ActivityRef;
    _9_3_SessionManager sessionManager;
    private Spinner spinnerMonth;
    private EditText edtName,edtEmail, edtbusNumber, edttotalpayment,edtcurrentbalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_33_pass_screen);
        sessionManager = new _9_3_SessionManager(getApplicationContext());
        SetupViews();
    }

    void SetupViews()
    {
        ActivityRef = FirebaseDatabase.getInstance().getReference();
        edtName = findViewById(R.id.edtname);
        edtEmail = findViewById(R.id.edtemail);
        edtcurrentbalance=findViewById(R.id.balance);
        edtbusNumber = findViewById(R.id.edtbusid);
        edttotalpayment = findViewById(R.id.edtpayment);
        Button btnRegister=findViewById(R.id.btnregister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Apply(v);
            }
        });
        Button btnhistory = findViewById(R.id.btnhistory);
        btnhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_3_3_PassScreen.this, _3_3_1_PassStatusScreen.class);
                startActivity(intent);
            }
        });

        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.Months,
                R.layout.activity_11_signup_screen_dropdown_layout
        );
        adapter.setDropDownViewResource(R.layout.activity_11_signup_screen_dropdown_item);
        spinnerMonth = findViewById(R.id.edtmonth);
        spinnerMonth.setAdapter(adapter);
        spinnerMonth.setOnItemSelectedListener(this);
        edtName.setText(sessionManager.getName());
        edtEmail.setText(sessionManager.getEmail());

        edtcurrentbalance.setText(String.valueOf(sessionManager.getBalance()));

    }
    private void FirebaseRegister(String _edtname, String _edtemail, String _edtbusNumber, String _edttotalpayment, String _edtmonth) {
        try {
            ActivityRef.child("Pass").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String key = ActivityRef.child("Pass").child(_edtemail).push().getKey();

                    ActivityRef.child("Pass").child(_edtemail).child("Name").setValue(_edtname);
                    ActivityRef.child("Pass").child(_edtemail).child("Email").setValue(_edtemail);
                    ActivityRef.child("Pass").child(_edtemail).child("BusNumber").setValue(_edtbusNumber);
                    ActivityRef.child("Pass").child(_edtemail).child("TotalPayment").setValue(_edttotalpayment);
                    ActivityRef.child("Pass").child(_edtemail).child("Month").setValue(_edtmonth);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date currentDate = new Date();
                    String formattedDate = dateFormat.format(currentDate);
                    ActivityRef.child("Pass").child(_edtemail).child("Datad").setValue(formattedDate);
                    ActivityRef.child("Pass").child(_edtemail).child("Status").setValue("Pending");

                    Toast.makeText(_3_3_PassScreen.this, "Pass Application Successfully", Toast.LENGTH_SHORT).show();

                    edtEmail.setText("");
                    edtName.setText("");
                    edtbusNumber.setText("");
                    edttotalpayment.setText("");
                    spinnerMonth.setSelection(0);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(_3_3_PassScreen.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(_3_3_PassScreen.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void Apply(View view) {

        String _edtname = edtName.getText().toString();
        String _edtemail = edtEmail.getText().toString();
        String _edtbusNumber = edtbusNumber.getText().toString();
        String _edttotalpayment = edttotalpayment.getText().toString();
        String _edtmonth= spinnerMonth.getSelectedItem().toString();

        if(!_edtname.isEmpty())
        {
            if (!_edtemail.isEmpty())
            {
                if (!_edtbusNumber.isEmpty())
                {
                    if (!_edttotalpayment.equals("Month"))
                    {
                        FirebaseRegister(_edtname,_edtemail,_edtbusNumber,_edttotalpayment,_edtmonth);

                    }
                    else
                    {
                        View selectedView = spinnerMonth.getSelectedView();
                        if (selectedView instanceof TextView) {
                            TextView selectedTextView = (TextView) selectedView;
                            selectedTextView.setError("Provide a time Peroid");
                        }
                        spinnerMonth.requestFocus();
                    }
                } else {
                    edtbusNumber.setError("Please enter Bus Number");
                    edtbusNumber.requestFocus();
                }
            } else {
                edtEmail.setError("Please enter your Email");
                edtEmail.requestFocus();
            }
        }
        else{
            edtName.setError("Please enter your Name");
            edtName.requestFocus();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int _permonth=12000;
        int _val=0;
        switch(position){
            case 0:
                edttotalpayment.setText("");
                break;
            case 1:
                _val=1*_permonth;
                edttotalpayment.setText(String.valueOf(_val));
                break;
            case 2:
                _val=3*_permonth;
                edttotalpayment.setText(String.valueOf(_val));
                break;
            case 3:
                _val=6*_permonth;
                edttotalpayment.setText(String.valueOf(_val));
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, _3_PassengerHomeScreen.class);
        startActivity(intent);

    }
}