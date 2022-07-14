package com.geelong.taxi_driver.Help_and_Support

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.geelong.taxi_driver.Help_and_Support.HelpSupportModel.HelpSupportResponse
import com.geelong.taxi_driver.LocalSaved.shareprefrences
import com.geelong.taxi_driver.Profile.Profile_Overview
import com.geelong.taxi_driver.R
import com.geelong.taxi_driver.navigation_drawer.HomeScreen
import com.gogo.gogokull.api.APIUtils
import kotlinx.android.synthetic.main.activity_help_support.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile_edit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Help_Support : AppCompatActivity() {
    var id_sharedprefence:String=""
    lateinit var shrp: shareprefrences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_support)

        shrp= shareprefrences(this)






        t_submit.setOnClickListener()
        {
           id_sharedprefence=shrp.getStringPreference("user_id").toString()
            help_support(h_name.text.toString(),h_email.text.toString(),h_message.text.toString())



            //   textSpan()

    }


        back_help.setOnClickListener {
            onBackPressed()
        }



  /*  private fun textSpan() {
        val mSpannableString=SpannableString("Name *")
        val fColor=ForegroundColorSpan(Color.BLACK)
        mSpannableString.setSpan(fColor ,0,4,Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        mSpannableString.setSpan(fColor ,5,6,Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        t_name.text=spannableString
    }*/
}







private fun showDialog() {
    val dialog = Dialog(this)
    dialog.getWindow()!!
        .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    dialog.setCancelable(true)
    dialog.setContentView(R.layout.popup_help_and_support)
    lateinit var button: CardView


    button = dialog.findViewById(R.id.okayCaerdview_help_support)

    button.setOnClickListener {
        val intent= Intent(this, HomeScreen::class.java)
        startActivity(intent)
    /*    finishAffinity()*/



    }

    // okay_button.setOnClickListener()
    // {

    // dialog.dismiss()

    // }



    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    dialog.show()

    dialog.window?.setLayout(700, 750)
    dialog.setCanceledOnTouchOutside(false);




}

    override fun onResume() {
        super.onResume()
        h_name.setText(shrp.getStringPreference("name"))
        h_email.setText(shrp.getStringPreference("email"))






    }











    private fun help_support(name:String,email:String,message:String) {
        help_edit_support.visibility= View.VISIBLE

        //val dialog: ProgressDialog = ProgressDialog.show(this, null, "Please Wait")
        //*{"emailid":"anuj@gmail.com","securityPin":"1234","securityPinCnf":"1234"}*//*
        var stringStringHashMap = HashMap<String, String>()
        stringStringHashMap.put("name", name)
        stringStringHashMap.put("email", email)
        stringStringHashMap.put("message", message)



        stringStringHashMap.put("driver_id",id_sharedprefence)

        var check = APIUtils.getServiceAPI()!!.helpSupport(stringStringHashMap)
        check.enqueue(object : Callback<HelpSupportResponse> {
            override fun onResponse(
                call: Call<HelpSupportResponse>,
                response: Response<HelpSupportResponse>
            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {


                            showDialog()

                            /* dialog.dismiss()*/
                            help_edit_support.visibility= View.GONE
                          //  Toast.makeText(this@Help_Support, response.body()!!.msg, Toast.LENGTH_SHORT)
                             //   .show()


                        } else {
                            /*   dialog.dismiss()*/
                            help_edit_support.visibility= View.GONE
                            Toast.makeText(this@Help_Support, response.body()!!.msg, Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        /*      dialog.dismiss()*/
                        help_edit_support.visibility= View.GONE
                        Toast.makeText(this@Help_Support, response.body()!!.msg, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {

                }


            }

            override fun onFailure(call: Call<HelpSupportResponse>, t: Throwable) {
                /* dialog.dismiss()*/
                help_edit_support.visibility= View.GONE
                //Toast.makeText(this@Help_Support, t.toString(), Toast.LENGTH_SHORT).show()

                Toast.makeText(this@Help_Support,"Your internate connection problem",Toast.LENGTH_SHORT).show()


            }
        })

    }





}













/*

val mText="Name*"
val mSpannableString=SpannableString(mText)
val mblack=ForegroundColorSpan(Color.BLACK)
val mred=ForegroundColorSpan(Color.RED)
mSpannableString.setSpan(mblack ,1,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
mSpannableString.setSpan(mred ,5,6,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
t_name.text=mSpannableString*/
