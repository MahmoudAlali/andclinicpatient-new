package com.ptmsa1.vizage.PayFort;

public interface IPaymentRequestCallBack {
    void onPaymentRequestResponse(int responseType, PayFortData responseData);

}
