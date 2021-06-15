package com.sunidhi.utilityclass

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sunidhi.utilityclass.databinding.ActivityMainBinding
import com.sunidhi.utilityclass.util.MyUtil

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Toast
        binding.toastButton.setOnClickListener {
            MyUtil.printToast(
                this,
                "This is my custom Toast"
            )
        }

        //Dialog Box
        binding.dialogButton.setOnClickListener {
            MyUtil.alertDialog(
                this,
                "This is title",
                "My message",
                "Okay",
                "Cancel"
            )
        }

        //Snackbar with message
        binding.messageButton.setOnClickListener {
            MyUtil.popUpMessage(
                binding.parentLayout,
                "This is a message snackbar"
            )
        }

        //Snackbar with Action
        binding.actionButton.setOnClickListener {
            MyUtil.actionSnackbar(
                binding.parentLayout,
                "This is a action snackbar",
                "DISMISS"
            )
        }

        //Snackbar from Top
        binding.topSnack.setOnClickListener {
            MyUtil.topSnackbar(
                binding.parentLayout,
                "Top view",
                "OK"
            )
        }

        //Share link/text/message using anther app (Implicit Intent)
        binding.shareButton.setOnClickListener {
            val link =
                "https://images.pexels.com/photos/1086178/pexels-photo-1086178.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
            startActivity(MyUtil.share(link))
        }

        //Rate App
        binding.rateButton.setOnClickListener { v -> startActivity(MyUtil.reviewApp(this)) }
    }

}