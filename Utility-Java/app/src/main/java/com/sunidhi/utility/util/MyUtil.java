package com.sunidhi.utility.util;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtil extends BroadcastReceiver {

    private static final int REQ_USER_CONSENT = 200;
    public static SmsBroadcastListener smsBroadcastListener;

    /**
     * Toast message
     */
    public static void printToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * alertDialog
     */
    public static void alertDialog(Context context, String title, String msg, String positive, String negative) {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(context);

        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton(positive,
                (dialog, which) -> printToast(context, positive));

        builder.setNegativeButton(negative,
                (dialog, which) -> dialog.cancel());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Snackbar with message
     */
    public static void popUpMessage(View view, String msg) {
        Snackbar snackbar = Snackbar
                .make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    /**
     * Snackbar with action
     */
    public static void actionSnackbar(View view, String msg, String actionMsg) {
        Snackbar snackbar = Snackbar
                .make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.setAction(actionMsg, new MyActionListener());
        snackbar.show();

    }

    private static class MyActionListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Action performed", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Snackbar from top
     */
    public static void topSnackbar(View v, String msg, String actionMsg) {
        Snackbar snack = Snackbar.make(v, msg, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        snack.setAction(actionMsg, new MyActionListener());
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snack.show();
    }


    /**
     * Share data
     */
    public static Intent share(String msg) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        return Intent.createChooser(intent, "Share Using");
    }


    /**
     * App Review
     */
    public static Intent reviewApp(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
        } catch (android.content.ActivityNotFoundException e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName));
        }
    }

    /**
     * Fetch OTP
     */

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SmsRetriever.SMS_RETRIEVED_ACTION)) {
            Bundle extras = intent.getExtras();
            Status smsReceiverStatus = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
            switch (smsReceiverStatus.getStatusCode()) {
                case CommonStatusCodes
                        .SUCCESS:
                    Intent messageIntent = extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT);
                    smsBroadcastListener.onSuccess(messageIntent);
                    break;
                case CommonStatusCodes.TIMEOUT:
                    smsBroadcastListener.onFailure();
                    break;
            }
        }
    }

    public static void startSmartUserConsent(Activity activity) {
        SmsRetrieverClient client = SmsRetriever.getClient(activity);
        client.startSmsUserConsent(null);
    }

    public static String returnOtp(int requestCode, int resultCode, @Nullable Intent data, int RESULT_OK) {
        String otp = "";
        if (requestCode == REQ_USER_CONSENT) {
            if (resultCode == RESULT_OK && data != null) {
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                otp = getOtpFromMessage(message);
            }
        }
        return otp;
    }

    private static String getOtpFromMessage(String message) {
        Pattern otpPattern = Pattern.compile("(|^)\\d{4}");
        Matcher matcher = otpPattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    //Interface
    public interface SmsBroadcastListener {
        void onSuccess(Intent intent);

        void onFailure();
    }

    /**
     * Validation
     */

    //Validate Name
    public static boolean validateName(String name){
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

        Matcher hasDigit = digit.matcher(name);
        Matcher hasSpecial = special.matcher(name);

        return name.length() > 2 && !hasDigit.find() && !hasSpecial.find();
    }

    public static boolean validatePassword(String password)
    {

        if(password.length()>=8)
        {
            Pattern letter = Pattern.compile("[a-zA-z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetter.find() && hasDigit.find() && hasSpecial.find();

        }
        else
            return false;

    }

    public static boolean validateEmail(String email){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).find();
    }

    public static boolean validatePhoneNumber(String number) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public static boolean validateAadharNumber(String str)
    {
        String regex = "^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$";
        Pattern p = Pattern.compile(regex);
        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean validatePANCard(String str){
        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");

        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
