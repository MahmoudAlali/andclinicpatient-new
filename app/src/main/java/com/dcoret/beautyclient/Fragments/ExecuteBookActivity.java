package com.dcoret.beautyclient.Fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Adapters.ReservationsAdapter2;
import com.dcoret.beautyclient.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ExecuteBookActivity extends AppCompatActivity {


    static LinearLayout myroot;
    Button okBtn;
    static Context context;
    public static LinearLayout.LayoutParams lp ;
    static Map<String, String> map = new HashMap<>();
    static boolean isOffer;
    public static String bookID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_book);
        map.clear();
        context=this;

        okBtn=findViewById(R.id.button2);
        lp= (LinearLayout.LayoutParams) okBtn.getLayoutParams();

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,RateSerEmpActivity.class);
                startActivity(intent);
                JSONArray p=getBookings();

//                APICall.setExecuted(context,p,bookID);


            }
        });
        myroot=findViewById(R.id.rootLayout);
        //region Check_Notification
        String execute_book_id="";
        try
        {
            Log.e("Notif", "Reserv Details is trying to get bookid");
            execute_book_id=getIntent().getStringExtra("execute_book_id");
        }
        catch (Exception e)
        {
            Log.e("NotifErr",e.getMessage());
        }

        if(execute_book_id!=null)
        {
            APICall.browseOneExecutedBooking(execute_book_id,this);
            isOffer=getIntent().getBooleanExtra("isOffer",false);

        }
        else
        {
            APICall.browseOneExecutedBooking(ReservationsAdapter2.book_id,this);
            isOffer=ReservationsAdapter2.isOffer;
        }

        //endregion

     //   APICall.browseOneExecutedBooking(ReservationsAdapter2.book_id,this);
    }

    public static void AddLayout(final String Id,String customerName,String serviceName,String serNameAr,String money,String deposit)
    {
        final View layout2;
        layout2 = LayoutInflater.from(context).inflate(R.layout.booking_execution_layout, myroot, false);
        TextView clientName,ServiceName;
        final EditText moneyTxt;
        final CheckBox checkBox =layout2.findViewById(R.id.isExecuted);
        clientName=layout2.findViewById(R.id.employee_name);
        ServiceName=layout2.findViewById(R.id.service_name);
        moneyTxt=layout2.findViewById(R.id.moneyTxt);
        clientName.setText(customerName);
        if (isOffer)
            moneyTxt.setActivated(false);
        if(context.getResources().getString(R.string.locale).equals("en"))
            ServiceName.setText(serviceName);
        else
            ServiceName.setText(serNameAr);




        //----- price-deposit
        Double p=Double.parseDouble(money)-Double.parseDouble(deposit);
        int pp=(int) Math.round(p);
        moneyTxt.setText(pp+"");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    map.put(Id,moneyTxt.getText().toString());
                else
                    map.remove(Id);

            }
        });
        moneyTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (checkBox.isChecked())
                    map.put(Id,moneyTxt.getText().toString());

            }
        });


    }

    private JSONArray getBookings() {
        JSONArray result = new JSONArray();
        JSONObject temp;

        if (map.size()==0){
            APICall.showSweetDialog(context,"",context.getResources().getString(R.string.please_select_an_service_or_back_to_cancel));
        }else
            for (Map.Entry<String, String> entry : map.entrySet())
            {
                try {
                    temp=new JSONObject();
                    temp.put("book_id",entry.getKey());
                    temp.put("final_price",entry.getValue());
                    result.put(temp);
                }
                catch (Exception e)
                {

                }
            }
        return result;
    }

}

