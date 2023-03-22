package com.store;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.Dashboard;
import com.store.promtech.ProductCart;
import com.store.promtech.R;
import com.store.promtech.SeasonalProductDetails;
import com.store.promtech.adapter.SeasonalAdapter;
import com.store.promtech.model.Seasonal;
import com.store.promtech.model.TermsConditionmodel;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.CircularTextView;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Preferences;
import com.store.promtech.utils.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SeasonalActivity extends AppCompatActivity implements View.OnClickListener,SeasonalAdapter.AdapterPosition,SeasonalAdapter.UpdateCartCount{

    Context mContext;
    ImageView btn_menu, btn_back;
    ConnectionDetector cd;
    TextView tv_pagename;
    TextView tv_title, tv_desc;
    TermsConditionmodel aboutUs;
    ProgressDialog pDialog;
    CircularTextView tv_cartcount;
    ImageView iv_cart;
   public static Seasonal getSeasonal;
    RecyclerView rv_mostselling;
    ImageView iv_banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasonal);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);

        initView();
    }

    private void initView() {
        ImageView iv_home=findViewById(R.id.iv_home);


        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext, Dashboard.class);
                startActivity(i);
                finishAffinity();
            }
        });
        iv_banner=findViewById(R.id.iv_banner);

        Glide.with(mContext)
                .load("http://www.rajtutorialgroup.com/xesors/uploads/user/IMG_20201014_133629.jpg")
                .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                .into(iv_banner);

        tv_pagename = findViewById(R.id.tv_pagename);
        rv_mostselling=findViewById(R.id.rv_mostselling);
        btn_back = findViewById(R.id.btn_back);
        btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setVisibility(View.GONE);
        btn_back.setVisibility(View.VISIBLE);
        btn_menu.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        tv_cartcount =  findViewById(R.id.tv_cartcount);
        String colorStr = getResources().getString(R.string.green_color);
        tv_cartcount.setSolidColor(colorStr);
        tv_title = findViewById(R.id.tv_title);
        tv_desc = findViewById(R.id.tv_desc);
        iv_cart = findViewById(R.id.iv_cart);
        iv_cart.setOnClickListener(this);
        rv_mostselling.setLayoutManager(new LinearLayoutManager(mContext));

        ImageView iv_phone=findViewById(R.id.iv_phone);
        iv_phone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Utility.CallContactNo(mContext);
            }
        });

        if (cd.isConnected()) {
          //  parsejsonSeasonalProduct();
        } else {
            Utility.showToastShort(mContext, getString(R.string.no_internet_msg));
        }
        //tv_pagename.setText(SubCategoryPage.productModel.getProductData().get(position).getProductDetails());
    }

    private void parsejsonSeasonalProduct() {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<Seasonal> call = redditAPI.GetSeasonalProduct("0","0");
        call.enqueue(new Callback<Seasonal>() {

            @Override
            public void onResponse(Call<Seasonal> call, retrofit2.Response<Seasonal> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    getSeasonal = response.body();
                    if (getSeasonal.getProductData().size() > 0) {

                        try {


                            Glide.with(mContext)
                                    .load(getSeasonal.getBanner_photo())
                                    .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                                    .into(iv_banner);

                        } catch (Exception e) {
                        }
                        setupSeasonal();
                    }
                }


            }

            @Override
            public void onFailure(Call<Seasonal> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setupSeasonal() {
        SeasonalAdapter ca = new SeasonalAdapter( getSeasonal.getProductData(),mContext,"Seasonal",this,this);
        rv_mostselling.setAdapter(ca);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_cartcount.setText(Preferences.get_Cartount(mContext));
    }

    private void setData() {
        tv_title.setText(aboutUs.getAboutData().contentTitle);
        tv_desc.setText(aboutUs.getAboutData().getContent());
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

    @Override
    public void adapterPositions(int pos) {
        Intent a = new Intent(mContext, SeasonalProductDetails.class);
        a.putExtra("position", "" + pos);
        a.putExtra("category_id", "");
        mContext.startActivity(a);
    }

    @Override
    public void updateCartCounts() {
        tv_cartcount.setText(Preferences.get_Cartount(mContext));
    }
}
