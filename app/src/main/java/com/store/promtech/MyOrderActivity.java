package com.store.promtech;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.adapter.MyOrdersAdapter;
import com.store.promtech.model.MyOrders;
import com.store.promtech.productdialog.ReturnedProductDialog;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Preferences;
import com.store.promtech.utils.Utility;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyOrderActivity extends AppCompatActivity implements View.OnClickListener,MyOrdersAdapter.Interaction {

    Context mContext;
    ImageView btn_menu, btn_back;
    ConnectionDetector cd;
    TextView tv_pagename;
    ProgressDialog pDialog;
    MyOrders myCart;
    public static List<MyOrders.OrderDatum> listmycart = new ArrayList<>();
    String ProductId = "", PacketId = "", From;
    RecyclerView rl_cart;
    FrameLayout cartvie;
    TextView tv_orderdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorders);
        cd = new ConnectionDetector(mContext);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);

        tv_orderdetails = findViewById(R.id.tv_orderdetails);
        rl_cart = findViewById(R.id.rl_cart);
        rl_cart.setLayoutManager(new LinearLayoutManager(mContext));

        if (cd.isConnected()) {
            LoadCartProduct();
        } else {
            Utility.showToastShort(mContext, getResources().getString(R.string.no_internet_msg));
        }
        //  }
        initView();
    }

    private void initView() {
        ImageView iv_home = findViewById(R.id.iv_home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, Dashboard.class);
                startActivity(i);
                finishAffinity();
            }
        });
        tv_pagename = findViewById(R.id.tv_pagename);
        cartvie = findViewById(R.id.cartvie);
        cartvie.setVisibility(View.GONE);
        btn_back = findViewById(R.id.btn_back);
        btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setVisibility(View.GONE);
        btn_back.setVisibility(View.VISIBLE);
        btn_menu.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        ImageView iv_phone = findViewById(R.id.iv_phone);
        iv_phone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Utility.CallContactNo(mContext);
            }
        });
        //tv_pagename.setText("My Orders");
    }

    @Override
    public void onClick(View v) {
        if (v == btn_back) {
            finish();
            onBackPressed();
        }
    }

    public void LoadCartProduct() {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<MyOrders> call = redditAPI.GetMyOrdes(Preferences.get_userId(mContext));
        call.enqueue(new Callback<MyOrders>() {

            @Override
            public void onResponse(Call<MyOrders> call, retrofit2.Response<MyOrders> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    myCart = response.body();
                    if (myCart.getAck() == 1) {
                        listmycart = myCart.getOrderData();
                        if (listmycart.size() > 0) {
                            inflateCartAdapter();
                        } else {
                            Utility.showToastShort(mContext, "No Data Found");
                        }
                    } else {
                        rl_cart.setVisibility(View.GONE);
                        Utility.showToastShort(mContext, myCart.getMsg());
                    }
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MyOrders> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    private void inflateCartAdapter() {
        MyOrdersAdapter mAdapter = new MyOrdersAdapter(listmycart, mContext,this);
        rl_cart.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadCartProduct();
    }

    @Override
    public void onCommentItemSelected(int position, MyOrders.OrderDatum data) {
        //Toast.makeText(mContext,data.getOrderId(),Toast.LENGTH_SHORT).show();
        ReturnedProductDialog dialogFragment=new ReturnedProductDialog(data.getOrderId(),mContext);
        dialogFragment.show(getSupportFragmentManager(),"My  Fragment");
    }
}
