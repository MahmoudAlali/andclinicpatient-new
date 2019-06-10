package com.dcoret.beautyclient.Activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Adapters.OffersAdapterNew;
import com.dcoret.beautyclient.Adapters.ServicesAdapterNew;
import com.dcoret.beautyclient.Fragments.AccountFragment;
import com.dcoret.beautyclient.Fragments.BagReservationFragment;
import com.dcoret.beautyclient.Fragments.FavoriteFragment;
import com.dcoret.beautyclient.Fragments.MapFragment;
import com.dcoret.beautyclient.Fragments.NotificationFragment;
import com.dcoret.beautyclient.Fragments.OfferFragment;
import com.dcoret.beautyclient.Fragments.ReservationFragment;
import com.dcoret.beautyclient.Fragments.ServiceFragment;
import com.dcoret.beautyclient.Fragments.ServicesTabsFragment2;
import com.dcoret.beautyclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class BeautyMainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    public static String FRAGMENT_NAME="";
    LinearLayout layout;
    View view;
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mToggle;
    public static Context context;
    BottomNavigationView navigation;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_tabs_layout);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        context=this;
        navigation=findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.services);

        fragment = new ServiceFragment();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        mDrawerLayout=findViewById(R.id.drawer);
        mToggle=new ActionBarDrawerToggle(BeautyMainPage.this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//        navigationView.setForegroundGravity(Gravity.NO_GRAVITY);
//        }
//    });
    }

    /*
    * ACCOUNTFRAGMENT
    * MAPFRAGMENT
    *
    * SERVICETABFRAGMENT
    * COMPAREFRAGMENT
    * */

    int doback=0,doback2=0;
    @Override
    public void onBackPressed() {
            if (FRAGMENT_NAME.equals("MAPFRAGMENT")) {
            Log.d("doback",2+"");
            doback=0;
            fragment = new AccountFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();
                FRAGMENT_NAME="";
            }else if (FRAGMENT_NAME.equals("ACCOUNTFRAGMENT")){
            Log.d("doback",1+"");
            FRAGMENT_NAME="";
            fragment = new ServiceFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();
                FRAGMENT_NAME="";
        }else if (FRAGMENT_NAME.equals("SERVICETABFRAGMENT")){
                fragment = new ServiceFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
                FRAGMENT_NAME="";
            }else if (FRAGMENT_NAME.equals("COMPAREFRAGMENT")){
                fragment = new ServicesTabsFragment2();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();

            }else if (FRAGMENT_NAME.equals("INVOICEFRAGMENT")){

                fragment = new ReservationFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();

            }else  {
        if(navigation.getSelectedItemId()!=R.id.services){
            navigation.setSelectedItemId(R.id.services);
            fragment = new ServiceFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();
        }else {
            AlertDialog.Builder builder=  new AlertDialog.Builder(context);
            builder.setTitle("خروج")
                    .setMessage("هل تريد الخروج من التطبيق؟")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            BeautyMainPage.super.onBackPressed();
                        }
                    }).setNegativeButton(android.R.string.no,null);

            builder.show();
        }
            Log.d("doback","NO");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
    }
//      public static   Menu menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.bar_menu2, menu);
//        this.menu=menu;
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            PopupMenu popup = new PopupMenu(this,toolbar );
//            popup.getMenuInflater().inflate(R.menu.bar_menu3, popup.getMenu());
//
//            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                public boolean onMenuItemClick(MenuItem item) {
//                    Toast.makeText(getApplicationContext(), "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//            });
//            popup.show();//showing popup menu
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.manageaccount) {
//            toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
            fragment = new AccountFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();



        }else if (id == R.id.compare) {

            Intent intent=new Intent(getApplicationContext(),Compartion.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

         } else if (id == R.id.help) {
            Intent intent=new Intent(getApplicationContext(),Help.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String sharebody="تطبيق beauty لخدمات التجميل و التصميم حمله الآن من App Store";
            String sharesub="Beauty";
            intent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
            intent.putExtra(Intent.EXTRA_TEXT,sharebody);
            startActivity(Intent.createChooser(intent,"Share using"));

        } else if (id == R.id.favorites) {
            Intent intent=new Intent(getApplicationContext(),Favorites.class);
            startActivity(intent);

        } else if (id == R.id.rate_app) {
            final Dialog dialog=new Dialog(this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.rating_dialog);
            final RatingBar ratingBar=dialog.findViewById(R.id.ratingBar);

            TextView ok=dialog.findViewById(R.id.ok);
            ok.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
//                    sendrating((int)ratingBar.getRating());
                    dialog.cancel();
//                    serviceBrowse(context,"1");
//                    APICall.okHttpServiceBrowse(context,"1");

//                    APICall.rateApp(ratingBar.getRating()+"","http://clientapp.dcoret.com/api/rating/rateApp",BeautyMainPage.context);

//                    test
//            APICall.deleteAccount("http://clientapp.dcoret.com/api/auth/user/deleteAccount",BeautyMainPage.context);
            // http://clientapp.dcoret.com/api/auth/user/deleteAccount
//                    APICall.detailsUser("http://clientapp.dcoret.com/api/auth/user/detailsUser",BeautyMainPage.context);
//                    APICall.addAddress("http://clientapp.dcoret.com/api/auth/user/addAddress","7","43.89598895","35.99999","home",BeautyMainPage.context);
//                    APICall.updateaddress("http://clientapp.dcoret.com/api/auth/user/updateAddress","7","43.89598895","35.99999","home",BeautyMainPage.context);
                    APICall.unfav("http://clientapp.dcoret.com/api/auth/user/addFav","2","10",BeautyMainPage.context);
//                    APICall.unfav("http://clientapp.dcoret.com/api/auth/user/unFav","2","10",BeautyMainPage.context);
                APICall.getcities("http://clientapp.dcoret.com/api/auth/user/getCities",BeautyMainPage.context);

                    try {
                        postRequest();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//            new BrowseService().execute("1");
//           APICall.getData(context,"http://clientapp.dcoret.com/api/service/browseService",null,"1");
                }
            });


            dialog.show();
        }else if (id == R.id.signout) {


            new AlertDialog.Builder(context)
                    .setTitle("Logout")
                    .setMessage("هل تريد تسجيل الخروج ؟")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            APICall.logout("http://clientapp.dcoret.com/api/auth/user/logout",BeautyMainPage.context);



                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    ProgressDialog pd;
    String data;
    private void sendrating(final int rating) {
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                String URL = "http://clientapp.dcoret.com/api/rating/rateApp";

                JSONObject jsonBody = new JSONObject();

                jsonBody.put("bdb_rate",rating);
//                jsonBody.put("password", pass);
                pd= new ProgressDialog(BeautyMainPage.this);
                pd.setMessage("جار التحقق");
                pd.show();
                JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Token",response.toString());
                        data=response.toString();
                        pd.dismiss();
                        Toast.makeText(BeautyMainPage.context,"شكراً لك, تقييمك لنا يسهم في تطورنا.",Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error :",error.toString());
                        data=error.toString();
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Response:  " + data, Toast.LENGTH_SHORT).show();

//                    onBackPressed();

                    }
                }) {

                    @Override
                    public String getBodyContentType() {

                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        final Map<String, String> headers = new HashMap<>();
                        String accesstoken="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjQyZmZlN2Q5NmYzNzM4OWRhZDNjMDllMDk5NGQwZDY4ODdmNGI0YzMxMzlmYjZhOTE1MTc5NWIwMzVmYTE5MDBhODI0NzI3NThmYTM2MDFhIn0.eyJhdWQiOiIxIiwianRpIjoiNDJmZmU3ZDk2ZjM3Mzg5ZGFkM2MwOWUwOTk0ZDBkNjg4N2Y0YjRjMzEzOWZiNmE5MTUxNzk1YjAzNWZhMTkwMGE4MjQ3Mjc1OGZhMzYwMWEiLCJpYXQiOjE1NTgyOTY3ODEsIm5iZiI6MTU1ODI5Njc4MSwiZXhwIjoxNTg5OTE5MTgxLCJzdWIiOiIyIiwic2NvcGVzIjpbXX0.MG04uKIgEjPHwU_bo3ai_f1oGhOJdk0DQr5_KFQYKAZOj5EUM7ZVi08JnefgSB9R9rPAE3VcZaIBCGJ2OrjI0zO3K7PQZEQ2m-gmdsMye4sMGL2LeRrm6aHOKpJDY_jdpJMgFgbOL3oFh9XbVxbha0f0ofhOhiSl4oIfZ8-G7VzWZPuwA1dIt1QfgqjfWBSpWd9JPe7s3PrwrcUcXIF8qObjl6MqWQ3I33I8g2-Vc9O7b356_21Un_XP6lYbZMN4VWKUifqYiO2t509M6RjUovDI_cd9a30EHB7hTdIkmxHP2JKudwHbHil7cEkel7UQAvfJrbcapm60Jb8fucWoAtedtuPpxYEgxAZ0HqZNi5ynPoO1VIygHOiYvI8iNzNwkRMJ5quV4PIK4SaGArZs6Nd5Pz9vXKc-apWo2WzDZ9R1KQg0y3LNNRyMPmGjVN_8u3QixbomiXuPoOAsKuZzzCsRZMdQ2sug0nlm69BiCSbq3Zn40gmIqTXAhG1AIcm2WqgCqi9SKWyWOBc8Tv2NnnccH_FCkUCPCa54ZRMsMGrkycG6oV1wYQkpBKF1lS0yx2NCX0RJGFLEATkMKRX1wdKOgjmyALtk5IBsN9KOr6rBl4sWEQb0zsVgzaTdHqex4j0a03jtsbq8RplKJeY5SbJOUv-o8EC6gjMbJzCa5ik";

                        headers.put("Authorization", "Bearer " + accesstoken);
//                        headers.put("Authorization",
                        headers.put("Content-Type", "application/json");
//                        headers.put("X-Requested-With", "XMLHttpRequest");
                        return headers;
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("bdb_rate", rating+"");
//                        params.put("password", pass);
                        return params;
                    }


                };
                Log.d("Request", jsonOblect.getBody().toString());
                Log.d("Request", jsonOblect.getUrl());
                Log.d("Request", jsonOblect.getBodyContentType());
                requestQueue.add(jsonOblect);

            } catch (Exception e) {
                e.printStackTrace();
                pd.dismiss();

            }
            // Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();





    }




    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.services:
                            fragment = new ServiceFragment();
                            fm = getFragmentManager();
                            fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment, fragment);
                            fragmentTransaction.addToBackStack("mainpage");
                            fragmentTransaction.detach(fragment);
                            fragmentTransaction.attach(fragment);
                            fragmentTransaction.commit();
                            return true;
                        case R.id.reservations:
                            FRAGMENT_NAME="";
                            fragment = new ReservationFragment();
                            fm = getFragmentManager();
                            fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment, fragment);
                            fragmentTransaction.commit();

                            return true;
                        case R.id.service_bag:
                            FRAGMENT_NAME="";
                            fragment = new BagReservationFragment();
                            fm = getFragmentManager();
                            fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment, fragment);
                            fragmentTransaction.commit();
                            return true;
                        case R.id.favorites:
                            FRAGMENT_NAME="";
                           fragment = new FavoriteFragment();
                            fm = getFragmentManager();
                            fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment, fragment);
                            fragmentTransaction.commit();
                            return true;
                        case R.id.notification:
                            FRAGMENT_NAME="";
                            fragment = new NotificationFragment();
                            fm = getFragmentManager();
                            fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment, fragment);
                            fragmentTransaction.commit();
                            return true;
                    }

                    return false;
                }
            };
    protected OnBackPressedListener onBackPressedListener;
    public static int dobacknumber;
    public interface OnBackPressedListener {
        int doBack();
    }
    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }



    protected OnBackPressedListener2 onBackPressedListener2;
    public interface OnBackPressedListener2 {
        int doBack();
    }
    public void setOnBackPressedListener2(OnBackPressedListener2 onBackPressedListener) {
        this.onBackPressedListener2 = onBackPressedListener2;
    }



    @Override
    protected void onDestroy() {
        onBackPressedListener = null;
        super.onDestroy();
    }
JSONObject object;
    public  JSONObject serviceBrowse(final Context context, final String bdb_ser_id){
        SharedPreferences sharedPreferences=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE);
        final String  token=sharedPreferences.getString("token",null);
        Log.d("TOKEN",token);
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            String URL = "http://clientapp.dcoret.com/api/service/browseService";

            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Token", response.toString());
                    Toast.makeText(context, "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("BrowseService",response.toString());

                    try {
                        object=new JSONObject(response.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


//                    pd.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error :", error.toString() + "\n" + error.getMessage()
                    );
//                    onBackPressed();

//                    pd.dismiss();

                }
            }) {


                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer "+token);
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("bdb_ser_id", bdb_ser_id);
                    return params;
                }

            };
            Log.d("Request", jsonOblect.getUrl());
            Log.d("Request", jsonOblect.getBodyContentType());
            Log.d("Request", jsonOblect.getHeaders().toString());
            requestQueue.add(jsonOblect);

            return object;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
        String mMessage="";
    public String postRequest() throws IOException {
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = "http://clientapp.dcoret.com/api/service/browseService";

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();
        try {
            postdata.put("bdb_ser_id", "1");
//            postdata.put("password", "12345");
        } catch(JSONException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjQyZmZlN2Q5NmYzNzM4OWRhZDNjMDllMDk5NGQwZDY4ODdmNGI0YzMxMzlmYjZhOTE1MTc5NWIwMzVmYTE5MDBhODI0NzI3NThmYTM2MDFhIn0.eyJhdWQiOiIxIiwianRpIjoiNDJmZmU3ZDk2ZjM3Mzg5ZGFkM2MwOWUwOTk0ZDBkNjg4N2Y0YjRjMzEzOWZiNmE5MTUxNzk1YjAzNWZhMTkwMGE4MjQ3Mjc1OGZhMzYwMWEiLCJpYXQiOjE1NTgyOTY3ODEsIm5iZiI6MTU1ODI5Njc4MSwiZXhwIjoxNTg5OTE5MTgxLCJzdWIiOiIyIiwic2NvcGVzIjpbXX0.MG04uKIgEjPHwU_bo3ai_f1oGhOJdk0DQr5_KFQYKAZOj5EUM7ZVi08JnefgSB9R9rPAE3VcZaIBCGJ2OrjI0zO3K7PQZEQ2m-gmdsMye4sMGL2LeRrm6aHOKpJDY_jdpJMgFgbOL3oFh9XbVxbha0f0ofhOhiSl4oIfZ8-G7VzWZPuwA1dIt1QfgqjfWBSpWd9JPe7s3PrwrcUcXIF8qObjl6MqWQ3I33I8g2-Vc9O7b356_21Un_XP6lYbZMN4VWKUifqYiO2t509M6RjUovDI_cd9a30EHB7hTdIkmxHP2JKudwHbHil7cEkel7UQAvfJrbcapm60Jb8fucWoAtedtuPpxYEgxAZ0HqZNi5ynPoO1VIygHOiYvI8iNzNwkRMJ5quV4PIK4SaGArZs6Nd5Pz9vXKc-apWo2WzDZ9R1KQg0y3LNNRyMPmGjVN_8u3QixbomiXuPoOAsKuZzzCsRZMdQ2sug0nlm69BiCSbq3Zn40gmIqTXAhG1AIcm2WqgCqi9SKWyWOBc8Tv2NnnccH_FCkUCPCa54ZRMsMGrkycG6oV1wYQkpBKF1lS0yx2NCX0RJGFLEATkMKRX1wdKOgjmyALtk5IBsN9KOr6rBl4sWEQb0zsVgzaTdHqex4j0a03jtsbq8RplKJeY5SbJOUv-o8EC6gjMbJzCa5ik")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {

                mMessage = response.body().string();
                Log.e("TAG", mMessage);
            }
        });
        return mMessage;
    }

}
