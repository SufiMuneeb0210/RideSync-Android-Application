package com.example.ridesync;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class _0_SplashScreen extends AppCompatActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_0_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        _9_3_SessionManager sessionManager = new _9_3_SessionManager(getApplicationContext());
        boolean isLoggedIn = sessionManager.isLoggedIn();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;

                if (!isLoggedIn) {
                    intent = new Intent(_0_SplashScreen.this, _1_LoginScreen.class);
                } else {
                    String activityName = sessionManager.getActivityName();
                    String packageName = getPackageName();
                    String fullyQualifiedClassName = packageName + "." + activityName;

                    try {
                        Class<?> activityClass = Class.forName(fullyQualifiedClassName);
                        intent = new Intent(_0_SplashScreen.this, activityClass);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        intent = new Intent(_0_SplashScreen.this, _1_LoginScreen.class);
                    }
                }

                startActivity(intent);
                finish();
            }
        }, 3000);
    }

}
