package com.afkoders.testtask18feb.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.afkoders.testtask18feb.R
import com.afkoders.testtask18feb.ui.MainActivity

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */


// Notification ID.
private val NOTIFICATION_ID = 23323


/**
 * Builds and delivers the notification.
 *
 * @param context, activity context.
 */

const val CONST_CHANNEL_ID = "very_secret_channel_id"


fun NotificationManager.sendNotification(messageBody: String, activityContext: Context) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            CONST_CHANNEL_ID,
            activityContext.getString(R.string.channel_title),
            NotificationManager.IMPORTANCE_HIGH
        )
        this.createNotificationChannel(channel)
    }

    // Create the content intent for the notification, which launches
    // this activity
    val contentIntent = Intent(activityContext, MainActivity::class.java)

    val contentPendingIntent = PendingIntent.getActivity(
        activityContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    // Build the notification
    val builder = NotificationCompat.Builder(
        activityContext,
        CONST_CHANNEL_ID
    )
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle(
            activityContext
                .getString(R.string.notification_title)
        )
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    notify(NOTIFICATION_ID, builder.build())
}
