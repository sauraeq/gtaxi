package com.geelong.taxi_driver.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.geelong.taxi_driver.LocalSaved.shareprefrences
import com.geelong.taxi_driver.R
import com.geelong.taxi_driver.fragment.ServiceTask
import com.geelong.taxi_driver.navigation_drawer.HomeScreen

class Splash : AppCompatActivity() {
    lateinit var shrp: shareprefrences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        shrp= shareprefrences(this)
       // startService(Intent(applicationContext, ServiceTask::class.java))
        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
            if (shrp.getStringPreference("user_id",).equals("")){
                val intent = Intent(this,Login::class.java)
                startActivity(intent)
                finish()
            }else
            {
                val intent = Intent(this,HomeScreen::class.java)
                startActivity(intent)
                finishAffinity()
            }


        }, 3000) // 3000 is the delayed time in milliseconds.

    }
}