package com.ptm.clinicpa.Fragments.Notifications;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Adapters.NotificationsAdapter;
import com.ptm.clinicpa.R;

public class NotificationsFragment extends Fragment {
    RecyclerView recyclerView;
    public static NotificationsAdapter notificationsAdapter;
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
            public void onClick(View v) {
                // If the navigation drawer is not open then open it, if its already open then close it.
                if(!BeautyMainPage.mDrawerLayout.isDrawerOpen(GravityCompat.START)) BeautyMainPage.mDrawerLayout.openDrawer(Gravity.START);
                else BeautyMainPage.mDrawerLayout.closeDrawer(Gravity.END);
            }
        });

        return view;
    }
}
