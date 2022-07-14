package com.geelong.taxi_driver.fragment

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.geelong.taxi_driver.LocalSaved.shareprefrences
import com.geelong.taxi_driver.navigation_drawer.HomeScreen
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import java.util.*

   class ServiceTask : Service(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    protected val REQUEST_CHECK_SETTINGS = 0x1
    var currlat: Double = 0.0
    var currlog: Double = 0.0
    private var fusedLocationClient: FusedLocationProviderClient? = null
    lateinit var editor: SharedPreferences.Editor
    lateinit var sharedPreferences: SharedPreferences
    var counter = 0
    var context: Context? = null
    private val serviceCallbacks: ServiceCallbacks? = null
    lateinit var shrp: shareprefrences
    fun ServiceTask(applicationContext: Context?) {

        Log.i("HERE", "here I am!")
    }

    fun ServiceTask() {}

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("onBind", "onBind")
        // startScanning();
        return null
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        sharedPreferences = this.getSharedPreferences("SharedPrefName", Context.MODE_PRIVATE)
        shrp= shareprefrences(this)
        editor = sharedPreferences.edit()
        Log.d("onCreate", "onCreate")
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
    }

    /* public void getLocationPermission() {
        getLocation();
    }*/
/*

    public void getLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

// Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
    }
*/

    /* public void getLocationPermission() {
        getLocation();
    }*/
    /*

    public void getLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

// Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
    }
*/
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            Log.d("service", "is running")
            startTimer()
            //   startScanning();
        } catch (e: Exception) {
            Log.d("excep", e.toString())
        }

        //we have some options for service
        //start sticky means service will be explicity started and stopped
        return START_STICKY
    }

    fun startScanning() {
        try {
            settingsrequest()
        } catch (e: Exception) {
            Log.d("excep", e.toString())
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy", "onDestroy")
        stoptimertask()
    }

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    var oldTime: Long = 0

    fun startTimer() {
        //set a new Timer
        timer = Timer()
        //initialize the TimerTask's job
        initializeTimerTask()
        //schedule the timer, to wake up every 1 second
        timer!!.schedule(timerTask, 5000, 60000) //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    fun initializeTimerTask() {
        timerTask = object : TimerTask() {
            override fun run() {
                Log.i("in timer", "in timer ++++  " + counter++)
                startScanning()
            }
        }
    }

    /**
     * not needed
     */
    fun stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }


    override fun onConnected(bundle: Bundle?) {}

    override fun onConnectionSuspended(i: Int) {}

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

    fun settingsrequest() {
        val googleApiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this).build()
        googleApiClient.connect()
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = (30 * 1000).toLong()
        locationRequest.fastestInterval = (5 * 1000).toLong()
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true) //this is the key ingredient
        val result =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback { result ->
            val status = result.status
            val state = result.locationSettingsStates
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS ->                         // All location settings are satisfied. The client can initialize location
                    // requests here.
                    //  setLoginScreen();
                    getLocation()
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                }
            }
        }
    }

    fun getLocation() {
        try {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
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
            fusedLocationClient!!.lastLocation
                .addOnSuccessListener { location -> // Got last known location. In some rare situations this can be null.
                    // Log.d("fused_location", location.toString() + "")
                    if (location != null) {
                        currlat = location.latitude
                        currlog = location.longitude
                        shrp.setStringPreference("lat",currlat.toString())
                        shrp.setStringPreference("long",currlog.toString())
                        Log.d("currlatservice", "$currlat...")
                       // Toast.makeText(applicationContext,currlat.toString()+","+currlog.toString(),Toast.LENGTH_SHORT).show()
                        /*editor.putString("lat",currlat.toString())
                        editor.putString("long",currlog.toString())
                        editor.apply()
                        editor.commit()*/
                        getCurrentAddress(currlat, currlog)
                            serviceCallbacks?.doSomething(currlat,currlog)
                        //(context.activity  as HomeScreen?)?.getlocation(currlat,currlog)
                    } else {
                        getLocationfromClient()
                    }
                }
        } catch (e: Exception) {
        }
    }

    fun getLocationfromClient() {
        try {
            Log.d("location_clientstart", "..........")
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            locationRequest = LocationRequest.create()
            locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest!!.interval = (20 * 1000).toLong()
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    try {
                        //  Log.d("location_client", locationResult.toString() + "")

                        for (location in locationResult.locations) {
                            if (location != null) {
                                if (mFusedLocationClient != null) {
                                    mFusedLocationClient!!.removeLocationUpdates(locationCallback!!)
                                }
                                currlat = location.latitude
                                currlog = location.longitude
                                Log.d("location_from_client", location.toString() + "")
                                Log.d("currlatservice", "$currlat...")
                                Log.d("currlogservice", currlog.toString() + "...")


                                // getCurrentAddress(currlat, currlog);
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
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
            mFusedLocationClient!!.requestLocationUpdates(
                locationRequest!!,
                locationCallback!!,
                null!!
            )
        } catch (e: Exception) {
        }
    }

    fun getCurrentAddress(latitude: Double, longitude: Double) {
        val gc = Geocoder(this, Locale.getDefault())
        try {
            val addresses = gc.getFromLocation(latitude, longitude, 1)
            val sb = StringBuilder()
            if (addresses.size > 0) {
                val address = addresses[0]
                // for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
                //    sb.append(address.getAddressLine(i)).append("\n");
                sb.append(address.subLocality).append(", ")
                sb.append(address.locality).append(", ")
                sb.append(address.adminArea).append(", ")
                sb.append(address.countryName).append(", ")
                sb.append(address.postalCode)
                shrp.setStringPreference("location_name",address.subLocality)

            }
            Log.d("currentAddress", sb.toString())
            // PreferenceUtil.setStringPrefs(this, PreferenceUtil.CURRENT_ADDRESS, sb.toString());
        } catch (e: Exception) {
        }
    }

    interface ServiceCallbacks {
        fun doSomething(lat:Double,Lonf:Double)
    }

}