package com.sunidhi.utility.util;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MyUtil {

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
                (dialog, which) -> {
                    printToast(context, positive);
                });

        builder.setNegativeButton(negative,
                (dialog, which) -> {
                    dialog.cancel();
                });

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
    public static void topSnackbar(View v, String msg, String actionMsg){
        Snackbar snack = Snackbar.make(v, msg, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        snack.setAction(actionMsg, new MyActionListener());
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
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
}
