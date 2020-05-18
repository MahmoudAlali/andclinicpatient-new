package com.ptmsa1.clinicclient.Activities.support;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ptmsa1.clinicclient.API.APICall;
import com.ptmsa1.clinicclient.Activities.BeautyMainPage;
import com.ptmsa1.clinicclient.Dialog.Dialogs;
import com.ptmsa1.clinicclient.Dialog.MyRunnable;
import com.ptmsa1.clinicclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class InternalChatActivity extends AppCompatActivity {

    Button sendBtn;
    EditText msgEditTxt;
    static ViewGroup root;
    ArrayAdapter adapter;
    static ScrollView scrollView;
    private boolean isFirstMsg =true;
    private static boolean isNoChat=false;
    static Context context;
    public static String NotificationMessage="";

    static CountDownTimer timer;
     CountDownTimer innerTimer;
    Dialogs terminateChat;

    static int TIMER_TIME = 300000; //5 minutes
    static boolean isRunning = true;
    static boolean firstRun= true;
    static boolean resume= false;
    public static String ChatID,Token,ProviderId;
    final private static String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private static String serverKey = "key=" + APICall.SERVER_KEY;
//            "AAAAjaJV_ws:APA91bEeV0bieSYrDrkwR0Yoj7lnvCQUSBgwwO2VyAJRWwzNeoGNoer9Mu3OoaRHBGB9ocEdwOEk2eN45o8YcB0CnG-HtrlWpKEu4jraPuagHKJy9a89g1nmzkOU7lkm6fniWCn5YxHg";


    //final  private String maiToken ="eVFnsR1ZGpy77CvK5J0sgM:APA91bFfzR83qKLt9NtWC-uWW6fcVeHuDa4IRhXlZmygHdrhuZWjmpSUw964Op-TU4KKc0_SdJymp6le_4R08Mmv-MtWngF5kcg9PaZq-kBM-A04dM9VjfuSJVddoWI6JjHWo6rhHT7d";
    //My server key
   // final private String serverKey = "key=" + "AAAAqc212tE:APA91bGdoZGyA74Qsdoul7ph79xB1Mct-KB89qxI0HgcP5aOf_cI1qoHsSgeuY_BIspCNTEB1_2v-Ky0G70Vy75iV6y6rkVBwqn1FzTVdFuQ0etKZx4wcYDgmIZz_rx-9YI2NFDeJYw2";

    final private static String contentType = "application/json";
    String TOPIC = "/topics/userABC";

    //My token
    String TO = "fSdNTRwRu8A:APA91bGrafVilHqL7ScpxdUkA02DDkYGCc9Q7ZsdcBbuAqsSlIufGZAynVoXSGfAXS6-OYSN4P5GzdcExBpE8xElsDdLS3ZsAY5oJ1A0zpTT9DoL6ZhU2Fs9RzCx7ikF37JXH7zJQPlU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_chat);
        sendBtn = findViewById(R.id.sendBtn);
        msgEditTxt=findViewById(R.id.msgEditTxt);
        root=findViewById(R.id.root);
        scrollView =findViewById(R.id.scrollView);
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        context=this;
        isNoChat=false;

        //----------- back btn process------
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        innerTimer = new CountDownTimer(TIMER_TIME, 1000) {

            public void onTick(long millisUntilFinished) {
                isRunning=true;
            }

            public void onFinish()
            {
                isRunning=false;
                terminateChatOkClick.run();

            }
        };
        CheckIntentForNotification();
        ProviderId = getIntent().getStringExtra("providerId");


        sendBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String string =msgEditTxt.getText().toString();
                if (string != null && !TextUtils.isEmpty(string.trim()) )
                {
                    NewMsg(context,string,true);
                    startNewInnerTimer();
                    isNoChat=false;
                    String NOTIFICATION_MESSAGE = msgEditTxt.getText().toString();

                    if(!isFirstMsg)
                    {

                        JSONObject notification = new JSONObject();
                        JSONObject notificationBody = new JSONObject();
                        String title="";
                        if (SupportActivity.providerSalonName!=null && !SupportActivity.providerSalonName.equals(""))
                            title= SupportActivity.providerSalonName;
                        else
                            title= SupportActivity.providerName;
                        try {
                            notificationBody.put("body", NOTIFICATION_MESSAGE);
                            notificationBody.put("title", title);
                            notificationBody.put("chat_id", ChatID);
                            notificationBody.put("content_available", true);
                            notificationBody.put("action", "5");
                            notificationBody.put("author", "2");
                            notificationBody.put("sender_id", ProviderId);
                            notificationBody.put("sender_id", ProviderId);
                            notification.put("to", Token);
                            notification.put("data", notificationBody);
                        } catch (JSONException e) {
                            Log.e("NotificationErr", "onCreate: " + e.getMessage() );
                        }
                        sendNotification(notification);
                        Log.e("N", notification.toString() );
                    }
                    else
                    {
                        APICall.sendFirstChatMsg(context,NOTIFICATION_MESSAGE);
                        isFirstMsg=false;
                    }
                    msgEditTxt.setText("");
                }

            }
        });



    }

    public static void sendNotification(JSONObject notification)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("response", "onResponse: " + response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  Toast.makeText(InternalChatActivity.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i("errResponse", "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(BeautyMainPage.context).addToRequestQueue(jsonObjectRequest);
    }

    public static void NewMsg(Context context, String body, boolean isSent)
    {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newMsg =isSent?layoutInflater.inflate(R.layout.layout_outcom_msg_item, null): layoutInflater.inflate(R.layout.layout_chat_msg_item, null);
        final TextView msg = newMsg.findViewById(R.id.msgTextView);
        final TextView timeTxt = newMsg.findViewById(R.id.timeTextView);
        msg.setText(body);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        timeTxt.setText( (hour24hrs+3)%24 + ":" + minutes);
        Log.e("ICA","internal chat :"+body);
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        Log.e("DATE",dateFormatGmt.format(new Date())+"");

        if(root!=null)
        root.addView(newMsg);
        if(scrollView!=null)
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
    public static void NewMsg(Context context, String body, boolean isSent, String time)
    {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newMsg =isSent?layoutInflater.inflate(R.layout.layout_outcom_msg_item, null): layoutInflater.inflate(R.layout.layout_chat_msg_item, null);
        final TextView msg = newMsg.findViewById(R.id.msgTextView);
        final TextView timeTxt = newMsg.findViewById(R.id.timeTextView);
        msg.setText(body);
        timeTxt.setText( time);
        Log.e("ICA","internal chat :"+body);
        if(root!=null)
            root.addView(newMsg);
        if(scrollView!=null)
            scrollView.fullScroll(View.FOCUS_DOWN);
    }
    @Override
    public void onBackPressed() {
        if(!isRunning && firstRun && resume)
        {
            Log.e("BACH from Resume2","TRUE");

            super.onBackPressed();
        }
        else
        {
            if(!isNoChat)
            {
                terminateChat = new Dialogs(context, R.string.terminatingChat, R.string.exit,terminateChatOkClick);
                terminateChat.show();
            }
            else
                super.onBackPressed();
        }


    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.e("onNewIntent","onNewIntent triggered");
        super.onNewIntent(intent);
       CheckIntentForNotification();
    }

    private void CheckIntentForNotification()
    {
        if (!NotificationMessage.equals(""))
        {
            NewMsg(context,NotificationMessage,false);
            startNewInnerTimer();
            NotificationMessage="";
        }
    }

    public MyRunnable terminateChatOkClick = new MyRunnable()

    {
        @Override
        public void run() {
            APICall.TerminateChat(context,ChatID);
            InternalChatActivity.super.onBackPressed();
            innerTimer.cancel();
            firstRun =false;

        }
    };

    private void startNewInnerTimer()
    {
        innerTimer.cancel();
        innerTimer.start();
    }
    public static void showUnavailableSupport()
    {
        Dialogs dialogs=new Dialogs(context, R.string.noOperator);
        isNoChat=true;
        dialogs.show();
    }

    @Override
    protected void onResume() {
        Log.e("RESUME","TRUE");

        resume=true;
        if(!isRunning && firstRun)
            onBackPressed();
        super.onResume();

    }
}
