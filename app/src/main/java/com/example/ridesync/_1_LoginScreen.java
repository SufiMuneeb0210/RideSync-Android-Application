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

public class _1_LoginScreen extends AppCompatActivity {

    _9_3_SessionManager sessionManager;
    private DatabaseReference loginActivityRef;
    private Spinner loginSpinner;
    private EditText loginEmail,loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_login_screen);
        Viewsetup();
    }

    private void Viewsetup() {
        loginActivityRef = FirebaseDatabase.getInstance().getReference();
        loginEmail=findViewById(R.id.edtemail);
        loginPassword=findViewById(R.id.edtpassword);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.LoginPerson,
                R.layout.activity_11_signup_screen_dropdown_layout
        );
        adapter.setDropDownViewResource(R.layout.activity_11_signup_screen_dropdown_item);
        loginSpinner=findViewById(R.id.edtloginperson);
        loginSpinner.setAdapter(adapter);
    }


    public void MoveToSignUp(View view) {
        Intent intent = new Intent(this, _1_1_SignupScreen.class);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(_1_LoginScreen.this).toBundle();
        startActivity(intent,bundle);
    }

    public void Log_in(View view) {
        String _loginemail = loginEmail.getText().toString();
        String _loginPassword = loginPassword.getText().toString();
        String _loginSpinner = loginSpinner.getSelectedItem().toString();

        if(!_loginSpinner.equals("Who?"))
        {
            if(!_loginemail.isEmpty())
            {
                if(!_loginPassword.isEmpty())
                {
                    if(_loginPassword.length() >= 8)
                    {
                        FirebaseLogin(_loginemail,_loginPassword,_loginSpinner);
                    }
                    else{
                        loginPassword.setError("Password must be greater than 8 characters");
                        loginPassword.requestFocus();
                    }
                }
                else{
                    loginPassword.setError("Provide a Password");
                    loginPassword.requestFocus();
                }
            }
            else{
                loginEmail.setError("Provide an Email");
                loginEmail.requestFocus();
            }

        }
        else{
            View selectedView = loginSpinner.getSelectedView();
            if (selectedView instanceof TextView) {
                TextView selectedTextView = (TextView) selectedView;
                selectedTextView.setError("Provide a User Type");
            }
            loginSpinner.requestFocus();
        }
    }

    private void FirebaseLogin(String _loginemail, String _loginPassword, String _loginSpinner) {
        loginActivityRef.child("Users").child(_loginSpinner).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(_loginemail).exists())
                {
                    String _passwordreturn = snapshot.child(_loginemail).child("Password").getValue().toString();
                    String _name = snapshot.child(_loginemail).child("Name").getValue().toString();
                    String _phone = snapshot.child(_loginemail).child("Phone").getValue().toString();
                    String _gender = snapshot.child(_loginemail).child("Gender").getValue().toString();
                    int _balance = 0;
                    if(_loginSpinner.equals("Passenger"))
                    {
                        _balance = Integer.parseInt(snapshot.child(_loginemail).child("Money").getValue().toString());
                    }
                    if(_passwordreturn.equals(_loginPassword))
                    {
                        sessionManager = new _9_3_SessionManager(getApplicationContext());
                        Intent intent = null;
                        if(_loginSpinner.equals("Passenger"))
                        {
                            sessionManager.setActivityName("_3_PassengerHomeScreen");
                            sessionManager.setBalance(_balance);
                            intent = new Intent(_1_LoginScreen.this, _3_PassengerHomeScreen.class);
                        }
                        else if(_loginSpinner.equals("Admin"))
                        {
                            sessionManager.setActivityName("_2_AdminHomeScreen");
                            intent = new Intent(_1_LoginScreen.this, _2_AdminHomeScreen.class);
                        }
                        sessionManager.setLoggedIn(true);
                        sessionManager.saveUserDetails(_name,_phone,_passwordreturn,_gender,_loginSpinner,_loginemail);
                        loginPassword.setText("");
                        loginEmail.setText("");
                        loginSpinner.setSelection(0);

                        Toast.makeText(_1_LoginScreen.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        intent.putExtra("email",_loginemail);
                        intent.putExtra("password",_loginPassword);
                        intent.putExtra("job",_loginSpinner);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        loginPassword.setError("Incorrect Password");
                        loginPassword.requestFocus();
                    }
                }
                else{
                    loginEmail.setError("User does not exists");
                    loginEmail.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(_1_LoginScreen.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
