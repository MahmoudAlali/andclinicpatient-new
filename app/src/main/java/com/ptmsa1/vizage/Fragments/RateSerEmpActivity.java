package com.ptmsa1.vizage.Fragments;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Adapters.ReservationsAdapter2;
import com.ptmsa1.vizage.DataModel.SupRatingModel;
import com.ptmsa1.vizage.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import hyogeun.github.com.colorratingbarlib.ColorRatingBar;

public class RateSerEmpActivity extends AppCompatActivity {

    static Context context;
    static LinearLayout myroot;
    public static TextView sup_name;
    public static ColorRatingBar sup_rating;
    public static String salon_id;
    public static int [] servicsImgsBasic ={ R.drawable.hair_basic,
            R.drawable.makeup_basic,
            R.drawable.massage_basic,
            R.drawable.spa_basic,
            R.drawable.nails_basic,
            R.drawable.body_basic,
            R.drawable.skin_basic,
            R.drawable.eyebrows_basic
    };
    Button eval;
    public  static ArrayList<SupRatingModel> arrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_ser_emp);
       int l= R.layout.emp_rating_layout;
       context=this;
       myroot=findViewById(R.id.myroot);
        sup_rating=findViewById(R.id.sup_rating);
        sup_name=findViewById(R.id.sup_name);
        eval=findViewById(R.id.eval);
       arrayList.clear();

//        ReservationsAdapter2.book_id;

        APICall.browseOneRatingBooking(ReservationsAdapter2.book_id,context);

//       addLayout("Hala","تسريحة");
//       addLayout("Rana","قص شعر");

        eval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.add(new SupRatingModel("supplier",sup_rating,arrayList.get(0).getBdb_book_id()));
                APICall.rateBooking(context,getrating(arrayList));
            }
        });
    }
    public static void addLayout(String book_id,String emp_name,String serviceName,String cat_id)
    {
        final View layout2;
        layout2 = LayoutInflater.from(context).inflate(R.layout.emp_rating_layout, myroot, false);
        TextView employee_name,ser_name;
        ImageView logoImg ;
        ColorRatingBar rating;

        employee_name=layout2.findViewById(R.id.employee_name);
        ser_name=layout2.findViewById(R.id.service_name);
        rating=layout2.findViewById(R.id.rating);
        logoImg=layout2.findViewById(R.id.logoImg);

        employee_name.setText(emp_name);
        ser_name.setText(serviceName);
        logoImg.setImageResource(servicsImgsBasic[Integer.parseInt(cat_id)]);


        arrayList.add(new SupRatingModel("employee",rating,book_id));
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });


    }

    JSONArray getrating(ArrayList<SupRatingModel> arrayList){
        JSONArray jsonArray=new JSONArray();
        for (int i=0;i<arrayList.size();i++){
            JSONObject object=new JSONObject();
            try{

                object.put("bdb_type",arrayList.get(i).getBdb_type());
                object.put("bdb_value",arrayList.get(i).getBdb_value().getRating()+"");
                object.put("bdb_book_id",arrayList.get(i).getBdb_book_id());
            }catch (Exception e){
                e.printStackTrace();
            }
            jsonArray.put(object);
        }
        return jsonArray;
    }


}
