package com.ptmsa1.vizage.PayFort;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.payfort.fort.android.sdk.base.FortSdk;
import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.payfort.fort.android.sdk.base.callbacks.FortCallback;
import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Fragments.MyReservationFragment;
import com.ptmsa1.vizage.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class PayTestActivity extends AppCompatActivity implements IPaymentRequestCallBack, View.OnClickListener {

    private TextView tvPurchage;
    private EditText etAmount;

    public static FortCallBackManager fortCallback = null;

    String device_id = "";
    static Context context;
    IPaymentRequestCallBack iPaymentRequestCallBack;
    public static String success_response="";
    String amount="10",name_booking="200";
    public static String check="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_test);
        context=this;
        device_id=FortSdk.getDeviceId(context);
         iPaymentRequestCallBack=this;
         amount=getIntent().getStringExtra("amount");
        name_booking=getIntent().getStringExtra("name_booking");
        initilizePayFortSDK();
        setReferences();
        getNewPaymentCode(context,amount,"SAR", BeautyMainPage.bdb_email,device_id,name_booking,fortCallback, iPaymentRequestCallBack);

//        setListeneres();
    }

    private void initilizePayFortSDK() {
        fortCallback = FortCallback.Factory.create();
    }

    private void setReferences() {
        etAmount =  findViewById(R.id.etAmount);
        tvPurchage =  findViewById(R.id.tvPurchage);
    }

    private void setListeneres() {
        tvPurchage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvPurchage:
//                fortCallback=null;
                getNewPaymentCode(context,etAmount.getText().toString(),"SAR", BeautyMainPage.bdb_email,device_id,name_booking,fortCallback, iPaymentRequestCallBack);
//                requestForPayfortPayment();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PayFortPayment.RESPONSE_PURCHASE) {
            fortCallback.onActivityResult(requestCode, resultCode, data);
        }

//        Log.e("resultCode1",getIntent().getIntExtra("some_key",0)+"is");
//        Log.e("resultCode1","0");
//        if (resultCode== Activity.RESULT_OK){
//            Log.e("resultCode1","OK");
//                        fortCallback.onActivityResult(requestCode, resultCode, data);
//        }
    }


    private void requestForPayfortPayment(String sdk_token,String signature ,String merchant_identifier ,String access_code
            ,String merchant_reference ,String customer_email,String device_id ,FortCallBackManager fortCallback, IPaymentRequestCallBack iPaymentRequestCallBack,String token_name ,String mMessage) {

        PayFortData payFortData = new PayFortData();
        if (amount!=null) {
            payFortData.serviceCommand = "SDK_TOKEN";
            payFortData.deviceId = device_id;
//            payFortData.merchantIdentifier = merchant_identifier;
//            payFortData.accessCode = access_code;
//            payFortData.signature = signature;
            payFortData.amount = String.valueOf((int) (Float.parseFloat(amount))*100);// Multiplying with 100, bcz amount should not be in decimal format
            Log.e("amountTXT","is"+amount);
            Log.e("amountTXT","is"+payFortData.amount);
            payFortData.command = PayFortPayment.PURCHASE;
            payFortData.currency = PayFortPayment.CURRENCY_TYPE;
            payFortData.customerEmail = customer_email;
            payFortData.tokenName = token_name;
            payFortData.language = "en";
            payFortData.merchantReference = merchant_reference;
            payFortData.sdkToken = sdk_token;
//            payFortData.paymentResponse = mMessage;

//            payFortData.status= "22";
            Log.e("amount","is: "+payFortData.amount);
            Log.e("command","is: "+payFortData.command);
            Log.e("currency","is: "+payFortData.currency);
            Log.e("customerEmail","is: "+payFortData.customerEmail);
            Log.e("deviceId","is: "+payFortData.deviceId);
            Log.e("sdkToken","is: "+payFortData.sdkToken);
            Log.e("signature","is: "+payFortData.signature);
            Log.e("merchantReference","is: "+payFortData.merchantReference);
            Log.e("merchantIdentifier","is: "+payFortData.merchantIdentifier);
            Log.e("accessCode","is: "+payFortData.accessCode);
            Log.e("tokenName","is: "+payFortData.tokenName);


//            parameters.put("amount", String.valueOf(payFortData.amount));
//            parameters.put("command", payFortData.command);
//            parameters.put("currency", payFortData.currency);
//            parameters.put("customer_email", payFortData.customerEmail);
//            parameters.put("language", payFortData.language);
//            parameters.put("merchant_reference", payFortData.merchantReference);
//            parameters.put("sdk_token", sdkToken);



            PayFortPayment payFortPayment = new PayFortPayment((AppCompatActivity)context, fortCallback,   iPaymentRequestCallBack);
            payFortPayment.requestForPayment(payFortData);
        }
    }
    private void requestForPayfortPayment() {
        PayFortData payFortData = new PayFortData();
        if (!TextUtils.isEmpty(amount)) {
            payFortData.amount = String.valueOf(Integer.parseInt(amount)*10000);// Multiplying with 100, bcz amount should not be in decimal format
            payFortData.command = PayFortPayment.PURCHASE;
            payFortData.currency = PayFortPayment.CURRENCY_TYPE;
            payFortData.customerEmail = BeautyMainPage.bdb_email;
            payFortData.language = PayFortPayment.LANGUAGE_TYPE;
            payFortData.merchantReference = String.valueOf(System.currentTimeMillis());

            PayFortPayment payFortPayment = new PayFortPayment(this, this.fortCallback, this);
            payFortPayment.requestForPayment(payFortData);
        }
    }

    public static String response1="";
    public  void getNewPaymentCode(final Context context, String amount, String currency, final String customer_email, final String device_id, String name_booking, final FortCallBackManager fortCallback, final IPaymentRequestCallBack iPaymentRequestCallBack)
    {

//        payFortData.sdkToken = "";


        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
               APICall.showDialog(context);
//
            }
        });

        //        String url = API_PREFIX_NAME+"/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("amount",amount);
            postdata.put("service_command","SDK_TOKEN");
            postdata.put("customer_email",BeautyMainPage.bdb_email);
            postdata.put("language",APICall.ln);
            postdata.put("currency",currency);
            postdata.put("device_id",device_id);
            postdata.put("bdb_name_booking",name_booking);
        }catch (Exception e){
            e.printStackTrace();
        }

        Log.e("PostData",postdata.toString());
        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(APICall.API_PREFIX_NAME+"/api/payment/getNewPaymentCode")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+APICall.gettoken(context))
                //                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.e("Payment_Response", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        APICall.pd.dismiss();
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });
                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname"))
                {
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.Null);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();

                                }
                            });
                            dialog.show();

                        }
                    });


                }
                else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            APICall.showSweetDialog(context,"",context.getResources().getString(R.string.an_error_occurred));

                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                final String mMessage = response.body().string();
                Log.e("Token", APICall.gettoken(context));
                Log.e("TAG", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        APICall.pd.dismiss();
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });


                try {
                    JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    String response_code=j.getString("response_code");
                    if (response_code.equals("37"))
                    {
                        JSONObject object=j.getJSONObject("result");
                        final String token_name=object.getString("token_name");

                        if (token_name.equals("") || token_name.equals("null") )
                        object.remove("token_name");

                        response1=object.toString();
                        final String signature=object.getString("signature");
                        final String merchant_identifier=object.getString("merchant_identifier");
                        final String access_code=object.getString("access_code");
                        final String merchant_reference=object.getString("merchant_reference");
                        final String sdk_token=object.getString("sdk_token");
                        Log.e("this_is","APIBefor");
                        Log.e("Token_name","iiiii"+token_name);

                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                requestForPayfortPayment(sdk_token,signature ,merchant_identifier ,access_code
                                        ,merchant_reference,customer_email,device_id,fortCallback,iPaymentRequestCallBack,token_name,mMessage);
                            }
                        });



                    }else if(j.getString("resonse_code").equals("38")){
                        APICall.showNumberErrMsg(context,context.getResources().getString(R.string.there_is_an_err)+" "+response_code+". "+
                                context.getResources().getString(R.string.try_again_later));
                    }else if(j.getString("resonse_code").equals("39")){
                        APICall.showNumberErrMsg(context,context.getResources().getString(R.string.there_is_an_err)+" "+response_code+". "+
                                context.getResources().getString(R.string.try_again_later));
                    }



                }catch (final JSONException je){
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           je.printStackTrace();
                            // Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }

        });
        //        Log.d("MessageResponse",mMessage);
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onPaymentRequestResponse(int responseType, final PayFortData responseData) {
        if (responseType == PayFortPayment.RESPONSE_GET_TOKEN) {
            Toast.makeText(this, "Token not generated", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Token not generated");
            check="2";
            onBackPressed();

        } else if (responseType == PayFortPayment.RESPONSE_PURCHASE_CANCEL) {
            Toast.makeText(this, "Payment cancelled", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Payment cancelled");
            check="2";
//            APICall.getPurchaseResponseFromFrontEnd(context,"","","","","","","","","","","","","","","","","","","","1");
            onBackPressed();

        } else if (responseType == PayFortPayment.RESPONSE_PURCHASE_FAILURE) {
            Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Payment failed");
            check="2";
            onBackPressed();

        } else {
            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Payment successful");
            check="1";
        APICall.getPurchaseResponseFromFrontEnd(context,"","","","","","","","","","","","","","","","","","","","0");
        onBackPressed();
        }
    }
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragment = new MyReservationFragment();
        fm = ((AppCompatActivity)BeautyMainPage.context).getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commitAllowingStateLoss();
        Resources res = getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(APICall.ln.toLowerCase())); // API 17+ only.
        // Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);
    }
}


