package com.aljazs.groupiephoto.handler

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.aljazs.groupiephoto.common.Constants
import com.aljazs.groupiephoto.util.NotificationsUtil


class NotificationBroadcastReceiver : BroadcastReceiver() {

    private val TAG = "MyBroadcastReceiver"

    override fun onReceive(context: Context, intent: Intent) {
     //   val notificationId = intent.getIntExtra("notificationId", 0)


        intent.also { intent ->
         //   intent.setAction("com.example.broadcast.MY_NOTIFICATION")
           // intent.putExtra("data", "Nothing to see here, move along.")

            when (intent.action){
                Constants.actionConstants.SNOOZE_ACTION -> {
                    snozeActionHandler(context)
                }
                Constants.actionConstants.DISMISS_ACTION -> {
                    dismissActionHandler(context)
                }
            }

        }

    }

    fun snozeActionHandler(context: Context) {
        val notificationManager = NotificationsUtil.getNotificationManager(context) // Get the NotificationManager
        val newNotification: Notification?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNotifications =
                NotificationsUtil.getNotificationManager(context).activeNotifications
            // Check for active notification
            if (activeNotifications != null && activeNotifications.isNotEmpty()) {
                for (notification in activeNotifications) {
                    // Cancel the notification if it is active.
                    if (notification.id == NotificationsUtil.ACTION_BUTTON_NOTIFICATION_ID) {
                        fireSnoozeNotification(notification.notification, notificationManager)
                    }
                }
            } else {
                newNotification = reCreateNotification(context)?.build() // Create a new notification from the scratch.
                newNotification?.let { fireSnoozeNotification(it, notificationManager)
                }
            }

        } else {
            var builderInstance = NotificationsUtil.getNotificationBuilder() // Get the notification builder instance

            // If notification builder instance is null recreate a notification
            if (builderInstance == null) {
                builderInstance = reCreateNotification(context)
            }

            val notification = builderInstance?.build() // Build a notification
            notification?.let { fireSnoozeNotification(it, notificationManager) }
        }
    }

    private fun fireSnoozeNotification(
        notification: Notification,
        notificationManager: NotificationManager
    ) {
        notificationManager.cancel(NotificationsUtil.ACTION_BUTTON_NOTIFICATION_ID)
        try {
            Thread.sleep(Constants.actionConstants.SNOOZE_TIME) // Sleep/Wait for snooze time then fire notification again.
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }

        notificationManager.notify(NotificationsUtil.ACTION_BUTTON_NOTIFICATION_ID, notification)
    }

    private fun reCreateNotification(context: Context): NotificationCompat.Builder? {
        return NotificationsUtil.buildNotificationWithActionButtons(context)
    }

    private fun dismissActionHandler(context: Context) {
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.cancel(NotificationsUtil.ACTION_BUTTON_NOTIFICATION_ID)
    }
}
