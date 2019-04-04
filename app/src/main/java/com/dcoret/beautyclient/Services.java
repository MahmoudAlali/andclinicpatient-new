package com.dcoret.beautyclient;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;

public class Services extends AppCompatActivity {
    static Context context;
    RecyclerView recyclerView;
    String[] items={"Service 1","Service 2","Service 3","Service 4","Service 5","Service 6"};
    String[] prices={"100","500","450","123","345","411"};
    String[] rank={"4.1","3.2","3.5","4.7","4.4","3.0"};
    String[] city={"الرياض","الدمام","مكة","الرياض","جدة","الدمام","مكة","مكة","الطائف","مكة"};
    Location_Beauty[] locations={
            new Location_Beauty(32.7792842,35.8816735),
            new Location_Beauty(31.964383, 35.918756),
            new Location_Beauty(32.709566, 36.137142),
            new Location_Beauty(32.777491, 35.935935),
            new Location_Beauty(32.755262, 35.986746),
            new Location_Beauty(32.725373, 35.944346),
            new Location_Beauty(32.688479, 35.992233),
            new Location_Beauty(32.670663, 36.052908),
            new Location_Beauty(33.506590, 36.299474),
            new Location_Beauty(33.546086, 36.200597),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_example);
        setTitle("الخدمات");
        context=this;
        recyclerView=findViewById(R.id.service_re_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ServicesAdapter(getApplicationContext(),items,prices,rank,city,locations));
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar_menu3, menu);
        return true;
    }
}
