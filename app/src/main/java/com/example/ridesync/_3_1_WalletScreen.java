package com.example.ridesync;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class _3_1_WalletScreen extends AppCompatActivity {


    private DatabaseReference ActivityRef;
    TextView txtcurrentbalance;
    EditText edtbalance;
    int _balancecurrent;
    _9_3_SessionManager sessionManager;
    String _useremail,_userjob;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_31_wallet_screen);

        ActivityRef = FirebaseDatabase.getInstance().getReference();
        txtcurrentbalance=findViewById(R.id.txtbalanceshow);
        edtbalance=findViewById(R.id.edtbalance);

        sessionManager=new _9_3_SessionManager(getApplicationContext());
        _useremail=sessionManager.getEmail();
        _userjob=sessionManager.getJob();

        _balancecurrent = sessionManager.getBalance();
        txtcurrentbalance.setText(String.valueOf(_balancecurrent));


    }
    public void Add_Amount(View view) {
        _balancecurrent+=Integer.parseInt(edtbalance.getText().toString());
        ActivityRef.child("Users").child(_userjob).child(_useremail).child("Money").setValue(_balancecurrent);
        Toast.makeText(this, "Balance Updated Sucessfully!", Toast.LENGTH_SHORT).show();
        txtcurrentbalance.setText(String.valueOf(_balancecurrent));
        sessionManager.setBalance(_balancecurrent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, _3_PassengerHomeScreen.class);
        startActivity(intent);

    }
}