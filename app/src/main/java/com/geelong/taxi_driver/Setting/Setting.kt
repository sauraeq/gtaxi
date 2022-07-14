package com.geelong.taxi_driver.Setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geelong.taxi_driver.R
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.android.synthetic.main.activity_setting.*

class Setting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        arrow_setting.setOnClickListener {
            onBackPressed()
        }
       arrow_t_c.setOnClickListener() {
            val intent=Intent(this,Term_and_Condition::class.java)
           startActivity(intent)




        }
        next_policy_arrow.setOnClickListener() {
            val intent=Intent(this,Privacy_Policy::class.java)
           startActivity(intent)
        }


    }
}