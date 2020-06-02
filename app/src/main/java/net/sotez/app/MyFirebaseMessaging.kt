package net.sotez.app

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessaging : FirebaseMessagingService() {

    override fun onMessageReceived(rm: RemoteMessage) {
       createNotification(rm.notification)

        Log.d("MyFirebaseMessaging", rm.notification?.title)
    }


    private fun createNotification(notification: RemoteMessage.Notification?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(this, 0,
            intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager

    creatingNotificationChannel(notificationManager,
            "123", "Sotez", "")
        val pattern = longArrayOf(500, 500, 500, 500, 500, 500, 500, 500, 500)
        val mBuilder = NotificationCompat.Builder(this, "123")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notification?.title)
            .setContentText(notification?.body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setVibrate(pattern)
            .setAutoCancel(true)

        val notification = mBuilder.build()
        notification.flags = Notification.FLAG_AUTO_CANCEL
        notificationManager.notify(0, notification);
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun creatingNotificationChannel(notificationManager: NotificationManager,
                                    channelId: String?, channelName: String,
                                    channelDescription: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val name: CharSequence = channelName;
            val description = channelDescription
            val channel = NotificationChannel(channelId, name, NotificationManager
                .IMPORTANCE_DEFAULT);
            channel.description = description;
            // Register the channel with the system

            notificationManager.createNotificationChannel(channel);
        }
    }

    override fun onNewToken(token: String) {

    }
}