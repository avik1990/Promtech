package com.store.promtech.returnedproadapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.store.promtech.Dashboard;
import com.store.promtech.LoginActivity;
import com.store.promtech.R;
import com.store.promtech.returnedproadapter.ReturnProductAdapter;
import com.store.promtech.model.MyOrdersDetailsModel;
import com.store.promtech.model.ReturnList;
import com.store.promtech.model.ZipCodeVerify;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Preferences;
import com.store.promtech.utils.Utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReturnProductOrderDetails extends AppCompatActivity implements View.OnClickListener,ReturnProductAdapter.Interaction {

    Context mContext;
    ImageView btn_menu, btn_back;
    ConnectionDetector cd;
    TextView tv_pagename;
    ProgressDialog pDialog;
    MyOrdersDetailsModel myCart;
    ZipCodeVerify ackMsg;
    public static List<MyOrdersDetailsModel.CartDatum> listmycart = new ArrayList<>();
    String ProductId = "", PacketId = "", From;
    RecyclerView rl_cart;
    LinearLayout footer, footerBtn;
    TextView tv_totalprice, tv_taxpercentage, tv_grandtotdal,tv_wallaet_balance,tv_discount,tv_cashback;
    Button btn_checkout;
    ZipCodeVerify zipCodeVerify;
    FrameLayout cartvie;
    Button btn_backhome;
    TextView tv_orderdetails;
    TextView tv_quick,tv_delivery_slot;
    TextView tv_delivery,tv_return;
    String isQuickDelivery = "0", order_id;
    public  static MyOrdersDetailsModel getMyoderDetailsModel;
    private ArrayList<ReturnList> returnArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_order);
        cd = new ConnectionDetector(mContext);
        mContext = this;
        returnArray.clear();
        cd = new ConnectionDetector(mContext);
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);
        order_id = getIntent().getStringExtra("order_id");
        /*ProductId = getIntent().getStringExtra("ProductId");
        PacketId = getIntent().getStringExtra("PacketId");
        From = getIntent().getStringExtra("From");*/
        btn_backhome = findViewById(R.id.btn_backhome);
        //tv_orderdetails = findViewById(R.id.tv_orderdetails);

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
        tv_wallaet_balance=findViewById(R.id.tv_wallaet_balance);
        ImageView iv_home = findViewById(R.id.iv_home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, Dashboard.class);
                startActivity(i);
                finishAffinity();
            }
        });

        tv_quick = findViewById(R.id.tv_quick);
        tv_pagename = findViewById(R.id.tv_pagename);
        tv_totalprice = findViewById(R.id.tv_totalprice);
        tv_taxpercentage = findViewById(R.id.tv_taxpercentage);
        tv_grandtotdal = findViewById(R.id.tv_grandtotdal);
        tv_discount = findViewById(R.id.tv_discount);
        tv_cashback = findViewById(R.id.tv_cashback);
        footer = findViewById(R.id.footer);
        footerBtn = findViewById(R.id.footerBtn);
        btn_checkout = findViewById(R.id.btn_checkout);
        cartvie = findViewById(R.id.cartvie);
        cartvie.setVisibility(View.GONE);
        btn_back = findViewById(R.id.btn_back);
        btn_menu = findViewById(R.id.btn_menu);
        tv_return = findViewById(R.id.tv_return);
        btn_menu.setVisibility(View.GONE);
        btn_back.setVisibility(View.VISIBLE);
        btn_menu.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_checkout.setOnClickListener(this);
        tv_return.setOnClickListener(this);
        tv_pagename.setText("Order Details");

        ImageView iv_phone = findViewById(R.id.iv_phone);
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
                /*Intent i = new Intent(mContext, Shippingactivity.class);
                i.putExtra("quick_delivery", isQuickDelivery);
                startActivity(i);*/
            } else {
                Preferences.set_checkClicked(mContext, "1");
                Intent i = new Intent(mContext, LoginActivity.class);
                startActivity(i);
            }
        } else if (v == btn_backhome) {
            Intent i = new Intent(mContext, Dashboard.class);
            startActivity(i);
            finishAffinity();
        }else if (v == tv_return) {
            if(returnArray.size()>0) {
                for(int i=0; i<returnArray.size();i++) {
                    if (returnArray.get(i).getQuentity().equals("0") || returnArray.get(i).getQuentity().equals("0")) {
                        Utility.showToastShort(getApplicationContext(), "Please enter quantity");
                        return;
                    }
                }

                String returnProducts="";
                if (returnArray.size()>0) {
                    for (int i = 0; i < returnArray.size(); i++) {
                        returnProducts = returnProducts+returnArray.get(i).getProduct_id()+","+returnArray.get(i).getQuentity()+"||";
                        Log.i("returnProducts", returnProducts);
                    }
                }

               // loadReturn();
            }else{
                Utility.showToastShort(getApplicationContext(),"Please select product to return");
            }
        }
    }


    public void LoadCartProduct() {
        returnArray.clear();
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<MyOrdersDetailsModel> call = redditAPI.GetOrderedProductDetails(Preferences.get_userId(mContext), order_id);
        //Call<MyOrdersDetailsModel> call = redditAPI.GetOrderedProductDetails(Preferences.get_userId(mContext), Preferences.get_UniqueId(mContext), order_id);
        call.enqueue(new Callback<MyOrdersDetailsModel>() {

            @Override
            public void onResponse(Call<MyOrdersDetailsModel> call, retrofit2.Response<MyOrdersDetailsModel> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    myCart = response.body();
                    getMyoderDetailsModel=response.body();
                    if (myCart.getAck()==1) {
                       // tv_orderdetails.setVisibility(View.VISIBLE);
                        // Preferences.set_Cartount(mContext, myCart.getPriceData().getTotal_quantity());
                        rl_cart.setVisibility(View.VISIBLE);
                        listmycart = myCart.getCartData();
                        inflateCartAdapter();

                    } else {
                       // tv_orderdetails.setVisibility(View.GONE);
                        footerBtn.setVisibility(View.GONE);
                        footer.setVisibility(View.GONE);
                        Utility.showToastShort(mContext, myCart.getMsg());
                        btn_checkout.setVisibility(View.GONE);
                        rl_cart.setVisibility(View.GONE);
                    }
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MyOrdersDetailsModel> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    private void inflateCartAdapter() {
        try {

            if (listmycart.size() > 0) {
                ReturnProductAdapter mAdapter = new ReturnProductAdapter(listmycart, mContext,this::onItemChecked);
                rl_cart.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                tv_totalprice.setText( myCart.getPriceData().getTotalPrice());
                tv_wallaet_balance.setText(myCart.getPriceData().getWalletBalance());

              //  if (Double.parseDouble(myCart.getPriceData().getTotalPrice()) >= 200) {
                    tv_taxpercentage.setVisibility(View.VISIBLE);
                    tv_delivery.setVisibility(View.VISIBLE);
                tv_taxpercentage.setText( myCart.getPriceData().getDeliveryCharge());
//                } else {
//                    tv_taxpercentage.setVisibility(View.VISIBLE);
//                    tv_delivery.setVisibility(View.VISIBLE);
//                    tv_taxpercentage.setText( myCart.getPriceData().getDeliveryCharge());
//                }

                tv_grandtotdal.setText(myCart.getPriceData().getGrandTotal());
                tv_discount.setText(myCart.getPriceData().getDiscount());
                tv_cashback.setText(myCart.getPriceData().getCashback());
                footer.setVisibility(View.GONE);
               // tv_quick.setText(myCart.getPriceData().quickDelivery);
               /* cb_quickdelivery.setOnCheck
                    @OverrideedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            isQuickDelivery = "1";
                            tv_grandtotdal.setText("\u20A8" + ". " + (Double.parseDouble(myCart.getPriceData().grandTotal) + 10.00));
                        } else {
                            isQuickDelivery = "0";
                            tv_grandtotdal.setText("\u20A8" + ". " + myCart.getPriceData().grandTotal);
                        }
                    }
                });*/

            } else {
                footer.setVisibility(View.GONE);
            }

        } catch (Exception e) {

        }


    }
    public void loadReturn() {
        String returnProducts="";
        if (returnArray.size()>0) {
            for (int i = 0; i < returnArray.size(); i++) {
                returnProducts = returnProducts+returnArray.get(i).getProduct_id()+","+returnArray.get(i).getQuentity()+"||";
                Log.i("returnProducts", returnProducts);
            }
        }else {
            return;
        }
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<ZipCodeVerify> call = redditAPI.returnProduct(Preferences.get_userId(mContext), order_id,returnProducts);
        call.enqueue(new Callback<ZipCodeVerify>() {

            @Override
            public void onResponse(Call<ZipCodeVerify> call, retrofit2.Response<ZipCodeVerify> response) {
                Log.d("String", "" + response);
                pDialog.dismiss();
                if (response.isSuccessful()) {
                       ackMsg = response.body();
                    if (ackMsg.getAck().equals("1")) {
                        pDialog.dismiss();
                        Utility.showToastShort(mContext, ackMsg.getMsg());
                        onBackPressed();
                    } else {
                        Utility.showToastShort(mContext, ackMsg.getMsg());
                    }
                }

            }

            @Override
            public void onFailure(Call<ZipCodeVerify> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    @Override
    public void onItemChecked(int position, MyOrdersDetailsModel.CartDatum data,int flag,int tempdata) {
        Toast.makeText(mContext,position+": "+data.getProductId()+" :"+tempdata,Toast.LENGTH_SHORT).show();
       if(flag == 1){
           returnArray.add(new ReturnList(data.getProductId(), String.valueOf(tempdata)));
       }else {
           Iterator<ReturnList> iter = returnArray.iterator();
           while (iter.hasNext())
           {
               ReturnList user = iter.next();
               if(user.getProduct_id().equals(data.getProductId()))
               {
                   //Use iterator to remove this User object.
                   iter.remove();
               }
           }
           //returnArray.remove(position);
       }

    }
}
