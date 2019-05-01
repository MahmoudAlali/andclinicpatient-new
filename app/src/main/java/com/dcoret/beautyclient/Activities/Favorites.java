package com.dcoret.beautyclient.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dcoret.beautyclient.Adapters.FavouriteAdapter;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {

          RecyclerView recyclerView;
         public static ArrayList<DataService> dataServices=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
     Toolbar toolbar = findViewById(R.id.toolbar_fav);
        setSupportActionBar(toolbar);

           recyclerView=findViewById(R.id.recycleview);
           recyclerView.setLayoutManager(new LinearLayoutManager(MyReservations.context));
           recyclerView.setAdapter(new FavouriteAdapter(this,dataServices));

//           toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   onBackPressed();
//               }
//           });


       }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar_menu3, menu);
        return true;
    }




    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }else if(id==R.id.shoppingcart){
            Intent intent=new Intent(this,ShoppingCart.class);
            startActivity(intent);

        }else if(id==R.id.notify){
            Intent intent=new Intent(this,Notification.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
