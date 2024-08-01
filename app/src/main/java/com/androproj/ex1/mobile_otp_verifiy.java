package com.androproj.ex1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class mobile_otp_verifiy extends AppCompatActivity {

    EditText input1, input2, input3, input4, input5, input6;
    Button verify_button;
    String getotpbackend;
    private DatabaseReference mDatabase;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mobile_otp_verifiy);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        verify_button = findViewById(R.id.verify_otp_button);
        input1 = findViewById(R.id.Ver1text);
        input2 = findViewById(R.id.Ver2text);
        input3 = findViewById(R.id.Ver3text);
        input4 = findViewById(R.id.Ver4text);
        input5 = findViewById(R.id.Ver5text);
        input6 = findViewById(R.id.Ver6text);

        TextView mobile_number = findViewById(R.id.mobile_number);
        ProgressBar progress_bar_verify = findViewById(R.id.progress_bar_verify);
        mobile_number.setText(String.format("+91-%s", getIntent().getStringExtra("mobile")));

        getotpbackend = getIntent().getStringExtra("backendOtp");
        phoneNumber = getIntent().getStringExtra("mobile");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        verify_button.setOnClickListener(v -> {
            String enterCodeOtp = input1.getText().toString() + input2.getText().toString() + input3.getText().toString() + input4.getText().toString() + input5.getText().toString() + input6.getText().toString();

            if (enterCodeOtp.length() == 6) {
                if (getotpbackend != null) {
                    progress_bar_verify.setVisibility(View.VISIBLE);
                    verify_button.setVisibility(View.INVISIBLE);

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getotpbackend, enterCodeOtp);

                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(task -> {
                                progress_bar_verify.setVisibility(View.GONE);
                                verify_button.setVisibility(View.VISIBLE);

                                if (task.isSuccessful()) {
                                    checkIfUserExists();
                                } else {
                                    Toast.makeText(mobile_otp_verifiy.this, "Enter Correct OTP", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(mobile_otp_verifiy.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mobile_otp_verifiy.this, "Please Enter 6-Digit OTP", Toast.LENGTH_SHORT).show();
            }
        });

        numberotpmove();

        findViewById(R.id.Resendotp).setOnClickListener(v -> {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+91" + getIntent().getStringExtra("mobile"),
                    60,
                    TimeUnit.SECONDS,
                    mobile_otp_verifiy.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            // Auto verification completed
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.makeText(mobile_otp_verifiy.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String newbackendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            getotpbackend = newbackendOtp;
                            Toast.makeText(mobile_otp_verifiy.this, "Otp Sent Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        });
    }

    private void checkIfUserExists() {
        mDatabase.child("users").child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Intent mainIntent = new Intent(mobile_otp_verifiy.this, verifying_animation.class);
                    mainIntent.putExtra("mobile", phoneNumber);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                } else {
                    Intent enterDetailsIntent = new Intent(mobile_otp_verifiy.this, verifying_animation.class);
                    enterDetailsIntent.putExtra("mobile", phoneNumber);
                    startActivity(enterDetailsIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mobile_otp_verifiy.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void numberotpmove() {
        input1.addTextChangedListener(createTextWatcher(input2));
        input2.addTextChangedListener(createTextWatcher(input3));
        input3.addTextChangedListener(createTextWatcher(input4));
        input4.addTextChangedListener(createTextWatcher(input5));
        input5.addTextChangedListener(createTextWatcher(input6));
    }

    private TextWatcher createTextWatcher(EditText nextInput) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    nextInput.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };
    }

}