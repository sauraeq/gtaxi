package com.geelong.taxi_driver.Payment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.geelong.taxi_driver.LocalSaved.shareprefrences
import com.geelong.taxi_driver.R
import com.geelong.taxi_driver.navigation_drawer.HomeScreen
import kotlinx.android.synthetic.main.activity_payment.*

class Payment : AppCompatActivity() {
  lateinit var shrp: shareprefrences
    lateinit var booking_id:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)



        shrp= shareprefrences(this)

     //   com_payment()

        arrow_msg.setOnClickListener {
            onBackPressed()
        }

        t_done_payment.setOnClickListener()
        {
            showDialog()
            //add_success_popup.visibility = View.VISIBLE
            /* okay_button.setOnClickListener()
            {


                intent= Intent(this, MainActivity1::class.java)
                startActivity(intent)
            }*/


        }


    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.getWindow()!!
            .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.setCancelable(true)
        dialog.setContentView(R.layout.payment__popup)
        lateinit var button: CardView


        button = dialog.findViewById(R.id.okayCaerdview_payment_done)

        button.setOnClickListener {
            val intent= Intent(this, HomeScreen::class.java)
            startActivity(intent)
            finishAffinity()



        }

        // okay_button.setOnClickListener()
        // {

        // dialog.dismiss()

        // }



        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()

        dialog.setCanceledOnTouchOutside(false);




    }





//    fun com_payment(){
//        //   progress_loader_home.visibility= View.VISIBLE
//
//        //val dialog: ProgressDialog = ProgressDialog.show(this, null, "Please Wait")
//        //*{"emailid":"anuj@gmail.com","securityPin":"1234","securityPinCnf":"1234"}*//*
//        var stringStringHashMap = HashMap<String, String>()
//        //  stringStringHashMap.put("driver_id","58")
//
//        stringStringHashMap.put("driver_id", shrp.getStringPreference("user_id").toString())
//        stringStringHashMap.put("booking_id", booking_id)
//        var check = APIUtils.getServiceAPI()!!.complete_payment(stringStringHashMap)
//        check.enqueue(object : Callback<completeResponse> {
//
//            override fun onResponse(
//                call: Call<completeResponse>,
//                response: Response<completeResponse>
//
//            ) {
//                try {
//                    if (response.code() == 200) {
//                        if (response.body()!!.success == "true") {
//
//
//                            Toast.makeText(this@Payment, response.body()!!.msg, Toast.LENGTH_SHORT)
//                                .show()
//
//
//
//                            //  progress_loader_home.visibility= View.GONE
//
//                        } else {
//                            /*  dialog.dismiss()*/
//
//                            //   progress_loader_home.visibility= View.GONE
//                            Toast.makeText(
//                                this@Payment, response.body()!!.msg,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    } else {
//                        /*   dialog.dismiss()*/
//                        Toast.makeText(this@Payment, response.body()!!.msg, Toast.LENGTH_SHORT)
//                            .show()
//
//                    }
//                } catch (e: java.lang.Exception) {
//
//                }
//
//
//            }
//
//            override fun onFailure(call: Call<completeResponse>, t: Throwable) {
//                /* dialog.dismiss()*/
//                progress_loader_home.visibility = View.GONE
//                //Toast.makeText( requireContext(),t.toString(), Toast.LENGTH_SHORT).show()
//                Toast.makeText(
//                  this@Payment,
//                    "Your internate connection problem",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        })
//    }




    override   fun onResume() {
        super.onResume()

        try {

          c_pick.setText(shrp.getStringPreference("pick").toString())


            c_drop.setText(shrp.getStringPreference("drop").toString())


            c_amount.setText("$"+shrp.getStringPreference("amount").toString())

            customer_name.setText(shrp.getStringPreference("name").toString())

        } catch (e: Exception) {

        }
    }





        }