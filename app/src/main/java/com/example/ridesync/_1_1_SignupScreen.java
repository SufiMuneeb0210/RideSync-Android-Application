package com.example.ridesync;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class _1_1_SignupScreen extends AppCompatActivity {

    private DatabaseReference signupActivityRef;
    private Spinner signinSpinnergender, signinSpinnernature;
    private EditText signinName,signinEmail, signinPassword, signinPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_11_signup_screen);
        Viewsetup();
    }


    private void Viewsetup() {

        signupActivityRef = FirebaseDatabase.getInstance().getReference();
        signinName = findViewById(R.id.edtname);
        signinEmail = findViewById(R.id.edtemail);
        signinPassword = findViewById(R.id.edtpassword);
        signinPhone = findViewById(R.id.edtcontact);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.Gender,
                R.layout.activity_11_signup_screen_dropdown_layout
        );
        adapter.setDropDownViewResource(R.layout.activity_11_signup_screen_dropdown_item);
        signinSpinnergender = findViewById(R.id.edtgender);
        signinSpinnergender.setAdapter(adapter);
        adapter = ArrayAdapter.createFromResource(
                this,
                R.array.Nature,
                R.layout.activity_11_signup_screen_dropdown_layout
        );
        adapter.setDropDownViewResource(R.layout.activity_11_signup_screen_dropdown_item);
        signinSpinnernature = findViewById(R.id.edtnatureofwork);
        signinSpinnernature.setAdapter(adapter);
    }

    public void MovetoLogin(View view) {
        Intent intent = new Intent(this, _1_LoginScreen.class);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(_1_1_SignupScreen.this).toBundle();
        startActivity(intent,bundle);
    }

    public void Sign_up(View view) {
        String _siginName = signinName.getText().toString();
        String _siginEmail = signinEmail.getText().toString();
        String _siginPassword = signinPassword.getText().toString();
        String _siginPhone = signinPhone.getText().toString();
        String _siginSpinnergender = signinSpinnergender.getSelectedItem().toString();
        String _siginSpinnernature = signinSpinnernature.getSelectedItem().toString();

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
                                if (!_siginSpinnernature.equals("Job Nature"))
                                {
                                    FirebaseRegister(_siginName,_siginEmail,_siginPassword,_siginPhone,_siginSpinnergender,_siginSpinnernature);
                                }
                                else
                                {
                                    View selectedView = signinSpinnernature.getSelectedView();
                                    if (selectedView instanceof TextView) {
                                        TextView selectedTextView = (TextView) selectedView;
                                        selectedTextView.setError("Provide a User Type");
                                    }
                                    signinSpinnernature.requestFocus();
                                }
                            }
                            else
                            {
                                View selectedView = signinSpinnergender.getSelectedView();
                                if (selectedView instanceof TextView) {
                                    TextView selectedTextView = (TextView) selectedView;
                                    selectedTextView.setError("Provide a User Type");
                                }
                                signinSpinnergender.requestFocus();
                            }
                        } else {
                            signinPhone.setError("Please enter your phone number");
                            signinPhone.requestFocus();
                        }
                    }
                    else{
                        signinPassword.setError("Password must be at least 8 characters");
                        signinPassword.requestFocus();
                    }
                } else {
                    signinPassword.setError("Please enter your password");
                    signinPassword.requestFocus();
                }
            } else {
                signinEmail.setError("Please enter your email");
                signinEmail.requestFocus();
            }
        }
        else{
            signinName.setError("Please enter your name");
            signinName.requestFocus();
        }
    }

    private void FirebaseRegister(String _siginName, String _siginEmail, String _siginPassword, String _siginPhone, String _siginSpinnergender, String _siginSpinnernature) {
        signupActivityRef.child("Users").child(_siginSpinnernature).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(_siginEmail).exists())
                {
                    signinEmail.setError("User already exists");
                    signinEmail.requestFocus();
                }
                else
                {
                    signupActivityRef.child("Users").child(_siginSpinnernature).child(_siginEmail).child("Name").setValue(_siginName);
                    signupActivityRef.child("Users").child(_siginSpinnernature).child(_siginEmail).child("Email").setValue(_siginEmail);
                    signupActivityRef.child("Users").child(_siginSpinnernature).child(_siginEmail).child("Password").setValue(_siginPassword);
                    signupActivityRef.child("Users").child(_siginSpinnernature).child(_siginEmail).child("Phone").setValue(_siginPhone);
                    signupActivityRef.child("Users").child(_siginSpinnernature).child(_siginEmail).child("Gender").setValue(_siginSpinnergender);
                    if(_siginSpinnernature.equals("Passenger"))
                    {
                        signupActivityRef.child("Users").child(_siginSpinnernature).child(_siginEmail).child("Money").setValue(0);

                    }
                    Toast.makeText(_1_1_SignupScreen.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();

                    signinEmail.setText("");
                    signinName.setText("");
                    signinPassword.setText("");
                    signinPhone.setText("");
                    signinSpinnergender.setSelection(0);
                    signinSpinnernature.setSelection(0);

                    Intent intent = new Intent(_1_1_SignupScreen.this, _1_LoginScreen.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(_1_1_SignupScreen.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

