package com.ptm.clinicpa.Fragments;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.R;


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
                        msg=null;
                    }else {
                        msg=message.getText().toString();
                    }
                    APICall.deleteAccount(APICall.API_PREFIX_NAME+"/api/user/deleteAccount",reason,msg,BeautyMainPage.context);

                }else {
                    String s=((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.please_select_one_reason);

                    APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.ExuseMeAlert),s);
                }

            }
        });
        Toolbar toolbar=view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)BeautyMainPage.context).onBackPressed();
            }
        });

        return view;
    }

}
