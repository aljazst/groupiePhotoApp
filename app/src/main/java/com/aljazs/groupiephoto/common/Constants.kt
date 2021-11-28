package com.aljazs.groupiephoto.common

import java.util.concurrent.TimeUnit

object Constants {

    const val INIT_VECTOR = "abcdefghijkl"

    const val CHANNEL_ID = "Chanel id"

    const val NOTIFICATION_CHANNEL = "CHANNEL_ID"

    object actionConstants {
        const val SNOOZE_ACTION = "com.aljazs.groupiephoto.handler.actions.SNOOZE"
        const val DISMISS_ACTION = "com.aljazs.groupiephoto.handler.actions.DISMISS"
        val SNOOZE_TIME = TimeUnit.SECONDS.toMillis(5)
        private const val TAG = "NotificationActionInten"
    }


}