package com.dcoret.beautyclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ShoppingCart extends AppCompatActivity {
    RecyclerView recyclerView;
    String item[]={"service1","offer3","service20","offer5","offer16"};
    String[] price={"500","100","350","150","790"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        recyclerView = findViewById(R.id.shop_cart_re_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new ShopCartAdapter(getApplicationContext(), item,price));

    }
}
