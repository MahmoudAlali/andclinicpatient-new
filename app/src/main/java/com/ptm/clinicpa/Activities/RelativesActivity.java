package com.ptm.clinicpa.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Adapters.HealthCentersAdapter;
import com.ptm.clinicpa.Adapters.RelativesAdapter;
import com.ptm.clinicpa.R;

public class RelativesActivity extends AppCompatActivity {

    public static RecyclerView recyclerView;
    public static SwipeRefreshLayout pullToRefresh;
    public static RelativesAdapter relativesAdapter;
    public static Context context;
    ImageView add;
    boolean isBooking,isOffer;
    public static String sup_id,max_age,min_age,supported_gender;
    public static String center_id;
    public static String pack_code,longNum,latNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatives);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        add = findViewById(R.id.add);
        context=this;

        isBooking=getIntent().getBooleanExtra("isBooking",false);
        isOffer=getIntent().getBooleanExtra("is_offer",false);
        if(isBooking)
        {
            sup_id=getIntent().getStringExtra("sup_id");
            max_age=getIntent().getStringExtra("max_age");
            min_age=getIntent().getStringExtra("min_age");
            supported_gender=getIntent().getStringExtra("supported_gender");
            center_id=getIntent().getStringExtra("center_id");
            int max=Integer.parseInt(max_age);
            int min =Integer.parseInt(min_age);
            APICall.getFollowersForBooking(context,max,min,supported_gender);
            if(isOffer)
            {
                pack_code=getIntent().getStringExtra("pack_code");
                longNum=getIntent().getStringExtra("longNum");
                latNum=getIntent().getStringExtra("latNum");
            }
        }
        else
        {
            APICall.getAllFollowers( context);
        }


        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                relativesAdapter.notifyDataSetChanged();
                //---------------------call API for Services and get items-------------
                if(isBooking)
                {
                    sup_id=getIntent().getStringExtra("sup_id");
                    max_age=getIntent().getStringExtra("max_age");
                    min_age=getIntent().getStringExtra("min_age");
                    supported_gender=getIntent().getStringExtra("supported_gender");
                    int max=Integer.parseInt(max_age);
                    int min =Integer.parseInt(min_age);
                    APICall.getFollowersForBooking(context,max,min,supported_gender);
                }
                else
                {
                    APICall.getAllFollowers( context);
                }
            }
        });




        recyclerView=findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        relativesAdapter=new RelativesAdapter(BeautyMainPage.context,  APICall.allRelativesList,isBooking,isOffer);
        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(relativesAdapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,AddRelativeActivity.class);
                i.putExtra("isEditRelative",false);
                startActivity(i);
            }
        });

    }
}
