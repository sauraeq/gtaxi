package com.geelong.taxi_driver.Bank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geelong.taxi_driver.R
import kotlinx.android.synthetic.main.activity_bank_detail_edit.*

class Bank_detail_edit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_detail_edit)


        arrow_bank_detail_edit.setOnClickListener {
            onBackPressed()
        }
        t_save.setOnClickListener {
            val intent=Intent(this,Bank_Detail::class.java)
            startActivity(intent)
            finish()
        }
    }
}