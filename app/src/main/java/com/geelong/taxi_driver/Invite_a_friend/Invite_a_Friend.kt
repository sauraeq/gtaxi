package com.geelong.taxi_driver.Invite_a_friend

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.geelong.taxi_driver.R
import kotlinx.android.synthetic.main.activity_invite_afriend.*


class Invite_a_Friend : AppCompatActivity() {
    //  private var link: String = "https://facebook.com/android"
    //private var link_1: String = "http://instagram.com"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_afriend)

        arrow_invite.setOnClickListener {
            onBackPressed()
        }

        whatsap_icon.setOnClickListener {



            val share = Intent(Intent.ACTION_SEND)
            val body: String = getPackageName()
            share.type = "text/plain"
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)

            // Add data to the intent, the receiving app will decide
            // what to do with it.

            // Add data to the intent, the receiving app will decide
            // what to do with it.
            share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post")
            share.putExtra(Intent.EXTRA_TEXT, body)

            startActivity(Intent.createChooser(share, "Share link!"))        }




        insta_icon.setOnClickListener {


            val share = Intent(Intent.ACTION_SEND)
            val body: String = getPackageName()
            share.type = "text/plain"
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)

            // Add data to the intent, the receiving app will decide
            // what to do with it.

            // Add data to the intent, the receiving app will decide
            // what to do with it.
            share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post")
            share.putExtra(Intent.EXTRA_TEXT, body)

            startActivity(Intent.createChooser(share, "Share link!"))

        }
        fb_icon.setOnClickListener {

            val share = Intent(Intent.ACTION_SEND)
            val body: String = getPackageName()
            share.type = "text/plain"
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)

            // Add data to the intent, the receiving app will decide
            // what to do with it.

            // Add data to the intent, the receiving app will decide
            // what to do with it.
            share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post")
            share.putExtra(Intent.EXTRA_TEXT, body)

            startActivity(Intent.createChooser(share, "Share link!"))
        }

        gmail_icon.setOnClickListener {

            val share = Intent(Intent.ACTION_SEND)
            val body: String = getPackageName()
            share.type = "text/plain"
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)

            // Add data to the intent, the receiving app will decide
            // what to do with it.

            // Add data to the intent, the receiving app will decide
            // what to do with it.
            share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post")
            share.putExtra(Intent.EXTRA_TEXT, body)

            startActivity(Intent.createChooser(share, "Share link!"))
        }




        messanger_icon.setOnClickListener {

            val share = Intent(Intent.ACTION_SEND)
            val body: String = getPackageName()
            share.type = "text/plain"
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)

            // Add data to the intent, the receiving app will decide
            // what to do with it.

            // Add data to the intent, the receiving app will decide
            // what to do with it.
            share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post")
            share.putExtra(Intent.EXTRA_TEXT, body)

            startActivity(Intent.createChooser(share, "Share link!"))
        }
        contact_icon.setOnClickListener {

            val share = Intent(Intent.ACTION_SEND)
            val body: String = getPackageName()
            share.type = "text/plain"
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)

            // Add data to the intent, the receiving app will decide
            // what to do with it.

            // Add data to the intent, the receiving app will decide
            // what to do with it.
            share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post")
            share.putExtra(Intent.EXTRA_TEXT, body)

            startActivity(Intent.createChooser(share, "Share link!"))
        }


    }


}




