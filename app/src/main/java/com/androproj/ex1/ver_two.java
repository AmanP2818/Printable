package com.androproj.ex1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ver_two extends AppCompatActivity {

    Button BtnContinueVer_2;
    String phoneNumber;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_two);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();
        phoneNumber = getIntent().getStringExtra("mobile");
        BtnContinueVer_2=findViewById(R.id.BtnContinueVer_2);

        BtnContinueVer_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIfUserExists();
            }
        });
    }
    private void checkIfUserExists() {
        mDatabase.child("users").child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Intent mainIntent = new Intent(ver_two.this, app_tour_one.class);
                    mainIntent.putExtra("mobile", phoneNumber);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                } else {
                    Intent enterDetailsIntent = new Intent(ver_two.this, Enter_details.class);
                    enterDetailsIntent.putExtra("mobile", phoneNumber);
                    startActivity(enterDetailsIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ver_two.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}