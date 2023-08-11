package com.example.khetai;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class NameAndLoginActivity1 extends AppCompatActivity {

    AppCompatButton getOTPBtn,getStartedBtn,verifyOTPBtn;
    TextView nameTextView, contactTextView;
    AppCompatEditText nameEditText,contactEditText,otpEditText;
    private String phoneNumber;
    private FirebaseAuth mAuth;
    private String verificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mCode;
    ProgressBar progressBar,VerifyProgress,getStartedProgress;
    Context context;
    private static final String KEY_VERIFICATION_ID = "key_verification_id";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    View tick;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_and_login_1);
        getStartedBtn = findViewById(R.id.btn_getStartedFirstPage);
        getOTPBtn=findViewById(R.id.btn_getOTP);
        nameTextView =findViewById(R.id.textView_yourName);
        contactTextView =findViewById(R.id.textView_contact);
        nameEditText =findViewById(R.id.editText_yourName);
        contactEditText=findViewById(R.id.editText_otp);
        AppCompatEditText editTextname = findViewById(R.id.editText_yourName);
        progressBar = findViewById(R.id.progressBar_getOTP);




        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseAuthSettings firebaseAuthSettings = mAuth.getFirebaseAuthSettings();
        firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, mCode);
        context = getApplicationContext();

            getStartedBtn.setEnabled(true);
        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preferences = getSharedPreferences("FormFilled", MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("name",editTextname.getText().toString());
                goToFormActivity();
            }
        });

        getOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preferences = getSharedPreferences("FormFilled", MODE_PRIVATE);
               editor = preferences.edit();
                editor.putString("name",editTextname.getText().toString());

                if (contactEditText.getText().toString().trim().length() != 10)
                    Toast.makeText(getApplicationContext(), "Please Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                else {
                    phoneNumber = "+91" + contactEditText.getText().toString().trim();
                    progressBar.setVisibility(View.VISIBLE);
                    getOTPBtn.setVisibility(View.INVISIBLE);

                    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                            signInWithPhoneAuthCredential(credential);
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Log.w(TAG, "onVerificationFailed", e);

                            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                // Invalid request
                                Toast.makeText(context, "Invalid request", Toast.LENGTH_SHORT).show();
                            } else if (e instanceof FirebaseTooManyRequestsException) {
                                // The SMS quota for the project has been exceeded
                                Toast.makeText(context, "SMS quota for the project has been exceeded", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(context, "OTP Verification Failed", Toast.LENGTH_SHORT).show();
                            // Show a message and update the UI
                        }


                        @Override
                        public void onCodeSent(@NonNull String verificationId,
                                               @NonNull PhoneAuthProvider.ForceResendingToken token) {

                            setContentView(R.layout.activity_otp);
                            Log.d(TAG, "onCodeSent:" + verificationId);
                            verifyOTPBtn = findViewById(R.id.btn_verifyOTP);
                            tick =findViewById(R.id.tick);
                            // Save verification ID and resending token so we can use them later
                            Toast.makeText(context, "Code Sent", Toast.LENGTH_SHORT).show();
                            NameAndLoginActivity1.this.verificationId = verificationId;
                            mResendToken = token;


                            verifyOTPBtn.setOnClickListener(v1 -> {

                                View view = NameAndLoginActivity1.this.getCurrentFocus();
                                if (view != null) {
                                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                }
                                otpEditText = findViewById(R.id.editText_otp);
                                mCode = otpEditText.getText().toString().trim();


                                if (verificationId == null && savedInstanceState != null) {
                                    onRestoreInstanceState(savedInstanceState);
                                }
                                verifyPhoneNumberWithCode(NameAndLoginActivity1.this.verificationId, mCode);
                            });
                        }
                    };

                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(mAuth)
                                    .setPhoneNumber(phoneNumber)       // Phone number to verify
                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                    .setActivity(NameAndLoginActivity1.this)                 // Activity (for callback binding)
                                    .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });
    }
    private void goToFormActivity() {
        finish();
        Intent intent = new Intent(NameAndLoginActivity1.this, FormTestActivity2.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_VERIFICATION_ID,verificationId);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        verificationId = savedInstanceState.getString(KEY_VERIFICATION_ID);
    }

    private void sendVerificationCode() {

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }


            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                Log.d(TAG, "onCodeSent:" + verificationId);
                //setContentView(R.layout.activity_otp);
                verifyOTPBtn=findViewById(R.id.btn_verifyOTP);
                VerifyProgress=findViewById(R.id.verify_progress);


                // Save verification ID and resending token so we can use them later
                Toast.makeText(context, "Code Sent",Toast.LENGTH_SHORT).show();
                NameAndLoginActivity1.this.verificationId = verificationId;
                mResendToken = token;
                verifyOTPBtn.setOnClickListener(v -> {

                    verifyOTPBtn.setVisibility(View.INVISIBLE);
                    VerifyProgress.setVisibility(View.VISIBLE);

                    otpEditText=findViewById(R.id.editText_otp);
                    mCode=otpEditText.getText().toString().trim();



                    verifyPhoneNumberWithCode(NameAndLoginActivity1.this.verificationId,mCode);
                });
            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyPhoneNumberWithCode(String mVerificationId,String code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        tick.setVisibility(View.VISIBLE);
                        FirebaseUser user = task.getResult().getUser();
                        Toast.makeText(context, "Verification Successful",Toast.LENGTH_SHORT).show();
                        VerifyProgress=findViewById(R.id.verify_progress);
                        getStartedBtn=findViewById(R.id.btn_getStartedFirstPage);
                        verifyOTPBtn=findViewById(R.id.btn_verifyOTP);
                        getStartedProgress=findViewById(R.id.getStarted_progress);


                        VerifyProgress.setVisibility(View.INVISIBLE);
                        getStartedBtn.setEnabled(true);
                        verifyOTPBtn.setVisibility(View.INVISIBLE);

                        getStartedBtn.setOnClickListener(v -> {

                            getStartedProgress.setVisibility(View.VISIBLE);
                            goToFormActivity();
                        });



                    } else {

                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(context, "Invalid Verification code",Toast.LENGTH_SHORT).show();
                            verifyOTPBtn=findViewById(R.id.btn_verifyOTP);
                            VerifyProgress=findViewById(R.id.verify_progress);
                            VerifyProgress.setVisibility(View.INVISIBLE);
                            verifyOTPBtn.setVisibility(View.VISIBLE);


                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(getApplicationContext(),"Back Disabled",Toast.LENGTH_SHORT);
    }
}