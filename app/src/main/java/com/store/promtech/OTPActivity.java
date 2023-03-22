package com.store.promtech;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.store.promtech.model.LoginResponse;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Preferences;
import com.store.promtech.utils.Utility;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    EditText et_email;
    Button btn_forgot, btn_resend;
    ConnectionDetector cd;
    ProgressDialog pDialog;
    String user_email, user_phoneno;
    LoginResponse registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        mContext = this;
        cd = new ConnectionDetector(mContext);

        user_phoneno = getIntent().getExtras().getString("user_phoneno");



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

        btn_resend = findViewById(R.id.btn_resend);

        btn_resend.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btn_forgot) {
            if (et_email.getText().toString().isEmpty()) {
                Utility.showToastShort(mContext, "Please Enter Your OTP");
                return;
            }
            /*if (!Utility.isValidEmail(et_email.getText().toString())) {
                Utility.showToastShort(mContext, "Please Enter Valid Email");
                return;
            }*/
            user_email = et_email.getText().toString().trim();

            verifyPhoneNo();
        }

        if (v == btn_resend) {

            ResendOtp();

        }

    }

    private void verifyPhoneNo() {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<LoginResponse> call = redditAPI.VerifyOTP(user_email, Preferences.get_userId(mContext));
        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                Log.d("ResponseOTP", "" + response);
                if (response.isSuccessful()) {
                    registration = response.body();
                    if (registration.getAck() == 1) {
                        if (registration.getLoginData().size() > 0) {
                            Preferences.setisVerified(mContext, true);

                            Preferences.set_userPhone(mContext, registration.getLoginData().get(0).getPhone());
                            Preferences.set_userId(mContext, registration.getLoginData().get(0).getUserId());

                            Intent intent = new Intent(mContext, Dashboard.class);
                            intent.putExtra("tag","otp");
                            intent.putExtra("msg",registration.getMsg());
                            startActivity(intent);
                            finishAffinity();
                        }
                    } else {
                        Utility.showToastShort(mContext, registration.getMsg());
                    }
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    private void ResendOtp() {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<LoginResponse> call = redditAPI.ResendOTP(user_phoneno);
        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                Log.d("ResponseOTP", "" + response);
                if (response.isSuccessful()) {
                    Utility.showToastShort(mContext, response.body().getMsg());
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }
}
