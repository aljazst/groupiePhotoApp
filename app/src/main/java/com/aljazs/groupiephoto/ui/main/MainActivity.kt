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
import java.util.*
import kotlin.concurrent.schedule

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //https://github.com/elfer07/SampleAndroidNotification/blob/main/app/src/main/java/ar/com/notificacionesandroid/MainActivity.kt
    private lateinit var notificationOne: Notification
    private val notificationOneId = 0


    private lateinit var notificationTwo: Notification
    private val notificationTwoId = 1

    private lateinit var notificationThree: Notification
    private val notificationThreeId = 2

    private lateinit var notificationFour: Notification
    private val notificationFourId = 3

    private lateinit var notificationFive: Notification
    private val notificationFiveId = 4

    private lateinit var customNotification: Notification
    private val customNotificationId = 5


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.text1.text = "bananarama"

        createNotificationChannel()

        buildNotificationOne()
        buildNotificationThree()

        val notificationManager = NotificationManagerCompat.from(this)

        binding.button.extClick {
            //notificationManager.notify(notificationOneId, notificationOne)
            Timer().schedule(10000){
                notificationManager.notify(notificationThreeId, notificationThree)
            }

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

    private fun buildNotificationOne() {
        val myBitmap = R.drawable.toronto.createBitmap(this)

        notificationOne = NotificationCompat.Builder(this, CHANNEL_ID).also {
            it.setSmallIcon(R.drawable.ic_launcher_background)
            it.setContentTitle("Notification with Image")
            it.setContentText("Body's Notification")
            it.setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(myBitmap)
                    .bigLargeIcon(null)
            )
            it.setLargeIcon(myBitmap)

        }.build()
    }
    private fun buildNotificationThree() {
        notificationThree = NotificationCompat.Builder(this, CHANNEL_ID).also {
            it.setSmallIcon(R.drawable.ic_launcher_background)
            it.setContentTitle("morenofernando@gmail.com")
            it.setContentText("You have 3 unread emails")
            it.setStyle(
                NotificationCompat.InboxStyle()
                    .addLine("See free Android Source Codes from GitHub")
                    .addLine("You have won 1000 dollars!!")
                    .addLine("You item you have bought was sent.")
            )
        }.build()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name ="Name" // getString(R.string.channel_name)
            val descriptionText ="description" // getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}