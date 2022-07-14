package com.geelong.taxi_driver.History

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.geelong.taxi_driver.Help_and_Support.HelpSupportModel.HelpSupportResponse
import com.geelong.taxi_driver.History.HistoryModel.HistoryResponse
import com.geelong.taxi_driver.History.WeekHistoryModel.WeeklyResponse
import com.geelong.taxi_driver.LocalSaved.shareprefrences

import com.geelong.taxi_driver.Notification.NotificationAdapter
import com.geelong.taxi_driver.R
import com.geelong.taxi_driver.Setting.TermAndCondition.TermAndConditionResource
import com.gogo.gogokull.api.APIUtils
import kotlinx.android.synthetic.main.activity_help_support.*

import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_term_and_condition.*
import kotlinx.android.synthetic.main.activity_your_earning.*
import kotlinx.android.synthetic.main.earning_list.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode
import java.text.DecimalFormat

class Your_Earning : AppCompatActivity() {
    lateinit var list_earning: ArrayList<String>

    lateinit var shrp: shareprefrences
     var diver_id:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_earning)
        shrp= shareprefrences(this)

        view_weekly.setBackgroundColor(resources.getColor(R.color.grey4))
        diver_id=shrp.getStringPreference("user_id").toString()
      //  Toast.makeText(this,diver_id,Toast.LENGTH_SHORT).show()

        history()


        click()

        arrow_history.setOnClickListener {
            onBackPressed()
        }
    }



    fun click(){

        try {


            t_today.setOnClickListener()
            {

             history()

                view_today.setBackgroundColor(resources.getColor(R.color.darkyellow))
                view_weekly.setBackgroundColor(resources.getColor(R.color.grey4))


            }





            t_weekly.setOnClickListener()
            {
                view_today.setBackgroundColor(resources.getColor(R.color.grey4))

                view_weekly.setBackgroundColor(resources.getColor(R.color.darkyellow))
              weekhistory()


            }
        }
        catch (e:Exception){

        }
    }

    fun history() {
        progress_loader_history.visibility= View.VISIBLE

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
        var check = APIUtils.getServiceAPI()!!.history(stringStringHashMap)
        check.enqueue(object : Callback<HistoryResponse> {
            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {
                            /*  dialog.dismiss()*/
                            rl_history.visibility=View.VISIBLE
                            linear.visibility=View.VISIBLE
                            progress_loader_history.visibility= View.GONE

                            t_ride.setText((response.body()!!.dataall[0].total_ride))
                            t_hrs.setText((response.body()!!.dataall[0].time))
                          // t_ammount.setText((response.body()!!.dataall[0].amount))






                            var amount_d=roundOffDecimal((response.body()!!.dataall[0].amount.toDouble()))

                            t_ammount.setText(("₹"+amount_d))






                            rl_history.layoutManager = LinearLayoutManager(this@Your_Earning)
                            rl_history.layoutManager =
                                LinearLayoutManager(this@Your_Earning, LinearLayoutManager.VERTICAL, false)
                            rl_history.adapter = HistoryAdaptor(this@Your_Earning,response.body()!!.data as ArrayList<HistoryResponse.Data>)

                        } else {
                            /*  dialog.dismiss()*/
                            linear.visibility=View.GONE
                            rl_history.visibility=View.GONE
                            progress_loader_history.visibility= View.GONE
                            Toast.makeText(
                                this@Your_Earning,
                                response.body()!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        /*   dialog.dismiss()*/
                        progress_loader_history.visibility= View.GONE
                        Toast.makeText(this@Your_Earning, response.body()!!.msg, Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {

                }



            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                /* dialog.dismiss()*/
                progress_loader_history.visibility= View.GONE
            //    Toast.makeText(this@Your_Earning, t.toString(), Toast.LENGTH_SHORT).show()

                Toast.makeText(this@Your_Earning,"Your internate connection problem",Toast.LENGTH_SHORT).show()
            }
        })




    }


  fun weekhistory() {
      progress_loader_history.visibility = View.VISIBLE

      //val dialog: ProgressDialog = ProgressDialog.show(this, null, "Please Wait")
      //*{"emailid":"anuj@gmail.com","securityPin":"1234","securityPinCnf":"1234"}*//*
      var stringStringHashMap = HashMap<String, String>()
    //  stringStringHashMap.put("driver_id", "1")

      stringStringHashMap.put("driver_id",shrp.getStringPreference("user_id").toString())

      /*    stringStringHashMap.put("name", name)
        stringStringHashMap.put("email", email)
        stringStringHashMap.put("message", message)

        stringStringHashMap.put("driver_id",id_sharedprefence)
*/
      var check = APIUtils.getServiceAPI()!!.weeklyhistory(stringStringHashMap)
      check.enqueue(object : Callback<WeeklyResponse> {
          override fun onResponse(
              call: Call<WeeklyResponse>,
              response: Response<WeeklyResponse>
          ) {
              try {
                  if (response.code() == 200) {
                      if (response.body()!!.success == "true") {
                          rl_history.visibility=View.VISIBLE
                          linear.visibility=View.VISIBLE
                          /*  dialog.dismiss()*/
                          progress_loader_history.visibility = View.GONE

                          t_ride.setText((response.body()!!.dataall[0].total_ride))
                          t_hrs.setText((response.body()!!.dataall[0].time.toInt()/60).toString())
                        //  t_ammount.setText("₹"+(response.body()!!.dataall[0].amount))






                          var amount_d=roundOffDecimal((response.body()!!.dataall[0].amount.toDouble()))

                          t_ammount.setText(("₹"+amount_d))




                          rl_history.layoutManager = LinearLayoutManager(this@Your_Earning)
                          rl_history.layoutManager =
                              LinearLayoutManager(this@Your_Earning, LinearLayoutManager.VERTICAL, false)
                          rl_history.adapter = WeekelyAdaptor(this@Your_Earning,response.body()!!.data as ArrayList<WeeklyResponse.Data>)


                      } else {
                          /*  dialog.dismiss()*/
                          linear.visibility=View.GONE
                          rl_history.visibility=View.GONE
                          progress_loader_history.visibility = View.GONE
                          Toast.makeText(
                              this@Your_Earning,
                              response.body()!!.msg,
                              Toast.LENGTH_SHORT
                          ).show()
                      }
                  } else {
                      /*   dialog.dismiss()*/
                      progress_loader_history.visibility = View.GONE
                      Toast.makeText(this@Your_Earning, response.body()!!.msg, Toast.LENGTH_SHORT)
                          .show()
                  }
              } catch (e: Exception) {

              }


          }

          override fun onFailure(call: Call<WeeklyResponse>, t: Throwable) {
              /* dialog.dismiss()*/
              progress_loader_history.visibility = View.GONE
             // Toast.makeText(this@Your_Earning, t.toString(), Toast.LENGTH_SHORT).show()

              Toast.makeText(this@Your_Earning,"Your internate connection problem",Toast.LENGTH_SHORT).show()

          }
      })


  }



    fun roundOffDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }












}