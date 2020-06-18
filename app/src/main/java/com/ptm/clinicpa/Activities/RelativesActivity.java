package com.ptm.clinicpa.Activities;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Adapters.HealthCentersAdapter;
import com.ptm.clinicpa.Adapters.RelativesAdapter;
import com.ptm.clinicpa.R;

public class RelativesActivity extends AppCompatActivity {

    public static RecyclerView recyclerView;
    public static SwipeRefreshLayout pullToRefresh;
    public static RelativesAdapter relativesAdapter;
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatives);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        context=this;
        APICall.getAllFollowers( context);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                relativesAdapter.notifyDataSetChanged();
                //---------------------call API for Services and get items-------------
                APICall.getAllFollowers( context);
            }
        });




        recyclerView=findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        relativesAdapter=new RelativesAdapter(BeautyMainPage.context,  APICall.allRelativesList,false);
        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(relativesAdapter);

    }
}
