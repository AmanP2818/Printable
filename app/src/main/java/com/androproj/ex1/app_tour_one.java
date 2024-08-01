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

public class app_tour_one extends AppCompatActivity {

    String phoneNumber;
    private DatabaseReference mDatabase;
    Button tr_nx_one;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_app_tour_one);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        phoneNumber = getIntent().getStringExtra("mobile");

        tr_nx_one=findViewById(R.id.tr_nx_one);

        tr_nx_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next=new Intent(app_tour_one.this, app_tour_two.class);
                next.putExtra("mobile", phoneNumber);
                startActivity(next);
            }
        });

    }
}