package com.dcoret.beautyclient.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.DataModel.NotificationModel;
import com.dcoret.beautyclient.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class NotificationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    Context context;
    String items[];
    ArrayList<NotificationModel> notificationModel;
    Boolean layout;
    public NotificationsAdapter(Context context, String items[]){
        this.context=context;
        this.items=items;
    }
    public NotificationsAdapter(Context context, ArrayList<NotificationModel> notificationModel){
        this.context=context;
        this.notificationModel=notificationModel;
    }
    public NotificationsAdapter(Context context,String items[],Boolean layout){
        this.context=context;
        this.items=items;
        this.layout=layout;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.layout_notification,parent,false);
        Item item=new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,final int position) {
        ((Item) holder).notificationTitle.setText(notificationModel.get(position).getNotificationTitle());
        ((Item) holder).notificationBody.setText(notificationModel.get(position).getNotificationBody());
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"); // or "YYYY-MM-DDThh:mm:ss±0000"
        String dateInString = notificationModel.get(position).getNotificationDate();
        inputFormat.setTimeZone(TimeZone.getTimeZone("GMT") );
        Date date=null;
        try
        {
            date = inputFormat.parse(dateInString);
        }
        catch (Exception e)
        {
        }

        SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        ((Item) holder).notificationDate.setText(day.format(date)+ "  " + time.format(date));

        ((Item) holder).notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(view.getContext().getResources().getColor(R.color.white));
                APICall.SetNotificationRead(context,notificationModel.get(position).getNotificationID());
                String type = notificationModel.get(position).getNotificationType();
                if(type.equals("4"))
                {
                    String url= notificationModel.get(position).getFromPairs("android_url");
                    Uri uri = Uri.parse(url);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(i);
                }
                else if(type.equals("2"))
                {
                    Intent i = new Intent(BeautyMainPage.context,BeautyMainPage.class);
                    i.putExtra("notify_pairs",notificationModel.get(position).getPairsStr());
                    BeautyMainPage.context.startActivity(i);

                }
                view.setClickable(false);
            }
        });
        ((Item) holder).actionOne.setText(notificationModel.get(position).getActionOne());
        ((Item) holder).actionTwo.setText(notificationModel.get(position).getActionTwo());
    }

    @Override
    public int getItemCount() {
        return notificationModel.size();
    }

    public class Item extends RecyclerView.ViewHolder {

        public TextView notificationTitle,notificationBody,actionOne,actionTwo,notificationDate;
        LinearLayout notification;
        public Item(View itemView){
            super(itemView);
            notificationTitle = itemView.findViewById(R.id.notif_title);
            notificationBody = itemView.findViewById(R.id.notif_body);
            notificationDate = itemView.findViewById(R.id.notif_date);
            notification = itemView.findViewById(R.id.notification);
            actionOne = itemView.findViewById(R.id.action1);
            actionTwo = itemView.findViewById(R.id.action2);

        }
    }

}
