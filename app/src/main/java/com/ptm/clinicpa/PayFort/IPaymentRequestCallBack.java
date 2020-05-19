package com.ptm.clinicpa.PayFort;

public interface IPaymentRequestCallBack {
    void onPaymentRequestResponse(int responseType, PayFortData responseData);

}
