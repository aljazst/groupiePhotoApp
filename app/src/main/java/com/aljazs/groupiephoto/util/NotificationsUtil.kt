package com.aljazs.groupiephoto.util

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.aljazs.groupiephoto.R
import com.aljazs.groupiephoto.common.Constants.NOTIFICATION_CHANNEL
import com.aljazs.groupiephoto.ui.main.MainActivity

object NotificationsUtil {


    private val notificationOneId = 0

    private var notificationBuilder: NotificationCompat.Builder? = null


    // Notification ids
    const val BASIC_NOTIFICATION_ID = 1
    const val ACTION_BUTTON_NOTIFICATION_ID = 2
    private const val PROGRESS_INDICATOR_NOTIFICATION_ID = 3
    const val BIG_PICTURE_STYLE_NOTIFICATION_ID = 4
    const val BIG_TEXT_STYLE_NOTIFICATION_ID = 5
    const val INBOX_STYLE_NOTIFICATION_ID = 6
    const val MEDIA_STYLE_NOTIFICATION_ID = 7
    const val CUSTOM_NOTIFICATION_ID = 8

    // Notification Channel Group
    const val CHANNEL_GROUP_ID_ONE = "channel_group_id_1"
    const val CHANNEL_GROUP_ID_TWO = "channel_group_id_2"
    const val CHANNEL_GROUP_ONE = "Important"
    const val CHANNEL_GROUP_TWO = "Not Important"

    fun buildNotification(context: Context) {
        // Pending Intent to open a new activity in future.
        // Create a pending intent with back stack for regular activities(normal flows).
        val regularIntent = Intent(context, MainActivity::class.java)

        // Create a special pending intent without back stack.
        // This intent will open activity in new task.
        val specialIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val regularPendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(regularIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val specialPendingIntent =
            PendingIntent.getActivity(context, 0, specialIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.ic_baseline_bike) // Display a small icon on the left side.
            .setContentTitle("Cycling") // Notification Title
            .setContentText("Let take a ride") // Notification Subtitle.
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Set the interrupting behaviour by giving priority.
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(specialPendingIntent) // Open an activity on new task.
//            .setContentIntent(regularPendingIntent) // Open an activity on existing task
            .setAutoCancel(true) // Dismiss/Cancel the notification on Tap.
            .setGroup("GROUP_NAME") //specify which group this notification belongs to
            .setNumber(10) // specify badge number
    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = context.getString(R.string.channel_number_one) // Channel name
            val channelDescription =
                context.getString(R.string.channel_number_one_desc) // Channel Description
            val importance =
                NotificationManager.IMPORTANCE_DEFAULT  // Channel Interrupting Level or priority.
            val notificationChannel =
                NotificationChannel(NOTIFICATION_CHANNEL, channelName, importance).apply {
                    description = channelDescription // Channel Description [Optional]
                    lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                    group = CHANNEL_GROUP_ID_ONE
                    setShowBadge(true)
                }

            Log.i("TAG", "createNotificationChannel: ${notificationChannel.lockscreenVisibility}")

            // Register the channel with the system

            // Create a notification channel group
            getNotificationManager(context).createNotificationChannelGroup(
                NotificationChannelGroup(
                    CHANNEL_GROUP_ID_ONE, CHANNEL_GROUP_ONE
                )
            )

            // Register the channel with system.
            getNotificationManager(context).createNotificationChannel(notificationChannel)
        }
    }

    private fun getNotificationManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun displayNotification(context: Context, notificationId: Int) {
        getNotificationManager(context).notify(notificationId, notificationBuilder?.build())
    }


}