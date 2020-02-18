package com.dcoret.beautyclient.Fragments.Notifications;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.NotificationsAdapter;
import com.dcoret.beautyclient.R;

public class NotificationsFragment extends Fragment {
    RecyclerView recyclerView;
    NotificationsAdapter notificationsAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_notifications_main, container, false);
        BeautyMainPage.FRAGMENT_NAME="NotificationsFragment";

        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));
        notificationsAdapter=new NotificationsAdapter(BeautyMainPage.context, APICall.notificationModelArrayList);
        recyclerView.setAdapter(notificationsAdapter);

        notificationsAdapter.notifyDataSetChanged();
        APICall.GetUnreadNotifications(BeautyMainPage.context,notificationsAdapter);
        //----------- back btn process------
        Toolbar toolbar=view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity)BeautyMainPage.context).onBackPressed();
            }
        });

        return view;
    }
}
