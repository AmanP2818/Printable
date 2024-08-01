package com.androproj.ex1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUserStatus();
            }
        }, 2000); // 2 seconds delay for splash screen
    }

    private void checkUserStatus() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is logged in, navigate to MainActivity
            Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(mainIntent);
        } else {
            // User is not logged in, navigate to WelcomeScreen
            Intent welcomeIntent = new Intent(SplashScreen.this, WelcomeScreen.class);
            startActivity(welcomeIntent);
        }
        finish();
    }
}
