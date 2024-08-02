package com.androproj.ex1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Mobile_Otp extends AppCompatActivity {

    EditText phoneNumberEditText;
    CheckBox termsCheckBox;
    Button getOtpButton;
    ProgressBar progress_bar_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mobile_otp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        termsCheckBox = findViewById(R.id.termsCheckBox);
        getOtpButton = findViewById(R.id.getOtpButton);
        progress_bar_otp = findViewById(R.id.progress_bar_otp);

        getOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumberres = phoneNumberEditText.getText().toString().trim();

//                Intent verifyIn = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(verifyIn);

                if (TextUtils.isEmpty(phoneNumberres)) {
                    Toast.makeText(Mobile_Otp.this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                    phoneNumberEditText.setError("Phone Number is Required");
                    phoneNumberEditText.requestFocus();
                } else if (phoneNumberres.length() != 10) {
                    Toast.makeText(Mobile_Otp.this, "Please Re-enter your mobile Number", Toast.LENGTH_SHORT).show();
                    phoneNumberEditText.setError("Phone Number 10 digits is req");
                    phoneNumberEditText.requestFocus();
                } else if (!termsCheckBox.isChecked()) {
                    Toast.makeText(Mobile_Otp.this, "Please accept the Terms and Conditions", Toast.LENGTH_SHORT).show();
                } else {
                    progress_bar_otp.setVisibility(View.VISIBLE);
                    getOtpButton.setVisibility(View.INVISIBLE);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + phoneNumberres,
                            60,
                            TimeUnit.SECONDS,
                            Mobile_Otp.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    // Auto verification completed
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progress_bar_otp.setVisibility(View.GONE);
                                    getOtpButton.setVisibility(View.VISIBLE);
                                    Toast.makeText(Mobile_Otp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String backendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    progress_bar_otp.setVisibility(View.GONE);
                                    getOtpButton.setVisibility(View.VISIBLE);
                                    Intent verifyIn = new Intent(getApplicationContext(), mobile_otp_verifiy.class);
                                    verifyIn.putExtra("mobile", phoneNumberres);
                                    verifyIn.putExtra("backendOtp", backendOtp);
                                    startActivity(verifyIn);
                                }
                            }
                    );
                }
            }
      });
    }
}
