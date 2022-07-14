package com.geelong.taxi_driver.Profile

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import com.geelong.taxi_driver.LocalSaved.shareprefrences
import com.geelong.taxi_driver.R
import com.geelong.taxi_driver.Setting.TermAndCondition.TermAndConditionResource
import com.geelong.taxi_driver.navigation_drawer.HomeScreen
import com.gogo.gogokull.api.APIUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_overview.*
import kotlinx.android.synthetic.main.activity_term_and_condition.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Profile_Overview : AppCompatActivity() {
   // val get_name:String=""
   lateinit var shrp: shareprefrences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_overview)

        shrp= shareprefrences(this)

        try {
            user_name_a.setText(shrp.getStringPreference("name"))

            user_mobile_num.setText("+61-"+shrp.getStringPreference("mobile"))
            // user_mobile_num.setText(shrp.getStringPreference("mobile"))
            // user_name_b.setText(shrp.getStringPreference("name"))
            //  user_mobile_num.setText((shrp.getStringPreference("mobile")))
            user_email.setText((shrp.getStringPreference("email")))

            user_address.setText((shrp.getStringPreference("address")))

            user_gender.setText((shrp.getStringPreference("gender")))


            Picasso.get()
                .load(shrp.getStringPreference("profile_photo").toString())
                .into(profile_img)
        }catch (e:Exception){

        }


        rl_icon.setOnClickListener {
            val intent = Intent(this,Profile_Edit::class.java);
            startActivity(intent);
            finish()

        }



        arrow_profile.setOnClickListener {
    val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)



        }






    }





}