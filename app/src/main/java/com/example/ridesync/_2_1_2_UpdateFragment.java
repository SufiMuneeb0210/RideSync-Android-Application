package com.example.ridesync;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class _2_1_2_UpdateFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    RecyclerView recyclerView;
    _9_2_1_RecyclerViewCustomAdapter myAdapter;
    ArrayList<_9_ModelUserData> list;
    Spinner _spinner;
    Button btnLogin;
    EditText edtEmail;
    private DatabaseReference ActivityRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_212_update_fragment, container, false);
        ViewSetup(view);
        ListView();
        return view;
    }


    private void ListView() {
        String _job = _spinner.getSelectedItem().toString();

        ActivityRef.child("Users").child(_job).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    _9_ModelUserData newdata = new _9_ModelUserData();
                    newdata.setName(dataSnapshot.child("Name").getValue().toString());
                    newdata.setEmail(dataSnapshot.child("Email").getValue().toString());
                    newdata.setPhone(dataSnapshot.child("Phone").getValue().toString());
                    newdata.setGender(dataSnapshot.child("Gender").getValue().toString());
                    newdata.setPassword(dataSnapshot.child("Password").getValue().toString());
                    newdata.setJob(_job);
                    list.add(newdata);
                }
                myAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ViewSetup(View view) {
        recyclerView = view.findViewById(R.id.datalist);
        ActivityRef = FirebaseDatabase.getInstance().getReference() ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btnLogin= view.findViewById(R.id.btnLogin);
        edtEmail = view.findViewById(R.id.edtemail);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });

        list = new ArrayList<>();
        myAdapter = new _9_2_1_RecyclerViewCustomAdapter(getContext(),list);
        recyclerView.setAdapter(myAdapter);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.SearchNature,
                R.layout.activity_11_signup_screen_dropdown_layout
        );
        adapter.setDropDownViewResource(R.layout.activity_11_signup_screen_dropdown_item);
        _spinner = view.findViewById(R.id.edtnatureofwork);
        _spinner.setAdapter(adapter);
        _spinner.setOnItemSelectedListener(this);

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
        String _job = _spinner.getSelectedItem().toString();
        String _edtemail = edtEmail.getText().toString();
        if(_edtemail.isEmpty())
        {
            edtEmail.setError("Please enter email");
            edtEmail.requestFocus();
            return;
        }
        list.clear();
        ActivityRef.child("Users").child(_job).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(_edtemail).exists())
                {
                    _9_ModelUserData newdata = new _9_ModelUserData();
                    newdata.setName(snapshot.child(_edtemail).child("Name").getValue().toString());
                    newdata.setEmail(snapshot.child(_edtemail).child("Email").getValue().toString());
                    newdata.setPhone(snapshot.child(_edtemail).child("Phone").getValue().toString());
                    newdata.setGender(snapshot.child(_edtemail).child("Gender").getValue().toString());
                    newdata.setPassword(snapshot.child(_edtemail).child("Password").getValue().toString());
                    newdata.setJob(_job);
                    list.add(newdata);
                    myAdapter.notifyDataSetChanged();
                }
                else{
                    edtEmail.setError("User does not exists");
                    edtEmail.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
