package com.geelong.taxi_driver

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.geelong.taxi_driver.fragment.HomeFragment
import com.geelong.taxi_driver.navigation_drawer.HomeScreen
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelID=    "notification_channel"
const val channelName=  "com.geelong.taxi_driver"

open class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {




        if(remoteMessage.getNotification()!=null){
            generateNotification(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!!)

        }
    }

     @SuppressLint("RemoteViewLayout")
    fun getRemoteview(title: String,message: String): RemoteViews {
        val remoteView= RemoteViews("com.geelong.taxi_driver",R.layout.notification)
        remoteView.setTextViewText(R.id.title,title)

        remoteView.setTextViewText(R.id.message,message)
        remoteView.setImageViewResource(R.id.icon_notification,R.drawable.notificaticon)

        return remoteView


    }





    fun generateNotification(title:String,message:String)
    {

        val intent= Intent(this,HomeScreen::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent= PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_ONE_SHOT)

        // channel id, channel name
        var builder: NotificationCompat.Builder= NotificationCompat.Builder(applicationContext,
            channelID)
            .setSmallIcon(R.drawable.notificaticon)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
        builder=builder.setContent(getRemoteview(title,message))

        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val notificationChannel=
                NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)


        }
        notificationManager.notify(0,builder.build())




/*
        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mp: MediaPlayer = MediaPlayer.create(this, notification)
        mp.start()*/

    }





}