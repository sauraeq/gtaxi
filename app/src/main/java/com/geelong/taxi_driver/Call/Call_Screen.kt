package com.geelong.taxi_driver.Call

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.geelong.taxi_driver.R
import kotlinx.android.synthetic.main.activity_call_screen.*


class Call_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_screen)



        phone_cut.setOnClickListener {
          /*  val intent = Intent(this, MainActivity1::class.java)
            startActivity


*/

//            card_two.setVisibility(View.GONE);
//            card_three.setVisibility(View.VISIBLE);
//            card_one.setVisibility(View.GONE);*//*
//        }*/
            onBackPressed()

        }


    }
}