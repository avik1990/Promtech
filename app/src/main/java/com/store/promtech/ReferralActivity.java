package com.store.promtech;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.adapter.WalletAdapter;
import com.store.promtech.model.TermsConditionmodel;
import com.store.promtech.model.Referral;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.CircularTextView;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Preferences;
import com.store.promtech.utils.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReferralActivity extends AppCompatActivity implements View.OnClickListener{

    Context mContext;
    Referral getWallet;
    ImageView btn_menu, btn_back;
    ConnectionDetector cd;
    TextView tv_pagename,tv_balance,tv_referal_code,tv_text;
    TextView tv_title;
    TermsConditionmodel aboutUs;
    ProgressDialog pDialog;
    CircularTextView tv_cartcount;
    ImageView iv_cart;
    RecyclerView rv_wallet;
    Button btn_share_now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);

        initView();
    }


    private void initView() {
        btn_share_now=findViewById(R.id.btn_share_now);
        tv_referal_code=findViewById(R.id.tv_referal_code);
        ImageView iv_home=findViewById(R.id.iv_home);
        tv_balance=findViewById(R.id.tv_balance);
        rv_wallet=findViewById(R.id.rv_wallet);
        rv_wallet.setLayoutManager(new GridLayoutManager(mContext,1));
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,Dashboard.class);
                startActivity(i);
                finishAffinity();
            }
        });
        tv_pagename = findViewById(R.id.tv_pagename);
        btn_back = findViewById(R.id.btn_back);
        btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setVisibility(View.GONE);
        btn_back.setVisibility(View.VISIBLE);
        btn_menu.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        tv_cartcount =  findViewById(R.id.tv_cartcount);
        tv_text =  findViewById(R.id.tv_text);
        String colorStr = getResources().getString(R.string.green_color);
        tv_cartcount.setSolidColor(colorStr);
        tv_title = findViewById(R.id.tv_title);

        iv_cart = findViewById(R.id.iv_cart);
        iv_cart.setOnClickListener(this);

        ImageView iv_phone=findViewById(R.id.iv_phone);
        iv_phone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Utility.CallContactNo(mContext);
            }
        });


        btn_share_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MacawTv");
                String shareMessage= "\nLet me recommend you this application, Reference code : "+tv_referal_code.getText().toString()+" \n\n";
                final String appPackageName = getPackageName();
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + appPackageName +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            }
        });

        if (cd.isConnected()) {
            parsejson();
        } else {
            Utility.showToastShort(mContext, getString(R.string.no_internet_msg));
        }
        //tv_pagename.setText(SubCategoryPage.productModel.getProductData().get(position).getProductDetails());
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_cartcount.setText(Preferences.get_Cartount(mContext));
    }



    @Override
    public void onClick(View v) {
        if (v == btn_back) {
            finish();
            onBackPressed();
        }else if (v == iv_cart) {
            Intent i = new Intent(mContext, ProductCart.class);
            i.putExtra("From", "Dashboard");
            startActivity(i);
        }
    }


    private void parsejson() {
        pDialog.show();

        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<Referral> call = redditAPI.GetReferralBenefit(Preferences.get_userId(mContext));
        call.enqueue(new Callback<Referral>() {

            @Override
            public void onResponse(Call<Referral> call, retrofit2.Response<Referral> response) {
                Log.d("String", "" + response);
                pDialog.dismiss();
                if (response.isSuccessful()) {
                    getWallet = response.body();
                    if (getWallet.getAck()==1) {
                        try {
                            if (getWallet.getRefData().size() > 0) {
                                setupRecylerview();
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        tv_referal_code.setText(getWallet.getRef_code());
                        tv_text.setText(getWallet.getWallet_text());
                      //  tv_balance.setText(getWallet.getWalletBalance());
                    }
                    else
                    {
                        tv_balance.setText("0");
                        tv_referal_code.setText(getWallet.getRef_code());
                    }
                }

            }

            @Override
            public void onFailure(Call<Referral> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setupRecylerview() {
        WalletAdapter ca = new WalletAdapter(mContext, getWallet.getRefData());
        rv_wallet.setAdapter(ca);
    }
}
