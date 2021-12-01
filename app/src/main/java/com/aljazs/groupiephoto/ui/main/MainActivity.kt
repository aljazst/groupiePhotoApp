package com.aljazs.groupiephoto.ui.main

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.aljazs.groupiephoto.R
import com.aljazs.groupiephoto.common.Constants.CHANNEL_ID
import com.aljazs.groupiephoto.databinding.ActivityMainBinding
import com.aljazs.groupiephoto.extensions.createBitmap
import com.aljazs.groupiephoto.extensions.extClick
import com.aljazs.groupiephoto.util.NotificationsUtil
import java.util.*
import kotlin.concurrent.schedule



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //https://github.com/elfer07/SampleAndroidNotification/blob/main/app/src/main/java/ar/com/notificacionesandroid/MainActivity.kt
    private lateinit var notificationOne: Notification
    private var notificationOneId = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.text1.text = "bananarama"



        NotificationsUtil.createNotificationChannel(this) // Create a notification channel for basic/non-expandable notification
       // NotificationsUtil.createExpandableNotificationChannel(this) // Crea


        binding.button.extClick {
            //notificationManager.notify(notificationOneId, notificationOne)
          Timer().schedule(1000) {
          }
              NotificationsUtil.buildNotificationWithActionButtons(applicationContext)
              notificationOneId = NotificationsUtil.ACTION_BUTTON_NOTIFICATION_ID
              NotificationsUtil.displayNotification(applicationContext, notificationOneId)

        }

/*        // Create an explicit intent for an Activity in your app
        val intent = Intent(this, AlertDetails::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("textTitle")
            .setContentText("textContent")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)*/
    }




}