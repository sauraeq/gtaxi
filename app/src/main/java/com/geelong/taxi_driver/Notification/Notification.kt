package com.geelong.taxi_driver.Notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.geelong.taxi_driver.History.HistoryAdaptor
import com.geelong.taxi_driver.History.HistoryModel.HistoryResponse
import com.geelong.taxi_driver.LocalSaved.shareprefrences
import com.geelong.taxi_driver.Notification.Noti_viewModel.noti_view_Response
import com.geelong.taxi_driver.Notification.NotificationModel.NotificationResponse
import com.geelong.taxi_driver.R
import com.gogo.gogokull.api.APIUtils
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.android.synthetic.main.activity_your_earning.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Notification : AppCompatActivity() {
    //lateinit var list_notification: ArrayList<String>
    lateinit var shrp: shareprefrences
    var diver_id:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        shrp= shareprefrences(this)
/*
        list_notification = ArrayList()
        list_notification.add("")
        list_notification.add("")
        list_notification.add("")
        list_notification.add("")
        list_notification.add("")*/
        notification_view()


        diver_id=shrp.getStringPreference("user_id").toString()

        notifcation()
        arrow_notification.setOnClickListener {
            onBackPressed()
        }



    }



/*
    private fun notificationsList() {
        rl_notification.layoutManager = LinearLayoutManager(this)
        rl_notification.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rl_notification.adapter = NotificationAdapter(this, list_notification)
    }*/







    fun notifcation() {
        progress_loader_notification.visibility= View.VISIBLE

        //val dialog: ProgressDialog = ProgressDialog.show(this, null, "Please Wait")
        //*{"emailid":"anuj@gmail.com","securityPin":"1234","securityPinCnf":"1234"}*//*
        var stringStringHashMap = HashMap<String, String>()
        //=   stringStringHashMap.put("driver_id","1")

        stringStringHashMap.put("driver_id",diver_id)


        /*    stringStringHashMap.put("name", name)
            stringStringHashMap.put("email", email)
            stringStringHashMap.put("message", message)

            stringStringHashMap.put("driver_id",id_sharedprefence)
    */
        var check = APIUtils.getServiceAPI()!!.notification_list(stringStringHashMap)
        check.enqueue(object : Callback<NotificationResponse> {
            override fun onResponse(
                call: Call<NotificationResponse>,
                response: Response<NotificationResponse>
            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {
                            /*  dialog.dismiss()*/
                            rl_notification.visibility= View.VISIBLE

                            progress_loader_notification.visibility= View.GONE

                       //     Toast.makeText(this@Notification,"Api is notification",Toast.LENGTH_SHORT).show();


                            rl_notification.layoutManager = LinearLayoutManager(this@Notification)
                            rl_notification.layoutManager =
                                LinearLayoutManager(this@Notification)






                            rl_notification.adapter = NotificationAdapter(this@Notification,response.body()!!.data as ArrayList<NotificationResponse.Data>)

                        } else {
                            /*  dialog.dismiss()*/
                         /*   linear.visibility= View.GONE
                            rl_history.visibility= View.GONE*/
                            progress_loader_notification.visibility= View.GONE
                            Toast.makeText(
                                this@Notification,
                                response.body()!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        /*   dialog.dismiss()*/
                        progress_loader_notification.visibility= View.GONE
                        Toast.makeText(this@Notification, response.body()!!.msg, Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {

                }


            }

            override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                /* dialog.dismiss()*/
                progress_loader_notification.visibility= View.GONE
              //  Toast.makeText(this@Notification, t.toString(), Toast.LENGTH_SHORT).show()

                Toast.makeText(this@Notification,"Your internate connection problem",Toast.LENGTH_SHORT).show()

            }
        })




    }





    fun notification_view() {


        //val dialog: ProgressDialog = ProgressDialog.show(this, null, "Please Wait")
        //*{"emailid":"anuj@gmail.com","securityPin":"1234","securityPinCnf":"1234"}*//*
        var stringStringHashMap = HashMap<String, String>()
        //  stringStringHashMap.put("driver_id","58")

        stringStringHashMap.put("driver_id",shrp.getStringPreference("user_id").toString())



        //  stringStringHashMap.put("booking_id",booking_id)
        /*    stringStringHashMap.put("name", name)
            stringStringHashMap.put("email", email)
            stringStringHashMap.put("message", message)

            stringStringHashMap.put("driver_id",id_sharedprefence)
    */
        var check = APIUtils.getServiceAPI()!!.noti_view(stringStringHashMap)
        check.enqueue(object : Callback<noti_view_Response> {

            override fun onResponse(
                call: Call<noti_view_Response>,
                response: Response<noti_view_Response>

            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {


                            Toast.makeText(this@Notification,response.body()!!.msg, Toast.LENGTH_SHORT).show()
                            //   notificationNumber?.setText((response.body()!!.data[0].count.toString()))



                        } else {
                            /*  dialog.dismiss()*/


                            Toast.makeText(
                                this@Notification,
                                response.body()!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        /*   dialog.dismiss()*/
                        Toast.makeText(this@Notification, response.body()!!.msg, Toast.LENGTH_SHORT)
                            .show()

                    }
                } catch (e: java.lang.Exception) {

                }


            }

            override fun onFailure(call: Call<noti_view_Response>, t: Throwable) {
                /* dialog.dismiss()*/
                //   progress_loader_home.visibility= View.GONE
                //Toast.makeText( requireContext(),t.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText( this@Notification,"Your internate connection problem", Toast.LENGTH_SHORT).show()
            }
        })

    }




}

