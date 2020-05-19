package com.ptm.clinicpa.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class PackegeRemovedBR extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.e("Removed","Package Removed");
        Toast.makeText(context,"The App removed,thanks for using beauty app",Toast.LENGTH_LONG).show();
    }
}
