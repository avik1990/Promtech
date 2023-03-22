package com.store.promtech;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.adapter.SubCategoryAdapter;
import com.store.promtech.model.SubCategoryDataResponse;
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

public class SubCategoryPage extends AppCompatActivity implements View.OnClickListener, SubCategoryAdapter.AdapterPos {

    Context mContext;
    ImageView btn_menu, btn_back;
    RecyclerView rv_product_listing;
    ConnectionDetector cd;
    ProgressDialog pDialog;
    public static SubCategoryDataResponse productModel;
    String category_id, category_name;
    TextView tv_pagename;
    CircularTextView tv_cartcount,tv_wallet;
    ImageView iv_cart,iv_wallet;
    SearchView v_searcview;
    ImageView iv_search;
    EditText searchEditText;
    ImageView iv_banner;
    FrameLayout walletvie,cartvie;
    LinearLayout ll_sub_cat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);

        category_id = getIntent().getExtras().getString("cat_id");
        category_name = getIntent().getExtras().getString("from");

        if (!category_name.isEmpty()) {
            if (category_name.contains("/")) {
                String a[] = category_name.split("/");
                String catName = a[0];
                category_name=catName;
            }
        }

        initView();
        if (cd.isConnected()) {
            parsejson();
        } else {
            Utility.showToastShort(mContext, getString(R.string.no_internet_msg));
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
        Call<SubCategoryDataResponse> call = redditAPI.GeSubCategoryDataResponse(category_id);
        call.enqueue(new Callback<SubCategoryDataResponse>() {

            @Override
            public void onResponse(Call<SubCategoryDataResponse> call, retrofit2.Response<SubCategoryDataResponse> response) {
                Log.d("String", "" + response);
                productModel = response.body();
                if (productModel.getAck() == 1) {
                    if (productModel.getSubCategoryData().size() > 0) {
                        try {

                            Glide.with(mContext)
                                    .load(productModel.getBanner_photo())
                                    .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                                    .into(iv_banner);

                        } catch (Exception e) {
                        }
                        inflateAdapter();
                    }
                } else {
                    Utility.showToastShort(mContext, productModel.getMsg());
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SubCategoryDataResponse> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    private void initView() {
        ll_sub_cat=findViewById(R.id.ll_sub_cat);
        walletvie=findViewById(R.id.walletvie);
        cartvie=findViewById(R.id.cartvie);
        tv_wallet=findViewById(R.id.tv_wallet);
        iv_banner=findViewById(R.id.iv_banner);
        iv_banner.setVisibility(View.GONE);
        ImageView iv_home=findViewById(R.id.iv_home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,Dashboard.class);
                startActivity(i);
                finishAffinity();
            }
        });
        ll_sub_cat.setVisibility(View.GONE);
        rv_product_listing = findViewById(R.id.rv_product_listing);
        rv_product_listing.setLayoutManager(new GridLayoutManager(mContext, 2));
        tv_pagename = findViewById(R.id.tv_pagename);
        btn_back = findViewById(R.id.btn_back);
        btn_menu = findViewById(R.id.btn_menu);
        iv_wallet=findViewById(R.id.iv_wallet);
        walletvie.setOnClickListener(this);
        walletvie.setVisibility(View.GONE);
        btn_menu.setVisibility(View.GONE);
        btn_back.setVisibility(View.VISIBLE);
        btn_menu.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        tv_cartcount = (CircularTextView) findViewById(R.id.tv_cartcount);
        String colorStr = getResources().getString(R.string.green_color);
        tv_cartcount.setSolidColor(colorStr);
        tv_pagename.setText(category_name);
        iv_cart = findViewById(R.id.iv_cart);
        cartvie.setOnClickListener(this);
        v_searcview = findViewById(R.id.v_searcview);
        v_searcview.setOnClickListener(this);
        iv_search = findViewById(R.id.iv_search);
        iv_search.setVisibility(View.GONE);
        iv_search.setOnClickListener(this);

        searchEditText = v_searcview.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setCursorVisible(true);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setCursorVisible(true);
        /*searchEditText.setHintTextColor(getResources().getColor(R.color.black));*/
        tv_wallet.setSolidColor(colorStr);
        Float wallet=Float.parseFloat(Preferences.get_userWallet(mContext));
        int a = Math.round(wallet);
        tv_wallet.setText(a+"");
        ImageView closeButton = (ImageView) this.v_searcview.findViewById(R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                iv_search.setVisibility(View.VISIBLE);
                tv_pagename.setVisibility(View.VISIBLE);
                v_searcview.setVisibility(View.GONE);
                searchEditText.setText("");
            }
        });

        ImageView iv_phone=findViewById(R.id.iv_phone);
        iv_phone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Utility.CallContactNo(mContext);
            }
        });


        v_searcview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
              //  getSearchedProduct(s.toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_cartcount.setText(Preferences.get_Cartount(mContext));

    }

    private void inflateAdapter() {
        SubCategoryAdapter ca = new SubCategoryAdapter(mContext, productModel.getSubCategoryData(), this);
        rv_product_listing.setAdapter(ca);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_back) {
            finish();
            onBackPressed();
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } else if (v == cartvie) {
            Intent i = new Intent(mContext, ProductCart.class);
            i.putExtra("From", "Dashboard");
            startActivity(i);
        } else if (v == iv_search) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(v_searcview.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
            v_searcview.requestFocus();
            iv_search.setVisibility(View.GONE);
            v_searcview.setVisibility(View.VISIBLE);
            tv_pagename.setVisibility(View.GONE);
            searchEditText.setText("");
        }
        else if(v==walletvie)
        {
            Intent i = new Intent(mContext, WalletActivity.class);
            i.putExtra("From", "Dashboard");
            startActivity(i);
        }
    }


    @Override
    public void adapterPositionSub(int pos) {
        //Utility.showToastShort(mContext, "" + productModel.getProductData());
        if (productModel.getSubCategoryData().get(pos).getNo_of_subsubcategory()==0) {
            Intent a = new Intent(mContext, ProductPage.class);
            a.putExtra("position", "" + pos);
            a.putExtra("category_id", category_id);
            a.putExtra("from", productModel.getSubCategoryData().get(pos).getSubcategoryName());
            a.putExtra("sub_category_id", productModel.getSubCategoryData().get(pos).getSubcategoryId());
            mContext.startActivity(a);
        }else {
            Intent a = new Intent(mContext, SubSubCategoryPage.class);
            a.putExtra("position", "" + pos);
            a.putExtra("cat_id", category_id);
            a.putExtra("from", productModel.getSubCategoryData().get(pos).getSubcategoryName());
            a.putExtra("sub_category_id", productModel.getSubCategoryData().get(pos).getSubcategoryId());
            mContext.startActivity(a);
        }
    }


    /*private void getSearchedProduct(final String searchString) {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);

        Call<SubCategoryDataResponse> call = redditAPI.Getsearch_food_list1(category_id, Preferences.get_userId(mContext), Preferences.get_UniqueId(mContext), searchString);
        call.enqueue(new Callback<SubCategoryDataResponse>() {

            @Override
            public void onResponse(Call<SubCategoryDataResponse> call, retrofit2.Response<SubCategoryDataResponse> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    productModel = response.body();
                    if (productModel.getAck() == 1) {
                        if (productModel.getSubCategoryData().size() > 0) {
                            Intent i = new Intent(mContext, SearchedProductPage.class);
                            i.putExtra("searchQuery", searchString);
                            i.putExtra("cat_id", category_id);
                            startActivity(i);
                        }
                    } else {
                        Utility.showToastShort(mContext, productModel.getMsg());
                    }
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SubCategoryDataResponse> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }*/
}
