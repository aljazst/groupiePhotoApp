package com.aljazs.groupiephoto.handler

import android.app.IntentService
import android.app.Notification
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.aljazs.groupiephoto.util.NotificationsUtil
import java.util.concurrent.TimeUnit

/**
 * Created by Dhruv Limbachiya on 07-07-2021.
 */

class NotificationActionService(name: String = "NotificationIntentService") :
    IntentService(name) {

    override fun onHandleIntent(intent: Intent?) {
        intent?.let { i ->
            when (i.action) {
                "" -> {
                    handleSnoozeAction()
                }
                "DISMISS_ACTION" -> {
                    handleDismissAction()
                }
            }
        }
    }


    /**
     * The method is responsible for canceling the active notification and create and fire new notification after snooze time over.
     */
    private fun handleSnoozeAction() {
        val notificationManager =
            NotificationsUtil.getNotificationManager(this) // Get the NotificationManager
        val _notification: Notification?

        // We use NotificationManager.getActiveNotifications() if we are targeting SDK 23
        // and above, but we are targeting devices with lower SDK API numbers, so we saved the
        // builder globally and get the notification back to recreate it later (the else part).
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNotifications =
                NotificationsUtil.getNotificationManager(this).activeNotifications
            // Check for active notification
            if (activeNotifications != null && activeNotifications.isNotEmpty()) {
                for (notification in activeNotifications) {
                    // Cancel the notification if it is active.
                    if (notification.id == NotificationsUtil.ACTION_BUTTON_NOTIFICATION_ID) {
                        fireSnoozeNotification(notification.notification, notificationManager)
                    }
                }
            } else {
                _notification =
                    reCreateNotification()?.build() // Create a new notification from the scratch.
                _notification?.let {
                    fireSnoozeNotification(it, notificationManager)
                }
            }

        } else {
            var builderInstance =
                NotificationsUtil.getNotificationBuilder() // Get the notification builder instance

            // If notification builder instance is null recreate a notification
            if (builderInstance == null) {
                builderInstance = reCreateNotification()
            }

            val notification = builderInstance?.build() // Build a notification
            notification?.let { fireSnoozeNotification(it, notificationManager) }
        }

    }

    /**
     * Cancel the active notification and notify new notification after completing snooze time.
     */
    private fun fireSnoozeNotification(
        notification: Notification,
        notificationManager: NotificationManager
    ) {
        notificationManager.cancel(NotificationsUtil.ACTION_BUTTON_NOTIFICATION_ID)
        try {
            Thread.sleep(3) // Sleep/Wait for snooze time then fire notification again.
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }

        notificationManager.notify(NotificationsUtil.ACTION_BUTTON_NOTIFICATION_ID, notification)
    }

    /**
     * Build a new Notification.
     * @return - builder
     */
    private fun reCreateNotification(): NotificationCompat.Builder? {
        return NotificationsUtil.buildNotificationWithActionButtons(this)
    }

    /**
     * Cancel the Notification using Notification id.
     */
    private fun handleDismissAction() {
        val notificationManagerCompat = NotificationManagerCompat.from(applicationContext)
        notificationManagerCompat.cancel(NotificationsUtil.ACTION_BUTTON_NOTIFICATION_ID)

    }


}