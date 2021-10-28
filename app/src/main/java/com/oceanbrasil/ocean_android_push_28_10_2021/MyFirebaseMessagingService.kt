package com.oceanbrasil.ocean_android_push_28_10_2021

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FIREBASE", "Message received: $remoteMessage")
    }

    override fun onNewToken(token: String) {
        Log.d("FIREBASE", "Token refreshed: $token")
    }
}
