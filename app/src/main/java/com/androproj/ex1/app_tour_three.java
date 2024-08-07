package com.androproj.ex1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class app_tour_three extends AppCompatActivity {
    String phoneNumber;
    private DatabaseReference mDatabase;
    Button tr_three_sample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_app_tour_three);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        phoneNumber = getIntent().getStringExtra("mobile");

        tr_three_sample=findViewById(R.id.tr_three_sample);

        tr_three_sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next=new Intent(app_tour_three.this, MainActivity.class);
                next.putExtra("mobile", phoneNumber);
                next.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(next);
            }
        });

    }
}