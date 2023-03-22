package com.store.promtech;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.store.promtech.model.BaseResponse;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Utility;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    EditText et_email;
    Button btn_forgot;
    ConnectionDetector cd;
    ProgressDialog pDialog;
    String user_email;
    BaseResponse registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Please Wait...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);

        initViews();

    }

    private void initViews() {
        et_email = findViewById(R.id.et_email);
        btn_forgot = findViewById(R.id.btn_forgot);

        btn_forgot.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btn_forgot) {

            if (et_email.getText().toString().isEmpty()) {
                Utility.showToastShort(mContext, "Please Enter Your Phone Number");
                return;
            }




            user_email = et_email.getText().toString().trim();

            verifyUser();

        }

    }

    private void verifyUser() {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<BaseResponse> call = redditAPI.UserForgotPassword(user_email);
        call.enqueue(new Callback<BaseResponse>() {

            @Override
            public void onResponse(Call<BaseResponse> call, retrofit2.Response<BaseResponse> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    registration = response.body();
                    if (registration.getAck()==1) {
                        Utility.showToastShort(mContext, registration.getMsg());
                    } else {
                        Utility.showToastShort(mContext, registration.getMsg());
                    }
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }
}
