package com.geelong.taxi_driver.Bank

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import com.geelong.taxi_driver.R
import com.geelong.taxi_driver.navigation_drawer.HomeScreen
import com.rehablab.librarys.adpater.AddClientAdapter
import kotlinx.android.synthetic.main.activity_bank_detail.*
import kotlinx.android.synthetic.main.activity_bank_detail.view.*
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.android.synthetic.main.activity_your_earning.*
import kotlinx.android.synthetic.main.add_sucessfully_popup.*
import kotlinx.android.synthetic.main.bank_card.*

class Bank_Detail : AppCompatActivity() {
    lateinit var addclientlist: ArrayList<GetClientResponse.Data>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_detail)





        addclientlist = ArrayList()
      //  addclientlist.add(1,addclientlist.get(1))


        bankCard_list()


        arrow_bank_detail.setOnClickListener {
            onBackPressed()
        }

        t_add_acc.setOnClickListener()
        {
            showDialog()
            //add_success_popup.visibility = View.VISIBLE
            /* okay_button.setOnClickListener()
            {

                intent= Intent(this, MainActivity1::class.java)
                startActivity(intent)
            }*/


        }
        write_edit1.setOnClickListener {
             val intent=Intent(this,Bank_detail_edit::class.java)
            startActivity(intent)
        }


    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.getWindow()!!
            .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.setCancelable(true)
        dialog.setContentView(R.layout.add_sucessfully_popup)
         lateinit var button: CardView


         button = dialog.findViewById(R.id.okayCaerdview)

        button.setOnClickListener {
        val intent=Intent(this,HomeScreen::class.java)
            startActivity(intent)
            finishAffinity()

         }

     // okay_button.setOnClickListener()
       // {

          // dialog.dismiss()

       // }



        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()

        dialog.window?.setLayout(700, 750)











    }

    private fun bankCard_list() {
        rl_bank_card.layoutManager = LinearLayoutManager(this)
        rl_bank_card.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rl_bank_card.adapter = AddClientAdapter(this,addclientlist)
    }










}