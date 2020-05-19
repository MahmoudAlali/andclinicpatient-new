package com.ptm.clinicpa.Service;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class CancelNotifyBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d("BroadCast","Canceled");
        int NOTIFICATION_ID=intent.getIntExtra("N_ID",0);
        String data=intent.getStringExtra("accept");
        if(data.equals("cancel")){
            Toast.makeText(context,"تم الغاء حجز العرض",Toast.LENGTH_LONG).show();
//            PushNotifications.sendnotification_client(context,token_client
//                    ,"Beauty","تم الغاء الحجز من قبل المزودة","","");
        }else if(data.equals("accept")) {
            Toast.makeText(context,"تم تأكيد الحجز",Toast.LENGTH_LONG).show();
//            PushNotifications.sendnotification_client(context,token_client,"Beauty","تم قبول الحجز من قبل المزودة يرجى دفع العربون","","");
            Log.d("N_ID",NOTIFICATION_ID+"");
        }
        NotificationManager notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }
}
