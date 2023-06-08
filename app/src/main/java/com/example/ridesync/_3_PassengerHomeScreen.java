package com.example.ridesync;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class _3_PassengerHomeScreen extends AppCompatActivity {

    _9_3_SessionManager sessionManager;
    String[] cardData = {
            "E wallet", "Tickets", "Passes",
            "My Profile", "Logout"
    };
    Integer[] image = {
            R.drawable.wallet, R.drawable.ticket, R.drawable.qrcode,
            R.drawable.selfprofile, R.drawable.logout
    };
    ListView listView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_passenger_home_screen);
        listView = findViewById(R.id.listviewAdmin);
        sessionManager= new _9_3_SessionManager(getApplicationContext());
        _9_1_ListViewCustom adapter = new _9_1_ListViewCustom(this, cardData, image);
        listView.setAdapter(adapter);
        ListEvents();
    }
    void ListEvents()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent = null;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        intent = new Intent(_3_PassengerHomeScreen.this,_3_1_WalletScreen.class);
                        break;
                    case 1:
                        intent = new Intent(_3_PassengerHomeScreen.this,_3_2_TicketsScreen.class);
                        break;
                    case 2:
                        intent= new Intent(_3_PassengerHomeScreen.this, _3_3_PassScreen.class);
                        break;
                    case 3:
                        intent = new Intent(_3_PassengerHomeScreen.this, _5_SelfProfileScreen.class);
                        break;
                    case 4:
                        intent = new Intent(_3_PassengerHomeScreen.this, _1_LoginScreen.class);
                        sessionManager.clearUserDetails();
                        sessionManager.setLoggedIn(false);
                        break;
                }
                if (intent != null) {
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
