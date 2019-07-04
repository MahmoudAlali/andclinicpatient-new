package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.R;
import com.google.android.gms.maps.MapView;


public class DeleteAccountFragment extends Fragment  {


    RadioGroup reason_redio_button;
    EditText message;
    Button send;
        String reason,msg;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.delete_account_fragment, container, false);
            BeautyMainPage.FRAGMENT_NAME="DELETEACCOUNT";

        reason_redio_button=view.findViewById(R.id.reason_redio_button);
        message=view.findViewById(R.id.message);
        send=view.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("checked",reason_redio_button.getCheckedRadioButtonId()+"");
                if (reason_redio_button.getCheckedRadioButtonId()!=-1){
                    RadioButton radioButton =view.findViewById(reason_redio_button.getCheckedRadioButtonId());
                    reason=radioButton.getText().toString();
                    if (message.getText().toString().isEmpty()){
                        msg="none";
                    }else {
                        msg=message.getText().toString();
                    }

                    APICall.deleteAccount("http://clientapp.dcoret.com/api/auth/user/deleteAccount",reason,msg,BeautyMainPage.context);

                }else {
                    APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.ExuseMeAlert),"Please Select one reason");
                }

            }
        });


        return view;
    }

}
