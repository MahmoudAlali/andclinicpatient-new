package com.dcoret.beautyclient.API;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dcoret.beautyclient.DataModel.DataReservation;
import com.dcoret.beautyclient.Fragments.ReservationFragment;
import com.dcoret.beautyclient.R;
import com.dcoret.beautyclient.Service.PushNotifications;

import java.util.ArrayList;

public class ReservationDialog {
    public static String context_dialog;
    static String date;
    static Dialog dialog1, dialog;
    static TextView canceltime, canceldate, okdate, oktime;

    public static String getContext_dialog() {
        return context_dialog;
    }

    public static void setContext_dialog(String context_dialog) {
        ReservationDialog.context_dialog = context_dialog;
    }

    public ReservationDialog(String context_dialog) {
        this.context_dialog=context_dialog;
    }



  public   static void dateDialog(final Context context, final String name,final String type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_calender);
        final DatePicker datePicker = dialog.findViewById(R.id.date);
        okdate = dialog.findViewById(R.id.ok_date);
        canceldate = dialog.findViewById(R.id.cancel_date);
        dialog.show();
        canceldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        okdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int month=datePicker.getMonth()+1;

                date = datePicker.getDayOfMonth() + "/" +month + " - ";
                dialog.cancel();
                timeDialog(context,name,type);
            }
        });

    }
       static ArrayList<String> times=new ArrayList<>();
    static void timeDialog(final Context context, final String name,final  String type) {
        dialog1 = new Dialog(context);
        dialog1.setContentView(R.layout.dialog_calender_time);
        final TimePicker timePicker = dialog1.findViewById(R.id.time);
        oktime = dialog1.findViewById(R.id.ok_time);
        canceltime = dialog1.findViewById(R.id.cancel_time);
        dialog1.show();
        oktime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                date = date + timePicker.getHour() + ":" + timePicker.getMinute() + "";
                dialog1.cancel();
//                   try{
                Toast.makeText(context.getApplicationContext(),
//                               ((ServicesAdapter1.Item) holder).textView.getText().toString()+"   "+
                        date, Toast.LENGTH_LONG).show();


                        times.add(date);
//                       ShoppingCartFragment.dataServices.add(new DataService(0
//                               ,items[position]
//                               ,Double.parseDouble(price[position])
//                               ,Double.parseDouble(rank[position])
//                               ,false
//                               ,false
//                       ));
//       }




                if(type.equals("s")) {
                   //----------- add the reserve to reservations and shopping cart --------
                    ReservationFragment.reservations.add(new DataReservation(name,0,0,false,23,times));
                   //----------- send notification to provider ----------------------------
                    new PushNotifications().sendnotification_provider(context, "Services", "تم حجز الخدمة " + name + "بتاريخ: " + date, "accept", "cancel");

                }else if(type.equals("o")){
                    //----------- send notification to provider ----------------------------
                    new PushNotifications().sendnotification_provider(context, "Offers", "تم حجز العرض " + name + "بتاريخ: " + date, "accept", "cancel");
                }else if (type.equals("os")){

                }
            }
        });
    }

    static TextView service_name;
//   public static void multiReservationDialog(final Context context, final DataOffer dataOffer){
//        Log.d("dataoffersize",dataOffer.getServices().length+"");
//        final ArrayList<Dialog> dialogs=new ArrayList<>();
//        for (int i=0;i<dataOffer.getServices().length;i++){
//
//                dialogs.add(new Dialog(context));
//                Log.d("offers_name", i + dataOffer.getServices()[i].getName() + "");
//
//            }
//        int i;
//        for( i=0;i<dialogs.size();i++) {
//            Log.d("iii", i + "");
//            dialogs.get(i).setContentView(R.layout.dialog_calender);
//             final DatePicker datePicker = dialogs.get(i).findViewById(R.id.date);
//            okdate = dialogs.get(i).findViewById(R.id.ok_date);
//            canceldate = dialogs.get(i).findViewById(R.id.cancel_date);
//            service_name = dialogs.get(i).findViewById(R.id.service_name);
//            service_name.setText(dataOffer.getServices()[i].getName());
//            dialogs.get(i).show();
////            final int finalI = i;
//            final int finalI = i;
//            canceldate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialogs.get(finalI).cancel();
//                }
//            });
//            final int finalI1 = i;
//            okdate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int month=datePicker.getMonth()+1;
//                    date = datePicker.getDayOfMonth() + "/" +month + " - ";
//                    dialogs.get(finalI1).cancel();
////                timeDialog(context,name,type);
//                    dialog1.show();
//                }
//            });
////       dialog.show();
//            dialog1 = new Dialog(context);
//            dialog1.setContentView(R.layout.dialog_calender_time);
//            final TimePicker timePicker = dialog1.findViewById(R.id.time);
//            oktime = dialog1.findViewById(R.id.ok_time);
//            canceltime = dialog1.findViewById(R.id.cancel_time);
//            canceltime.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog1.dismiss();
//                }
//            });
//            oktime.setOnClickListener(new View.OnClickListener() {
//                @RequiresApi(api = Build.VERSION_CODES.M)
//                @Override
//                public void onClick(View v) {
////               int month=timePicker.getMonth()+1;
//                    date = date + timePicker.getHour() + ":" + timePicker.getMinute() + "";
//                    times.add(date);
//                    date="";
//
////               date = date + timePicker.getHour() + ":" + timePicker.getMinute() + "";
//                    dialog1.cancel();
//                }
//            });
//        }
//
//
//   dialogs.get(0).setOnDismissListener(new DialogInterface.OnDismissListener() {
//         @Override
//            public void onDismiss(DialogInterface dialog) {
//             //----------- send notification to provider ----------------------------
//             new PushNotifications().sendnotification_provider(context, "Offers", "تم حجز العرض " + dataOffer.getName() + "بتاريخ: " + date, "accept", "cancel");
//            }
//        });
//
//   }
}

