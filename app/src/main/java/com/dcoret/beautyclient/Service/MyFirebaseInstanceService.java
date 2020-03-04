package com.dcoret.beautyclient.Service;


import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.support.InternalChatActivity;
import com.dcoret.beautyclient.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;
import java.util.Random;

public class MyFirebaseInstanceService extends FirebaseMessagingService {
    String TAG="Firebase_tag",title,body,clickAction,pairs;
    public static int RANDOM_N_ID;


    public static String notification_id_channel="com.dcoret.beautyprovider.test";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        // ...
//
//        // TODO(developer): Handle FCM messages here.
//        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.e(TAG, "From: " + remoteMessage.getData());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
        String key_1 = "",key_2="",notify_type= "";
        try{
            title=remoteMessage.getData().get("title");
            body=remoteMessage.getData().get("body");
            //type=remoteMessage.getData().get("click_action");
            clickAction=remoteMessage.getData().get("click_action");
            pairs=remoteMessage.getData().get("pairs");
        }catch (Exception e){
            e.printStackTrace();

        }

        if(clickAction.equals("1"))
        {
            new NotificationsBeauty().showNotificationnormal(this,title,body);
        }
        if(clickAction.equals("2"))
        {
            Log.e("Notif", "click action is 2");

            new NotificationsBeauty().AnalizeNotificationCode(this,title,body,pairs);
        }
        if(clickAction.equals("4"))
        {
            Log.e("Notif", "click action is 4");

            new NotificationsBeauty().showURLNotification(this,title,body,pairs);
        }
/*
        if(action1!=null){
            new NotificationsBeauty().showNotification(this,title,body);

        }
*/
        else if (clickAction.equals("5"))
        {
            Log.e("Notif","ClickAction is 5");
            ActivityManager am = (ActivityManager) this.getSystemService( ACTIVITY_SERVICE );
            final Context context = this;
            // Get info from the currently active task
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks( 1 );
            String activityName = taskInfo.get( 0 ).topActivity.getClassName();
            String time="";
            try{
                time=remoteMessage.getData().get("time");

            }catch (Exception e){
                e.printStackTrace();

            }
            final String time2 =time;
            if(activityName.equals( InternalChatActivity.class.getName() ))
            {
                // Execute that special method from ActivityListView
                ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,body,Toast.LENGTH_LONG).show();
                        InternalChatActivity.NewMsg(context,body,false,time2);

                    }                });
            }
            else
            {
                // Show the notification
                //new NotificationsBeauty().showChatNotification(this,title,body);
            }
        }
//        Log.e("click_action",click_action);
            showNotification(this,title,body,notify_type,key_1,key_2);
    }


    public void showNotification(Context context,String title, String body,String notify_type,String key_1,String key_2) {

        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel=new NotificationChannel(notification_id_channel,
                    "Notificatio", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Firebase channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.WHITE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        RANDOM_N_ID=new Random().nextInt();
        Intent intent = new Intent(context, CancelNotifyBroadcast.class);
        Intent intent1 = new Intent(context, CancelNotifyBroadcast.class);
        Intent i=new Intent(context, BeautyMainPage.class);

        intent.putExtra("yourpackage.notifyId", notification_id_channel);
        intent.putExtra("fragment_notify", title);
        intent1.putExtra("yourpackage.notifyId", notification_id_channel);
        intent1.putExtra("fragment_notify", title);
        intent1.putExtra("N_ID",RANDOM_N_ID);
        intent.putExtra("N_ID",RANDOM_N_ID);

        PendingIntent pIntent;
        context.startService(intent1);
        context.startService(intent);
        intent1.putExtra("accept","cancel");
        intent1.setAction("cancel");

        intent.putExtra("accept","accept");
        intent.setAction("accept");
        pIntent = PendingIntent.getActivity(context, 0, i, 0);
        PendingIntent cancelintent = PendingIntent.getBroadcast(context,1,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent acceptintent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);



        NotificationCompat.Builder nBuilder=new NotificationCompat.Builder(context,notification_id_channel);
        nBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .setContentInfo("INFO");

                if(notify_type.equals("1")) {
                nBuilder.addAction(R.drawable.ic_close_black_24dp, key_1, cancelintent)
                        .addAction(R.drawable.ic_check_black_24dp, key_2, acceptintent);
                }




        notificationManager.notify(RANDOM_N_ID,nBuilder.build());
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
       // APICall.updateFBToken(BeautyMainPage.context, token, token);

        SharedPreferences.Editor editor = getSharedPreferences("REG_ID", MODE_PRIVATE).edit();
        editor.putString("token_client",token);
        editor.apply();
        Log.e("preference",token);

    }


}
