package com.geelong.taxi_driver.RideLtaer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.geelong.taxi_driver.LocalSaved.shareprefrences
import com.geelong.taxi_driver.Notification.NotificationAdapter
import com.geelong.taxi_driver.Notification.NotificationModel.NotificationResponse
import com.geelong.taxi_driver.R
import com.geelong.taxi_driver.RideLtaer.RidemoduleApi.ridelaterResponse
import com.gogo.gogokull.api.APIUtils
import kotlinx.android.synthetic.main.activity_notification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RideLater : AppCompatActivity() {

    lateinit var shrp: shareprefrences
    var diver_id:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_later)
        shrp= shareprefrences(this)


        arrow_notification.setOnClickListener {
            onBackPressed()
        }

        ridelaterr()

       // diver_id=shrp.getStringPreference("user_id").toString()

    }
    fun ridelaterr() {
        progress_loader_notification.visibility= View.VISIBLE

        var stringStringHashMap = HashMap<String, String>()
        //=   stringStringHashMap.put("driver_id","1")

        stringStringHashMap.put("driver_id",shrp.getStringPreference("user_id").toString())

        var check = APIUtils.getServiceAPI()!!.ridelater(stringStringHashMap)
        check.enqueue(object : Callback<ridelaterResponse> {
            override fun onResponse(
                call: Call<ridelaterResponse>,
                response: Response<ridelaterResponse>
            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {
                            /*  dialog.dismiss()*/
                            rl_notification.visibility= View.VISIBLE

                            progress_loader_notification.visibility= View.GONE

                            //     Toast.makeText(this@Notification,"Api is notification",Toast.LENGTH_SHORT).show();


                            rl_notification.layoutManager = LinearLayoutManager(this@RideLater)
                            rl_notification.layoutManager =
                                LinearLayoutManager(this@RideLater)






                            rl_notification.adapter = RidelaterAdapter(this@RideLater,response.body()!!.data as ArrayList<ridelaterResponse.Data>)

                        } else {
                            /*  dialog.dismiss()*/
                            /*   linear.visibility= View.GONE
                               rl_history.visibility= View.GONE*/
                            progress_loader_notification.visibility= View.GONE
                            Toast.makeText(
                                this@RideLater,
                                response.body()!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        /*   dialog.dismiss()*/
                        progress_loader_notification.visibility= View.GONE
                        Toast.makeText(this@RideLater, response.body()!!.msg, Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {

                }


            }

            override fun onFailure(call: Call<ridelaterResponse>, t: Throwable) {
                /* dialog.dismiss()*/
                progress_loader_notification.visibility= View.GONE
                //  Toast.makeText(this@Notification, t.toString(), Toast.LENGTH_SHORT).show()

                Toast.makeText(this@RideLater,"Your internate connection problem", Toast.LENGTH_SHORT).show()

            }
        })




    }






}