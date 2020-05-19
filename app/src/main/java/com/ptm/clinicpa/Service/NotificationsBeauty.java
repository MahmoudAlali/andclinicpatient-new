package com.ptm.clinicpa.Service;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.support.InternalChatActivity;
import com.ptm.clinicpa.DataModel.DataOffer;
import com.ptm.clinicpa.R;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotificationsBeauty {


    //public static DataOffer.SupIdClass tempOffer;
    public static ArrayList<DataOffer.SupIdClass> supIdClasses=new ArrayList<>();
    public static String offer_place;

    String action_intent;
    public static int RANDOM_N_ID;


    public static String notification_id_channel="com.dcoret.beautyprovider.test";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showNotification(Context context, String title, String body) {

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
//        Intent i=new Intent(context,ProviderMainPage.class);
        Intent i=null;
        try {
            i = launchIntent(context);
        }catch (Exception e){
            i=new Intent(context, BeautyMainPage.class);
        }
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
        PendingIntent cancelintent = PendingIntent.getBroadcast(context,1,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent acceptintent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);



        NotificationCompat.Builder nBuilder=new NotificationCompat.Builder(context,notification_id_channel);
        nBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo2)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .setContentInfo("INFO")
                .addAction(R.drawable.ic_close_black_24dp,"cancel",cancelintent)
                .addAction(R.drawable.ic_check_black_24dp,"accept",acceptintent);





        notificationManager.notify(RANDOM_N_ID,nBuilder.build());
    }

    public void showNotificationnormal(Context context, String title, String body) {



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
//        Intent intent = new Intent(context, ProviderMainPage.class);
        Intent intent=null;
        try {
            intent = launchIntent(context);
        }catch (Exception e){
            intent=new Intent(context,BeautyMainPage.class);
        }
        intent.putExtra("yourpackage.notifyId", notification_id_channel);
        intent.putExtra("fragment_notify", title);
//        Intent intent1 = new Intent(context, ProviderMainPage.class);
        Intent intent1=null;
        try {
            intent1 = launchIntent(context);
        }catch (Exception e){
            intent1=new Intent(context,BeautyMainPage.class);
        }
        intent1.putExtra("yourpackage.notifyId", notification_id_channel);
        intent1.putExtra("fragment_notify", title);
        PendingIntent pIntent;
        pIntent = PendingIntent.getActivity(context, 0, intent,
                0);
        intent1.putExtra("accept","cancel");
        intent1.setAction("cancel");
        intent.putExtra("NOTFICATION_ID",RANDOM_N_ID);
        PendingIntent cancelintent = PendingIntent.getActivity(context, 0, intent1,
                PendingIntent.FLAG_CANCEL_CURRENT);


        intent.putExtra("accept","accept");
        intent.setAction("accept");
        PendingIntent acceptintent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder nBuilder=new NotificationCompat.Builder(context,notification_id_channel);
        nBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo2)
                .setContentTitle(title)
                .setContentText(body)

                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .setContentInfo("INFO")
        ;



        notificationManager.notify(RANDOM_N_ID,nBuilder.build());
    }

    public void showChatNotification(Context context, String title, String body)
    {

        Intent resultIntent = new Intent(context, InternalChatActivity.class);
        resultIntent.putExtra("notify_title", title);
        resultIntent.putExtra("notify_msg", body);
        InternalChatActivity.NotificationMessage=body;
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);



       /* Intent notifyIntent = new Intent(context, InternalChatActivity.class);
        notifyIntent.putExtra("notify_title", title);
        notifyIntent.putExtra("notify_msg", body);*/

// Set the Activity to start in a new, empty task
        /*notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
// Create the PendingIntent
        /*PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context, 1, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, notification_id_channel);
        builder.setContentIntent(notifyPendingIntent);
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(body)

                .setAutoCancel(true)
                .setContentInfo("INFO")
        ;*/
        /*NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(RANDOM_N_ID, builder.build());
*/
        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(context, notification_id_channel);
        builder2.setContentIntent(resultPendingIntent);
        builder2.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo2)
                .setContentTitle(title)
                .setContentText(body)

                .setAutoCancel(true)
                .setContentInfo("INFO")
        ;
        NotificationManagerCompat notificationManager2 = NotificationManagerCompat.from(context);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel=new NotificationChannel(notification_id_channel,
                    "Notificatio", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Firebase channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.WHITE);
            ((NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(notificationChannel);
        }
        notificationManager2.notify(RANDOM_N_ID, builder2.build());
    }

    public static void showBookingDetailsNotification(Context context, String title, String body, JSONArray pairs, String code)
    {

        Intent resultIntent = new Intent(context, BeautyMainPage.class);
        resultIntent.putExtra("notify_title", title);
        resultIntent.putExtra("notify_code", code);
        String book_id="";
        for (int i=0;i<pairs.length();i++)
        {
            Log.e("Notif","i :"+i);
            try{
                JSONObject object = pairs.getJSONObject(i);
                book_id = object.getString("book_id");
                break;
            }
            catch (Exception e)
            {
                Log.e("NotifErr",i+" : "+e.getMessage());

            }
        }

        resultIntent.putExtra("book_id", book_id);
        resultIntent.putExtra("notify_pairs", pairs.toString());
        Log.e("Notif", "showBookingDetailsNotification is triggered");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(context, notification_id_channel);
        builder2.setContentIntent(resultPendingIntent);
        builder2.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo2)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentInfo("INFO")
        ;
        NotificationManagerCompat notificationManager2 = NotificationManagerCompat.from(context);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel=new NotificationChannel(notification_id_channel,
                    "Notificatio", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Firebase channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.WHITE);
            ((NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(notificationChannel);
        }
        notificationManager2.notify(RANDOM_N_ID, builder2.build());
    }

    public static void showOfferDetailsNotification(Context context,  String title, String body, JSONArray pairs, String code,Boolean isNotif)
    {
        String bdb_offer_type="";
        for (int i=0;i<pairs.length();i++)
        {
            Log.e("Notif","i :"+i);
            try{
                JSONObject object = pairs.getJSONObject(i);
                bdb_offer_type = object.getString("bdb_offer_type");
                break;
            }
            catch (Exception e)
            {
                Log.e("NotifErr",i+" : "+e.getMessage());

            }
        }
        String packCode="";
        for (int i=0;i<pairs.length();i++)
        {
            Log.e("Notif","i :"+i);
            try{
                JSONObject object = pairs.getJSONObject(i);
                packCode = object.getString("bdb_pack_code");
                break;
            }
            catch (Exception e)
            {
                Log.e("NotifErr",i+" : "+e.getMessage());

            }
        }



       /* if (bdb_offer_type.equals("2")
                || bdb_offer_type.equals("5")){*/
            APICall.browseOneMultiOfferNotification(packCode,context,title,body,pairs,code,isNotif);

        /*}else if (bdb_offer_type.equals("1")
                || bdb_offer_type.equals("4")){

        }else if (bdb_offer_type.equals("3")
                || bdb_offer_type.equals("6")){


        }*/
    }


    @SuppressLint("NewApi")
    public Intent launchIntent(Context ctx) {
        final ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        Intent intent = new Intent();
        boolean activated = false;
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            List<ActivityManager.AppTask> tasks = am.getAppTasks();
            for (ActivityManager.AppTask task: tasks){
                intent = task.getTaskInfo().baseIntent;
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activated = true;
                break;
            }
        } else {
            @SuppressWarnings("deprecation")
            final List<ActivityManager.RecentTaskInfo> recentTaskInfos = am.getRecentTasks(1024,0);
            String myPkgNm = ctx.getPackageName();
            if (!recentTaskInfos.isEmpty()) {
                ActivityManager.RecentTaskInfo recentTaskInfo;
                final int size = recentTaskInfos.size();
                for (int i = 0; i < size; i++) {
                    recentTaskInfo = recentTaskInfos.get(i);
                    if (recentTaskInfo.baseIntent.getComponent().getPackageName().equals(myPkgNm))
                    {
                        intent = recentTaskInfo.baseIntent;
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activated = true;
                    }
                }
            }
        }
        if (!activated) {
            intent = new Intent(ctx,((AppCompatActivity)ctx).getApplication().getClass());
        }
        return intent;
    }

    public void AnalizeNotificationCode(Context context, String title, String body, String pairs)
    {
        String notificationTitle,notificationBody,notificationPairs="",code="";
        JSONArray j=new JSONArray();
        try
        {
            Log.e("Notif", "CodeAnalize Page is trying to get pairs");
            j=new JSONArray(pairs);
            code = j.getJSONObject(0).getString("code");
        }
        catch (Exception e)
        {
            Log.e("NotifErr",e.getMessage());
        }

        Log.e("NotifCode",code);
        Log.e("Notif", " pairs :"+notificationPairs);

        if(code.equals("24")||code.equals("26")||code.equals("27")||code.equals("21")||code.equals("38")
                ||code.equals("45")||code.equals("46")||code.equals("49")||code.equals("50")||code.equals("40")
                ||code.equals("2")||code.equals("3")||code.equals("15")||code.equals("19")
                ||code.equals("20")||code.equals("22")||code.equals("25")||code.equals("32")||code.equals("52")
        )
        {
            showBookingDetailsNotification(context,title,body,j,code);
        }
        else if(code.equals("16")||code.equals("18"))
        {
            showOfferDetailsNotification(context,title,body,j,code,true);
        }


    }

    public void showURLNotification(Context context, String title, String body, String pairs)
    {
        String URLstr="";
        JSONArray j=new JSONArray();
        try
        {
            Log.e("Notif", "CodeAnalize Page is trying to get pairs");
            j=new JSONArray(pairs);
            for (int i=0;i<j.length();i++)
            {
                Log.e("Notif","i :"+i);
                try{
                    JSONObject object = j.getJSONObject(i);
                    URLstr = object.getString("android_url");
                    break;
                }
                catch (Exception e)
                {
                    Log.e("NotifErr",i+" : "+e.getMessage());

                }
            }
        }
        catch (Exception e)
        {
            Log.e("NotifErr",e.getMessage());
        }
        // Uri uri = Uri.parse("market://details?id=" + "com.ubnt.umobile");
        Uri uri = Uri.parse(URLstr);
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
       /* Intent notifyIntent = new Intent(context, InternalChatActivity.class);
        notifyIntent.putExtra("notify_title", title);
        notifyIntent.putExtra("notify_msg", body);*/

// Set the Activity to start in a new, empty task
       /* notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
// Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context, 1, myAppLinkToMarket, PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, notification_id_channel);
        builder.setContentIntent(notifyPendingIntent);
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo2)
                .setContentTitle(title)
                .setContentText(body)

                .setAutoCancel(true)
                .setContentInfo("INFO")
        ;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(RANDOM_N_ID, builder.build());
    }

}
