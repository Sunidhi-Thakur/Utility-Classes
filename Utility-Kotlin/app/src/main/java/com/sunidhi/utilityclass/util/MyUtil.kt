package com.sunidhi.utilityclass.util

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class MyUtil {

    companion object {


        /**
         * Toast message
         */
        fun printToast(context: Context?, msg: String?) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        /**
         * alertDialog
         */
        fun alertDialog(
            context: Context?,
            title: String?,
            msg: String?,
            positive: String?,
            negative: String?
        ) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(msg)
            builder.setCancelable(false)
            builder.setPositiveButton(
                positive
            ) { dialog: DialogInterface?, which: Int ->
                printToast(
                    context,
                    positive
                )
            }
            builder.setNegativeButton(
                negative
            ) { dialog: DialogInterface, which: Int -> dialog.cancel() }
            val alertDialog = builder.create()
            alertDialog.show()
        }

        /**
         * Snackbar with message
         */
        fun popUpMessage(view: View?, msg: String?) {
            val snackbar = Snackbar
                .make(view!!, msg!!, Snackbar.LENGTH_LONG)
            snackbar.show()
        }

        /**
         * Snackbar with action
         */
        fun actionSnackbar(view: View?, msg: String?, actionMsg: String?) {
            val snackbar = Snackbar
                .make(view!!, msg!!, Snackbar.LENGTH_LONG)
            snackbar.setAction(actionMsg, MyActionListener())
            snackbar.show()
        }


        private class MyActionListener : View.OnClickListener {
            override fun onClick(v: View) {
                Toast.makeText(v.context, "Action performed", Toast.LENGTH_SHORT).show()
            }
        }

        /**
         * Snackbar from top
         */
        fun topSnackbar(v: View?, msg: String?, actionMsg: String?) {
            val snack = Snackbar.make(v!!, msg!!, Snackbar.LENGTH_LONG)
            val view = snack.view
            snack.setAction(actionMsg, MyActionListener())
            val params = view.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
            view.layoutParams = params
            snack.show()
        }


        /**
         * Share data
         */
        fun share(msg: String?): Intent? {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/html"
            intent.putExtra(Intent.EXTRA_TEXT, msg)
            return Intent.createChooser(intent, "Share Using")
        }


        /**
         * App Review
         */
        fun reviewApp(context: Context): Intent? {
            val appPackageName = context.packageName
            return try {
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName"))
            } catch (e: ActivityNotFoundException) {
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            }
        }
    }

}