package com.ptmsa1.clinicclient.PayFort;

public interface IPaymentRequestCallBack {
    void onPaymentRequestResponse(int responseType, PayFortData responseData);

}
