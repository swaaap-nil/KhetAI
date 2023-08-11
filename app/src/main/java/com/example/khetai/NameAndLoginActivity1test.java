package com.example.khetai;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class NameAndLoginActivity1test extends AppCompatActivity {

    MaterialButton getOTPBtn,getStartedBtn,verifyOTPBtn,LetsGOBtn;
    TextView nameTextView, contactTextView;
    TextInputEditText editTextname,contactEditText,otpEditText;
    private String phoneNumber;
    private FirebaseAuth mAuth;
    private String verificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mCode;
    ProgressBar progressBar,VerifyProgress, LetsGoProgress;
    Context context;
    private static final String KEY_VERIFICATION_ID = "key_verification_id";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    View tick;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage);

        getStartedBtn = findViewById(R.id.btn_getStartedFirstPage);
        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToSecondPage();
            }

            private void moveToSecondPage() {

                setContentView(R.layout.secondpage);
                getOTPBtn=findViewById(R.id.btn_getOTP1);
                editTextname =findViewById(R.id.editText_yourName);
                contactEditText =findViewById(R.id.contactEditText1);
                progressBar = findViewById(R.id.progressBarSecondPage);
                TextInputLayout editTextLayout = findViewById(R.id.textInputLayout3);
                TextInputLayout editContactLayout = findViewById(R.id.textInputLayout5);


                contactEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                if(contactEditText.getText().toString().length()>=1)
                                    editContactLayout.setHint("");
                                else editContactLayout.setHint("Contact Number");
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                editTextname.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(editTextname.getText().toString().length()>=1)
                            editTextLayout.setHint("");
                        else editTextLayout.setHint("Name");
                    }

                    @Override
                    public void afterTextChanged(Editable s) {


                    }
                });


                context = getApplicationContext();
                FirebaseApp.initializeApp(context);
                mAuth = FirebaseAuth.getInstance();
                FirebaseAuthSettings firebaseAuthSettings = mAuth.getFirebaseAuthSettings();
                firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, mCode);

                getOTPBtn.setOnClickListener(v -> {

                    preferences = getSharedPreferences("FormFilled", MODE_PRIVATE);
                    editor = preferences.edit();
                    editor.putString("name", editTextname.getText().toString());

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

                                setContentView(R.layout.thirdpage);
                                Log.d(TAG, "onCodeSent:" + verificationId);
                                verifyOTPBtn = findViewById(R.id.btn_verifyotp);
                                VerifyProgress=findViewById(R.id.progressBaryoo);
                                VerifyProgress.setVisibility(View.INVISIBLE);
                                LetsGoProgress =findViewById(R.id.LetsGoProgress);
                                LetsGoProgress.setVisibility(View.INVISIBLE);
                                tick = findViewById(R.id.tick2);
                                // Save verification ID and resending token so we can use them later
                                Toast.makeText(context, "Code Sent", Toast.LENGTH_SHORT).show();
                                NameAndLoginActivity1test.this.verificationId = verificationId;
                                mResendToken = token;


                                verifyOTPBtn.setOnClickListener(v1 -> {

                                    VerifyProgress.setVisibility(View.VISIBLE);
                                    View view = NameAndLoginActivity1test.this.getCurrentFocus();
                                    if (view != null) {
                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                    }
                                    otpEditText = findViewById(R.id.edittextotp);
                                    mCode = otpEditText.getText().toString().trim();


                                    if (verificationId == null && savedInstanceState != null) {
                                        onRestoreInstanceState(savedInstanceState);
                                    }
                                    verifyPhoneNumberWithCode(NameAndLoginActivity1test.this.verificationId, mCode);
                                });
                            }
                        };

                        PhoneAuthOptions options =
                                PhoneAuthOptions.newBuilder(mAuth)
                                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                        .setActivity(NameAndLoginActivity1test.this)                 // Activity (for callback binding)
                                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                        .build();
                        PhoneAuthProvider.verifyPhoneNumber(options);
                    }
                });
            }
        });




//        getStartedBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                preferences = getSharedPreferences("FormFilled", MODE_PRIVATE);
//                editor = preferences.edit();
//                editor.putString("name",editTextname.getText().toString());
//                goToFormActivity();
//            }
//        });


    }
    private void goToFormActivity() {
        finish();
        Intent intent = new Intent(NameAndLoginActivity1test.this, FormTestActivity2.class);
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
                NameAndLoginActivity1test.this.verificationId = verificationId;
                mResendToken = token;
                verifyOTPBtn.setOnClickListener(v -> {

                    verifyOTPBtn.setVisibility(View.INVISIBLE);
                    VerifyProgress.setVisibility(View.VISIBLE);

                    otpEditText=findViewById(R.id.editText_otp);
                    mCode=otpEditText.getText().toString().trim();



                    verifyPhoneNumberWithCode(NameAndLoginActivity1test.this.verificationId,mCode);
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
                        VerifyProgress.setVisibility(View.INVISIBLE);
                        tick.setVisibility(View.VISIBLE);
                        FirebaseUser user = task.getResult().getUser();
                        Toast.makeText(context, "Verification Successful",Toast.LENGTH_SHORT).show();
                        LetsGOBtn = findViewById(R.id.btn_letsGO);
                        LetsGOBtn.setEnabled(true);
                        LetsGOBtn.setVisibility(View.VISIBLE);
                        verifyOTPBtn.setVisibility(View.INVISIBLE);

                        LetsGOBtn.setOnClickListener(v -> {

                            LetsGOBtn.setVisibility(View.INVISIBLE);
                            LetsGoProgress.setVisibility(View.VISIBLE);
                            goToFormActivity();
                        });



                    } else {

                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(context, "Invalid Verification code",Toast.LENGTH_SHORT).show();
                            verifyOTPBtn=findViewById(R.id.btn_verifyotp);
                            VerifyProgress=findViewById(R.id.progressBaryoo);
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