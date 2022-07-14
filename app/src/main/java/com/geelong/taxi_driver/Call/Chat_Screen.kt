package com.geelong.taxi_driver.Call

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geelong.taxi_driver.R
import kotlinx.android.synthetic.main.activity_chat_screen.*

class Chat_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_screen)

        arrow_msg.setOnClickListener {
            onBackPressed()
        }
    }
}