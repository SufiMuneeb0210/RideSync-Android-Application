package com.example.ridesync;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class _2_AdminHomeScreen extends AppCompatActivity {

    _9_3_SessionManager sessionManager;
    String[] cardData = {
            "Profiles", "Buses",
            "Passes",
            "My Profile", "Logout"
    };
    Integer[] image = {
            R.drawable.profiles, R.drawable.bus,
            R.drawable.qrcode, R.drawable.selfprofile, R.drawable.logout
    };
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_admin_home_screen);
        listView = findViewById(R.id.listviewAdmin);
        sessionManager= new _9_3_SessionManager(getApplicationContext());
        _9_1_ListViewCustom adapter = new _9_1_ListViewCustom(this, cardData, image);
        listView.setAdapter(adapter);
        ListEvents();

    }
    void ListEvents()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {

                    case 0:
                        intent = new Intent(_2_AdminHomeScreen.this, _2_1_AdminProfilesScreen.class);
                        break;
                    case 1:
                        intent = new Intent(_2_AdminHomeScreen.this, _2_2_AdminBusesScreen.class);
                        break;
                    case 2:
                        intent=new Intent(_2_AdminHomeScreen.this,_2_3_AdminPassesScreen.class);
                        break;
                    case 3:
                        intent = new Intent(_2_AdminHomeScreen.this, _5_SelfProfileScreen.class);
                        break;
                    case 4:
                        intent = new Intent(_2_AdminHomeScreen.this, _1_LoginScreen.class);
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
