package com.example.ridesync;

import android.content.Intent;
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

public class _2_2_AdminBusesScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private boolean closeapp;
    Spinner actionSpinner;
    Fragment ifragment,udfragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_22_admin_buses_screen);
        Viewsetup();
    }

    private void Viewsetup() {
        closeapp=false;
        ifragment=new _2_2_1_BusInsertFragment();
        udfragment=new _2_2_2_BusUpdateDeleteFragment();
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

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, _2_AdminHomeScreen.class);
        startActivity(intent);

    }
}
