package com.geelong.taxi_driver.navigation_drawer

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customnavigationdrawerexample.ClickListener
import com.example.customnavigationdrawerexample.NavigationItemModel
import com.example.customnavigationdrawerexample.NavigationRVAdapter
import com.example.customnavigationdrawerexample.RecyclerTouchListener
import com.geelong.taxi_driver.Bank.Bank_Detail
import com.geelong.taxi_driver.Help_and_Support.Help_Support
import com.geelong.taxi_driver.History.Your_Earning
import com.geelong.taxi_driver.Invite_a_friend.Invite_a_Friend
import com.geelong.taxi_driver.LocalSaved.shareprefrences
import com.geelong.taxi_driver.Login.Login
import com.geelong.taxi_driver.Notification.Notification
import com.geelong.taxi_driver.Profile.Profile_Overview
import com.geelong.taxi_driver.R
import com.geelong.taxi_driver.Setting.Setting
import com.geelong.taxi_driver.fragment.HomeFragment
import com.geelong.taxi_driver.navigation_drawer.Noti_countModel.NotificationCountResponse
import com.geelong.taxi_driver.Notification.Noti_viewModel.noti_view_Response
import com.geelong.taxi_driver.RideLtaer.RideLater
import com.gogo.gogokull.api.APIUtils
import com.squareup.picasso.Picasso
import com.suke.widget.SwitchButton
import kotlinx.android.synthetic.main.activity_home_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeScreen : AppCompatActivity() {


    private var notificationNumber: TextView? = null


    lateinit var customprogress:Dialog

        lateinit var drawerLayout: DrawerLayout
    private lateinit var adapter: NavigationRVAdapter
    var switch = "false"
    lateinit var switchButton: SwitchButton
    private lateinit var locationManager: LocationManager
    lateinit var shrp: shareprefrences
    private val locationPermissionCode = 2
    var lat: Double = 0.0
    var lang: Double = 0.0
    lateinit var locat:String
    var staus_string:String=""
    var resultReceiver: ResultReceiver? = null




   private fun count(view: View)

   {
      // notificationNumber=view.findViewById(R.id.noti_count)

   }



    private var items = arrayListOf(

        NavigationItemModel(R.drawable.icon_profile, "Profile Overview"),
        NavigationItemModel(R.drawable.icon_history, "Your History"),

        NavigationItemModel(R.drawable.icon_notification, "Notification"),
        NavigationItemModel(R.drawable.icon_setting, "Settings"),
        NavigationItemModel(R.drawable.icon_headset, "Help & Support"),
        NavigationItemModel(R.drawable.icon_invite, "Invite friends"),

        NavigationItemModel(R.drawable.icon_notification, "Ride later detail"),


        NavigationItemModel(R.drawable.icon_logout, "Log Out"),

     //   NavigationItemModel(R.drawable.ridelater, "Ride later"),

        // NavigationItemModel(R.drawable.back, "Like us on facebook")
    )
    private var items1 = arrayListOf(
        NavigationItemModel(R.drawable.icon_profile, "Profile Overview"),
        NavigationItemModel(R.drawable.icon_history, "Your History"),

        NavigationItemModel(R.drawable.icon_notification, "Notification"),
        NavigationItemModel(R.drawable.icon_setting, "Settings"),
        NavigationItemModel(R.drawable.icon_headset, "Help & Support"),
        NavigationItemModel(R.drawable.icon_invite, "Invite friends"),
        NavigationItemModel(R.drawable.icon_notification, "Ride later detail"),
        NavigationItemModel(R.drawable.icon_logout, "Log Out"),

       // NavigationItemModel(R.drawable.ridelater, "Ride later"),



    )

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)






         ///   resultReceiver = AddressResultReceiver(Handler())
            drawerLayout = findViewById(R.id.drawer_layout)
            switchButton =findViewById(R.id.switch_button)
            customprogress= Dialog(this)
            customprogress.setContentView(R.layout.loader_layout)


            shrp= shareprefrences(this)
            count_newNo()
            //customprogress.show()

            switchButton.setChecked(false)
            switchButton.isChecked()
            switchButton.toggle() //switch state

            switchButton.toggle(false) //switch without animation

            switchButton.setShadowEffect(true) //disable shadow effect

            //startService(Intent(applicationContext, ServiceTask::class.java))
            switchButton.setEnableEffect(false) //disable the switch animation

            val bundle = Bundle()
            bundle.putString("fragmentName", "Settings Fragment")
            val settingsFragment = HomeFragment()
            settingsFragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_content_id, settingsFragment).commit()
            customprogress.hide()

            try {
                var status:String=shrp.getStringPreference("app_status").toString()

                if (status.equals("offline")){
                    switchButton.isChecked=false


                    activity_main_toolbar_title.setText("Offline")
                }else if (status.equals("online")){
                    switchButton.isChecked=true
                    activity_main_toolbar_title.setText("Online")
                }else{
                    switchButton.isChecked=false


                    activity_main_toolbar_title.setText("Offline")
                }
            }catch (e: java.lang.Exception){
            }


            switchButton.setOnCheckedChangeListener(SwitchButton.OnCheckedChangeListener { view, isChecked ->
                //TODO do your job
                if (isChecked) {
                    activity_main_toolbar_title.setText("Online")
                    staus_string="2"

                    shrp.setStringPreference("app_status","online")
                    val fragment: HomeFragment? =
                        supportFragmentManager.findFragmentById(R.id.activity_main_content_id) as HomeFragment?
                    fragment?.online("online")
                    // (fragmentManager as HomeFragment?)?.data("online")
                } else {
                    activity_main_toolbar_title.setText("Offline")
                    staus_string="1"
                    shrp.setStringPreference("app_status","offline")
                    val fragment: HomeFragment? =
                        supportFragmentManager.findFragmentById(R.id.activity_main_content_id) as HomeFragment?
                    fragment?.offline("Offline")


                }
            })

          /*  if ((ContextCompat.checkSelfPermission(
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
                currentLocation
            }
*/





/*
        switch1.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(this@MainActivity1, "ON", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity1, "OFF", Toast.LENGTH_SHORT).show()
            }
        })
*/
/*
        switch1.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                activity_main_toolbar_title.setText("Online")
                // The toggle is enabled
            } else {
                activity_main_toolbar_title.setText("Offline")

                // The toggle is disabled
            }
        })
*/
            //    val switch1 =findViewById<Switch>(R.id.switch2)

/*
        switch2.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked) {
                //switch2.text = "Switch ON"
                Toast.makeText(this, "First Switch Button: ON", Toast.LENGTH_LONG).show()
                activity_main_toolbar_title.setText("Online")
                switch2.setBackgroundResource(R.drawable.)


            }
            else {
               // switch2.text = "Switch "
                Toast.makeText(this, "First Switch Button: OFF", Toast.LENGTH_LONG).show()
                activity_main_toolbar_title.setText("Offline")

            }
        }
*/
            /*if (switch == "false")
            {
                activity_main_toolbar_title.setText("online")

            }
            else
            {
                activity_main_toolbar_title.setText("offline")
                Toast.makeText(this, "no", Toast.LENGTH_SHORT).show()
            }*/









            ivClose.setOnClickListener() {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            // Set the toolbar
            // setSupportActionBar(activity_main_toolbar)
            getSupportActionBar()?.setDisplayShowTitleEnabled(false);
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(false);



            // Setup Recyclerview's Layout
            navigation_rv.layoutManager = LinearLayoutManager(this)
            navigation_rv.setHasFixedSize(true)
            ivMenu.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }




            // Add Item Touch Listener
            navigation_rv.addOnItemTouchListener(RecyclerTouchListener(this, object : ClickListener {
                override fun onClick(view: View, position: Int) {
                    when (position) {
                        0 -> {
                            drawerLayout.closeDrawer(GravityCompat.START)
                            val intent = Intent(this@HomeScreen, Profile_Overview::class.java)
                            startActivity(intent)

                        }
                        1 -> {
                            drawerLayout.closeDrawer(GravityCompat.START)
                            val intent = Intent(this@HomeScreen, Your_Earning::class.java)
                            startActivity(intent)


                        }


                        2 -> {
                            drawerLayout.closeDrawer(GravityCompat.START)
                            val intent = Intent(this@HomeScreen, Notification::class.java)
                            startActivity(intent)




                        }

                        3-> {
                            drawerLayout.closeDrawer(GravityCompat.START)
                            val intent = Intent(this@HomeScreen, Setting::class.java)
                            startActivity(intent)

                        }

                        4-> {
                            drawerLayout.closeDrawer(GravityCompat.START)
                            val intent = Intent(this@HomeScreen, Help_Support::class.java)
                            startActivity(intent)


                            // # Settings Fragment
//                       *//* val bundle = Bundle()
//                         bundle.putString("fragmentName", "Settings Fragment")
//                         val settingsFragment = DemoFragment()
//                         settingsFragment.arguments = bundle
//                         supportFragmentManager.beginTransaction()
//                             .replace(R.id.activity_main_content_id, settingsFragment).commit()*//*
                        }
                        5 -> {
                            drawerLayout.closeDrawer(GravityCompat.START)
                            val intent = Intent(this@HomeScreen, Invite_a_Friend::class.java)
                            startActivity(intent)


                        }


                        6 -> {
                            drawerLayout.closeDrawer(GravityCompat.START)
                        val intent = Intent(this@HomeScreen, RideLater::class.java)
                            startActivity(intent)


                        }

                        7 -> {
                        drawerLayout.closeDrawer(GravityCompat.START)
                        val intent = Intent(this@HomeScreen, Login::class.java)
                        startActivity(intent)
                        finishAffinity()
                    shrp.clearAllPreferences()

                    }
                    }
                    // Don't highlight the 'Profile' and 'Like us on Facebook' item row
                    updateAdapter(position)
                    if (position != 6 && position != 4) {

                    }
                    Handler().postDelayed({
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }, 200)
                }
            }))

            // Update Adapter with item data and highlight the default menu item ('Home' Fragment)
            updateAdapter(0)

            // Set 'Home' as the default fragment when the app starts
            /*  val bundle = Bundle()
              bundle.putString("fragmentName", "Home Fragment")
              val homeFragment = DemoFragment()
              homeFragment.arguments = bundle
              supportFragmentManager.beginTransaction()
                  .replace(R.id.activity_main_content_id, homeFragment).commit()*/


            // Close the soft keyboard when you open or close the Drawer
            val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                this,
                drawerLayout,
                activity_main_toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            ) {
                override fun onDrawerClosed(drawerView: View) {
                    // Triggered once the drawer closes
                    super.onDrawerClosed(drawerView)
                    try {
                        val inputMethodManager =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                    } catch (e: Exception) {
                        e.stackTrace
                    }
                }

                override fun onDrawerOpened(drawerView: View) {
                    // Triggered once the drawer opens
                    super.onDrawerOpened(drawerView)
                    try {
                        val inputMethodManager =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                    } catch (e: Exception) {
                        e.stackTrace
                    }
                }
            }
            drawerLayout.addDrawerListener(toggle)

            toggle.syncState()


            // Set Header Image
            // navigation_header_img.setImageResource(R.drawable.logo)

            // Set background of Drawer
            navigation_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        }


    private fun updateAdapter(highlightItemPos: Int) {
        adapter = NavigationRVAdapter(items,items1, highlightItemPos)
        navigation_rv.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            // Checking for fragment count on back stack
            if (supportFragmentManager.backStackEntryCount > 0) {
                // Go to the previous fragment
                supportFragmentManager.popBackStack()
            } else {
                // Exit the app
                super.onBackPressed()
            }
        }
    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

        }
    }

/*
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
                Toast.makeText(this, "Permission is denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }*/

    // TODO: Consider calling
    //    ActivityCompat#requestPermissions
    // here to request the missing permissions, and then overriding
    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
    //                                          int[] grantResults)
    // to handle the case where the user grants the permission. See the documentation
    // for ActivityCompat#requestPermissions for more details.
  /*  private val currentLocation: Unit
        private get() {

            val locationRequest = LocationRequest()
            locationRequest.interval = 10000
            locationRequest.fastestInterval = 3000
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(locationRequest, object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)
                        LocationServices.getFusedLocationProviderClient(applicationContext)
                            .removeLocationUpdates(this)
                        if(locationResult.locations !=null) {
                            if (locationResult.locations.size > 0) {
                                val latestlocIndex = locationResult.locations.size - 1
                                val lati = locationResult.locations[latestlocIndex].latitude
                                val longi = locationResult.locations[latestlocIndex].longitude
                                lat=lati
                                lang=longi
                                *//* textLatLong!!.text =
                                     String.format("Latitude : %s\n Longitude: %s", lati, longi)*//*

                                //  toast.maketext(this@search1,lati.tostring()+longi.tostring(),toast.length_long).show()
                                val location = Location("providerNA")
                                location.longitude = longi
                                location.latitude = lati
                                fetchaddressfromlocation(location)
                            } else {
                                *//* progressBar!!.visibility = View.GONE*//*
                            }
                        }
                    }
                }, Looper.getMainLooper())
        }

    private inner class AddressResultReceiver(handler: Handler?) : ResultReceiver(handler) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
            super.onReceiveResult(resultCode, resultData)
            if (resultCode == Constants.SUCCESS_RESULT) {
                var  address: String? =resultData.getString(Constants.ADDRESS)
                var  locaity: String? =resultData.getString(Constants.LOCAITY)
                var  state: String? =resultData.getString(Constants.STATE)
                var  district: String? =resultData.getString(Constants.DISTRICT)
                var  country: String? =resultData.getString(Constants.ADDRESS)
                locat=address+locaity+state+district+district+country

                val bundle = Bundle()
                bundle.putString("fragmentName", "Settings Fragment")
                bundle.putString("Location",locat)
                bundle.putString("Late",lat.toString())
                bundle.putString("Long",lang.toString())
                val settingsFragment = HomeFragment()
                settingsFragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_main_content_id, settingsFragment).commit()
                customprogress.hide()

                // toast.maketext(this@search1,address+locaity+state+district+district+country,toast.length_long).show()

                *//* address!!.text = resultData.getString(Constants.ADDRESS)
                 locaity!!.text = resultData.getString(Constants.LOCAITY)
                 state!!.text = resultData.getString(Constants.STATE)
                 district!!.text = resultData.getString(Constants.DISTRICT)
                 country!!.text = resultData.getString(Constants.COUNTRY)
                 postcode!!.text = resultData.getString(Constants.POST_CODE)*//*
            } else {

            }
            *//*  progressBar!!.visibility = View.GONE*//*
        }
    }

    private fun fetchaddressfromlocation(location: Location) {
        val intent = Intent(this, FetchAddressServices::class.java)
        intent.putExtra(Constants.RECEVIER, resultReceiver)
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location)
        startService(intent)
    }

    companion object {
        private val LOCATION_PERMISSION_REQUEST_CODE = 1
    }*/


    fun count_newNo() {


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
        var check = APIUtils.getServiceAPI()!!.count_notification(stringStringHashMap)
        check.enqueue(object : Callback<NotificationCountResponse> {

            override fun onResponse(
                call: Call<NotificationCountResponse>,
                response: Response<NotificationCountResponse>

            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {


                        //    Toast.makeText(this@HomeScreen,response.body()!!.msg, Toast.LENGTH_SHORT).show()
                         //   notificationNumber?.setText((response.body()!!.data[0].count.toString()))

                            shrp.setStringPreference("count",response.body()!!.data[0].count)



                        } else {
                            /*  dialog.dismiss()*/


                            Toast.makeText(
                                this@HomeScreen,
                                response.body()!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        /*   dialog.dismiss()*/
                        Toast.makeText(this@HomeScreen, response.body()!!.msg, Toast.LENGTH_SHORT)
                            .show()

                    }
                } catch (e: java.lang.Exception) {

                }


            }

            override fun onFailure(call: Call<NotificationCountResponse>, t: Throwable) {
                /* dialog.dismiss()*/
             //   progress_loader_home.visibility= View.GONE
                //Toast.makeText( requireContext(),t.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText( this@HomeScreen,"Your internate connection problem", Toast.LENGTH_SHORT).show()
            }
        })

    }
















    override fun onResume() {
        try {
            Picasso.get()
                .load(shrp.getStringPreference("profile_photo").toString())
                .into(profile_image)

            name.setText(shrp.getStringPreference("name"))
            mobile_no.setText(shrp.getStringPreference("mobile").toString())
        }catch (e:Exception){

        }

        super.onResume()
    }













}