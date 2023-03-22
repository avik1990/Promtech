package com.store.promtech;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.store.promtech.model.CartDeleteAction;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.CircularTextView;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Preferences;
import com.store.promtech.utils.Utility;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Context mContext;
    Button btn_placeorder;
    ConnectionDetector cd;
    ProgressDialog pDialog;
    String user_name, user_email, user_phone, user_comment,user_rate;
    CartDeleteAction registration;
    TextView tv_pagename;
    FrameLayout cartvie;
    ImageView btn_menu, btn_back;
    EditText et_comment;
    CircularTextView tv_cartcount;
    Spinner sp_rate;
    String[] courses = { "Please Rate this App", "Poor",
            "Average", "Good",
            "Very Good", "Excellent" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        mContext = this;

        if(Preferences.get_userId(mContext).equals("0")){
            Intent i = new Intent(mContext, LoginActivity.class);
            startActivity(i);
            finishAffinity();
        }
        cd = new ConnectionDetector(mContext);
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Please Wait...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);


        initViews();

        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                courses);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        sp_rate.setAdapter(ad);

    }

    private void initViews() {
        user_rate= "";
        ImageView iv_home=findViewById(R.id.iv_home);
        sp_rate=findViewById(R.id.sp_rate);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,Dashboard.class);
                startActivity(i);
                finishAffinity();
            }
        });
        cartvie = findViewById(R.id.cartvie);
        cartvie.setVisibility(View.GONE);
        tv_pagename = findViewById(R.id.tv_pagename);

        btn_placeorder = findViewById(R.id.btn_placeorder);
        //tv_pagename.setText("Feedback");
        btn_placeorder.setOnClickListener(this);

        btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setVisibility(View.GONE);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setVisibility(View.VISIBLE);

        et_comment = findViewById(R.id.et_comment);
        btn_menu.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        sp_rate.setOnItemSelectedListener(this);
        tv_cartcount = (CircularTextView) findViewById(R.id.tv_cartcount);
        tv_cartcount.setVisibility(View.VISIBLE);
        String colorStr = getResources().getString(R.string.green_color);
        tv_cartcount.setSolidColor(colorStr);
       /* if(Preferences.get_firstName(mContext).equals("0"))
        {
            et_name.setText("");
            et_email.setText("");
            et_phoneno.setText("");
        }
        else {
            et_name.setText(Preferences.get_firstName(mContext) + " " + Preferences.get_lastName(mContext));
            et_email.setText(Preferences.get_userEmail(mContext));
            et_phoneno.setText(Preferences.get_userPhone(mContext));
        }*/
        ImageView iv_phone=findViewById(R.id.iv_phone);
        iv_phone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Utility.CallContactNo(mContext);

            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == btn_placeorder) {

            if (et_comment.getText().toString().isEmpty()) {
                Utility.showToastShort(mContext, "Please Enter Comment");
                return;
            }
            if (user_rate.isEmpty()) {
                Utility.showToastShort(mContext, "Please Rate this App");
                return;
            }



            user_comment = et_comment.getText().toString().trim();



            postShippingDetails();

        } else if (v == btn_back) {
            onBackPressed();
            finish();
        }
    }

    private void postShippingDetails() {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);

        Call<CartDeleteAction> call = redditAPI.PostFeedback(Preferences.get_userId(mContext), user_comment, user_rate);
        call.enqueue(new Callback<CartDeleteAction>() {

            @Override
            public void onResponse(Call<CartDeleteAction> call, retrofit2.Response<CartDeleteAction> response) {
                pDialog.dismiss();
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    registration = response.body();
                    if (registration.getAck().equals("1")) {
                        pDialog.dismiss();
                        Utility.showToastShort(mContext, "Feedback Submitted Successfully");
                        onBackPressed();
                    } else {
                        Utility.showToastShort(mContext, registration.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<CartDeleteAction> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }





    public void showDialog(Context activity, String message) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_thankyou_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final TextView et_pincode = dialog.findViewById(R.id.et_pincode);
        et_pincode.setText(message);
        FrameLayout mDialogOk = dialog.findViewById(R.id.frmOk);
        mDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, Dashboard.class);
                startActivity(i);
                finishAffinity();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_cartcount.setText(Preferences.get_Cartount(mContext));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0){
            user_rate = "";
        }else {
            user_rate = courses[position];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
