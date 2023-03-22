package com.store.promtech;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.store.promtech.model.LoginResponse;
import com.store.promtech.model.ZipCodemodel;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Preferences;
import com.store.promtech.utils.Utility;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;

    EditText et_phoneno;
    EditText et_password;
    Button btn_register, btn_login, btn_forgotpassword;
    ConnectionDetector cd;
    ProgressDialog pDialog;
    String user_phone, user_password;
    LoginResponse registration;
    ZipCodemodel zipCodemodel;
    List<String> list_text = new ArrayList<>();
    String deviceToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Please Wait...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);



        initViews();

    }

    private void initViews() {
        et_phoneno = findViewById(R.id.et_phoneno);
        et_password = findViewById(R.id.et_password);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);
        btn_forgotpassword = findViewById(R.id.btn_forgotpassword);

        btn_forgotpassword.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btn_login) {
            if (et_phoneno.getText().toString().isEmpty()) {
                Utility.showToastShort(mContext, "Please Enter Phone No.");
                return;
            }
            if (et_password.getText().toString().isEmpty()) {
                Utility.showToastShort(mContext, "Please Enter Password");
                return;
            }

            user_phone = et_phoneno.getText().toString().trim();
            user_password = et_password.getText().toString().trim();

            verifyUser();

        } else if (v == btn_register) {

           //fetchZipCode();
            Intent i = new Intent(mContext, RegisterActivity.class);
            startActivity(i);
        } else if (v == btn_forgotpassword) {
            Intent i = new Intent(mContext, ForgotPasswordActivity.class);
            startActivity(i);
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
        Call<LoginResponse> call = redditAPI.UserLogin(user_phone, user_password);
        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    registration = response.body();
                    Log.d("Stringjwgerje", "" + registration);
                    if (registration.getAck() == 1) {
                        Preferences.setisVerified(mContext, true);
                        Preferences.set_firstuserName(mContext, registration.getLoginData().get(0).getName());

                        Preferences.set_userPhone(mContext, registration.getLoginData().get(0).getPhone());
                        Preferences.set_userId(mContext, registration.getLoginData().get(0).getUserId());

                        Intent intent = new Intent(mContext, Dashboard.class);
                        intent.putExtra("tag","otp");
                        intent.putExtra("msg",registration.getMsg());
                        startActivity(intent);
                        finishAffinity();
                            /*Intent intent = new Intent(mContext, OTPActivity.class);
                            intent.putExtra("user_phoneno", user_phone);
                            Preferences.set_userId(mContext, registration.getUserId());
                            startActivity(intent);*/
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


    private void fetchZipCode() {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<ZipCodemodel> call = redditAPI.GetZipCodeList();
        call.enqueue(new Callback<ZipCodemodel>() {

            @Override
            public void onResponse(Call<ZipCodemodel> call, retrofit2.Response<ZipCodemodel> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    zipCodemodel = response.body();
                    if (zipCodemodel.getAck() == 1) {
                        if (zipCodemodel.getZipData().size() > 0) {
                            list_text.clear();
                            for (int i = 0; i < zipCodemodel.getZipData().size(); i++) {
                                list_text.add(zipCodemodel.getZipData().get(i).getAvailableZipcode());
                            }
                            showCustomDialog();
                        }
                    } else {
                        Utility.showToastShort(mContext, zipCodemodel.getMsg());
                    }
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ZipCodemodel> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }


    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialog, viewGroup, false);
        FlexboxLayout container = dialogView.findViewById(R.id.v_container);
        Button btn_proceed = dialogView.findViewById(R.id.btn_proceed);
        Button btn_cancel = dialogView.findViewById(R.id.btn_cancel);


        inflatelayout(container);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent i = new Intent(mContext, RegisterActivity.class);
                startActivity(i);
            }
        });

    }


    private void inflatelayout(FlexboxLayout container) {
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(5, 5, 5, 5);
        for (int i = 0; i < list_text.size(); i++) {
            final TextView tv = new TextView(getApplicationContext());
            tv.setText(list_text.get(i));
            tv.setTextSize(16.0f);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.parseColor("#555555"));
            tv.setBackground(getResources().getDrawable(R.drawable.rounded_corner_flex));
            tv.setId(i + 1);
            tv.setLayoutParams(buttonLayoutParams);
            tv.setTag(i);
            tv.setPadding(20, 0, 20, 10);
            container.addView(tv);
        }

    }


}
