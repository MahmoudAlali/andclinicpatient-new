package com.ptmsa1.clinicclient.Fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ptmsa1.clinicclient.Activities.BeautyMainPage;
import com.ptmsa1.clinicclient.Activities.TabOne;
import com.ptmsa1.clinicclient.R;

public class CompareServiceActivity extends AppCompatActivity {
    String places2="",places1="",places3="";
    TextView srname1,srname2,srname3,prName1,prName2,prName3,ev1,ev2,ev3,place1,place2,place3,price1,price2,price3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartion);


//            BeautyMainPage.FRAGMENT_NAME="COMPAREFRAGMENT";

            srname1=findViewById(R.id.sr1);
            srname2=findViewById(R.id.sr2);
            srname3=findViewById(R.id.sr3);
            prName1=findViewById(R.id.pr1);
            prName2=findViewById(R.id.pr2);
            prName3=findViewById(R.id.pro3);
            ev1=findViewById(R.id.ev1);
            ev2=findViewById(R.id.ev2);
            ev3=findViewById(R.id.ev3);
            place1=findViewById(R.id.place1);
            place2=findViewById(R.id.place2);
            place3=findViewById(R.id.place3);
            price1=findViewById(R.id.loc1);
            price2=findViewById(R.id.loc2);
            price3=findViewById(R.id.price3);

        places1="";places2="";places3="";
        srname1.setText("");srname2.setText("");srname3.setText("");
        prName1.setText("");prName2.setText("");prName3.setText("");
        ev1.setText("");ev2.setText("");ev3.setText("");
        price1.setText("");price2.setText("");price3.setText("");
            if (TabOne.compareModels.size()==3){
             srname3.setVisibility(View.VISIBLE);
                prName3.setVisibility(View.VISIBLE);
                ev3.setVisibility(View.VISIBLE);
                place3.setVisibility(View.VISIBLE);
                price3.setVisibility(View.VISIBLE);
//             srname3.setVisibility(View.VISIBLE);
            }else {
//                srname3.setVisibility(View.GONE);
//                prName3.setVisibility(View.GONE);
//                ev3.setVisibility(View.GONE);
//                place3.setVisibility(View.GONE);
//                price3.setVisibility(View.GONE);
            }

            for (int i=0;i< TabOne.compareModels.size();i++) {
                if (i==0) {
                    srname1.setText(TabOne.compareModels.get(i).getSrName());
                    prName1.setText(TabOne.compareModels.get(i).getSpname());
                    ev1.setText(TabOne.compareModels.get(i).getEv());
                    price1.setText(TabOne.compareModels.get(i).getPrice());

                    if (TabOne.compareModels.get(i).getBdb_ser_salon().equals("1")) {
                        places1 = places1 + "salon";
                    }
                    if (TabOne.compareModels.get(i).getBdb_hotel().equals("1")) {
                        places1 = places1 + ", hotel";
                    }
                    if (TabOne.compareModels.get(i).getBdb_ser_hall().equals("1")) {
                        places1 = places1 + ", hall";
                    }
                    if (TabOne.compareModels.get(i).getBdb_ser_home().equals("1")) {
                        places1 = places1 + ", home";
                    }
                    place1.setText(places1);


                }else if (i==1){
                    srname2.setText(TabOne.compareModels.get(i).getSrName());
                    prName2.setText(TabOne.compareModels.get(i).getSpname());
                    ev2.setText(TabOne.compareModels.get(i).getEv());
                    price2.setText(TabOne.compareModels.get(i).getPrice());

                    if (TabOne.compareModels.get(i).getBdb_ser_salon().equals("1")) {
                        places2 = places2 + "salon";
                    }
                    if (TabOne.compareModels.get(i).getBdb_hotel().equals("1")) {
                        places2 = places2 + ", hotel";
                    }
                    if (TabOne.compareModels.get(i).getBdb_ser_hall().equals("1")) {
                        places2 = places2 + ", hall";
                    }
                    if (TabOne.compareModels.get(i).getBdb_ser_home().equals("1")) {
                        places2 = places2 + ", home";
                    }
                    place2.setText(places2);

                }else if (i==2){
                    srname3.setText(TabOne.compareModels.get(i).getSrName());
                    prName3.setText(TabOne.compareModels.get(i).getSpname());
                    ev3.setText(TabOne.compareModels.get(i).getEv());
                    price3.setText(TabOne.compareModels.get(i).getPrice());

                    if (TabOne.compareModels.get(i).getBdb_ser_salon().equals("1")) {
                        places3 = places3 + "salon";
                    }
                    if (TabOne.compareModels.get(i).getBdb_hotel().equals("1")) {
                        places3 = places3 + ", hotel";
                    }
                    if (TabOne.compareModels.get(i).getBdb_ser_hall().equals("1")) {
                        places3 = places3 + ", hall";
                    }
                    if (TabOne.compareModels.get(i).getBdb_ser_home().equals("1")) {
                        places3 = places3 + ", home";
                    }
                    place3.setText(places3);

                }

//                price2.setText(ServicesAdapter.price2);
//                price3.setText(ServicesAdapter.price3);
//                prName2.setText(ServicesAdapter.spname2);
//                prName3.setText(ServicesAdapter.spname3);
//                ev1.setText(ServicesAdapter.ev1);
//                ev2.setText(ServicesAdapter.ev2);
//                ev3.setText(ServicesAdapter.ev3);


                //----------------------------------------------
//                if (TabOne.compareModels.get(i).getBdb_ser_salon().equals("1")) {
//                    places1 = places1 + "salon";
//                }
//                if (TabOne.compareModels.get(i).getBdb_hotel().equals("1")) {
//                    places1 = places1 + ", hotel";
//                }
//                if (TabOne.compareModels.get(i).getBdb_ser_hall().equals("1")) {
//                    places1 = places1 + ", hall";
//                }
//                if (TabOne.compareModels.get(i).getBdb_ser_home().equals("1")) {
//                    places1 = places1 + ", home";
//                }
//                //----------------------------------------------
//                if (ServicesAdapter.bdb_ser_salon1.equals("1")) {
//                    places2 = places2 + ", salon";
//                }
//                if (ServicesAdapter.bdb_hotel1.equals("1")) {
//                    places2 = places2 + ", hotel";
//                }
//                if (ServicesAdapter.bdb_ser_hall1.equals("1")) {
//                    places2 = places2 + ", hall";
//                }
//                if (ServicesAdapter.bdb_ser_home1.equals("1")) {
//                    places2 = places2 + ", home";
//                }
//                //----------------------------------------------
//                if (ServicesAdapter.bdb_ser_salon2.equals("1")) {
//                    places3 = places3 + ", salon";
//                }
//                if (ServicesAdapter.bdb_hotel2.equals("1")) {
//                    places3 = places3 + ", hotel";
//                }
//                if (ServicesAdapter.bdb_ser_hall2.equals("1")) {
//                    places3 = places3 + ", hall";
//                }
//                if (ServicesAdapter.bdb_ser_home2.equals("1")) {
//                    places3 = places3 + ", home";
//                }


//            ServicesAdapter.srName1="";
//            ServicesAdapter.srName3="";
//            ServicesAdapter.srName2="";
//            ServicesAdapter.spname1="";
//            ServicesAdapter.spname2="";
//            ServicesAdapter.spname3="";
//            ServicesAdapter.ev1="";
//            ServicesAdapter.ev2="";
//            ServicesAdapter.ev3="";
//            ServicesAdapter.place1="";
//            ServicesAdapter.place2="";
//            ServicesAdapter.place3="";
//            ServicesAdapter.price1="";
//            ServicesAdapter.price2="";
//            ServicesAdapter.price3="";
            }

            Log.d("doback",BeautyMainPage.FRAGMENT_NAME);
            Toolbar toolbar=findViewById(R.id.toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  onBackPressed();
                }
            });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
