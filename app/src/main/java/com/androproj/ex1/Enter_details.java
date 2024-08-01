package com.androproj.ex1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Enter_details extends AppCompatActivity {

    EditText username;
    EditText email;
    AutoCompleteTextView city;
    EditText DOB;
    Button enter_button;

    private DatabaseReference mDatabase;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enter_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toast.makeText(this, "Register Yourself Please!", Toast.LENGTH_SHORT).show();
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        city = findViewById(R.id.city);
        DOB = findViewById(R.id.DOB);
        enter_button = findViewById(R.id.enter_button);

        String[] cities = {"Mumbai", "Nagpur", "Pune", "Nasik"};

        ArrayAdapter<String> adapterCompleteCity = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cities);
        city.setAdapter(adapterCompleteCity);
        // Set the threshold for showing suggestions
        city.setThreshold(1);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        phoneNumber = getIntent().getStringExtra("mobile"); //mobile number entered

        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Obtain the data entered
                String usernameres = username.getText().toString().trim();
                String emailres = email.getText().toString().trim();
                String cityres = city.getText().toString().trim();
                String DOBres = DOB.getText().toString().trim();

                boolean isValidCity = false;

                // Check if the selected city is in the list of valid cities
                for (String city : cities) {
                    if (city.equalsIgnoreCase(cityres)) {
                        isValidCity = true;
                        break;
                    }
                }

                if (TextUtils.isEmpty(usernameres)) {
                    Toast.makeText(Enter_details.this, "Please Enter Full Name", Toast.LENGTH_LONG).show();
                    username.setError("Full Name Is Required");
                    username.requestFocus();
                } else if (TextUtils.isEmpty(emailres)) {
                    Toast.makeText(Enter_details.this, "Please Enter Email Address", Toast.LENGTH_LONG).show();
                    email.setError("Email Is Required");
                    email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailres).matches()) {
                    Toast.makeText(Enter_details.this, "Please Re-Enter Email Address", Toast.LENGTH_LONG).show();
                    email.setError("Valid Email Address is Required");
                    email.requestFocus();
                } else if (TextUtils.isEmpty(cityres)) {
                    Toast.makeText(Enter_details.this, "Please Enter City Name", Toast.LENGTH_LONG).show();
                    city.setError("City Name is Required");
                    city.requestFocus();
                } else if (!isValidCity) {
                    Toast.makeText(Enter_details.this, "Service not available in your city.\n Please select from the given cities.", Toast.LENGTH_SHORT).show();
                    city.setError("Select from the given");
                    city.requestFocus();
                } else if (TextUtils.isEmpty(DOBres)) {
                    Toast.makeText(Enter_details.this, "Please Enter Your DOB", Toast.LENGTH_SHORT).show();
                    DOB.setError("DOB is Required");
                    DOB.requestFocus();
                } else {
                    saveUserProfile(usernameres, emailres, cityres, DOBres);
                }
            }
        });
    }

    private void saveUserProfile(String name, String email, String city, String dob) {
        UserProfile userProfile = new UserProfile(name, email, city, dob);
        mDatabase.child("users").child(phoneNumber).setValue(userProfile)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent mainIntent = new Intent(Enter_details.this, MainActivity.class);
                            mainIntent.putExtra("mobile", phoneNumber);
                            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                        } else {
                            Toast.makeText(Enter_details.this, "Failed to save user data. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static class UserProfile {
        public String name;
        public String email;
        public String city;
        public String dob;

        public UserProfile() {
        }

        public UserProfile(String name, String email, String city, String dob) {
            this.name = name;
            this.email = email;
            this.city = city;
            this.dob = dob;
        }
    }
}
