package com.sunidhi.utility;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

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