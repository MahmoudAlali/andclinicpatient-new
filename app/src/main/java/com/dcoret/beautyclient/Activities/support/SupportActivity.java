package com.dcoret.beautyclient.Activities.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Dialog.Dialogs;
import com.dcoret.beautyclient.Dialog.MyRunnable;
import com.dcoret.beautyclient.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SupportActivity extends AppCompatActivity {



    Button whatsAppSupport,internalChatBtn,callMeBtn;
    public static String providerName,providerID,providerMail,providerMobile,providerSalonName;

    Dialogs getReasonDialog;
    Date openningTime,closingTime,now;

    static Dialogs confirmationDialog;
    static Context context;
    public Calendar calendarOpenning,calendarClosing,calendarNow;
    int nowHour,nowMinuts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        APICall.getProviderId(this);
        calendarNow = Calendar.getInstance();
        nowHour=calendarNow.get(Calendar.HOUR_OF_DAY);
        nowMinuts=calendarNow.get(Calendar.MINUTE);
        context=this;

        whatsAppSupport=findViewById(R.id.whatsapp_support);
        internalChatBtn=findViewById(R.id.internalChatBtn);
        callMeBtn=findViewById(R.id.callMeBtn);


        //----------- back btn process------
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        whatsAppSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(nowHour >=9 &&nowHour<20 || nowHour==20 && nowMinuts<30)
                {
                    if (providerSalonName!=null && !providerSalonName.equals(""))
                        APICall.sendWhatsappEvent(context,providerID,providerSalonName,providerMobile);
                    else
                        APICall.sendWhatsappEvent(context,providerID,providerName,providerMobile);
                }
                else
                {
                    showOutOfTime();
                }



            }
        });
        internalChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nowHour >=9 &&nowHour<20 || nowHour==20 && nowMinuts<30)
                {
                    Intent intent = new Intent(getApplicationContext(), InternalChatActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("providerId",providerID);
                    startActivity(intent);
                }
                else
                {
                    showOutOfTime();
                }

            }
        });

        callMeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nowHour >=9 &&nowHour<20 || nowHour==20 && nowMinuts<30)
                {
                    getReasonDialog =new Dialogs(context, R.string.empty, R.string.enterReasonMsg, R.string.ok,OnClickCallMeBtn);
                    getReasonDialog.show();
                }
                else
                {
                    showOutOfTime();
                }

            }
        });




    }
    public static void openWhatsappChat(Context context){
        try {
            String whatsAppRoot = "http://api.whatsapp.com/";
            String number = "send?phone=966563434455"; //here the mobile number with its international prefix
            //String text = "&text=HERE YOUR TEXT";
            String uri = whatsAppRoot+number;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uri));
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context,
                    "WhatsApp cannot be opened", Toast.LENGTH_SHORT).show();
        }
    }

    private MyRunnable OnClickCallMeBtn =new MyRunnable()
    {
        @Override
        public void run() {

            Log.e("SupportActivity","OnClickCallMeBtn triggered");
            if (providerSalonName!=null && !providerSalonName.equals(""))
                APICall.sendCallMeEvent(context,providerID,providerSalonName,providerMobile,providerMail,getValue());
            else
                APICall.sendCallMeEvent(context,providerID,providerName,providerMobile,providerMail,getValue());

        }
    };

    public static void showUnavailableSupport()
    {
        Dialogs dialogs=new Dialogs(context,R.string.noOperator);
        dialogs.show();
    }
    public static void showOutOfTime()
    {
        Dialogs dialogs=new Dialogs(context,R.string.teamWorkingTime);
        dialogs.show();
    }

    public static  void showConfirmationMsg()
    {
        confirmationDialog=new Dialogs(context, R.string.weWillCall);
        confirmationDialog.show();
    }

}
