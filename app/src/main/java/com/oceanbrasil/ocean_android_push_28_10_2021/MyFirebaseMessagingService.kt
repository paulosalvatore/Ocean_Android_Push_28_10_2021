package com.oceanbrasil.ocean_android_push_28_10_2021

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

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.d("FIREBASE", "Token refreshed: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FIREBASE", "Message received: $remoteMessage")

        val notification = remoteMessage.notification
        val data = remoteMessage.data

        notification?.let {
            pushNotification(it.title, it.body)
        }
    }

    private fun pushNotification(title: String?, body: String?) {
        // Obtemos o serviço Notification Manager

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Criação do canal de notificações, apenas para Androis API 26 (OREO) ou superior

        val channelId = "OCEAN_PRINCIPAL"

        val channelName = "Ocean - Canal Principal"
        val channelDescription = "Ocean - Canal utilizado para as principais notícias do Samsung Ocean"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = channelDescription
            }

            notificationManager.createNotificationChannel(channel)
        }

        // Criação da Intent

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_ONE_SHOT
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, pendingIntentFlags)

        // Criação da notificação

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Envio da notificação

        val notification = notificationBuilder.build()

        val notificationId = 1

        notificationManager.notify(notificationId, notification)
    }
}
