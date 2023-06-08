package com.example.ridesync;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.gson.Gson;

public class _2_1_3_ShowActivity extends AppCompatActivity {
    private DatabaseReference ActivityRef;
    EditText edtName,edtPassword,edtContact;
    TextView edtEmail;
    Spinner edtgender,edtNature;
    _9_ModelUserData myObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_213_show);
        Intent intent = getIntent();
        String json = intent.getStringExtra("data");
        ActivityRef = FirebaseDatabase.getInstance().getReference();
        Gson gson = new Gson();
        myObject = gson.fromJson(json, _9_ModelUserData.class);

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


        adapter = ArrayAdapter.createFromResource(
                this,
                R.array.LoginPerson,
                R.layout.activity_11_signup_screen_dropdown_layout
        );
        adapter.setDropDownViewResource(R.layout.activity_11_signup_screen_dropdown_item);
        edtNature = findViewById(R.id.edtnatureofwork);
        edtNature.setAdapter(adapter);

        edtName.setText(myObject.getName());
        edtEmail.setText(myObject.getEmail());
        edtPassword.setText(myObject.getPassword());
        edtContact.setText(myObject.getPhone());
        if(myObject.getJob().equals("Admin"))
        {
            edtNature.setSelection(1);
        }
        else if(myObject.getJob().equals("Passenger"))
        {
            edtNature.setSelection(2);
        }
        if(myObject.getGender().equals("Male"))
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
        String _siginSpinnernature = edtNature.getSelectedItem().toString();

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
                                    View selectedView = edtNature.getSelectedView();
                                    if (selectedView instanceof TextView) {
                                        TextView selectedTextView = (TextView) selectedView;
                                        selectedTextView.setError("Provide a User Type");
                                    }
                                    edtNature.requestFocus();
                                }
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

    private void FirebaseRegister(String _siginName, String _siginEmail, String _siginPassword, String _siginPhone, String _siginSpinnergender, String _siginSpinnernature) {
        ActivityRef.child("Users").child(_siginSpinnernature).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ActivityRef.child("Users").child(_siginSpinnernature).child(_siginEmail).child("Name").setValue(_siginName);
                ActivityRef.child("Users").child(_siginSpinnernature).child(_siginEmail).child("Email").setValue(_siginEmail);
                ActivityRef.child("Users").child(_siginSpinnernature).child(_siginEmail).child("Password").setValue(_siginPassword);
                ActivityRef.child("Users").child(_siginSpinnernature).child(_siginEmail).child("Phone").setValue(_siginPhone);
                ActivityRef.child("Users").child(_siginSpinnernature).child(_siginEmail).child("Gender").setValue(_siginSpinnergender);

                Toast.makeText(getApplicationContext(), "User Updated Successfully", Toast.LENGTH_SHORT).show();

                edtEmail.setText("");
                edtPassword.setText("");
                edtName.setText("");
                edtContact.setText("");
                edtgender.setSelection(0);
                edtNature.setSelection(0);

                Intent intent = new Intent(getApplicationContext(), _2_1_AdminProfilesScreen.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Delete(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete User");
        builder.setMessage("Are you sure you want to delete this user?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityRef.child("Users").child(myObject.getJob()).child(myObject.getEmail()).removeValue();
                Toast.makeText(getApplicationContext(), "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), _2_1_AdminProfilesScreen.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}