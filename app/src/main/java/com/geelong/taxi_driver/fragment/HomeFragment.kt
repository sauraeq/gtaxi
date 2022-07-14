package com.geelong.taxi_driver.fragment

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.geelong.taxi_driver.Call.Chat_Screen
import com.geelong.taxi_driver.LocalSaved.shareprefrences
import com.geelong.taxi_driver.Payment.Payment
import com.geelong.taxi_driver.R
import com.geelong.taxi_driver.fragment.AcceptTripModel.AcceptTripResponse
import com.geelong.taxi_driver.fragment.Complete_Model.completeResponse
import com.geelong.taxi_driver.fragment.NoJob.nojonResponse
import com.geelong.taxi_driver.fragment.OfflineDetaliModel.OffDeatailResponse
import com.geelong.taxi_driver.fragment.OnlineDetailModel.OnlineResponse
import com.geelong.taxi_driver.fragment.OtpModel.otpmatchResponse
import com.geelong.taxi_driver.fragment.RejectTripModel.RejectResponse
import com.geelong.taxi_driver.navigation_drawer.HomeScreen
import com.geelong.user.Util.Constants
import com.geelong.user.Util.FetchAddressServices
import com.gogo.gogokull.api.APIUtils
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_otp.*
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.android.synthetic.main.activity_profile_overview.*
import kotlinx.android.synthetic.main.activity_term_and_condition.*
import kotlinx.android.synthetic.main.activity_your_earning.*
import kotlinx.android.synthetic.main.earning_list.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.profile_icon
import kotlinx.android.synthetic.main.nojob.*
import kotlinx.android.synthetic.main.otp_verify_popup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var param1: String? = null
    private var param2: String? = null
    lateinit var on: Button
    lateinit var off: Button
    private lateinit var locationManager: LocationManager
    private lateinit var tvGpsLocation: TextView
    private val locationPermissionCode = 2
    lateinit var shrp: shareprefrences
    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 1000
    var locate: String=""
    var latitudee: String=""
    var longitudee: String=""
    var satus_str:String=""
    var booking_id:String=""
    var LOCATION_PERMISSION_REQUEST_CODE=100
    var no_job:String=""
    var checked_item:String=""

    var resultReceiver: ResultReceiver? = null


    val radioGroup: RadioGroup? = null

    lateinit var piclat:String
    lateinit var piclong:String
    lateinit var droplat:String
    lateinit var droplong:String
    private var myRunnable: Runnable? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            /*locat = it.getString("Location", "")
            lati = it.getString("Late", "")
            longi = it.getString("Long", "")
*/

        }


    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?



    ):

            View? {
        // Inflate the layout for this fragment
        val rootview = inflater.inflate(R.layout.fragment_home, container, false)
        shrp = shareprefrences(requireContext())




        try {
            val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val mp: MediaPlayer = MediaPlayer.create(requireContext(), notification)
            mp.start()
        } catch (e: Exception) {

        }


        checkGPSLocationStatus()









        val acceptlayout = rootview.findViewById<TextView>(R.id.t_accept)
        val rejectlayout = rootview.findViewById<TextView>(R.id.t_reject)

        val recall_acncel=rootview.findViewById<TextView>(R.id.recall)



        recall_acncel.setOnClickListener {
            Reject_Trip()
            val intent=Intent(requireContext(), HomeScreen::class.java)
            startActivity(intent)
        }




        resultReceiver = AddressResultReceiver(Handler())

        /*  customprogress= Dialog(requireContext())
          customprogress.setContentView(R.layout.loader_layout)*/

        /*arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)


        }*/

        if ((ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
                    != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            currentLocation
        }






        /*  map()*/
        //offline_Detail()



        val nojob=rootview.findViewById<TextView>(R.id.nojob)

        nojob.setOnClickListener {
            showDialog_job()
        }






        rejectlayout.setOnClickListener {


            Reject_Trip()
            card_two.setVisibility(View.GONE);
            card_three.setVisibility(View.GONE);
            card_one.setVisibility(View.GONE);
            card_five.setVisibility(View.GONE)


            /*  card_four.setVisibility(View.VISIBLE);*/




        }



        /*card_five.setOnClickListener {
            card_two.setVisibility(View.GONE);
            card_three.setVisibility(View.GONE);
            card_one.setVisibility(View.GONE);




        }*/





        acceptlayout.setOnClickListener {
            card_two.setVisibility(View.GONE);
            card_three.setVisibility(View.VISIBLE);
            card_one.setVisibility(View.GONE);
            card_five.setVisibility(View.GONE)



            val picllt=shrp.getStringPreference("picllt")

            val piclld=shrp.getStringPreference("piclld")


            /// val droplt=shrp.getStringPreference("droplt")


            // val dropld=shrp.getStringPreference("dropld")





            //   Toast.makeText( requireContext(),longitudee+latitudee,Toast.LENGTH_SHORT).show()

            // Toast.makeText( requireContext(),latitudee,Toast.LENGTH_SHORT).show()

            if (picllt != null) {
                if (piclld != null){
                    loadNavigationView(piclat,piclong)
                }

            }
            // if (droplt != null) {
            //  if (dropld != null){
            //    loadNavigationView(droplt,dropld)
            //  }

            //     }


            AcceptTrip()


            /*     card_four.setVisibility(View.GONE);
 */






            img_call.setOnClickListener {
                /* startActivity(Intent(requireContext(), Call_Screen::class.java))*/
                val phone_number=shrp.getStringPreference("call").toString()
                Log.d("phone_number",phone_number+"...")
                val intent=Intent(Intent.ACTION_DIAL)
                intent.data= Uri.parse("tel:$phone_number")
                startActivity(intent)
            }
            img_msg.setOnClickListener {
                startActivity(Intent(requireContext(), Chat_Screen::class.java))



            }


        }


        val completelayout = rootview.findViewById<TextView>(R.id.t_complete)
        completelayout.setOnClickListener {


            com_payment()



        }




        val start=rootview.findViewById<TextView>(R.id.start_ride)
        start.setOnClickListener {


            showDialog(piclat,piclong,droplat,droplong)

/*
            val picklt=shrp.getStringPreference("pickllt")

              val pickld=shrp.getStringPreference("piclld")


              val droplt=shrp.getStringPreference("droplt")


              val dropld=shrp.getStringPreference("dropld")





         //   Toast.makeText( requireContext(),longitudee+latitudee,Toast.LENGTH_SHORT).show()

           // Toast.makeText( requireContext(),latitudee,Toast.LENGTH_SHORT).show()

                    if (picklt != null) {
                        if (pickld != null){
                    loadNavigationView(picklt,pickld)
                }

            }
                    if (droplt != null) {
                        if (dropld != null){
                    loadNavigationView(droplt,dropld)
                }

            }*/

        }






        var status:String=shrp.getStringPreference("app_status").toString()
        if (status.equals("offline")){
            satus_str="1"
            offline_Detail()

        }else{

        }
        if (status.equals("online")){
            satus_str="2"
            online_Detail()

        }else{

        }

        if (status.equals("")){
            satus_str="1"
            offline_Detail()

        }else{

        }
        return rootview
    }

    /* fun map() {

         try {

             if (lati.toString().equals("")) {

             } else {

             }
         } catch (e: Exception) {

         }


     }*/


    fun online(data: String) {


        online_Detail()
        satus_str="2"
        updatelocation(locate,latitudee,longitudee,satus_str)
        // resultReceiver = AddressResultReceiver(Handler())
        // updatelocation("2")


    }

    fun offline(data: String) {


        offline_Detail()
        satus_str="1"
        updatelocation(locate,latitudee,longitudee,satus_str)
        //resultReceiver = AddressResultReceiver(Handler())
        //  updatelocation("1")



    }


    override   fun onResume() {
        super.onResume()

        try {
            Picasso.get()
                .load(shrp.getStringPreference("profile_photo").toString())
                .into(profile_icon)

            pofile_name.setText(shrp.getStringPreference("name").toString())
        } catch (e: Exception) {

        }
    }


    fun offline_Detail() {
        //customprogress.show()
        /* progress_loader_home.visibility= View.VISIBLE*/

        //val dialog: ProgressDialog = ProgressDialog.show(this, null, "Please Wait")
        //*{"emailid":"anuj@gmail.com","securityPin":"1234","securityPinCnf":"1234"}*//*
        var stringStringHashMap = HashMap<String, String>()
        stringStringHashMap.put("driver_id",shrp.getStringPreference("user_id").toString())
        /*    stringStringHashMap.put("name", name)
            stringStringHashMap.put("email", email)
            stringStringHashMap.put("message", message)

            stringStringHashMap.put("driver_id",id_sharedprefence)
    */
        var check = APIUtils.getServiceAPI()!!.OffDeatail(stringStringHashMap)
        check.enqueue(object : Callback<OffDeatailResponse> {
            override fun onResponse(
                call: Call<OffDeatailResponse>,
                response: Response<OffDeatailResponse>
            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {
                            myRunnable?.let { handler.removeCallbacks(it) };
                            card_two.setVisibility(View.GONE);
                            card_one.setVisibility(View.VISIBLE);
                            card_five.setVisibility(View.GONE)
                            card_three.setVisibility(View.GONE);
                            moon_offline.setVisibility(View.VISIBLE);


                            total_time.setText((response.body()!!.data[0].time.toInt()/60).toString())
                            total_distance.setText((response.body()!!.data[0].distance))
                            total_ride.setText((response.body()!!.data[0].total_ride))
/*

                            val df = DecimalFormat("#.##")
                            df.roundingMode = RoundingMode.FLOOR
                            total_earn.setText(df.format(response.body()!!.data[0].amount))
*/


                            var amount_d=roundOffDecimal(response.body()!!.data[0].amount.toDouble())

                            total_earn.setText(("₹"+amount_d))
                            //customprogress.hide()







                        } else {
                            /*  dialog.dismiss()*/
                            //customprogress.hide()

                            Toast.makeText(
                                requireContext(),
                                response.body()!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        /*   dialog.dismiss()*/
                        //customprogress.hide()
                        Toast.makeText(       requireContext(), response.body()!!.msg, Toast.LENGTH_SHORT)
                            .show()

                    }

                } catch (e: java.lang.Exception) {
                    // customprogress.hide()
                }


            }

            override fun onFailure(call: Call<OffDeatailResponse>, t: Throwable) {
                /* dialog.dismiss()*/

                //customprogress.hide()
                //   Toast.makeText( requireContext(),t.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText( requireContext(),"something wrong",Toast.LENGTH_SHORT).show()

            }
        })
    }

    fun online_Detail() {
        // progress_loader_home.visibility= View.VISIBLE

        //val dialog: ProgressDialog = ProgressDialog.show(this, null, "Please Wait")
        //*{"emailid":"anuj@gmail.com","securityPin":"1234","securityPinCnf":"1234"}*//*
        var stringStringHashMap = HashMap<String, String>()


        stringStringHashMap.put("driver_id",shrp.getStringPreference("user_id").toString())

        /*    stringStringHashMap.put("name", name)
            stringStringHashMap.put("email", email)
            stringStringHashMap.put("message", message)

            stringStringHashMap.put("driver_id",id_sharedprefence)
    */
        var check = APIUtils.getServiceAPI()!!.OnlineDetail(stringStringHashMap)
        check.enqueue(object : Callback<OnlineResponse> {
            override fun onResponse(
                call: Call<OnlineResponse>,
                response: Response<OnlineResponse>
            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {




                            if(response.body()!!.data[0].status.equals("1")){



                                myRunnable?.let { handler.removeCallbacks(it) };
                                card_one.setVisibility(View.GONE);
                                card_two.setVisibility(View.VISIBLE);
                                card_three.setVisibility(View.GONE);
                                card_five.setVisibility(View.GONE)
                                moon_offline.setVisibility(View.GONE)



                              online_respnse()




                                //progress_loader_home.visibility= View.GONE

/*


                            val picklt=shrp.getStringPreference("picklt")
                            Log.d(picklt,"");

*/




                                shrp.setStringPreference("otp",response.body()!!.data[0].otp)



                                shrp.setStringPreference("picllt",response.body()!!.data[0].pickup_latitude)


                                shrp.setStringPreference("piclld",response.body()!!.data[0].pickup_longitude)


                                shrp.setStringPreference("droplt",response.body()!!.data[0].drop_latitude)


                                shrp.setStringPreference("dropld",response.body()!!.data[0].drop_longitude)



                                shrp.setStringPreference("pick",response.body()!!.data[0].pickup_address)


                                shrp.setStringPreference("drop",response.body()!!.data[0].drop_address)



                                shrp.setStringPreference("amount",response.body()!!.data[0].amount)
                                shrp.setStringPreference("customer",response.body()!!.data[0].user_name)


                                u_name.setText((response.body()!!.data[0].user_name))
                                u_distance.setText(((response.body()!!.data[0].distance+" km")))








                               /* val fl = DecimalFormat("#.##")

                                fl.roundingMode = RoundingMode.FLOOR

                                u_ammount.setText(fl.format("₹"+response.body()!!.data[0].amount))

*/




                                var amount_ddjj=roundOffDecimal(response.body()!!.data[0].amount.toDouble())
                               u_ammount.setText(("$"+amount_ddjj))
                                u_pickup.setText((response.body()!!.data[0].pickup_address))
                                u_drop.setText((response.body()!!.data[0].drop_address))
                                u_time.setText((response.body()!!.data[0].time+" min"))






                                booking_id=response.body()!!.data[0].id
                                user_t_time.setText(response.body()!!.data[0].time+" min")
                                us_distance.setText(response.body()!!.data[0].distance+" Km")
                                t_user_name.setText("Picking up "+response.body()!!.data[0].user_name)


                                ppickup.setText((response.body()!!.data[0].pickup_address))
                                ddrop.setText((response.body()!!.data[0].drop_address))

                                remark.setText((response.body()!!.data[0].remark))





                                user_2_t_time.setText(response.body()!!.data[0].time+" min")
                                us_2_distance.setText(response.body()!!.data[0].distance+" Km")
                                piclat=response.body()!!.data[0].pickup_latitude
                                piclong=response.body()!!.data[0].pickup_longitude
                                droplat=response.body()!!.data[0].drop_latitude
                                droplong=response.body()!!.data[0].drop_longitude

                                try {
                                    Picasso.get()
                                        .load(response.body()!!.data[0].user_photo)
                                        .into(user_t_pic)



                                    Picasso.get()
                                        .load((response.body()!!.data[0].user_photo))
                                        .into(u_image)




                                    Picasso.get()
                                        .load((response.body()!!.data[0].user_photo))
                                        .into(user_2_t_pic)

                                }catch (e:Exception){

                                }

                                Ride_status()

                            }
                            else if (response.body()!!.data[0].otp_status.equals("0"))
                            {
                                card_two.setVisibility(View.GONE);
                                card_three.setVisibility(View.VISIBLE);
                                card_one.setVisibility(View.GONE);
                                card_five.setVisibility(View.GONE)
                                moon_offline.setVisibility(View.GONE)
                                u_time.setText((response.body()!!.data[0].time+" min"))
                                u_distance.setText(((response.body()!!.data[0].distance+" km")))
                                t_user_name.setText("Picking up "+response.body()!!.data[0].user_name)

                                piclat=response.body()!!.data[0].pickup_latitude
                                piclong=response.body()!!.data[0].pickup_longitude
                                droplat=response.body()!!.data[0].drop_latitude
                                droplong=response.body()!!.data[0].drop_longitude

                                booking_id=response.body()!!.data[0].id




                                user_2_t_time.setText(response.body()!!.data[0].time+" min")
                                us_2_distance.setText(response.body()!!.data[0].distance+" Km")

                                try {
                                    Picasso.get()
                                        .load(response.body()!!.data[0].user_photo)
                                        .into(user_t_pic)
                                    Picasso.get()
                                        .load((response.body()!!.data[0].user_photo))
                                        .into(user_2_t_pic)

                                }catch (e:Exception){

                                }


                            } else if(response.body()!!.data[0].otp_status.equals("1")){
                                card_two.setVisibility(View.GONE);
                                card_three.setVisibility(View.GONE);
                                card_one.setVisibility(View.GONE);
                                card_five.setVisibility(View.VISIBLE)
                                moon_offline.setVisibility(View.GONE)
                                booking_id=response.body()!!.data[0].id



                                user_2_t_time.setText(response.body()!!.data[0].time+" min")
                                us_2_distance.setText(response.body()!!.data[0].distance+" Km")

                                try {
                                    Picasso.get()
                                        .load((response.body()!!.data[0].user_photo))
                                        .into(user_2_t_pic)

                                }catch (e:Exception){

                                }

                            }







                        }




                        else {
                            /*  dialog.dismiss()*/
                            card_two.visibility=View.GONE
                            card_one.visibility=View.GONE
                            moon_offline.setVisibility(View.GONE)
                            handler = Handler()
                            myRunnable = Runnable {
                                online_Detail()
                                // Things to be done
                            }

                            handler.postDelayed(myRunnable!!, 3000)
                            //  progress_loader_home.visibility= View.GONE




                        }
                    } else {

                        // progress_loader_home.visibility= View.GONE


                    }
                } catch (e: java.lang.Exception) {
                    // Toast.makeText(requireContext(),e.toString(),Toast.LENGTH_LONG).show()
                    // progress_loader_home.visibility= View.GONE



                }


            }

            override fun onFailure(call: Call<OnlineResponse>, t: Throwable) {
                /* dialog.dismiss()*/
                card_two.visibility=View.GONE
                // progress_loader_home.visibility= View.GONE
                // Toast.makeText( requireContext(),t.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText( requireContext(),"something wrong",Toast.LENGTH_SHORT).show()

            }
        })




    }

    fun online_respnse() {
        // progress_loader_home.visibility= View.VISIBLE

        //val dialog: ProgressDialog = ProgressDialog.show(this, null, "Please Wait")
        //*{"emailid":"anuj@gmail.com","securityPin":"1234","securityPinCnf":"1234"}*//*
        var stringStringHashMap = HashMap<String, String>()


        stringStringHashMap.put("driver_id",shrp.getStringPreference("user_id").toString())

        /*    stringStringHashMap.put("name", name)
            stringStringHashMap.put("email", email)
            stringStringHashMap.put("message", message)

            stringStringHashMap.put("driver_id",id_sharedprefence)
    */
        var check = APIUtils.getServiceAPI()!!.OnlineDetail(stringStringHashMap)
        check.enqueue(object : Callback<OnlineResponse> {
            override fun onResponse(
                call: Call<OnlineResponse>,
                response: Response<OnlineResponse>
            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {




                            if(response.body()!!.data[0].status.equals("1")){



                                myRunnable?.let { handler.removeCallbacks(it) };
                                card_one.setVisibility(View.GONE);
                                card_two.setVisibility(View.VISIBLE);
                                card_three.setVisibility(View.GONE);
                                card_five.setVisibility(View.GONE)
                                moon_offline.setVisibility(View.GONE)








                                //progress_loader_home.visibility= View.GONE

/*


                            val picklt=shrp.getStringPreference("picklt")
                            Log.d(picklt,"");

*/




                                shrp.setStringPreference("otp",response.body()!!.data[0].otp)



                                shrp.setStringPreference("picllt",response.body()!!.data[0].pickup_latitude)


                                shrp.setStringPreference("piclld",response.body()!!.data[0].pickup_longitude)


                                shrp.setStringPreference("droplt",response.body()!!.data[0].drop_latitude)


                                shrp.setStringPreference("dropld",response.body()!!.data[0].drop_longitude)



                                shrp.setStringPreference("pick",response.body()!!.data[0].pickup_address)


                                shrp.setStringPreference("drop",response.body()!!.data[0].drop_address)



                                shrp.setStringPreference("amount",response.body()!!.data[0].amount)
                                shrp.setStringPreference("customer",response.body()!!.data[0].user_name)


                                u_name.setText((response.body()!!.data[0].user_name))
                                u_distance.setText(((response.body()!!.data[0].distance+" km")))








                                /* val fl = DecimalFormat("#.##")

                                 fl.roundingMode = RoundingMode.FLOOR

                                 u_ammount.setText(fl.format("₹"+response.body()!!.data[0].amount))

 */




                                var amount_ddjj=roundOffDecimal(response.body()!!.data[0].amount.toDouble())
                                u_ammount.setText(("$"+amount_ddjj))
                                u_pickup.setText((response.body()!!.data[0].pickup_address))
                                u_drop.setText((response.body()!!.data[0].drop_address))
                                u_time.setText((response.body()!!.data[0].time+" min"))






                                booking_id=response.body()!!.data[0].id
                                user_t_time.setText(response.body()!!.data[0].time+" min")
                                us_distance.setText(response.body()!!.data[0].distance+" Km")
                                t_user_name.setText("Picking up "+response.body()!!.data[0].user_name)


                                ppickup.setText((response.body()!!.data[0].pickup_address))
                                ddrop.setText((response.body()!!.data[0].drop_address))

                                remark.setText((response.body()!!.data[0].remark))





                                user_2_t_time.setText(response.body()!!.data[0].time+" min")
                                us_2_distance.setText(response.body()!!.data[0].distance+" Km")
                                piclat=response.body()!!.data[0].pickup_latitude
                                piclong=response.body()!!.data[0].pickup_longitude
                                droplat=response.body()!!.data[0].drop_latitude
                                droplong=response.body()!!.data[0].drop_longitude

                                try {
                                    Picasso.get()
                                        .load(response.body()!!.data[0].user_photo)
                                        .into(user_t_pic)



                                    Picasso.get()
                                        .load((response.body()!!.data[0].user_photo))
                                        .into(u_image)




                                    Picasso.get()
                                        .load((response.body()!!.data[0].user_photo))
                                        .into(user_2_t_pic)

                                }catch (e:Exception){

                                }

                                Ride_status()

                            }
                            else if (response.body()!!.data[0].otp_status.equals("0"))
                            {
                                card_two.setVisibility(View.GONE);
                                card_three.setVisibility(View.VISIBLE);
                                card_one.setVisibility(View.GONE);
                                card_five.setVisibility(View.GONE)
                                moon_offline.setVisibility(View.GONE)
                                u_time.setText((response.body()!!.data[0].time+" min"))
                                u_distance.setText(((response.body()!!.data[0].distance+" km")))
                                t_user_name.setText("Picking up "+response.body()!!.data[0].user_name)

                                piclat=response.body()!!.data[0].pickup_latitude
                                piclong=response.body()!!.data[0].pickup_longitude
                                droplat=response.body()!!.data[0].drop_latitude
                                droplong=response.body()!!.data[0].drop_longitude

                                booking_id=response.body()!!.data[0].id




                                user_2_t_time.setText(response.body()!!.data[0].time+" min")
                                us_2_distance.setText(response.body()!!.data[0].distance+" Km")

                                try {
                                    Picasso.get()
                                        .load(response.body()!!.data[0].user_photo)
                                        .into(user_t_pic)
                                    Picasso.get()
                                        .load((response.body()!!.data[0].user_photo))
                                        .into(user_2_t_pic)

                                }catch (e:Exception){

                                }


                            } else if(response.body()!!.data[0].otp_status.equals("1")){
                                card_two.setVisibility(View.GONE);
                                card_three.setVisibility(View.GONE);
                                card_one.setVisibility(View.GONE);
                                card_five.setVisibility(View.VISIBLE)
                                moon_offline.setVisibility(View.GONE)
                                booking_id=response.body()!!.data[0].id



                                user_2_t_time.setText(response.body()!!.data[0].time+" min")
                                us_2_distance.setText(response.body()!!.data[0].distance+" Km")

                                try {
                                    Picasso.get()
                                        .load((response.body()!!.data[0].user_photo))
                                        .into(user_2_t_pic)

                                }catch (e:Exception){

                                }

                            }







                        }




                        else {
                            /*  dialog.dismiss()*/
                            card_two.visibility=View.GONE
                            card_one.visibility=View.GONE
                            moon_offline.setVisibility(View.GONE)
                            handler = Handler()
                            myRunnable = Runnable {
                                online_respnse()
                                // Things to be done
                            }

                            handler.postDelayed(myRunnable!!, 3000)
                            //  progress_loader_home.visibility= View.GONE




                        }
                    } else {

                        // progress_loader_home.visibility= View.GONE


                    }
                } catch (e: java.lang.Exception) {
                    // Toast.makeText(requireContext(),e.toString(),Toast.LENGTH_LONG).show()
                    // progress_loader_home.visibility= View.GONE



                }


            }

            override fun onFailure(call: Call<OnlineResponse>, t: Throwable) {
                /* dialog.dismiss()*/
                card_two.visibility=View.GONE
                // progress_loader_home.visibility= View.GONE
                // Toast.makeText( requireContext(),t.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText( requireContext(),"something wrong",Toast.LENGTH_SHORT).show()

            }
        })




    }


    fun Ride_status()
    {

        var stringStringHashMap = HashMap<String, String>()


        stringStringHashMap.put("driver_id",shrp.getStringPreference("user_id").toString())

        /*    stringStringHashMap.put("name", name)
            stringStringHashMap.put("email", email)
            stringStringHashMap.put("message", message)

            stringStringHashMap.put("driver_id",id_sharedprefence)
    */
        var check = APIUtils.getServiceAPI()!!.OnlineDetail(stringStringHashMap)
        check.enqueue(object : Callback<OnlineResponse> {
            override fun onResponse(
                call: Call<OnlineResponse>,
                response: Response<OnlineResponse>
            ) {
                try {


                    if (response.body()!!.success.equals("true")) {





                        if (response.body()!!.data[0].status.equals("3") )

                        {
                           // CompleteDialog()


                        }
                        else if( response.body()!!.data[0].cancel.equals("1"))
                        {
                           /* sharedpreferenceutils.getinstance(this@driverdetails)!!.removekey(constantutils.booking_id)
                            sharedpreferenceutils.getinstance(this@driverdetails)!!.removekey(constantutils.driver_Id)
                            sharedpreferenceutils.getinstance(this@driverdetails)!!.removekey(constantutils.driver_name)
                            sharedpreferenceutils.getinstance(this@driverdetails)!!.removekey(constantutils.vechile_number)
                            sharedpreferenceutils.getinstance(this@driverdetails)!!.removekey(constantutils.vechilename)
                            sharedpreferenceutils.getinstance(this@driverdetails)!!.removekey(constantutils.vechile_image)
                            sharedpreferenceutils.getinstance(this@driverdetails)!!.removekey(constantutils.distance)
                            sharedpreferenceutils.getinstance(this@driverdetails)!!.removekey(constantutils.driver_Rating)*/







                            var intent=Intent(requireContext(),HomeScreen::class.java)
                            startActivity(intent)





                        }
                        else

                        {
                            var handler: Handler? = null
                            handler = Handler()
                            handler.postDelayed(Runnable {
                                Ride_status()

                            }, 5000)




                        }


                        /* if()
                         {


                         }*/

                    }
                    else {


                        card_two.setVisibility(View.GONE);
                        card_three.setVisibility(View.GONE);
                        card_one.setVisibility(View.GONE);
                        card_five.setVisibility(View.GONE)

                        dialog()


                   //     Toast.makeText(requireContext(),response.body()!!.msg, Toast.LENGTH_LONG).show()
                      //  customprogress.hide()
                    }

                }  catch (e: Exception) {
                    Log.e("saurav", e.toString())
                    Toast.makeText(requireContext(),"Weak Internet Connection" ,Toast.LENGTH_LONG).show()
                    //customprogress.hide()

                }

            }

            override fun onFailure(call: Call<OnlineResponse>, t: Throwable) {
                Log.e("Saurav", t.message.toString())
                Toast.makeText(requireContext(),"Weak Internet Connection", Toast.LENGTH_LONG).show()
               // customprogress.hide()

            }

        })
    }

    fun AcceptTrip() {
        progress_loader_home.visibility= View.VISIBLE

        //val dialog: ProgressDialog = ProgressDialog.show(this, null, "Please Wait")
        //*{"emailid":"anuj@gmail.com","securityPin":"1234","securityPinCnf":"1234"}*//*
        var stringStringHashMap = HashMap<String, String>()
        //  stringStringHashMap.put("driver_id","55")

        stringStringHashMap.put("driver_id",shrp.getStringPreference("user_id").toString())
        stringStringHashMap.put("booking_id",booking_id)
        /*    stringStringHashMap.put("name", name)
            stringStringHashMap.put("email", email)
            stringStringHashMap.put("message", message)

            stringStringHashMap.put("driver_id",id_sharedprefence)
    */
        var check = APIUtils.getServiceAPI()!!.Accept_Trip(stringStringHashMap)
        check.enqueue(object : Callback<AcceptTripResponse> {
            override fun onResponse(
                call: Call<AcceptTripResponse>,
                response: Response<AcceptTripResponse>
            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {
                            //  online_Detail()
                            progress_loader_home.visibility= View.GONE
                            //  Toast.makeText(requireContext(),"API  is runningmessage",Toast.LENGTH_SHORT).show()

                            shrp.setStringPreference("call",response.body()!!.data[0].user_phone)





                        } else {
                            /*  dialog.dismiss()*/

                            progress_loader_home.visibility= View.GONE
                            Toast.makeText(
                                requireContext(),
                                response.body()!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        /*   dialog.dismiss()*/
                        Toast.makeText(       requireContext(), response.body()!!.msg, Toast.LENGTH_SHORT)
                            .show()

                    }
                } catch (e: java.lang.Exception) {

                }

            }
            override fun onFailure(call: Call<AcceptTripResponse>, t: Throwable) {
                /* dialog.dismiss()*/
                progress_loader_home.visibility= View.GONE
                //  Toast.makeText( requireContext(),t.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText( requireContext(),"Your internate connection problem",Toast.LENGTH_SHORT).show()
            }
        })

    }


    fun Reject_Trip() {
        progress_loader_home.visibility= View.VISIBLE

        //val dialog: ProgressDialog = ProgressDialog.show(this, null, "Please Wait")
        //*{"emailid":"anuj@gmail.com","securityPin":"1234","securityPinCnf":"1234"}*//*
        var stringStringHashMap = HashMap<String, String>()
        //  stringStringHashMap.put("driver_id","58")

        stringStringHashMap.put("driver_id",shrp.getStringPreference("user_id").toString())
        stringStringHashMap.put("booking_id",booking_id)
        /*    stringStringHashMap.put("name", name)
            stringStringHashMap.put("email", email)
            stringStringHashMap.put("message", message)

            stringStringHashMap.put("driver_id",id_sharedprefence)
    */
        var check = APIUtils.getServiceAPI()!!.reject_Trip(stringStringHashMap)
        check.enqueue(object : Callback<RejectResponse> {

            override fun onResponse(
                call: Call<RejectResponse>,
                response: Response<RejectResponse>

            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {



                            Toast.makeText(requireContext(),response.body()!!.msg,Toast.LENGTH_SHORT).show()


                            progress_loader_home.visibility= View.GONE
                            online_Detail()
                        } else {
                            /*  dialog.dismiss()*/

                            progress_loader_home.visibility= View.GONE
                            Toast.makeText(
                                requireContext(),
                                response.body()!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        /*   dialog.dismiss()*/
                        Toast.makeText(       requireContext(), response.body()!!.msg, Toast.LENGTH_SHORT)
                            .show()

                    }
                } catch (e: java.lang.Exception) {

                }


            }

            override fun onFailure(call: Call<RejectResponse>, t: Throwable) {
                /* dialog.dismiss()*/
                progress_loader_home.visibility= View.GONE
                //Toast.makeText( requireContext(),t.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText( requireContext(),"Your internate connection problem",Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                currentLocation
            } else {
                Toast.makeText(requireContext(), "Permission is denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private val currentLocation: Unit
        private get() {

            val locationRequest = LocationRequest()
            locationRequest.interval = 10000
            locationRequest.fastestInterval = 3000
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }

            LocationServices.getFusedLocationProviderClient(requireContext())
                .requestLocationUpdates(locationRequest, object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)
                        LocationServices.getFusedLocationProviderClient(requireContext())
                            .removeLocationUpdates(this)
                        if (locationResult.locations != null) {
                            if (locationResult.locations.size > 0) {
                                val latestlocIndex = locationResult.locations.size - 1
                                var lati = locationResult.locations[latestlocIndex].latitude
                                var longi = locationResult.locations[latestlocIndex].longitude
                                latitudee= lati.toString()
                                longitudee= longi.toString()

                                val location = Location("providerNA")
                                location.longitude = longi
                                location.latitude = lati
                                fetchaddressfromlocation(location)
                            } else {

                            }
                        }
                    }
                }, Looper.getMainLooper())
        }

    private inner class AddressResultReceiver(handler: Handler?) : ResultReceiver(handler) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
            super.onReceiveResult(resultCode, resultData)
            if (resultCode == Constants.SUCCESS_RESULT) {
                var address: String? = resultData.getString(Constants.ADDRESS)
                var locaity: String? = resultData.getString(Constants.LOCAITY)
                var state: String? = resultData.getString(Constants.STATE)
                var district: String? = resultData.getString(Constants.DISTRICT)
                var country: String? = resultData.getString(Constants.ADDRESS)
                locate = address + "," + locaity + "," + state
                loadmap(locate,latitudee,longitudee)
                updatelocation(locate,latitudee,longitudee,satus_str)

            } else {
                Toast.makeText(
                    requireContext(),
                    resultData.getString(Constants.RESULT_DATA_KEY),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
    private fun fetchaddressfromlocation(location: Location) {
        val intent = Intent(requireContext(), FetchAddressServices::class.java)
        intent.putExtra(Constants.RECEVIER, resultReceiver)
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location)
        requireContext().startService(intent)
    }

    fun  loadmap(loc:String,lat:String,Lon:String)
    {


        /*var latlanglist:ArrayList<LatLng>?=null
        latlanglist?.add(pickup_latlang)
        latlanglist?.add(drop_latlang)*/


        val mapFragment =
            childFragmentManager.findFragmentById(R.id.frg) as SupportMapFragment?  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment!!.getMapAsync { mMap ->
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

            mMap.clear() //clear old markers


            val googlePlex = CameraPosition.builder()
                .target(LatLng(lat.toDouble(), Lon.toDouble()))
                .zoom(12f)
                .tilt(45f)
                .build()

            mMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(googlePlex),
                1000,
                null
            )
            val height = 100
            val width = 100
            val bitmapdraw = resources.getDrawable(R.drawable.marker) as BitmapDrawable
            val b = bitmapdraw.bitmap
            val smallMarker = Bitmap.createScaledBitmap(b, width, height, false)
            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(lat.toDouble(), Lon.toDouble()))
                    .title(loc)
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
            )
        }
    }
    fun updatelocation(var1:String,var2:String,var3:String,var4:String) {
        progress_loader_home.visibility= View.VISIBLE
        // val dialog: ProgressDialog = ProgressDialog.show(requireContext(), null, "Please Wait")
/*{"emailid":"anuj@gmail.com","securityPin":"1234","securityPinCnf":"1234"}*/
        var stringStringHashMap = HashMap<String, String>()
        stringStringHashMap.put("driver_id", shrp.getStringPreference("user_id").toString())
        stringStringHashMap.put("latitude",var2)
        stringStringHashMap.put("longitude",var3)
        stringStringHashMap.put("location_name",var1)
        stringStringHashMap.put("status",var4)
        // Toast.makeText(requireContext(),var1+var2+var3+var4,Toast.LENGTH_SHORT).show()

        var repassword = APIUtils.getServiceAPI()!!.locationUpdate(stringStringHashMap)
        repassword.enqueue(object : Callback<LocationUpdate> {
            override fun onResponse(
                call: Call<LocationUpdate>,
                response: Response<LocationUpdate>
            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {

                            /* dialog.dismiss()*/
                            progress_loader_home.visibility= View.GONE
                            /* Toast.makeText(
                                 requireContext(),
                                 response.body()!!.msg,
                                 Toast.LENGTH_SHORT
                             ).show()*/


                        } else {
                            /*   dialog.dismiss()*/

                            card_two.setVisibility(View.GONE);
                            progress_loader_home.visibility= View.GONE
                            Toast.makeText(
                                requireContext(),
                                response.body()!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        /* dialog.dismiss()*/
                        progress_loader_home.visibility= View.GONE
                        Toast.makeText(
                            requireContext(),
                            response.body()!!.msg,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } catch (e: Exception) {

                }
            }
            override fun onFailure(call: Call<LocationUpdate>, t: Throwable) {
                /*  dialog.dismiss()*/
                progress_loader_home.visibility= View.GONE
                // Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText( requireContext(),"Your internate connection problem",Toast.LENGTH_SHORT).show()
            }
        })
    }





    private fun showDialog(picllt:String,piclld:String,droplt:String,dropld:String) {
        val dialog = Dialog(requireContext())
        dialog.getWindow()!!
            .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.setCancelable(true)
        dialog.setContentView(R.layout.otp_verify_popup)
        lateinit var button: CardView
        button = dialog.findViewById(R.id.okayCaerdview_d)
        var ss: EditText =dialog.findViewById(R.id.otp_v);


        var ff=  shrp.getStringPreference("otp").toString()
        if(ff.equals(""))
        {

        }
        else
        {
            ss.setText(ff)
        }

        Toast.makeText(requireContext(),ff,Toast.LENGTH_LONG).show()
        button.setOnClickListener {

            val value = ss.text.toString()


                    otp_m(value,picllt,piclld,droplt,dropld)
                    /* card_two.setVisibility(View.GONE);
                     card_three.setVisibility(View.VISIBLE);
                     card_one.setVisibility(View.GONE);*/




                    dialog.hide()




        }



        /*  okay_button.setOnClickListener()
          {

          dialog.dismiss()

          }*/



        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()

        dialog.setCanceledOnTouchOutside(false);



    }
    /* fun call(view: View) {
         val dialIntent = Intent(Intent.ACTION_DIAL)
         dialIntent.data = Uri.parse("tel:" + "8344814819")
         startActivity(dialIntent)
     }

 */





    fun no_Trip() {

        /* when(description.toInt()){
             0 -> no_job="Passenger not found"
             1 -> no_job="No longer required"
             2 -> no_job="No pre-pay"
             3 -> no_job="Abusive"
             3 -> no_job="other"

         }*/

        progress_loader_home.visibility= View.VISIBLE

        //val dialog: ProgressDialog = ProgressDialog.show(this, null, "Please Wait")
        //*{"emailid":"anuj@gmail.com","securityPin":"1234","securityPinCnf":"1234"}*//*
        var stringStringHashMap = HashMap<String, String>()
        //  stringStringHashMap.put("driver_id","58")

        stringStringHashMap.put("booking_id",booking_id)
        stringStringHashMap.put("description",checked_item)


        var check = APIUtils.getServiceAPI()!!.no_job(stringStringHashMap)
        check.enqueue(object : Callback<nojonResponse> {

            override fun onResponse(
                call: Call<nojonResponse>,
                response: Response<nojonResponse>

            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {



                            Toast.makeText(requireContext(),response.body()!!.msg,Toast.LENGTH_SHORT).show()


                            progress_loader_home.visibility= View.GONE
                            online_Detail()
                        } else {
                            /*  dialog.dismiss()*/

                            progress_loader_home.visibility= View.GONE
                            Toast.makeText(
                                requireContext(),
                                response.body()!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        /*   dialog.dismiss()*/
                        Toast.makeText(       requireContext(), response.body()!!.msg, Toast.LENGTH_SHORT)
                            .show()

                    }
                } catch (e: java.lang.Exception) {

                }


            }

            override fun onFailure(call: Call<nojonResponse>, t: Throwable) {
                /* dialog.dismiss()*/
                progress_loader_home.visibility= View.GONE
                //Toast.makeText( requireContext(),t.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText( requireContext(),"Your internate connection problem",Toast.LENGTH_SHORT).show()
            }
        })

    }








    private fun showDialog_job() {
        card_three.setVisibility(View.GONE);





        val dialog = Dialog(requireContext())
        dialog.getWindow()!!
            .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.setCancelable(true)
        dialog.setContentView(R.layout.jobno)


        var a="Passenger not found"
        var b="No longer required"
        var c="No pre-pay"
        var d="Abusive"
        lateinit var c1 : CheckBox
        lateinit var c2 : CheckBox
        lateinit var c3 : CheckBox
        lateinit var c4 : CheckBox

        lateinit var button: CardView
        button=dialog.findViewById(R.id.sendradio)


        c1=dialog.findViewById(R.id.checkbo_a)
        c2=dialog.findViewById(R.id.checkbo_b)
        c3=dialog.findViewById(R.id.checkbo_c)
        c4=dialog.findViewById(R.id.checkbo_d)
        c1.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (b) {
                c2.setChecked(false)
                c3.setChecked(false)
                c4.setChecked(false)

                checked_item="Passenger not found"


            }
        })
        c2.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (b) {
                c1.setChecked(false)
                c3.setChecked(false)
                c4.setChecked(false)
                checked_item="No longer required"


            }
        })
        c3.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (b) {
                c2.setChecked(false)
                c1.setChecked(false)
                c4.setChecked(false)
                checked_item="No pre-pay"


            }
        })
        c4.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (b) {
                c2.setChecked(false)
                c3.setChecked(false)
                c1.setChecked(false)
                checked_item="Abusive"


            }
        })



        button.setOnClickListener {



            if(c1.isChecked)
            {
                no_Trip()
                dialog.dismiss()

            }
            else if(c2.isChecked){

                no_Trip()
                dialog.dismiss()
            }
            else if(c3.isChecked){
                no_Trip()
                dialog.dismiss()
            }
            else if(c4.isChecked){
                no_Trip()
                dialog.dismiss()
            }
            else{
                Toast.makeText(requireContext(),"Please select  type",Toast.LENGTH_LONG).show()
            }


            /*val intent = Intent(this, FareDetails::class.java)
            startActivity(intent)*/
        }







        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()


        dialog.setCanceledOnTouchOutside(false);





    }













/*
private fun showDialog_job() {


 */
/*val dialog = Dialog(requireContext())
 dialog.getWindow()!!
     .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

 dialog.setCancelable(true)*//*

 // dialog.setContentView(R.layout.nojob)
 card_three.setVisibility(View.GONE);






 val builder = AlertDialog.Builder(requireContext())

 builder.setTitle("AlertDialog")
 val items = arrayOf("Passenger not found", "No longer required", "No pre-pay", "Abusive", "other")
 val checkedItem = 1
 builder.setSingleChoiceItems(items, checkedItem,
     DialogInterface.OnClickListener { dialog, which ->
         when (which) {
             0 -> Toast.makeText(requireContext(), "Passenger not found", Toast.LENGTH_LONG)
                 .show()
             1 -> Toast.makeText(requireContext(), "No longer required", Toast.LENGTH_LONG)
                 .show()
             2 -> Toast.makeText(
                 requireContext(),
                 "No pre-pay",
                 Toast.LENGTH_LONG
             ).show()
             3 -> Toast.makeText(requireContext(), "Abusive", Toast.LENGTH_LONG)
                 .show()
             4 -> Toast.makeText(requireContext(), "other", Toast.LENGTH_LONG)
                 .show()
         }
     })



     .setPositiveButton("Ok") { dialog, which ->


      no_Trip(which.toString())


         Toast.makeText(requireContext(), "$which Selected", Toast.LENGTH_SHORT)
             .show()
     }


     .setNegativeButton("Cancel") { dialog, which ->
         dialog.dismiss()
     }
     .show()

 val alert: AlertDialog = builder.create()
 alert.setCanceledOnTouchOutside(false)
 alert.show()
}
*/






    fun com_payment(){
        val dialog = ProgressDialog(requireContext(), R.style.AppCompatAlertDialogStyle)
        dialog.setTitle("Please Wait")
        dialog.show()
//   progress_loader_home.visibility= View.VISIBLE

//val dialog: ProgressDialog = ProgressDialog.show(this, null, "Please Wait")
//*{"emailid":"anuj@gmail.com","securityPin":"1234","securityPinCnf":"1234"}*//*
        var stringStringHashMap = HashMap<String, String>()
//  stringStringHashMap.put("driver_id","58")

        stringStringHashMap.put("driver_id", shrp.getStringPreference("user_id").toString())
        stringStringHashMap.put("booking_id", booking_id)
        var check = APIUtils.getServiceAPI()!!.complete_payment(stringStringHashMap)
        check.enqueue(object : Callback<completeResponse> {

            override fun onResponse(
                call: Call<completeResponse>,
                response: Response<completeResponse>

            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {

                            dialog.dismiss()
                            Toast.makeText(requireContext(),response.body()!!.msg, Toast.LENGTH_SHORT)
                            //   .show()

                            startActivity(Intent(requireContext(), Payment::class.java))

                            //  progress_loader_home.visibility= View.GONE

                        } else {
                            /*  dialog.dismiss()*/
                            dialog.dismiss()
                            //   progress_loader_home.visibility= View.GONE
                            Toast.makeText(
                                requireContext(), response.body()!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        dialog.dismiss()
                        /*   dialog.dismiss()*/
                        Toast.makeText(requireContext(), response.body()!!.msg, Toast.LENGTH_SHORT)
                            .show()

                    }
                } catch (e: java.lang.Exception) {
                    dialog.dismiss()
                }


            }

            override fun onFailure(call: Call<completeResponse>, t: Throwable) {
                /* dialog.dismiss()*/
                dialog.dismiss()
                //Toast.makeText( requireContext(),t.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(
                    requireContext(),
                    "Your internate connection problem",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun otp_m(otp_get:String,picllt:String,piclld:String,droplt:String,dropld:String){

        var stringStringHashMap = HashMap<String, String>()
//  stringStringHashMap.put("driver_id","58")

//   stringStringHashMap.put("driver_id", shrp.getStringPreference("user_id").toString())
        stringStringHashMap.put("booking_id", booking_id)

        stringStringHashMap.put("otp", otp_get)

        var check = APIUtils.getServiceAPI()!!.otp_match(stringStringHashMap)
        check.enqueue(object : Callback<otpmatchResponse> {

            override fun onResponse(
                call: Call<otpmatchResponse>,
                response: Response<otpmatchResponse>

            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {



                            Toast.makeText(requireContext(), response.body()!!.msg.toString(), Toast.LENGTH_SHORT).show()


                            val picllt=picllt

                            val piclld=piclld


                            val droplt=droplt


                            val dropld=dropld





                            //   Toast.makeText( requireContext(),longitudee+latitudee,Toast.LENGTH_SHORT).show()

                            // Toast.makeText( requireContext(),latitudee,Toast.LENGTH_SHORT).show()

                            if (picllt != null) {
                                if (piclld != null){
                                    loadNavigationView(picllt,piclld)
                                }

                            }
                            if (droplt != null) {
                                if (dropld != null){
                                    loadNavigationView(droplt,dropld)
                                }

                            }




                            card_two.setVisibility(View.GONE);
                            card_three.setVisibility(View.GONE);
                            card_one.setVisibility(View.GONE);
                            card_five.setVisibility(View.VISIBLE)




                        } else {
                            /*  dialog.dismiss()*/

                            //   progress_loader_home.visibility= View.GONE
                            Toast.makeText(
                                requireContext(), response.body()!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        /*   dialog.dismiss()*/
                        Toast.makeText(requireContext(), response.body()!!.msg, Toast.LENGTH_SHORT)
                            .show()

                    }
                } catch (e: java.lang.Exception) {

                }


            }

            override fun onFailure(call: Call<otpmatchResponse>, t: Throwable) {
                /* dialog.dismiss()*/
                progress_loader_home.visibility = View.GONE
                //Toast.makeText( requireContext(),t.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(
                    requireContext(),
                    "Your internate connection problem",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    fun loadNavigationView(latitudee: String, longitudee: String) {
        val navigation = Uri.parse("google.navigation:q=$latitudee,$longitudee")
        val navigationIntent = Intent(Intent.ACTION_VIEW, navigation)
        navigationIntent.setPackage("com.google.android.apps.maps")
        startActivity(navigationIntent)
    }


    fun onClick() {

        // When submit button is clicked,
        // Ge the Radio Button which is set
        // If no Radio Button is set, -1 will be returned
        val selectedId = radioGroup!!.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(requireContext(),
                "No answer has been selected",
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            val radioButton = radioGroup
                .findViewById<View>(selectedId) as RadioButton

            // Now display the value of selected item
            // by the Toast message
            Toast.makeText(
                requireContext(),
                radioButton.text,
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }






    fun checkGPSLocationStatus() {
        val manager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        if (!manager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
        }
    }


    private fun buildAlertMessageNoGps() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setMessage("Your GPS seems to be disabled, Please enable it.")
            .setCancelable(false)
            .setPositiveButton(
                "OK"
            ) { dialog, id ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
        val alert = builder.create()
        alert.show()
    }



    fun roundOffDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }







     fun dialog(){
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(requireContext())



        // set message of alert dialog
      dialogBuilder.setMessage("Ride Cancelled")
            // if the dialog is cancelable
            .setCancelable(false)
          .setPositiveButton("Ok", DialogInterface.OnClickListener {
                  dialog, id -> dialog.cancel()
          })
            // negative button text and action
          //  .setNegativeButton("Cancel", DialogInterface.OnClickListener {
              //      dialog, id -> dialog.cancel()
        //    })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Cancel Ride")
        // show alert dialog
        alert.show()
    }



}
