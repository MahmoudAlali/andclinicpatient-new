package com.ptm.clinicpa.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ptm.clinicpa.PayFort.FortRequest;
import com.ptm.clinicpa.R;
import com.payfort.fort.android.sdk.base.FortSdk;
import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.payfort.fort.android.sdk.base.callbacks.FortCallback;
import com.payfort.sdk.android.dependancies.base.FortInterfaces;

import java.util.HashMap;
import java.util.Map;

public class Payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        payfortpayment();
    }
    //------------------- payfort section----------
    public static FortCallBackManager fortCallback = null;
    static String deviceId = "";
    String sdkToken = "";

    private static Map<String, Object> collectRequestMap(String sdkToken) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("command", "PURCHASE");
        requestMap.put("customer_email", "Sam@gmail.com");
        requestMap.put("currency", "SAR");
        requestMap.put("amount", "100");
        requestMap.put("language", "en");
        requestMap.put("merchant_reference", "ORD-0000007682");
        requestMap.put("customer_name", "Sam");
        requestMap.put("customer_ip", "172.150.16.10");
        requestMap.put("payment_option", "VISA");
        requestMap.put("eci", "ECOMMERCE");
        requestMap.put("order_description", "DESCRIPTION");
        requestMap.put("sdk_token", sdkToken);
        return requestMap;
    }
    private  void callSdk(FortRequest fortrequest) {

        try {
            FortSdk.getInstance().registerCallback(Payment.this, fortrequest, FortSdk.ENVIRONMENT.TEST, 5, fortCallback, new FortInterfaces.OnTnxProcessed() {
                @Override
                public void onCancel(Map<String, Object> map, Map<String, Object> map1) {
                    //TODO: handle me
                    Log.d("Cancelled ", map1.toString());
                }

                @Override
                public void onSuccess(Map<String, Object> map, Map<String, Object> map1) {
                    //TODO: handle me
                    Log.d("Success  ", map1.toString());
                }

                @Override
                public void onFailure(Map<String, Object> map, Map<String, Object> map1) {
                    //TODO: handle me
                    Log.d("Failure   ", map1.toString());
                }
            });
        } catch (Exception e) {
            Log.e("execute Payment", "call FortSdk", e);
        }

    }

    public  void payfortpayment(){
        //--------------------payfort-------------

// create Fort callback instance
        fortCallback = FortCallback.Factory.create();

// Generating deviceId
        deviceId = FortSdk.getDeviceId(getApplicationContext());
        Log.d("DeviceId ", deviceId);

// prepare payment request
        FortRequest fortrequest = new FortRequest();
        fortrequest.setRequestMap(collectRequestMap("PASS_THE_GENERATED_SDK_TOKEN_HERE"));
        fortrequest.setShowResponsePage(true); // to [display/use] the SDK response page

// execute payment request
        callSdk(fortrequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fortCallback.onActivityResult(requestCode, resultCode, data);
        Log.d("request_code  ", requestCode+"");
        Log.d("request_code  ", resultCode+"");
        Log.d("request_code  ", data+"");

    }
}
