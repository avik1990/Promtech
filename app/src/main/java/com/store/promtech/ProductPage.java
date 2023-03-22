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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.adapter.ProductAdapter;
import com.store.promtech.adapter.SubCategoryProductPageAdapter;
import com.store.promtech.model.MyCart;
import com.store.promtech.model.ProductListAcfold;
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

public class ProductPage extends AppCompatActivity implements View.OnClickListener,  SubCategoryProductPageAdapter.AdapterPos ,ProductAdapter.AdapterPos, ProductAdapter.UpdateCartCount {

    Context mContext;
    ImageView btn_menu, btn_back;
    RecyclerView rv_product_listing;
    ConnectionDetector cd;
    ProgressDialog pDialog;
    public static ProductListAcfold productModel;

    String category_id, category_name,brand_id;
    TextView tv_pagename;
    CircularTextView tv_cartcount,tv_wallet;
    ImageView iv_cart,iv_wallet;
    SearchView v_searcview;
    ImageView iv_search;
    EditText searchEditText;
    String sub_category_id="0",subSub_category_id="0",position;
    MyCart myCart;
    LinearLayout llContainer;
    RelativeLayout rlEmpty;
    ImageView iv_banner;
    FrameLayout walletvie,cartvie;
    RecyclerView rv_sub_cat;
    LinearLayout ll_sub_cat;
    public static SubCategoryDataResponse subCategoryDataResponse;
    SubCategoryProductPageAdapter ca;
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

        category_id = getIntent().getExtras().getString("category_id");
        category_name = getIntent().getExtras().getString("from");
        try {
            sub_category_id = getIntent().getExtras().getString("sub_category_id");
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            subSub_category_id = getIntent().getExtras().getString("subSub_category_id");
        }catch (Exception e){
            e.printStackTrace();
        }

        brand_id=getIntent().getExtras().getString("brand_id");
        position=getIntent().getExtras().getString("position");


        if (!category_name.isEmpty()) {
            if (category_name.contains("/")) {
                String a[] = category_name.split("/");
                String catName = a[0];
                category_name = catName;
            }
        }

        initView();

        if (cd.isConnected()) {
            parsejson();
            parsejsonSub();
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
        // category_id="3";
        //sub_category_id="3";

        Call<ProductListAcfold> call = redditAPI.GetProductListResponse(category_id, sub_category_id,subSub_category_id);
        call.enqueue(new Callback<ProductListAcfold>() {

            @Override
            public void onResponse(Call<ProductListAcfold> call, retrofit2.Response<ProductListAcfold> response) {
                Log.d("String", "" + response);
                productModel = response.body();
                if (productModel.getAck() == 1) {
                    if (productModel.getProductData().size() > 0) {
                        try {
                            Glide.with(mContext)
                                    .load(productModel.getProductData().get(1).getPhotos().get(0).getPhoto())
                                    .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                                    .into(iv_banner);


                        } catch (Exception e) {
                        }

                        llContainer.setVisibility(View.VISIBLE);
                        rlEmpty.setVisibility(View.GONE);
                        inflateAdapter();
                    }
                } else {
                    llContainer.setVisibility(View.GONE);
                    rlEmpty.setVisibility(View.VISIBLE);
                   // Utility.showToastShort(mContext, productModel.getMsg());
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ProductListAcfold> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    private void initView() {

        ll_sub_cat=findViewById(R.id.ll_sub_cat);
        rv_sub_cat=findViewById(R.id.rv_sub_cat);
        rv_sub_cat.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        cartvie=findViewById(R.id.cartvie);
        walletvie=findViewById(R.id.walletvie);
        iv_banner=findViewById(R.id.iv_banner);
        iv_banner.setVisibility(View.GONE);
        ImageView iv_home = findViewById(R.id.iv_home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, Dashboard.class);
                startActivity(i);
                finishAffinity();
            }
        });
        tv_wallet=findViewById(R.id.tv_wallet);
        llContainer=findViewById(R.id.llContainer);
        rlEmpty=findViewById(R.id.rlEmpty);
        rv_product_listing = findViewById(R.id.rv_product_listing);
        rv_product_listing.setLayoutManager(new GridLayoutManager(mContext, 2));
        tv_pagename = findViewById(R.id.tv_pagename);
        btn_back = findViewById(R.id.btn_back);
        btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setVisibility(View.GONE);
        btn_back.setVisibility(View.VISIBLE);
        btn_menu.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        tv_cartcount = (CircularTextView) findViewById(R.id.tv_cartcount);
        String colorStr = getResources().getString(R.string.green_color);
        tv_cartcount.setSolidColor(colorStr);
        tv_pagename.setText(category_name);
        iv_cart = findViewById(R.id.iv_cart);
        iv_wallet=findViewById(R.id.iv_wallet);
        cartvie.setOnClickListener(this);
        walletvie.setOnClickListener(this);
        walletvie.setVisibility(View.GONE);
        v_searcview = findViewById(R.id.v_searcview);
        v_searcview.setOnClickListener(this);
        iv_search = findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);

        tv_wallet.setSolidColor(colorStr);
        Float wallet=Float.parseFloat(Preferences.get_userWallet(mContext));
        int a = Math.round(wallet);
        tv_wallet.setText(a+"");

        ll_sub_cat.setVisibility(View.VISIBLE);

        searchEditText = v_searcview.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setCursorVisible(true);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setCursorVisible(true);

        /*searchEditText.setHintTextColor(getResources().getColor(R.color.black));*/

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


        v_searcview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //Utility.showToastShort(mContext, s.toString());
                getSearchedProduct(s.toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

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
    protected void onResume() {
        super.onResume();
      //  tv_cartcount.setText(Preferences.get_Cartount(mContext));
        LoadCartProduct();
        parsejson();
       parsejsonSub();

    }


    @Override
    public void adapterPositionSub(int pos) {
        ca.checkPosition(pos);
        sub_category_id =subCategoryDataResponse.getSubCategoryData().get(pos).getSubcategoryId();
        tv_pagename.setText(subCategoryDataResponse.getSubCategoryData().get(pos).getSubcategoryName());
        parsejson();

//
//        Intent a = new Intent(mContext, ProductPage.class);
//        a.putExtra("position", "" + pos);
//        a.putExtra("category_id", category_id);
//        a.putExtra("from", productModel.getSubCategoryData().get(pos).getSubcategoryName());
//        a.putExtra("sub_category_id", subCategoryDataResponse.getSubCategoryData().get(pos).getSubcategoryId());
//        mContext.startActivity(a);
    }

    public void LoadCartProduct() {
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
                if (response.isSuccessful()) {
                    myCart = response.body();
                    if (response.isSuccessful()) {
                        myCart = response.body();
                        if (myCart.getAck()==1) {
                           // Preferences.set_Cartount(mContext, myCart.getPriceData().get());
                            tv_cartcount.setText(Preferences.get_Cartount(mContext));
                        } else {
                            Preferences.set_Cartount(mContext,"0");
                            tv_cartcount.setText("0");
                        }
                    }
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MyCart> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }


    private void parsejsonSub() {
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
                subCategoryDataResponse = response.body();
                if (subCategoryDataResponse.getAck() == 1) {
                    if (subCategoryDataResponse.getSubCategoryData().size() > 0) {

                        ll_sub_cat.setVisibility(View.VISIBLE);
                        inflateAdapterSub();
                    }
                    else
                    {
                        ll_sub_cat.setVisibility(View.GONE);
                    }
                } else {
                    ll_sub_cat.setVisibility(View.GONE);
                   // Utility.showToastShort(mContext, productModel.getMsg());
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SubCategoryDataResponse> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    private void inflateAdapterSub() {
        ca = new SubCategoryProductPageAdapter(mContext, subCategoryDataResponse.getSubCategoryData(), this);
        rv_sub_cat.setAdapter(ca);
        ca.checkPosition(Integer.parseInt(position));
    }
    private void inflateAdapter() {
        ProductAdapter ca = new ProductAdapter(mContext, productModel.getProductData(), this, this);
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
    public void adapterPosition(int pos) {
        Intent a = new Intent(mContext, ProductDetails.class);
        a.putExtra("position", "" + pos);
        a.putExtra("cat_id", category_id);
        mContext.startActivity(a);
    }


    private void getSearchedProduct(final String searchString) {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);

        Call<ProductListAcfold> call = redditAPI.Getsearch_food_list1(Preferences.get_dashCatId(mContext), Preferences.get_userId(mContext), Preferences.get_UniqueId(mContext), sub_category_id, searchString);
        call.enqueue(new Callback<ProductListAcfold>() {

            @Override
            public void onResponse(Call<ProductListAcfold> call, retrofit2.Response<ProductListAcfold> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    productModel = response.body();
                    if (productModel.getAck() == 1) {
                        if (productModel.getProductData().size() > 0) {
                            Intent i = new Intent(mContext, SearchedProductPage.class);
                            i.putExtra("searchQuery", searchString);
                            i.putExtra("scat_id", sub_category_id);
                            i.putExtra("cat_id", Preferences.get_dashCatId(mContext));
                            startActivity(i);
                        }
                    } else {
                        Utility.showToastShort(mContext, productModel.getMsg());
                    }
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ProductListAcfold> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    @Override
    public void updateCartCount() {
        tv_cartcount.setText(Preferences.get_Cartount(mContext));
    }
}
