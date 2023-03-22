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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.adapter.CartAdapter;
import com.store.promtech.model.AddToCart;
import com.store.promtech.model.MyCart;
import com.store.promtech.model.TimeZone;
import com.store.promtech.model.ZipCodeVerify;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Preferences;
import com.store.promtech.utils.Utility;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductCart extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ImageView btn_menu, btn_back;
    ConnectionDetector cd;
    TextView tv_pagename;
    ProgressDialog pDialog;
    AddToCart addToCart;
    RelativeLayout llEmpty,llContainer;
    MyCart myCart;
    public static List<MyCart.CartDatum> listmycart = new ArrayList<>();
    String ProductId = "", PacketId = "", From;
    RecyclerView rl_cart;
    LinearLayout footer, footerBtn;
    TextView tv_totalprice, tv_discount, tv_taxpercentage, tv_grandtotdal, tv_cashback;
    Button btn_checkout;
    ZipCodeVerify zipCodeVerify;
    FrameLayout cartvie;
    Button btn_backhome;
    TextView tv_orderdetails;
    CheckBox cb_quickdelivery;
    TextView tv_delivery,tv_wallaet_balance;
    String isQuickDelivery = "0";
    TimeZone timeZone;
    String deliveryType = "home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cart);
        cd = new ConnectionDetector(mContext);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);

        /*ProductId = getIntent().getStringExtra("ProductId");
        PacketId = getIntent().getStringExtra("PacketId");
        From = getIntent().getStringExtra("From");*/
        btn_backhome = findViewById(R.id.btn_backhome);
        tv_orderdetails = findViewById(R.id.tv_orderdetails);
        tv_cashback = findViewById(R.id.tv_cashback);
        llContainer=findViewById(R.id.llContainer);
        llEmpty=findViewById(R.id.llEmpty);
        btn_backhome.setOnClickListener(this);
        rl_cart = findViewById(R.id.rl_cart);
        rl_cart.setLayoutManager(new LinearLayoutManager(mContext));
        tv_delivery = findViewById(R.id.tv_delivery);
        /*if (From.equals("ProductDetails")) {
            if (cd.isConnected()) {
                AddToCart();
            } else {
                Utility.showToastShort(mContext, getResources().getString(R.string.no_internet_msg));
            }
        } else {*/
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

        tv_wallaet_balance=findViewById(R.id.tv_wallaet_balance);
        cb_quickdelivery = findViewById(R.id.cb_quickdelivery);
        tv_pagename = findViewById(R.id.tv_pagename);
        tv_totalprice = findViewById(R.id.tv_totalprice);
        tv_discount = findViewById(R.id.tv_discount);
        tv_taxpercentage = findViewById(R.id.tv_taxpercentage);
        tv_grandtotdal = findViewById(R.id.tv_grandtotdal);
        footer = findViewById(R.id.footer);
        footerBtn = findViewById(R.id.footerBtn);
        btn_checkout = findViewById(R.id.btn_checkout);
        cartvie = findViewById(R.id.cartvie);
        cartvie.setVisibility(View.GONE);
        btn_back = findViewById(R.id.btn_back);
        btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setVisibility(View.GONE);
        btn_back.setVisibility(View.VISIBLE);
        btn_menu.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_checkout.setOnClickListener(this);
        tv_pagename.setText("My Basket");

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
        if (v == btn_back) {
            finish();
            onBackPressed();
        } else if (v == btn_checkout) {
            if (Preferences.getisVerified(mContext)) {
                //showDialog(this);

                Intent i = new Intent(mContext, Shippingactivity.class);
                i.putExtra("solt_date","");
                i.putExtra("slot_time","");
                i.putExtra("deliveryType",deliveryType);
                i.putExtra("quick_delivery",isQuickDelivery);
                i.putExtra("total_amount",tv_grandtotdal.getText().toString().trim());
                i.putExtra("deliveryType",deliveryType);
                startActivity(i);
            } else {
                Preferences.set_checkClicked(mContext, "1");
                Intent i = new Intent(mContext, LoginActivity.class);
                startActivity(i);
            }
        } else if (v == btn_backhome) {
            Intent i = new Intent(mContext, Dashboard.class);
            startActivity(i);
            finishAffinity();
        }
    }


    public void showDialog(Context activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_delivery_type);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        final Button btn_submit=dialog.findViewById(R.id.btn_submit);
        final AppCompatRadioButton rbHome=dialog.findViewById(R.id.rbHome);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

                deliveryType = rbHome.isChecked() ? "home" : "local";

                parsejsonTime();
            }
        });

        dialog.show();
    }


    private void parsejsonTime() {

        pDialog.dismiss();
        Intent i = new Intent(mContext, TimeSlotActivity.class);
        i.putExtra("quick_delivery",isQuickDelivery);
        i.putExtra("total_amount",tv_grandtotdal.getText().toString().trim());
        i.putExtra("deliveryType",deliveryType);
        startActivity(i);

        pDialog.show();
        String BASE_URL ="http://worldtimeapi.org/api/timezone/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<TimeZone> call = redditAPI.GetTimeZone();
        call.enqueue(new Callback<TimeZone>() {

            @Override
            public void onResponse(Call<TimeZone> call, retrofit2.Response<TimeZone> response) {
                Log.d("DateTime", "" + response);
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    timeZone = response.body();
                    String hr=timeZone.getDatetime().substring(11,19);
                    //Toast.makeText(getApplicationContext(),hr,Toast.LENGTH_SHORT).show();
                    String pattern = "HH:mm";
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);

                    try {
                        Date start = sdf.parse("23:01");
                        Date end = sdf.parse("07:01");
                        Date time=sdf.parse(hr);

                        if(time.before(end)||time.after(start)) {

                     //       Toast.makeText(getApplicationContext(),"You can't order your products from 11PM to 7AM.",Toast.LENGTH_SHORT).show();
                        } else {

//                            Intent i = new Intent(mContext, TimeSlotActivity.class);
//                               i.putExtra("quick_delivery",isQuickDelivery);
//                               i.putExtra("total_amount",tv_grandtotdal.getText().toString().trim());
//                                startActivity(i);
                        }
                    } catch (ParseException e){
                        e.printStackTrace();
                    }


                }
                else
                {
                    pDialog.dismiss();
//                    Intent i = new Intent(mContext, TimeSlotActivity.class);
//                    i.putExtra("quick_delivery",isQuickDelivery);
//                    i.putExtra("total_amount",tv_grandtotdal.getText().toString().trim());
//                    startActivity(i);
                }

            }

            @Override
            public void onFailure(Call<TimeZone> call, Throwable t) {
                pDialog.dismiss();

            }
        });
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
        Call<MyCart> call = redditAPI.GetMyCart(Preferences.get_userId(mContext), Preferences.get_UniqueId(mContext));
        call.enqueue(new Callback<MyCart>() {

            @Override
            public void onResponse(Call<MyCart> call, retrofit2.Response<MyCart> response) {
                Log.d("String", "" + response);
                pDialog.dismiss();
                if (response.isSuccessful()) {

                    myCart = response.body();
                    if (myCart.getAck()==1) {
                        llContainer.setVisibility(View.VISIBLE);
                        llEmpty.setVisibility(View.GONE);
                        tv_orderdetails.setVisibility(View.VISIBLE);
                        //Preferences.set_Cartount(mContext, myCart.getPriceData().getTotal_quantity());
                        rl_cart.setVisibility(View.VISIBLE);
                        listmycart = myCart.getCartData();
                        inflateCartAdapter();
                    } else {
                        llContainer.setVisibility(View.GONE);
                        llEmpty.setVisibility(View.VISIBLE);
                        tv_orderdetails.setVisibility(View.GONE);
                        footerBtn.setVisibility(View.GONE);
                        footer.setVisibility(View.GONE);
                        //Utility.showToastShort(mContext, myCart.getMsg());
                        btn_checkout.setVisibility(View.GONE);
                        rl_cart.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFailure(Call<MyCart> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    private void inflateCartAdapter() {
        CartAdapter mAdapter = new CartAdapter(listmycart, mContext);
        rl_cart.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        tv_totalprice.setText( myCart.getPriceData().totalPrice);
        tv_discount.setText(myCart.getPriceData().discount);
        tv_cashback.setText(myCart.getPriceData().cashback);
        //tv_wallaet_balance.setText(myCart.getPriceData().wallet_balance);


        /*if (Double.parseDouble(myCart.getPriceData().totalPrice) >= 250) {
            tv_taxpercentage.setVisibility(View.GONE);
            tv_delivery.setVisibility(View.GONE);
        } else {
            tv_taxpercentage.setVisibility(View.VISIBLE);
            tv_delivery.setVisibility(View.VISIBLE);
            tv_taxpercentage.setText( myCart.getPriceData().delivery_charge);
        }*/
        tv_taxpercentage.setText( myCart.getPriceData().delivery_charge);
        tv_grandtotdal.setText(myCart.getPriceData().grand_total);
        footer.setVisibility(View.VISIBLE);

        cb_quickdelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isQuickDelivery = "1";
                    tv_grandtotdal.setText(""+(Double.parseDouble(myCart.getPriceData().grand_total) + 15.00));
                } else {
                    isQuickDelivery = "0";
                    tv_grandtotdal.setText( myCart.getPriceData().grand_total);
                }
            }
        });


    }
}
