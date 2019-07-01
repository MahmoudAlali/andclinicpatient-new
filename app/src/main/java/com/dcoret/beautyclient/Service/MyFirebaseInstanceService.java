package com.dcoret.beautyclient.Service;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dcoret.beautyclient.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyFirebaseInstanceService extends FirebaseMessagingService {
    String TAG="Firebase_tag";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        // ...
//
//        // TODO(developer): Handle FCM messages here.
//        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        String title, body;
        try{
            title=remoteMessage.getData().get("title");
            body=remoteMessage.getData().get("body");
        }catch (Exception e){
            e.printStackTrace();
            title="xxx";
            body="xxxx";
        }

            showNotification(title,body);
    }

    private void showNotification(String title, String body) {
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String notification_id_channel="com.dcoret.beautyclient.test";

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel=new NotificationChannel(notification_id_channel,
                    "Notificatio",NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Firebase channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.WHITE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder nBuilder=new NotificationCompat.Builder(this,notification_id_channel);
        nBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("INFO")
                ;
        notificationManager.notify(new Random().nextInt(),nBuilder.build());
    }



    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        //----------------------------- Reg in DATABASE AFTER---

        //------------------ Reg In SharedPreference------------
        SharedPreferences.Editor editor = getSharedPreferences("REG_ID", MODE_PRIVATE).edit();
        editor.putString("token_client",token);
        editor.apply();
        Log.e("preference",token);

    }


}
