package com.androproj.ex1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class verifying_animation extends AppCompatActivity {
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verifying_animation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        phoneNumber = getIntent().getStringExtra("mobile");
        Intent iNext=new Intent(verifying_animation.this, ver_two.class);
        iNext.putExtra("mobile", phoneNumber);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(iNext);
                finish();
            }
        },3000);
    }
}