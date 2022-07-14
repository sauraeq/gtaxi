package com.geelong.taxi_driver.Login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.geelong.taxi_driver.Login.OTPModel.OtpResponce
import com.geelong.taxi_driver.R
import com.geelong.taxi_driver.navigation_drawer.HomeScreen
import com.gogo.gogokull.api.APIUtils
import kotlinx.android.synthetic.main.activity_otp.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess


class Otp : AppCompatActivity() {
    var num: String =""
    val get_otp:String=""





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)



        var get_otp:String=intent.getStringExtra("otp").toString()

        Toast.makeText(this,get_otp,Toast.LENGTH_LONG).show()

        t_next_otp.setOnClickListener() {

            val value = otp_view.text.toString()
//            Toast.makeText(this, value, Toast.LENGTH_LONG).show()


            if (value==get_otp)
            {
                val intent=Intent(this,HomeScreen::class.java)

                startActivity(intent)
                finishAffinity()





            }








      else {
           Toast.makeText(this,"Please Enter Correct Otp",Toast.LENGTH_LONG).show()
            }/* try {
                num= otp_view.text.toString()
            if (num.length>3) {
                //login(no)


                val intent = Intent(this, HomeScreen::class.java)

                startActivity(intent)
            } else {
                Toast.makeText(this@Otp,"Please Enter 4 Digit OTP", Toast.LENGTH_SHORT).show()


            }


        }
            catch (e:Exception){


            }
        }*/
            }
        }


    }

    /*private fun otp() {

        val dialog: ProgressDialog = ProgressDialog.show(this, null, "Please Wait")
*//*{"emailid":"anuj@gmail.com","securityPin":"1234","securityPinCnf":"1234"}*//*
        var stringStringHashMap = HashMap<String, String>()
        stringStringHashMap.put("mobile", "9318301958")
        stringStringHashMap.put("otp", "1704581")

        var check = APIUtils.getServiceAPI()!!.otp(stringStringHashMap)
        check.enqueue(object : Callback<OtpResponce> {
            override fun onResponse(
                call: Call<OtpResponce>,
                response: Response<OtpResponce>
            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {

                            dialog.dismiss()
                            Toast.makeText(this, response.body()!!.msg, Toast.LENGTH_SHORT)
                                .show()


                        } else {
                            dialog.dismiss()
                            Toast.makeText(this@Otp, response.body()!!.msg, Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        dialog.dismiss()
                        Toast.makeText(this@Otp, response.body()!!.msg, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {

                }


            }

            override fun onFailure(call: Call<OtpResponce>, t: Throwable) {
                dialog.dismiss()
                Toast.makeText(this@Otp, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }
*/


