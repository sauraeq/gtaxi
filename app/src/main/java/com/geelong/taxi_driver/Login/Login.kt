package com.geelong.taxi_driver.Login

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.geelong.taxi_driver.LocalSaved.shareprefrences
import com.geelong.taxi_driver.Login.LoginModels.LoginResponse
import com.geelong.taxi_driver.R
import com.gogo.gogokull.api.APIUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Login : AppCompatActivity() {
    var no: String =""
    lateinit var shrp: shareprefrences
    var token_id:String=""
    lateinit var ccp: CountryCodePicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Generatetoken()

        setContentView(R.layout.activity_login)
        ccp = findViewById(R.id.countryCodePickerView)

        shrp= shareprefrences(this)

        if ((ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
                    != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        } else {

        }






        cross_login.setOnClickListener {

            phone_num.getText().clear();

        }








/*
        phone_num.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?,
                                           p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?,
                                       p1: Int, p2: Int, p3: Int) {
                // check inputted characters is a valid phone number or not
                no=p0.toString()
                if (p0.isValidPhoneNumber()){
                    phone_num.error = null
                }else{
                    phone_num.error = "Invalid phone number."
                }
            }

            override fun afterTextChanged(p0: Editable?) { }
        })
*/
        t_next.setOnClickListener() {
            no = phone_num.text.toString()
            if (no.length <13 && no.length>9) {
                var country_code="+"+ccp.selectedCountryCode.toString()
                login(country_code+no)

               /* val intent = Intent(this@Login, Otp::class.java)
                startActivity(intent)*/
            }
            else {
                phone_num.error = "Invalid phone number."
                Toast.makeText(this@Login, "atleast 10 digit", Toast.LENGTH_SHORT).show()

            }

/*
            if (NetworkUtil.checkInternetConnection(this)) {
                login()


            }
*/

        }


        fun CharSequence?.isValidPhoneNumber(): Boolean {
            return !isNullOrEmpty() && Patterns.PHONE.matcher(this).matches()
        }


    }

    fun login(mobile_no: String) {
    //  progress_loader.visibility= View.VISIBLE



       // val dialog: ProgressDialog = ProgressDialog.show(this, null, "Please Wait")
        val dialog = ProgressDialog(this, R.style.AppCompatAlertDialogStyle)
        dialog.setTitle("Please Wait")
        dialog.show()

/*{"emailid":"anuj@gmail.com","securityPin":"1234","securityPinCnf":"1234"}*/
        var stringStringHashMap = HashMap<String, String>()
        stringStringHashMap.put("mobile", mobile_no)
      stringStringHashMap.put("device_tokanid", token_id)


        var repassword = APIUtils.getServiceAPI()!!.login(stringStringHashMap)
        repassword.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {
                            shrp.setStringPreference("user_id",response.body()!!.data.user_id.toString())
                            shrp.setStringPreference("mobile",response.body()!!.data.mobile)
                            shrp.setStringPreference("name",response.body()!!.data.name)
                            shrp.setStringPreference("email",response.body()!!.data.email)
                            shrp.setStringPreference("address",response.body()!!.data.address)
                            shrp.setStringPreference("gender",response.body()!!.data.gender)
                            shrp.setStringPreference("profile_photo",response.body()!!.data.profile_photo)
                        //    Generatetoken()

                            val intent = Intent(this@Login, Otp::class.java)
                           // intent.putExtra("otp",response.body()!!.data.otp)
                            intent.putExtra("otp", response.body()?.data?.otp.toString())

                            startActivity(intent)
                            finish()
                           // progress_loader.visibility= View.GONE



                            dialog.dismiss()
                            Toast.makeText(
                                this@Login,
                                response.body()!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()


                        } else {
                            dialog.dismiss()

                          //  progress_loader.visibility= View.GONE

                            Toast.makeText(
                                this@Login,
                                response.body()!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                       // progress_loader.visibility= View.GONE

                          dialog.dismiss()
                        Toast.makeText(this@Login, response.body()!!.msg, Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {
                  //  progress_loader.visibility= View.GONE

                  dialog.dismiss()
                    val intent = Intent(this@Login, Otp::class.java)
                    // intent.putExtra("otp",response.body()!!.data.otp)
                    intent.putExtra("otp", response.body()?.data?.otp.toString())

                    startActivity(intent)
                   // finish()
                  //  progress_loader.visibility= View.GONE
                }


            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
               // progress_loader.visibility= View.GONE

                dialog.dismiss()
             //   Toast.makeText(this@Login, t.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText( this@Login,"Your internate connection problem",Toast.LENGTH_SHORT).show()
              //  progress_loader.visibility= View.GONE
            }
        })

    }




    private fun Generatetoken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                //Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            token_id=token
            Log.e("TAG", token)
        })
    }


}
