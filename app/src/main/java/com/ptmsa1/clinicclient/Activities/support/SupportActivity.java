package com.ptmsa1.clinicclient.Activities.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ptmsa1.clinicclient.API.APICall;
import com.ptmsa1.clinicclient.Dialog.Dialogs;
import com.ptmsa1.clinicclient.Dialog.MyRunnable;
import com.ptmsa1.clinicclient.R;

import java.util.Calendar;
import java.util.Date;

public class SupportActivity extends AppCompatActivity {



    Button whatsAppSupport,internalChatBtn,callMeBtn;
    public static String providerName,providerID,providerMail,providerMobile,providerSalonName;

    Dialogs getReasonDialog;
    Date openningTime,closingTime,now;

    static Dialogs confirmationDialog;
    static Context context;
    public Calendar calendarNow;
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
                if(nowHour >=9 &&nowHour<21 || nowHour==21 && nowMinuts<30)
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
                if(nowHour >=9 &&nowHour<21 || nowHour==21 && nowMinuts<30)
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
                if(nowHour >=9 &&nowHour<21 || nowHour==21 && nowMinuts<30)
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
        Dialogs dialogs=new Dialogs(context, R.string.noOperator);
        dialogs.show();
    }
    public static void showOutOfTime()
    {
        Dialogs dialogs=new Dialogs(context, R.string.out_of_support_time,R.string.out_of_support_time_link);
        dialogs.show();
    }

    public static  void showConfirmationMsg()
    {
        confirmationDialog=new Dialogs(context, R.string.weWillCall);
        confirmationDialog.show();
    }

}
