package com.example.ridesync;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class _2_1_1_InsertFragment extends Fragment {

    private DatabaseReference signupActivityRef;
    private Spinner Spinnergender, Spinnernature;
    private EditText edtName,edtEmail, edtPassword, edtPhone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_211_insert_fragment, container, false);
        SetupViews(view);
        return view;
    }

    void SetupViews(View view)
    {
        signupActivityRef = FirebaseDatabase.getInstance().getReference();
        edtName = view.findViewById(R.id.edtname);
        edtEmail = view.findViewById(R.id.edtemail);
        edtPassword = view.findViewById(R.id.edtpassword);
        edtPhone = view.findViewById(R.id.edtcontact);
        Button btnRegister=view.findViewById(R.id.btnregister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register(v);
            }
        });

        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                view.getContext(),
                R.array.Gender,
                R.layout.activity_11_signup_screen_dropdown_layout
        );
        adapter.setDropDownViewResource(R.layout.activity_11_signup_screen_dropdown_item);
        Spinnergender = view.findViewById(R.id.edtgender);
        Spinnergender.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(
                view.getContext(),
                R.array.AdminNature,
                R.layout.activity_11_signup_screen_dropdown_layout
        );
        adapter.setDropDownViewResource(R.layout.activity_11_signup_screen_dropdown_item);
        Spinnernature = view.findViewById(R.id.edtnatureofwork);
        Spinnernature.setAdapter(adapter);
    }
    private void FirebaseRegister(String _edtname, String _edtemail, String _edtpassword, String _edtphone, String _edtgender, String _edtnature) {
        try {
            signupActivityRef.child("Users").child(_edtnature).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(_edtemail).exists())
                    {
                        edtEmail.setError("User already exists");
                        edtEmail.requestFocus();
                    }
                    else
                    {
                        signupActivityRef.child("Users").child(_edtnature).child(_edtemail).child("Name").setValue(_edtname);
                        signupActivityRef.child("Users").child(_edtnature).child(_edtemail).child("Email").setValue(_edtemail);
                        signupActivityRef.child("Users").child(_edtnature).child(_edtemail).child("Password").setValue(_edtpassword);
                        signupActivityRef.child("Users").child(_edtnature).child(_edtemail).child("Phone").setValue(_edtphone);
                        signupActivityRef.child("Users").child(_edtnature).child(_edtemail).child("Gender").setValue(_edtgender);

                        if(_edtnature.equals("Passenger"))
                        {
                            signupActivityRef.child("Users").child(_edtnature).child(_edtemail).child("Money").setValue(0);

                        }

                        Toast.makeText(getView().getContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();

                        edtEmail.setText("");
                        edtName.setText("");
                        edtPassword.setText("");
                        edtPhone.setText("");
                        Spinnergender.setSelection(0);
                        Spinnernature.setSelection(0);

                        Intent intent = new Intent(getView().getContext(), _2_1_AdminProfilesScreen.class);
                        startActivity(intent);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getView().getContext(), "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getView().getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void Register(View view) {
        String _edtname = edtName.getText().toString();
        String _edtemail = edtEmail.getText().toString();
        String _edtpassword = edtPassword.getText().toString();
        String _edtphone = edtPhone.getText().toString();
        String _edtgender = Spinnergender.getSelectedItem().toString();
        String _edtnature = Spinnernature.getSelectedItem().toString();

        if(!_edtname.isEmpty())
        {
            if (!_edtemail.isEmpty())
            {
                if (!_edtpassword.isEmpty())
                {
                    if(_edtpassword.length()>=8)
                    {
                        if (!_edtphone.isEmpty())
                        {
                            if (!_edtphone.equals("Gender"))
                            {
                                if (!_edtnature.equals("Job Nature"))
                                {
                                    FirebaseRegister(_edtname,_edtemail,_edtpassword,_edtphone,_edtgender,_edtnature);
                                }
                                else
                                {
                                    View selectedView = Spinnernature.getSelectedView();
                                    if (selectedView instanceof TextView) {
                                        TextView selectedTextView = (TextView) selectedView;
                                        selectedTextView.setError("Provide a User Type");
                                    }
                                    Spinnernature.requestFocus();
                                }
                            }
                            else
                            {
                                View selectedView = Spinnergender.getSelectedView();
                                if (selectedView instanceof TextView) {
                                    TextView selectedTextView = (TextView) selectedView;
                                    selectedTextView.setError("Provide a User Type");
                                }
                                Spinnergender.requestFocus();
                            }
                        } else {
                            edtPhone.setError("Please enter your Phone number");
                            edtPhone.requestFocus();
                        }
                    }
                    else{
                        edtPassword.setError("Password must be at least 8 characters");
                        edtPassword.requestFocus();
                    }
                } else {
                    edtPassword.setError("Please enter your Password");
                    edtPassword.requestFocus();
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
}
