package com.example.ridesync;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

public class _2_1_AdminProfilesScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner actionSpinner;
    Fragment ifragment,udfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_21_admin_profiles_screen);
        Viewsetup();
    }

    private void Viewsetup() {
        ifragment=new _2_1_1_InsertFragment();
         udfragment=new _2_1_2_UpdateFragment();
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.AdminProfilesAction,
                R.layout.activity_11_signup_screen_dropdown_layout
        );
        adapter.setDropDownViewResource(R.layout.activity_11_signup_screen_dropdown_item);
        actionSpinner=findViewById(R.id.edtactionspinner);
        actionSpinner.setAdapter(adapter);
        actionSpinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (position) {
            case 0:
                fragmentTransaction.hide(Objects.requireNonNull(fragmentManager.findFragmentById(R.id.insertfragment)));
                fragmentTransaction.show(Objects.requireNonNull(fragmentManager.findFragmentById(R.id.updatefragment)));
                break;
            case 1:
                fragmentTransaction.show(Objects.requireNonNull(fragmentManager.findFragmentById(R.id.insertfragment)));
                fragmentTransaction.hide(Objects.requireNonNull(fragmentManager.findFragmentById(R.id.updatefragment)));
                break;
        }

        fragmentTransaction.commit();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Implement this method if needed
    }

}
