package com.example.ridesync;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class _5_SelfProfileScreen extends AppCompatActivity {

    private DatabaseReference ActivityRef;
    EditText edtName,edtPassword,edtContact;
    TextView edtEmail;
    Spinner edtgender;
    String _loginemail,_loginjob,_loginname,_logingender,_loginpassowrd,_logincontact;
    _9_3_SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5_self_profile_screen);
        session = new _9_3_SessionManager(getApplicationContext());
        _loginemail=session.getEmail();
        _logincontact=session.getPhone();
        _loginjob=session.getJob();
        _logingender=session.getGender();
        _loginname=session.getName();
        _loginpassowrd=session.getPassword();
        ViewSetup();
    }
    private void ViewSetup() {
        ActivityRef = FirebaseDatabase.getInstance().getReference();
        edtName=findViewById(R.id.edtname);
        edtEmail=findViewById(R.id.edtemail);
        edtPassword=findViewById(R.id.edtpassword);
        edtContact=findViewById(R.id.edtcontact);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.Gender,
                R.layout.activity_11_signup_screen_dropdown_layout
        );
        adapter.setDropDownViewResource(R.layout.activity_11_signup_screen_dropdown_item);
        edtgender = findViewById(R.id.edtgender);
        edtgender.setAdapter(adapter);

        edtName.setText(_loginname);
        edtContact.setText(_logincontact);
        edtPassword.setText(_loginpassowrd);
        edtEmail.setText(_loginemail);

        if(_logingender.equals("Male"))
        {
            edtgender.setSelection(1);
        }
        else
        {
            edtgender.setSelection(2);
        }
    }
    public void Update(View view) {
        String _siginName = edtName.getText().toString();
        String _siginEmail = edtEmail.getText().toString();
        String _siginPassword = edtPassword.getText().toString();
        String _siginPhone = edtContact.getText().toString();
        String _siginSpinnergender = edtgender.getSelectedItem().toString();
        if(!_siginName.isEmpty())
        {
            if (!_siginEmail.isEmpty())
            {
                if (!_siginPassword.isEmpty())
                {
                    if(_siginPassword.length()>=8)
                    {
                        if (!_siginPhone.isEmpty())
                        {
                            if (!_siginSpinnergender.equals("Gender"))
                            {
                                FirebaseRegister(_siginName,_siginEmail,_siginPassword,_siginPhone,_siginSpinnergender,_loginjob);

                            }
                            else
                            {
                                View selectedView = edtgender.getSelectedView();
                                if (selectedView instanceof TextView) {
                                    TextView selectedTextView = (TextView) selectedView;
                                    selectedTextView.setError("Provide a User Type");
                                }
                                edtgender.requestFocus();
                            }
                        } else {
                            edtContact.setError("Please enter your phone number");
                            edtContact.requestFocus();
                        }
                    }
                    else{
                        edtPassword.setError("Password must be 8 characters long");
                        edtPassword.requestFocus();
                    }
                } else {
                    edtPassword.setError("Please enter your password");
                    edtPassword.requestFocus();
                }
            } else {
                edtEmail.setError("Please enter your email");
                edtEmail.requestFocus();
            }
        }
        else{
            edtName.setError("Please enter your name");
            edtName.requestFocus();
        }
    }

    private void FirebaseRegister(String _siginName, String _siginEmail, String _siginPassword, String _siginPhone, String _siginSpinnergender, String _loginJob) {
        ActivityRef.child("Users").child(_loginJob).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ActivityRef.child("Users").child(_loginjob).child(_siginEmail).child("Name").setValue(_siginName);
                ActivityRef.child("Users").child(_loginjob).child(_siginEmail).child("Email").setValue(_siginEmail);
                ActivityRef.child("Users").child(_loginjob).child(_siginEmail).child("Password").setValue(_siginPassword);
                ActivityRef.child("Users").child(_loginjob).child(_siginEmail).child("Phone").setValue(_siginPhone);
                ActivityRef.child("Users").child(_loginjob).child(_siginEmail).child("Gender").setValue(_siginSpinnergender);

                Toast.makeText(getApplicationContext(), "User Updated Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(session.getJob().equals("Admin"))
        {
            Intent intent = new Intent(this, _2_AdminHomeScreen.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, _3_PassengerHomeScreen.class);
            startActivity(intent);
        }

    }
}
