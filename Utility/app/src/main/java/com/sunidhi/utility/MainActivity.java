package com.sunidhi.utility;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.sunidhi.utility.databinding.ActivityMainBinding;
import com.sunidhi.utility.util.MyUtil;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final int REQ_USER_CONSENT = 200;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Toast
        binding.toastButton.setOnClickListener(v -> MyUtil.printToast(this, "This is my custom Toast"));

        //Dialog Box
        binding.dialogButton.setOnClickListener(v ->
                MyUtil.alertDialog(this, "This is title", "My message", "Okay", "Cancel"));

        //Snackbar with message
        binding.messageButton.setOnClickListener(v ->
                MyUtil.popUpMessage(binding.parentLayout, "This is a message snackbar"));

        //Snackbar with Action
        binding.actionButton.setOnClickListener(v ->
                MyUtil.actionSnackbar(binding.parentLayout, "This is a action snackbar", "DISMISS"));

        //Snackbar from Top
        binding.topSnack.setOnClickListener(v->
                MyUtil.topSnackbar(binding.parentLayout, "Top view", "OK"));

        //Share link/text/message using anther app (Implicit Intent)
        binding.shareButton.setOnClickListener(v -> {
            String link = "https://images.pexels.com/photos/1086178/pexels-photo-1086178.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260";
            startActivity(MyUtil.share(link));
        });

        //Rate App
        binding.rateButton.setOnClickListener(v -> startActivity(MyUtil.reviewApp(this)));

        //FetchOTP
        MyUtil.startSmartUserConsent(this);
        registerBroadcastReceiver();
        binding.submitOTP.setOnClickListener(v->{
                Intent intent = new Intent(this, Activity2.class);
                startActivity(intent);

        });

        /**
         * Validation
         */
        //Name
        binding.name.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String name = binding.name.getText().toString();
                if(!MyUtil.validateName(name)){
                    binding.name.setError("Invalid name");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });


        //E-Mail
        binding.email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String email = binding.email.getText().toString();
                if(!MyUtil.validateEmail(email)){
                    binding.email.setError("Invalid email");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });


        //Password
        binding.password.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                MyUtil.topSnackbar(binding.parentLayout,
                        "Password must be 8 characters long and contain letters, digits and special character",
                        "OK");
            }
            });

        binding.password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String password = binding.password.getText().toString();
                if(!MyUtil.validatePassword(password)){
                    binding.password.setError("Invalid Password");

                }

            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });


        //Phone
        binding.phone.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String phone = binding.phone.getText().toString();
                if(!MyUtil.validatePhoneNumber(phone)){
                    binding.phone.setError( "Invalid phone number");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });


        //Aadhar Card
        binding.aadhar.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                MyUtil.topSnackbar(binding.parentLayout,
                        "Add space after every 4 digit",
                        "OK");
            }
        });
        binding.aadhar.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String aadhar = binding.aadhar.getText().toString();
                if(!MyUtil.validateAadharNumber(aadhar)){
                    binding.aadhar.setError("Invalid ID");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });


        //PAN Card
        binding.pan
                .addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                String pan = binding.pan.getText().toString();
                if(!MyUtil.validatePANCard(pan)){
                    binding.pan.setError("Invalid ID");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });

    }

//Fetch OTP
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String otp = MyUtil.returnOtp(requestCode, resultCode, data, RESULT_OK);
        binding.otpField.setText(otp);
            }

    private void registerBroadcastReceiver() {
            MyUtil.smsBroadcastListener = new MyUtil.SmsBroadcastListener() {
                @Override
                public void onSuccess(Intent intent) {
                    startActivityForResult(intent, REQ_USER_CONSENT);
                }
                @Override
                public void onFailure() {
                }
            };
            IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
            registerReceiver(new MyUtil(), intentFilter);
        }


}