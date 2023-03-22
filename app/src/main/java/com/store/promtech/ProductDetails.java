package com.store.promtech;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.store.promtech.model.AddToCart;
import com.store.promtech.model.BaseResponse;
import com.store.promtech.model.MyCart;
import com.store.promtech.model.ProductList;
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

import static com.store.promtech.Dashboard.footwear;
import static com.store.promtech.Dashboard.mensFashion;
import static com.store.promtech.Dashboard.watch;

public class ProductDetails extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ImageView btn_menu, btn_back;
    ConnectionDetector cd;
    String category_name;
    TextView tv_pagename;
    TextView tv_title, tv_desc, tv_price,tv_color,tv_shortdesc,tv_delivery,tv_return;
    Spinner spinner;
    int position = 0;
    String[] items;
    TextView tv_quantity, tv_brand;
    ProgressDialog pDialog;
    String product_id = "";
    RelativeLayout v_spinner;
    AddToCart addToCart;
    MyCart myCart;
    String imageUrlPass="";

    public static ProductList productModel;
    LinearLayout footer,ll_four,ll_three,ll_two,ll_one;


    SearchView v_searcview;
    ImageView iv_search;
    EditText searchEditText;
    String category_id = "", st_packet_id = "",st_color_id ="", from ="", seller_id="", image_id="";

    LinearLayout ll_radios,ll_radiosColor,ll_radiosImages;
    int amount = 1;

    Button btn_backhome;
    CircularTextView tv_cartcount;
    ImageView iv_cart;
    FrameLayout cartvie;

    Button btn_add;
    LinearLayout ll_cart_quantity,ll_price,llSubmit,llfooter;
    ImageView iv_sub, iv_add;
    TextView et_qty,tv_price_discount,tv_price_orginal,tv_dis_percent,tv_modelNo,tv_stock;
    ImageView iv_productimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        cd = new ConnectionDetector(mContext);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);
        position = Integer.parseInt(getIntent().getStringExtra("position").trim());
        category_id = getIntent().getExtras().getString("cat_id");
        from = "";
        try {
            from = getIntent().getExtras().getString("from");
            if (from.equalsIgnoreCase("mens")){
                ProductPage.productModel = mensFashion;
            }else if(from.equalsIgnoreCase("foot")){
                ProductPage.productModel = footwear;
            }else if(from.equalsIgnoreCase("watch")){
                ProductPage.productModel = watch;
            }
        }catch (Exception e){
            e.printStackTrace();
        }



        //Log.d("JsonData", SubCategoryPage.productModel.getProductData().get(position).getProductDetails());

        initView();
    }


    private void initView() {
        cartvie=findViewById(R.id.cartvie);
        ImageView iv_home = findViewById(R.id.iv_home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, Dashboard.class);
                startActivity(i);
                finishAffinity();
            }
        });
        tv_stock=findViewById(R.id.tv_stock);
        ll_four=findViewById(R.id.ll_four);
        ll_three=findViewById(R.id.ll_three);
        ll_two=findViewById(R.id.ll_two);
        ll_one=findViewById(R.id.ll_one);
        tv_brand = findViewById(R.id.tv_brand);
        btn_add = findViewById(R.id.btn_add);
        ll_cart_quantity = findViewById(R.id.ll_cart_quantity);
        iv_sub = findViewById(R.id.iv_sub);
        iv_add = findViewById(R.id.iv_add);
        et_qty = findViewById(R.id.et_qty);

        btn_add.setOnClickListener(this);
        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);


        btn_backhome = findViewById(R.id.btn_backhome);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setOnClickListener(this);
        tv_cartcount = findViewById(R.id.tv_cartcount);
        String colorStr = getResources().getString(R.string.green_color);
        tv_cartcount.setSolidColor(colorStr);
        btn_backhome.setOnClickListener(this);

        footer = findViewById(R.id.footer);
        tv_pagename = findViewById(R.id.tv_pagename);

        btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setVisibility(View.GONE);

        btn_menu.setOnClickListener(this);
        v_spinner = findViewById(R.id.v_spinner);
        v_spinner.setVisibility(View.GONE);
        iv_productimg = findViewById(R.id.iv_productimg);
        tv_title = findViewById(R.id.tv_title);
        tv_desc = findViewById(R.id.tv_desc);

        spinner = findViewById(R.id.spinner);
        iv_cart = findViewById(R.id.iv_cart);
        cartvie.setOnClickListener(this);

        tv_quantity = findViewById(R.id.tv_quantity);
        tv_price = findViewById(R.id.tv_price);
        v_searcview = findViewById(R.id.v_searcview);
        v_searcview.setOnClickListener(this);
        iv_search = findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
        ll_radios = findViewById(R.id.ll_radios);
        tv_price_discount = findViewById(R.id.tv_price_discount);
        tv_price_orginal = findViewById(R.id.tv_price_orginal);
        tv_dis_percent = findViewById(R.id.tv_dis_percent);
        ll_price = findViewById(R.id.ll_price);
        llSubmit = findViewById(R.id.llSubmit);
        llfooter = findViewById(R.id.llfooter);
        tv_color = findViewById(R.id.tv_color);
        tv_modelNo = findViewById(R.id.tv_modelNo);
        tv_shortdesc = findViewById(R.id.tv_shortdesc);
        ll_radiosColor = findViewById(R.id.ll_radiosColor);
        tv_delivery = findViewById(R.id.tv_delivery);
        tv_return = findViewById(R.id.tv_return);
        ll_radiosImages = findViewById(R.id.ll_radiosImages);

        iv_productimg.setOnClickListener(this);

        //if (Preferences.get_userComplete(mContext).equalsIgnoreCase("Yes")){
            ll_price.setVisibility(View.VISIBLE);
            llfooter.setVisibility(View.VISIBLE);
            llSubmit.setVisibility(View.GONE);
       /* }else {
            ll_price.setVisibility(View.GONE);
            llfooter.setVisibility(View.GONE);
            llSubmit.setVisibility(View.VISIBLE);
        }*/


        setData();

        /*v_searcview.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Utility.showToastShort(mContext, "Hello");
                return false;
            }
        });*/
        searchEditText = v_searcview.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setCursorVisible(true);

        /*searchEditText.setHintTextColor(getResources().getColor(R.color.black));*/

        ImageView closeButton = (ImageView) this.v_searcview.findViewById(R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                iv_search.setVisibility(View.VISIBLE);
                v_searcview.setVisibility(View.GONE);
                tv_pagename.setVisibility(View.VISIBLE);
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

        //  tv_pagename.setText(SubCategoryPage.productModel.getProductData().get(position).getProductDetails());
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_cartcount.setText(Preferences.get_Cartount(mContext));
        LoadCartProduct();
        btn_add.setVisibility(View.VISIBLE);
        ll_cart_quantity.setVisibility(View.GONE);

    }

    private void setData() {
        // tv_brand.setText(ProductPage.productModel.getProductData().get(position).getBrand());
//        if (!ProductPage.productModel.getProductData().get(position).getBrand().isEmpty()) {
//            tv_brand.setVisibility(View.VISIBLE);
//            tv_brand.setText(ProductPage.productModel.getProductData().get(position).getBrand());
//
//        } else {
//            tv_brand.setVisibility(View.GONE);
//        }

       // Toast.makeText(getApplicationContext(),ProductPage.productModel.getProductData().get(position).getProductId()+ProductPage.productModel.getProductData().get(position).getStockAvailable(),Toast.LENGTH_SHORT).show();
        if(ProductPage.productModel.getProductData().get(position).getStockAvailable().equals("Yes"))
        {
            /*inflateRadioGroup();
            inflateRadioGroupColor();
            inflateRadioGroupImages();*/
           // tv_packsize.setVisibility(View.VISIBLE);
            btn_add.setText("Add To Basket");
            btn_add.setClickable(true);
            btn_add.setVisibility(View.VISIBLE);
        }
        else if(ProductPage.productModel.getProductData().get(position).getStockAvailable().equals("No"))
        {
          //  tv_packsize.setVisibility(View.GONE);
            btn_add.setText("Out of Stock");
            btn_add.setClickable(false);
            btn_add.setVisibility(View.GONE);
        }
        else
        {

        }
        tv_stock.setText("Stock: "+ProductPage.productModel.getProductData().get(position).getStock());
        tv_title.setText(ProductPage.productModel.getProductData().get(position).getProductNameEnglish());
        tv_desc.setVisibility(View.VISIBLE);
        tv_desc.setText(ProductPage.productModel.getProductData().get(position).getProductLongDetails());
        tv_shortdesc.setText(ProductPage.productModel.getProductData().get(position).getProductShortDetails());
        product_id = ProductPage.productModel.getProductData().get(position).getProductId();
        seller_id = String.valueOf(ProductPage.productModel.getProductData().get(position).getSeller_id());

        tv_price_discount.setText(ProductPage.productModel.getProductData().get(position).getSalePrice());
        tv_price_orginal.setPaintFlags(tv_price_orginal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tv_price_orginal.setText(ProductPage.productModel.getProductData().get(position).getOriginalPrice());
        tv_return.setText(ProductPage.productModel.getProductData().get(position).getReturnRefundText());
        tv_delivery.setText(ProductPage.productModel.getProductData().get(position).getDeliveryText());
        tv_dis_percent.setText(ProductPage.productModel.getProductData().get(position).getDiscount() + " off");
        tv_modelNo.setText("Model No - "+ProductPage.productModel.getProductData().get(position).getModel_no());
        try {

            imageUrlPass = ProductPage.productModel.getProductData().get(position).getPhotos().get(0).getPhoto();
            Glide.with(mContext)
                    .load(ProductPage.productModel.getProductData().get(position).getPhotos().get(0).getPhoto())
                    .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                    .into(iv_productimg);

        } catch (Exception e) {

        }

    }

    /*private void inflateRadioGroup() {
        if (ProductPage.productModel.getProductData().get(position).getSizes().size() > 0) {
            tv_packsize.setVisibility(View.VISIBLE);
            ll_radios.removeAllViews();
            LayoutInflater layoutInflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (int i = 0; i < ProductPage.productModel.getProductData().get(position).getSizes().size(); i++) {
                View view2 = layoutInflator.inflate(R.layout.row_radio_views, null);
                final TextView tv_cat = view2.findViewById(R.id.tv_offers);

                LinearLayout ll_border = view2.findViewById(R.id.ll_border);
                RelativeLayout v_main_layout = view2.findViewById(R.id.v_main_layout);
                tv_cat.setText(ProductPage.productModel.getProductData().get(position).getSizes().get(i).getSize());

                TextView tv_offered_price = view2.findViewById(R.id.tv_offered_price);
                TextView tv_discount_percent = view2.findViewById(R.id.tv_discount_percent);
                TextView tv_orginal_price = view2.findViewById(R.id.tv_orginal_price);
                tv_orginal_price.setPaintFlags(tv_orginal_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                tv_cat.setTextColor(Color.parseColor("#585858"));
                tv_cat.setId(i);



                if (i == ProductPage.productModel.getProductData().get(position).getSizes().size() - 1) {
                    ll_border.setVisibility(View.GONE);
                } else {
                    ll_border.setVisibility(View.VISIBLE);
                }

                if (!st_packet_id.isEmpty()) {
                    if (ProductPage.productModel.getProductData().get(position).getSizes().get(i).getSizeId().equalsIgnoreCase(st_packet_id)) {

                        v_main_layout.setBackgroundColor(Color.parseColor("#dde7f5"));

                    } else {

                    }
                }


                v_main_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        st_packet_id = ProductPage.productModel.getProductData().get(position).getSizes().get(tv_cat.getId()).getSizeId();
                        inflateRadioGroup();
                    }
                });


                ll_radios.addView(view2);
            }
        } else {
            tv_packsize.setVisibility(View.GONE);
        }


    }*/

    /*private void inflateRadioGroupColor() {
        if (ProductPage.productModel.getProductData().get(position).getColors().size() > 0) {
            tv_color.setVisibility(View.VISIBLE);
            ll_radiosColor.removeAllViews();
            LayoutInflater layoutInflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (int i = 0; i < ProductPage.productModel.getProductData().get(position).getColors().size(); i++) {
                View view2 = layoutInflator.inflate(R.layout.row_radio_views, null);
                final TextView tv_cat = view2.findViewById(R.id.tv_offers);
                //ImageView iv_circle = view2.findViewById(R.id.iv_circle);
                LinearLayout ll_border = view2.findViewById(R.id.ll_border);
                RelativeLayout v_main_layout = view2.findViewById(R.id.v_main_layout);
                tv_cat.setText(ProductPage.productModel.getProductData().get(position).getColors().get(i).getColor());

                TextView tv_offered_price = view2.findViewById(R.id.tv_offered_price);
                TextView tv_discount_percent = view2.findViewById(R.id.tv_discount_percent);
                TextView tv_orginal_price = view2.findViewById(R.id.tv_orginal_price);

//                tv_offered_price.setText(ProductPage.productModel.getProductData().get(position).getPackets().get(i).getPrice());
//                tv_discount_percent.setText(ProductPage.productModel.getProductData().get(position).getPackets().get(i).getDiscount() + " off");
//                tv_orginal_price.setText(ProductPage.productModel.getProductData().get(position).getPackets().get(i).getOriginalPrice());
                tv_orginal_price.setPaintFlags(tv_orginal_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                tv_cat.setTextColor(Color.parseColor("#585858"));
                tv_cat.setId(i);
                // iv_circle.setId(i);


                if (i == ProductPage.productModel.getProductData().get(position).getColors().size() - 1) {
                    ll_border.setVisibility(View.GONE);
                } else {
                    ll_border.setVisibility(View.VISIBLE);
                }

                if (!st_color_id.isEmpty()) {
                    if (ProductPage.productModel.getProductData().get(position).getColors().get(i).getColor_id().equalsIgnoreCase(st_color_id)) {
                        //iv_circle.setVisibility(View.VISIBLE);
                        //iv_circle.setImageResource(R.drawable.ic_check_black_24dp);
                        v_main_layout.setBackgroundColor(Color.parseColor("#dde7f5"));

                    } else {
                        // iv_circle.setVisibility(View.INVISIBLE);
                    }
                }


                v_main_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        st_color_id = ProductPage.productModel.getProductData().get(position).getColors().get(tv_cat.getId()).getColor_id();
                        inflateRadioGroupColor();
                    }
                });


                ll_radiosColor.addView(view2);
            }
        } else {
            tv_color.setVisibility(View.GONE);
        }



    }*/

   /* private void inflateRadioGroupImages() {
        if (ProductPage.productModel.getProductData().get(position).getPhotos().size() > 0) {
            tv_color.setVisibility(View.VISIBLE);
            ll_radiosImages.removeAllViews();
            LayoutInflater layoutInflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (int i = 0; i < ProductPage.productModel.getProductData().get(position).getPhotos().size(); i++) {
                View view2 = layoutInflator.inflate(R.layout.row_radio_images_views, null);
                final ImageView iv_product = view2.findViewById(R.id.iv_product);
                final TextView tv_offers = view2.findViewById(R.id.tv_offers);

                LinearLayout ll_border = view2.findViewById(R.id.ll_border);
                RelativeLayout v_main_layout = view2.findViewById(R.id.v_main_layout);




                if (i == ProductPage.productModel.getProductData().get(position).getPhotos().size() - 1) {
                    ll_border.setVisibility(View.GONE);
                } else {
                    ll_border.setVisibility(View.VISIBLE);
                }

                Glide.with(mContext)
                        .load(ProductPage.productModel.getProductData().get(position).getPhotos().get(i).getPhoto())
                        .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                        .into(iv_product);

                if (!image_id.isEmpty()) {
                    if (ProductPage.productModel.getProductData().get(position).getPhotos().get(i).getPhotoId().equalsIgnoreCase(image_id)) {

                        v_main_layout.setBackgroundResource(R.drawable.image_bg);

                    } else {
                        v_main_layout.setBackgroundResource(R.color.white);
                    }
                }
                tv_offers.setId(i);

                v_main_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageUrlPass = ProductPage.productModel.getProductData().get(position).getPhotos().get(tv_offers.getId()).getPhoto();
                        Glide.with(mContext)
                                .load(ProductPage.productModel.getProductData().get(position).getPhotos().get(tv_offers.getId()).getPhoto())
                                .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                                .into(iv_productimg);
                        image_id = ProductPage.productModel.getProductData().get(position).getPhotos().get(tv_offers.getId()).getPhotoId();
                        inflateRadioGroupImages();
                    }
                });


                ll_radiosImages.addView(view2);
            }
        }



    }*/


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
        } else if (v == btn_backhome) {
            finish();
        }else if (v == iv_productimg) {
            Intent intent = new Intent(mContext, ImageFullActivity.class);
            intent.putExtra("url",imageUrlPass);
            mContext.startActivity(intent);
        } else if (v == btn_add) {
            //Toast.makeText(getApplicationContext(), Preferences.get_blockOrNot(mContext), Toast.LENGTH_SHORT).show();
            if (!Preferences.get_userId(mContext).equalsIgnoreCase("0")) {
                if (Preferences.get_blockOrNot(mContext).equalsIgnoreCase("No")) {
                    amount = 1;
                    if (cd.isConnected()) {
                        AddtoCartServices2("1");
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "You are blocked!", Toast.LENGTH_SHORT).show();
                }
            }else {
                Intent i = new Intent(mContext, LoginActivity.class);
                startActivity(i);
            }
        }
    }

    private void AddtoCartServices2(String isCartAdd) {
        pDialog.show();
        String BASE_URL = mContext.getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);

        Call<BaseResponse> call = redditAPI.AddtoCartService2(Preferences.get_userId(mContext), Preferences.get_UniqueId(mContext), product_id,seller_id);
        call.enqueue(new Callback<BaseResponse>() {

            @Override
            public void onResponse(Call<BaseResponse> call, retrofit2.Response<BaseResponse> response) {
                pDialog.dismiss();
                BaseResponse baseResponse = response.body();
                if(baseResponse.getAck()==1) {
                    Utility.showToastShort(mContext, baseResponse.getMsg());
                    LoadCartProduct();
                }
                else {
                    Utility.showToastShort(mContext, baseResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                pDialog.dismiss();
            }
        });
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
                            Preferences.set_Cartount(mContext, myCart.getPriceData().getTotal_quantity());
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


    private void getSearchedProduct(final String searchString) {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);

        Call<ProductList> call = redditAPI.Getsearch_food_list( Preferences.get_userId(mContext), Preferences.get_UniqueId(mContext), searchString);
        call.enqueue(new Callback<ProductList>() {

            @Override
            public void onResponse(Call<ProductList> call, retrofit2.Response<ProductList> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    productModel = response.body();
                    if (productModel.getAck() == 1) {
                        if (productModel.getProductData().size() > 0) {
                            Intent i = new Intent(mContext, SearchedProductPage.class);
                            i.putExtra("searchQuery", searchString);
                            i.putExtra("cat_id", category_id);
                            startActivity(i);
                            //inflateAdapter();
                        }
                    } else {
                        Utility.showToastShort(mContext, productModel.getMsg());
                    }
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }


}
