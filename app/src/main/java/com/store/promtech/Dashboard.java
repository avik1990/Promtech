package com.store.promtech;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.store.SeasonalActivity;
import com.store.promtech.adapter.AutoCompleteCatAdapter;
import com.store.promtech.adapter.BannerImagePagerAdapter;
import com.store.promtech.adapter.BrandAdapter;
import com.store.promtech.adapter.CategoryAdapter;
import com.store.promtech.adapter.DealsAdapter;
import com.store.promtech.adapter.MostSellingGridAdapter;
import com.store.promtech.adapter.OfferAdapter;
import com.store.promtech.adapter.RecyclerViewAdapter;
import com.store.promtech.adapter.SeasonalAdapter;
import com.store.promtech.adapter.SliderPagerAdapter;
import com.store.promtech.adapter.SlidingProductAdapter;
import com.store.promtech.adapter.WhyChooseAdapter;
import com.store.promtech.model.AdvantageList;
import com.store.promtech.model.AllBanners;
import com.store.promtech.model.Banners;
import com.store.promtech.model.Brand;
import com.store.promtech.model.Category;
import com.store.promtech.model.ContactUsModel;
import com.store.promtech.model.DeaslsProduct;
import com.store.promtech.model.HomeSlot;
import com.store.promtech.model.MostSellingProduct;
import com.store.promtech.model.MyCart;
import com.store.promtech.model.MyProfile;
import com.store.promtech.model.Notes;
import com.store.promtech.model.Notification;
import com.store.promtech.model.Offer;
import com.store.promtech.model.Offers;
import com.store.promtech.model.OrganicBenefit;
import com.store.promtech.model.ProductListAcfold;
import com.store.promtech.model.ReferandEarnText;
import com.store.promtech.model.Seasonal;
import com.store.promtech.model.SlidingProduct;
import com.store.promtech.model.SocialMedia;
import com.store.promtech.model.Wallet;
import com.store.promtech.model.WhyChoose;
import com.store.promtech.model.WhyChooseText;
import com.store.promtech.model.ZipCodemodel;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.CircularTextView;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Preferences;
import com.store.promtech.utils.Utility;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.store.promtech.ProductPage.productModel;

public class Dashboard extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener,  SeasonalAdapter.AdapterPosition, SeasonalAdapter.UpdateCartCount
        , DealsAdapter.AdapterPosD, DealsAdapter.UpdateCartCountD,OfferAdapter.AdapterPos,OfferAdapter.UpdateCartCount, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        EasyPermissions.PermissionCallbacks {

    Context mContext;
    SearchView v_searcview;
    AutoCompleteTextView product_search;
    RecyclerView rv_sliding_product;
    ImageView btn_menu;
    NavigationView navigationView;
    ConnectionDetector cd;
    ProgressDialog pDialog;
    RelativeLayout rl_fish,rl_meat;
    Category getCategory;
    public static Offer getOffer;
    Notes notes;
    Banners banners;
    RecyclerView rv_cat, rv_offer, rv_mostselling, rv_brand, rv_seasonal, rv_deals;
    ImageView iv_cart, ivdelivery, iv_refer, iv_why_choose, iv_org_benefit,ivemptyOffer;
    CircularTextView tv_cartcount, tv_wallet;
    View headerView;
    RelativeLayout rl_image;
    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    SliderPagerAdapter sliderPagerAdapter;
    BannerImagePagerAdapter imageAdapter;

    ArrayList<String> slider_image_list;
    private ImageView[] dots;
    int page_position = 0;
    List<String> list_text = new ArrayList<>();
    ZipCodemodel zipCodemodel;
    public static MostSellingProduct getMostSelling;
    public static Offers getOffers;
    public static DeaslsProduct getDeals;
    public static HomeSlot getHomeSlot;
    public static SlidingProduct getSlidingProduct;
    // WhyChooseText getWhyChoose;
    public static Seasonal getSeasonal;
    AllBanners getallBanners;
    SocialMedia getSocialMedia;
    WhyChoose getWhyChoose;
    Brand getBrand;
    MyCart myCart;
    CardView llEmptyOffer;
    TextView tv_cartprice, tv_note, tvSpecialInstruction, tvViewAllMostSelling, tvViewAllSeasonal, tvViewAllDeals,tv_location,tv_delivery;
    boolean doubleBackToExitPressedOnce = false;
    ContactUsModel aboutUs;
    LinearLayout ll_Cart_Price, ll_three, ll_four;
    Notification getDelivery;
    ReferandEarnText getReferandEarn;
    Spinner sp_caterory;
    private Handler myHandler = new Handler();
    FrameLayout walletvie;
    private int currentPage = 0, NUM_PAGES;
    private Timer timer;
    private final long DELAY_MS = 1000;//delay in milliseconds before task is to be executed
    private final long PERIOD_MS = 5000;
    DrawerLayout drawer;
    ArrayList<String> cat_id = new ArrayList<>();
    ArrayList<String> cat_name = new ArrayList<>();
    LinearLayout ll_offers,llMens,llWomens,llfootwear,llWatch;
    // public static ProductListAcfold productModel;
    String spCatId = "0",tag,msg;
    CardView lldelivery, llrefer;
    ImageView iv_facebook, iv_twitter, iv_insta, iv_pinterest, iv_youtube, iv_wallet,ivEmptyDeal;
    CardView llEmptyDeal;
    MyProfile registration;
    TextView txt_name,tv_phone,tv_special_noti,tvDelivery;
    FrameLayout cartvie;
    Wallet getWallet;
    ImageView userImage;
    String whatsappNo="", deliveryCharge="";
    ImageView fl_whatspp;

    private GoogleApiClient mGoogleApiClient;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final int REQUEST_CHECK_SETTINGS = 1452;
    public static final int RC_LOC_PERM = 101;

    AutoCompleteCatAdapter adapter;
    List<ProductListAcfold.ProductDatum> data = new ArrayList<>();
    private Timer timer2;
    private String searchTxt = "";
    private boolean isItemSelected = false;
    public static ProductListAcfold mensFashion;
    public static AdvantageList womensFashion;
    public static ProductListAcfold footwear;
    public static ProductListAcfold watch;

    private ArrayList<ProductListAcfold.ProductDatum> mensFashionList = new ArrayList<>();
    private ArrayList<AdvantageList.AdvantageData> womensFashionList = new ArrayList<>();
    private ArrayList<ProductListAcfold.ProductDatum> footwearList = new ArrayList<>();
    private ArrayList<ProductListAcfold.ProductDatum> watchList = new ArrayList<>();
    private RecyclerViewAdapter<ProductListAcfold.ProductDatum> viewAdapterMens;
    private RecyclerViewAdapter<AdvantageList.AdvantageData> viewAdapterWomen;
    private RecyclerViewAdapter<ProductListAcfold.ProductDatum> viewAdapterFoot;
    private RecyclerViewAdapter<ProductListAcfold.ProductDatum> viewAdapterWatch;
    private RecyclerView rv_mnens,rv_womens,rv_foot,rv_watch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_inc);
        mContext = this;
        Fresco.initialize(mContext);
        cd = new ConnectionDetector(mContext);

        tag=getIntent().getStringExtra("tag");
        msg=getIntent().getStringExtra("msg");
        if(tag!=null) {
            if (tag.equals("otp")) {
                showDialog(Dashboard.this, msg, "");

            }
        }
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);
        slider_image_list = new ArrayList<>();

        initView();

        rv_mnens.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL, false));
        viewAdapterMens = new RecyclerViewAdapter<>(getApplicationContext(), R.layout.item_products,mensFashionList);
        viewAdapterMens.setMapper((viewHolder, source) -> {
            TextView tv_productname = (TextView) viewHolder.getView(R.id.tv_productname);
            TextView tv_stockavailable = (TextView) viewHolder.getView(R.id.tv_stockavailable);
            TextView tv_dis_percent = (TextView) viewHolder.getView(R.id.tv_dis_percent);
            TextView tv_price_orginal = (TextView) viewHolder.getView(R.id.tv_price_orginal);
            TextView tv_price_discount = (TextView) viewHolder.getView(R.id.tv_price_discount);
            TextView tv_docSubmit = (TextView) viewHolder.getView(R.id.tv_docSubmit);
            TextView tv_position = (TextView) viewHolder.getView(R.id.tv_position);
            TextView tv_outof = (TextView) viewHolder.getView(R.id.tv_outof);
            TextView tv_ava = (TextView) viewHolder.getView(R.id.tv_ava);
            RelativeLayout rl_mainbody = (RelativeLayout) viewHolder.getView(R.id.rl_mainbody);
            ImageView iv_chefimage = (ImageView) viewHolder.getView(R.id.iv_chefimage);
            LinearLayout ll_price = (LinearLayout) viewHolder.getView(R.id.ll_price);
            tv_productname.setText(source.getProductNameEnglish());
            tv_stockavailable.setText("Stock: "+source.getStock());

            // if (Preferences.get_userComplete(mContext).equalsIgnoreCase("Yes")){
            // tv_docSubmit.setVisibility(View.GONE);
            if(source.getStockAvailable().equalsIgnoreCase("yes")){
                //ll_price.setVisibility(View.VISIBLE);
                tv_outof.setVisibility(View.GONE);
                tv_ava.setVisibility(View.VISIBLE);
            }else {
                //ll_price.setVisibility(View.GONE);
                tv_ava.setVisibility(View.GONE);
                tv_outof.setVisibility(View.VISIBLE);
            }
           /* }else {
                ll_price.setVisibility(View.GONE);
                tv_docSubmit.setVisibility(View.VISIBLE);
                tv_outof.setVisibility(View.GONE);
            }*/
            //  if (c.getPackets().get(c.getSelectedPos()).getDiscount().equals("0%")) {
            tv_dis_percent.setVisibility(View.VISIBLE);
            tv_price_orginal.setPaintFlags(tv_price_orginal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tv_dis_percent.setText(" " + source.getDiscount() + " off");
            tv_price_discount.setVisibility(View.VISIBLE);
            tv_price_discount.setText(source.getSalePrice());
            tv_price_orginal.setText(source.getOriginalPrice());

            try {
                Glide.with(mContext)
                        .load(source.getPhotos().get(0).getPhoto())
                        .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                        .into(iv_chefimage);
            }catch (Exception e){
                e.printStackTrace();
            }
            rl_mainbody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent a = new Intent(mContext, ProductDetails.class);
                    a.putExtra("position", "" + String.valueOf(viewHolder.getAdapterPosition()));
                    a.putExtra("category_id", "");
                    a.putExtra("from", "mens");
                    mContext.startActivity(a);
                }
            });


        });
        rv_mnens.setAdapter(viewAdapterMens);
        viewAdapterMens.notifyDataSetChanged();

        rv_womens.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL, false));
        viewAdapterWomen = new RecyclerViewAdapter<>(getApplicationContext(), R.layout.item_advantage, womensFashionList);
        viewAdapterWomen.setMapper((viewHolder, source) -> {


            TextView tv_productname = (TextView) viewHolder.getView(R.id.tv_productname);
            TextView tv_outof = (TextView) viewHolder.getView(R.id.tv_outof);

            ImageView iv_chefimage = (ImageView) viewHolder.getView(R.id.iv_chefimage);
            LinearLayout ll_price = (LinearLayout) viewHolder.getView(R.id.ll_price);

            tv_productname.setText(source.getAdvantage_text());
            ll_price.setVisibility(View.GONE);
            tv_outof.setVisibility(View.GONE);




            try {

                Glide.with(mContext)
                        .load(source.getAdvantage_photo())
                        .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                        .into(iv_chefimage);
            }catch (Exception e){
                e.printStackTrace();
            }


        });
        rv_womens.setAdapter(viewAdapterWomen);
        viewAdapterWomen.notifyDataSetChanged();

        /*rv_foot.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL, false));
        viewAdapterFoot = new RecyclerViewAdapter<>(getApplicationContext(), R.layout.item_products,footwearList);
        viewAdapterFoot.setMapper((viewHolder, source) -> {


            TextView tv_productname = (TextView) viewHolder.getView(R.id.tv_productname);
            TextView tv_dis_percent = (TextView) viewHolder.getView(R.id.tv_dis_percent);
            TextView tv_price_orginal = (TextView) viewHolder.getView(R.id.tv_price_orginal);
            TextView tv_price_discount = (TextView) viewHolder.getView(R.id.tv_price_discount);
            TextView tv_docSubmit = (TextView) viewHolder.getView(R.id.tv_docSubmit);
            TextView tv_position = (TextView) viewHolder.getView(R.id.tv_position);
            TextView tv_outof = (TextView) viewHolder.getView(R.id.tv_outof);
            RelativeLayout rl_mainbody = (RelativeLayout) viewHolder.getView(R.id.rl_mainbody);
            ImageView iv_chefimage = (ImageView) viewHolder.getView(R.id.iv_chefimage);
            LinearLayout ll_price = (LinearLayout) viewHolder.getView(R.id.ll_price);
            tv_productname.setText(source.getProductNameEnglish());



            if (Preferences.get_userComplete(mContext).equalsIgnoreCase("Yes")){
                tv_docSubmit.setVisibility(View.GONE);
                if(source.getStockAvailable().equalsIgnoreCase("yes")){
                    ll_price.setVisibility(View.VISIBLE);
                    tv_outof.setVisibility(View.GONE);
                }else {
                    ll_price.setVisibility(View.GONE);
                    tv_outof.setVisibility(View.VISIBLE);
                }
            }else {
                ll_price.setVisibility(View.GONE);
                tv_docSubmit.setVisibility(View.VISIBLE);
                tv_outof.setVisibility(View.GONE);
            }




            //  if (c.getPackets().get(c.getSelectedPos()).getDiscount().equals("0%")) {
            tv_dis_percent.setVisibility(View.VISIBLE);
            tv_price_orginal.setPaintFlags(tv_price_orginal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tv_dis_percent.setText(" " + source.getDiscount() + " off");
            tv_price_discount.setVisibility(View.VISIBLE);
            tv_price_discount.setText(source.getSalePrice());
            tv_price_orginal.setText(source.getOriginalPrice());

            try {

                Glide.with(mContext)
                        .load(source.getPhotos().get(0).getPhoto())
                        .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                        .into(iv_chefimage);
            }catch (Exception e){
                e.printStackTrace();
            }
            rl_mainbody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent a = new Intent(mContext, ProductDetails.class);
                    a.putExtra("position", "" + String.valueOf(viewHolder.getAbsoluteAdapterPosition()));
                    a.putExtra("category_id", "");
                    a.putExtra("from", "foot");
                    mContext.startActivity(a);
                }
            });
        });
        rv_foot.setAdapter(viewAdapterFoot);
        viewAdapterFoot.notifyDataSetChanged();

        rv_watch.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL, false));
        viewAdapterWatch = new RecyclerViewAdapter<>(getApplicationContext(), R.layout.item_products,watchList);
        viewAdapterWatch.setMapper((viewHolder, source) -> {


            TextView tv_productname = (TextView) viewHolder.getView(R.id.tv_productname);
            TextView tv_dis_percent = (TextView) viewHolder.getView(R.id.tv_dis_percent);
            TextView tv_price_orginal = (TextView) viewHolder.getView(R.id.tv_price_orginal);
            TextView tv_price_discount = (TextView) viewHolder.getView(R.id.tv_price_discount);
            TextView tv_docSubmit = (TextView) viewHolder.getView(R.id.tv_docSubmit);
            TextView tv_position = (TextView) viewHolder.getView(R.id.tv_position);
            TextView tv_outof = (TextView) viewHolder.getView(R.id.tv_outof);
            RelativeLayout rl_mainbody = (RelativeLayout) viewHolder.getView(R.id.rl_mainbody);
            ImageView iv_chefimage = (ImageView) viewHolder.getView(R.id.iv_chefimage);
            LinearLayout ll_price = (LinearLayout) viewHolder.getView(R.id.ll_price);
            tv_productname.setText(source.getProductNameEnglish());



            if (Preferences.get_userComplete(mContext).equalsIgnoreCase("Yes")){
                tv_docSubmit.setVisibility(View.GONE);
                if(source.getStockAvailable().equalsIgnoreCase("yes")){
                    ll_price.setVisibility(View.VISIBLE);
                    tv_outof.setVisibility(View.GONE);
                }else {
                    ll_price.setVisibility(View.GONE);
                    tv_outof.setVisibility(View.VISIBLE);
                }
            }else {
                ll_price.setVisibility(View.GONE);
                tv_docSubmit.setVisibility(View.VISIBLE);
                tv_outof.setVisibility(View.GONE);
            }





            //  if (c.getPackets().get(c.getSelectedPos()).getDiscount().equals("0%")) {
            tv_dis_percent.setVisibility(View.VISIBLE);
            tv_price_orginal.setPaintFlags(tv_price_orginal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tv_dis_percent.setText(" " + source.getDiscount() + " off");
            tv_price_discount.setVisibility(View.VISIBLE);
            tv_price_discount.setText(source.getSalePrice());
            tv_price_orginal.setText(source.getOriginalPrice());

            try {

                Glide.with(mContext)
                        .load(source.getPhotos().get(0).getPhoto())
                        .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                        .into(iv_chefimage);
            }catch (Exception e){
                e.printStackTrace();
            }
            rl_mainbody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent a = new Intent(mContext, ProductDetails.class);
                    a.putExtra("position", "" + String.valueOf(viewHolder.getAbsoluteAdapterPosition()));
                    a.putExtra("category_id", "");
                    a.putExtra("from", "watch");
                    mContext.startActivity(a);
                }
            });


        });
        rv_watch.setAdapter(viewAdapterWatch);
        viewAdapterWatch.notifyDataSetChanged();*/

        if (timer != null) {
            timer.cancel();
        }
        buildGoogleApiClient();
        loadWithLocationTask();
        Utility.hideKeyboard(Dashboard.this);

        if (cd.isConnected()) {

            parsejson();


        } else {
            Utility.showToastShort(mContext, getString(R.string.no_internet_msg));
        }


        /*dashboardProductMens("1","dashboard");
        dashboardProductWomens("2","dashboard");
        dashboardProductFootwear("3","dashboard");
        dashboardProductWatch("4","dashboard");*/
    }

    private void parsejsonWhyCHoose() {
        pDialog.show();


        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<WhyChoose> call = redditAPI.GetWhyChoose();
        call.enqueue(new Callback<WhyChoose>() {

            @Override
            public void onResponse(Call<WhyChoose> call, retrofit2.Response<WhyChoose> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    getWhyChoose = response.body();
                    if (getWhyChoose.getChooseData().size() > 0) {
                        setupWhyChoose();
                    }
                }


                //  tvSpecialInstruction.setText(getCategory.getDeliveryData().getContent());
                //pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<WhyChoose> call, Throwable t) {
                pDialog.dismiss();
                //  Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setupWhyChoose() {
        WhyChooseAdapter ca = new WhyChooseAdapter(mContext, getWhyChoose.getChooseData());
        rv_brand.setAdapter(ca);
    }

    public void showDialog(Context activity, String message,String image) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_congrates_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        final TextView et_msg=dialog.findViewById(R.id.et_msg);
        final ImageView ivthanku=dialog.findViewById(R.id.ivthanku);
        final ImageView iv_cancel=dialog.findViewById(R.id.iv_cancel);
//        Picasso.with(mContext)
//                .load(image)
//                .into(ivthanku, new com.squareup.picasso.Callback() {
//                    @Override
//                    public void onSuccess() {
//                        //  Toast.makeText(getApplication(),"succ",Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError() {
//                        ivthanku.setImageResource(R.mipmap.logoo);
//                        // Toast.makeText(getApplication(),"fail",Toast.LENGTH_SHORT).show();
//                    }
//                });

        et_msg.setText(message);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }



    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                //finish();
            }
            return false;
        }
        return true;
    }



    public void settingsrequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        Log.e("TAG", "onsetiing Result success: ");
                        startLocationUpdates();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(Dashboard.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        Log.e("TAG", "onResult: " + " SETTINGS_CHANGE_UNAVAILABLE");
                        break;
                }
            }
        });
    }

    public void startLocationUpdates()  {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Toast.makeText(mContext,"No location permission",Toast.LENGTH_SHORT).show();
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (mLastLocation != null) {
            Log.e("TAG", "startLocationUpdates: "+mLastLocation.getLatitude()+mLastLocation.getLongitude());
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                tv_location.setText(city+", "+state+", "+postalCode);
                Preferences.set_userGeoAddress(mContext,address);
                Preferences.set_userGeoCity(mContext,city);
                Preferences.set_userGeoState(mContext,state);
                Preferences.set_userGeoCountry(mContext,country);
                Preferences.set_userGeoPostCode(mContext,postalCode);

            }catch (Exception e) {

            }
            return;
        }

        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10 * 1000);
            mLocationRequest.setFastestInterval(5 * 1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterPermissionGranted(RC_LOC_PERM)
    public void loadWithLocationTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            if (checkPlayServices()) {
                //  Toast.makeText(mContext, "Google play services  found", Toast.LENGTH_SHORT).show();
                settingsrequest();
            } else {
                Toast.makeText(mContext, "Google play services not found", Toast.LENGTH_SHORT).show();
                finish();
            }

        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "You need to enable location permission to use this app",
                    RC_LOC_PERM, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(mContext, "onStart", Toast.LENGTH_SHORT).show();

        try {
            mGoogleApiClient.connect();
            // startLocationUpdates();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onStop() {
        //Toast.makeText(mContext, "onStop", Toast.LENGTH_SHORT).show();
        try {
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        super.onStop();
        /*if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY)!= 0)
        {
            finishAffinity();
        }*/
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            if (location != null)
                Log.e("TAG", "startLocationUpdates: "+location.getLatitude()+location.getLongitude());

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                tv_location.setText(city+", "+state+", "+postalCode);
                Preferences.set_userGeoAddress(mContext,address);
                Preferences.set_userGeoCity(mContext,city);
                Preferences.set_userGeoState(mContext,state);
                Preferences.set_userGeoCountry(mContext,country);
                Preferences.set_userGeoPostCode(mContext,postalCode);



            }catch (Exception e)
            {

            }



            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // tv_cartcount.setText(Preferences.get_Cartount(mContext));
        if (!Preferences.isgenerateUniqueKey(mContext)) {
            generateUniqueId();
            Preferences.setUniqueKey(mContext, true);
            if (Preferences.getisVerified(mContext)) {
                getUserDetails();
            }
        }
        if (cd.isConnected()) {

            getBannerData();

            dashboardfeatured_products();
            dashboardAdvantage();


            // if (Preferences.get_userComplete(mContext).equalsIgnoreCase("Yes")){
           // LoadCartProduct();

            //  }




        } else {
            Utility.showToastShort(mContext, getString(R.string.no_internet_msg));
        }

        // parsejsonWallet();
    }

    private void initslider() {
        try {
            vp_slider = (ViewPager) findViewById(R.id.vp_slider);
            ll_dots = (LinearLayout) findViewById(R.id.ll_dots);

            sliderPagerAdapter = new SliderPagerAdapter(Dashboard.this, slider_image_list);
            vp_slider.setAdapter(sliderPagerAdapter);
            sliderPagerAdapter.notifyDataSetChanged();

            vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    addBottomDots(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                public void run() {
                    if (page_position == slider_image_list.size()) {
                        page_position = 0;
                    } else {
                        page_position = page_position + 1;
                    }
                    vp_slider.setCurrentItem(page_position, true);
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBottomDots(int currentPage) {
        try {
            dots = new ImageView[NUM_PAGES];
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(7, 0, 7, 0);
            ll_dots.removeAllViews();
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new ImageView(this);
                dots[i].setBackgroundResource(R.drawable.ic_dot_one);
                dots[i].setLayoutParams(lp);
                ll_dots.addView(dots[i]);
            }

            if (dots.length > 0)
                dots[currentPage].setBackgroundResource(R.drawable.ic_dot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateUniqueId() {
        Random rand = new Random();
        String uniqueId = System.currentTimeMillis() + "" + rand.nextInt(500);
        Log.d("uniqueId", uniqueId);
        Preferences.set_UniqueId(mContext, uniqueId);
        // tv_wallet.setText(Preferences.get_userWallet(mContext));
    }

    private void initView() {

        rl_meat=findViewById(R.id.rl_meat);
        rl_fish=findViewById(R.id.rl_fish);
        tv_location=findViewById(R.id.tv_location);
        tv_special_noti=findViewById(R.id.tv_special_noti);
        walletvie=findViewById(R.id.walletvie);
        cartvie=findViewById(R.id.cartvie);
        rv_sliding_product=findViewById(R.id.rv_sliding_product);
        /*tvSlotThree=findViewById(R.id.tvSlotThree);
        tvSlotTwo=findViewById(R.id.tvSlotTwo);
        tvSlotOne=findViewById(R.id.tvSlotOne);*/
        ivEmptyDeal=findViewById(R.id.ivEmptyDeal);
        llEmptyDeal=findViewById(R.id.llEmptyDeal);
        ll_offers=findViewById(R.id.ll_offers);
        llEmptyOffer=findViewById(R.id.llEmptyOffer);
        ivemptyOffer=findViewById(R.id.ivemptyOffer);
        tvViewAllDeals = findViewById(R.id.tvViewAllDeals);
        rv_deals = findViewById(R.id.rv_deals);
        iv_wallet = findViewById(R.id.iv_wallet);
        iv_facebook = findViewById(R.id.iv_facebook);
        iv_twitter = findViewById(R.id.iv_twitter);
        iv_insta = findViewById(R.id.iv_insta);
        iv_pinterest = findViewById(R.id.iv_pinterest);
        iv_youtube = findViewById(R.id.iv_youtube);
        tvViewAllSeasonal = findViewById(R.id.tvViewAllSeasonal);
        rv_seasonal = findViewById(R.id.rv_seasonal);
        ll_four = findViewById(R.id.ll_four);
        ll_three = findViewById(R.id.ll_three);
        llrefer = findViewById(R.id.llrefer);
        lldelivery = findViewById(R.id.lldelivery);
        product_search = findViewById(R.id.product_search);
        v_searcview = findViewById(R.id.v_searcview);
        v_searcview.setOnClickListener(this);
        sp_caterory = findViewById(R.id.sp_caterory);
        tv_wallet = findViewById(R.id.tv_wallet);
        iv_org_benefit = findViewById(R.id.iv_org_benefit);
        iv_why_choose = findViewById(R.id.iv_why_choose);
        ivdelivery = findViewById(R.id.ivdelivery);
        rv_brand = findViewById(R.id.rv_why_choose);
        rv_mostselling = findViewById(R.id.rv_mostselling);
        tv_delivery = findViewById(R.id.tv_delivery);
        ImageView iv_home = findViewById(R.id.iv_home);
        iv_home.setVisibility(View.GONE);
        tv_cartprice = findViewById(R.id.tv_cartprice);
        tv_cartcount = (CircularTextView) findViewById(R.id.tv_cartcount);
        String colorStr = getResources().getString(R.string.green_color);
        tv_cartcount.setSolidColor(colorStr);
        tv_wallet.setSolidColor(colorStr);
        Float wallet = Float.parseFloat(Preferences.get_userWallet(mContext));
        int a = Math.round(wallet);
        tv_wallet.setText(a + "");

        tvSpecialInstruction = findViewById(R.id.tvSpecialInstruction);
        ll_Cart_Price = findViewById(R.id.ll_Cart_Price);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tv_note = findViewById(R.id.tv_note);

        iv_refer = findViewById(R.id.iv_refer);
        llMens = findViewById(R.id.llMens);
        llWomens = findViewById(R.id.llWomens);
        llfootwear = findViewById(R.id.llfootwear);
        llWatch = findViewById(R.id.llWatch);

        tv_note.setSelected(true);
        rl_image = findViewById(R.id.rl_image);
        rv_offer = findViewById(R.id.rv_offer);
        drawer = findViewById(R.id.drawer_layout);
        tvViewAllMostSelling = findViewById(R.id.tvViewAllMostSelling);
        rv_mnens = findViewById(R.id.rv_mnens);
        rv_womens = findViewById(R.id.rv_womens);
        rv_foot = findViewById(R.id.rv_foot);
        rv_watch = findViewById(R.id.rv_watch);
        fl_whatspp = findViewById(R.id.fl_whatspp);
        tvDelivery = findViewById(R.id.tvDelivery);
        mensFashionList = new ArrayList<>();


        if (!Preferences.getisVerified(mContext)) {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_myprofile).setVisible(false);
            //nav_Menu.findItem(R.id.nav_changepass).setVisible(false);
            nav_Menu.findItem(R.id.nav_myprofile).setVisible(false);
            nav_Menu.findItem(R.id.nav_myorders).setVisible(false);
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            nav_Menu.findItem(R.id.ttAccount).setVisible(false);
            //  nav_Menu.findItem(R.id.nav_mywallet).setVisible(false);
        } else {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_myprofile).setVisible(true);
            // nav_Menu.findItem(R.id.nav_changepass).setVisible(true);
            nav_Menu.findItem(R.id.nav_myprofile).setVisible(true);
            nav_Menu.findItem(R.id.nav_myorders).setVisible(true);
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
            nav_Menu.findItem(R.id.ttAccount).setVisible(true);
            //    nav_Menu.findItem(R.id.nav_mywallet).setVisible(true);
        }


        rv_cat = findViewById(R.id.rv_cat);
        rv_cat.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL, false));

        rv_brand.setLayoutManager(new LinearLayoutManager(mContext));

        rv_offer.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_mostselling.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rv_seasonal.setLayoutManager(new LinearLayoutManager(mContext));
        rv_deals.setLayoutManager(new LinearLayoutManager(mContext));
        rv_sliding_product.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        parsejsonOffer();
        //  parsejsonPrize();
        if (!Preferences.get_userId(mContext).equalsIgnoreCase("0")) {
            headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
            txt_name = (TextView) headerView.findViewById(R.id.tv_user_name);
            tv_phone = (TextView) headerView.findViewById(R.id.tv_phone);
            userImage = (ImageView) headerView.findViewById(R.id.iv_product_photo1);

            getUserDetails();

        } else {
            headerView = navigationView.inflateHeaderView(R.layout.nav_header_main_before_login);
            Button btn_login = headerView.findViewById(R.id.btn_login);
            Button btn_register = headerView.findViewById(R.id.btn_register);
            btn_login.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    drawer.closeDrawer(GravityCompat.START);
                    Preferences.set_checkClicked(mContext, "0");
                    Intent i = new Intent(mContext, LoginActivity.class);
                    startActivity(i);
                }
            });

            btn_register.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    drawer.closeDrawer(GravityCompat.START);

                    //fetchZipCode();
                    Intent i = new Intent(mContext, RegisterActivity.class);
                    startActivity(i);
                }
            });


        }

        tvViewAllMostSelling.setOnClickListener(this);

        btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(this);
        iv_cart = findViewById(R.id.iv_cart);
        walletvie.setOnClickListener(this);
        walletvie.setVisibility(View.GONE);
        cartvie.setOnClickListener(this);
        rl_fish.setOnClickListener(this);
        rl_meat.setOnClickListener(this);



        rl_meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DelivereyTextActivity.class);
                startActivity(i);
            }
        });
        rl_fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DelivereyTextActivity.class);
                startActivity(i);
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

        lldelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DelivereyTextActivity.class);
                startActivity(i);
            }
        });

        llrefer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, ReferandEarnActivity.class);
                startActivity(i);
            }
        });
        ll_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, WhyChooseActivity.class);
                startActivity(i);
            }
        });

        tvViewAllSeasonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, SeasonalActivity.class);
                startActivity(i);
            }
        });


        ll_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, OrganicBenefitActivity.class);
                startActivity(i);
            }
        });

        sp_caterory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spCatId = cat_id.get(i);
//                if(searchTxt.length() > 0)
//                    getSearchedProduct(searchTxt, spCatId);

                if(i==0)
                    return;

                Intent intent = new Intent(Dashboard.this, SubCategoryPage.class);
                intent.putExtra("cat_id", spCatId);
                intent.putExtra("from", sp_caterory.getSelectedItem().toString());
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spCatId = "0";
            }
        });
        v_searcview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                v_searcview.setFocusable(true);
                v_searcview.setFocusableInTouchMode(true);
                //Utility.showToastShort(mContext, s.toString());
                getSearchedProduct(s.toString(), spCatId);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        product_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer2 != null) {
                    timer2.cancel();
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {
                searchTxt = s.toString();
                timer2 = new Timer();
                timer2.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(searchTxt.length() > 0)
                            getSearchedProduct(searchTxt, spCatId);
                    }
                }, 600);
            }
        });

        product_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                isItemSelected = true;

                Intent a = new Intent(mContext, ProductDetails.class);
                a.putExtra("position", "" + position);
                a.putExtra("cat_id", spCatId);
                mContext.startActivity(a);
            }
        });
        fl_whatspp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
                /*Uri uri = Uri.parse("smsto:" + whatsappNo);
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.putExtra("sms_body", "hi");
                i.setPackage("com.whatsapp");
                startActivity(i);*/
                //08240138798
                try {
                    String text = "";// Replace with your message.

                    String toNumber = whatsappNo; // Replace with mobile phone number without +Sign or leading zeros, but with country code
                    //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.


                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
                    startActivity(intent);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
       /* fl_whatspp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager packageManager = getApplicationContext().getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);

                try {
                    String url = "https://api.whatsapp.com/send?phone="+ whatsappNo +"&text=" + URLEncoder.encode("", "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        startActivity(i);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });*/
    }


    private void getUserDetails() {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);


        Call<MyProfile> call = redditAPI.GetMYProfile(Preferences.get_userId(mContext));
        call.enqueue(new Callback<MyProfile>() {

            @Override
            public void onResponse(Call<MyProfile> call, retrofit2.Response<MyProfile> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    registration = response.body();
                    if (registration.getAck() == 1) {
                        txt_name.setText(registration.getUserData().get(0).getName());
                        tv_phone.setText(registration.getUserData().get(0).getPhone());
                        Log.e("Image", Preferences.get_userImage(mContext));

                    }
                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<MyProfile> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Utility.openNavDrawer(id, mContext);
        item.setCheckable(true).setChecked(false);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == btn_menu) {
            drawer.openDrawer(GravityCompat.START);
        } else if (v == cartvie) {
            drawer.closeDrawer(GravityCompat.START);
            Intent i = new Intent(mContext, ProductCart.class);
            i.putExtra("From", "Dashboard");
            startActivity(i);
        } else if (v == walletvie) {
            Intent i = new Intent(mContext, WalletActivity.class);
            i.putExtra("From", "Dashboard");
            startActivity(i);
        }
    }

    private void getSearchedProduct(final String searchString, final String spCatId) {
        if(isItemSelected) {
            isItemSelected = false;
            return;
        }
        runOnUiThread(new Runnable() {
            public void run() {
                pDialog.show();
            }
        });

        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);

        Call<ProductListAcfold> call = redditAPI.Getsearch_food_listAc(Preferences.get_userId(mContext), Preferences.get_UniqueId(mContext), searchString);
        call.enqueue(new Callback<ProductListAcfold>() {

            @Override
            public void onResponse(Call<ProductListAcfold> call, retrofit2.Response<ProductListAcfold> response) {
                Log.d("String", "" + response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pDialog.dismiss();
                    }
                });
                if (response.isSuccessful()) {
                    productModel = response.body();
                    if (productModel.getAck() == 1) {
                        if (productModel.getProductData().size() > 0) {
//                            Intent i = new Intent(mContext, SearchedProductPage.class);
//                            i.putExtra("searchQuery", searchString);
//                            i.putExtra("scat_id", "0");
//                            i.putExtra("cat_id", spCatId);
//                            startActivity(i);

                            data = productModel.getProductData();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter = new AutoCompleteCatAdapter(Dashboard.this, data);
                                    product_search.setAdapter(adapter);

                                    product_search.showDropDown();
                                }
                            });

                        }
                    } else {
                        Utility.showToastShort(mContext, productModel.getMsg());
                    }
                }

            }

            @Override
            public void onFailure(Call<ProductListAcfold> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pDialog.dismiss();
                    }
                });
            }
        });
    }




    private void getDeliveryTextData() {

        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<Notification> call = redditAPI.GetNotification();
        call.enqueue(new Callback<Notification>() {

            @Override
            public void onResponse(Call<Notification> call, retrofit2.Response<Notification> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    getDelivery = response.body();
                    if(getDelivery.getNotificationData().getAck()==1) {

                        if(!getDelivery.getNotificationData().getNotificationText().equals("")) {

                            tv_special_noti.setVisibility(View.VISIBLE);
                            tv_special_noti.setText(getDelivery.getNotificationData().getNotificationText());
                        }
                        else
                        {
                            tv_special_noti.setVisibility(View.GONE);
                        }
                    }
                    else {
                        tv_special_noti.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }


    private void getSocialMediaLink() {

        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<SocialMedia> call = redditAPI.GetSocialMedia();
        call.enqueue(new Callback<SocialMedia>() {

            @Override
            public void onResponse(Call<SocialMedia> call, retrofit2.Response<SocialMedia> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    getSocialMedia = response.body();
                    if(getSocialMedia.getSocialMediaData().getAck()==1) {

                        iv_facebook.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String url = getSocialMedia.getSocialMediaData().getFacebookLink();
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        });

                        iv_twitter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String url = getSocialMedia.getSocialMediaData().getTwitterLink();
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        });

                        iv_insta.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String url = getSocialMedia.getSocialMediaData().getInstagramLink();
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        });

                        iv_pinterest.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String url = getSocialMedia.getSocialMediaData().getPinterestLink();
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        });
                        iv_youtube.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String url = getSocialMedia.getSocialMediaData().getYoutubeLink();
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<SocialMedia> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    private void getReferandEarnTextData() {

        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<ReferandEarnText> call = redditAPI.GetReferandEarnText();
        call.enqueue(new Callback<ReferandEarnText>() {

            @Override
            public void onResponse(Call<ReferandEarnText> call, retrofit2.Response<ReferandEarnText> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    getReferandEarn = response.body();
                    if (getReferandEarn.getTermData().getAck() == 1) {
                        Picasso.with(mContext)
                                .load(getReferandEarn.getTermData().getContentPhoto())
                                .into(iv_refer, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                    }
                }
            }

            @Override
            public void onFailure(Call<ReferandEarnText> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    private void getWhyChooseTextData() {

        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<WhyChooseText> call = redditAPI.GetWhyChooseText();
        call.enqueue(new Callback<WhyChooseText>() {

            @Override
            public void onResponse(Call<WhyChooseText> call, retrofit2.Response<WhyChooseText> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<WhyChooseText> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }


    private void getOrganicBenefit() {
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<OrganicBenefit> call = redditAPI.GetOeganicBenefit();
        call.enqueue(new Callback<OrganicBenefit>() {

            @Override
            public void onResponse(Call<OrganicBenefit> call, retrofit2.Response<OrganicBenefit> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {

                    if(response.body().getAboutData().getAck()==1) {
                        Picasso.with(mContext)
                                .load(response.body().getAboutData().getContent_photo())
                                .into(iv_org_benefit, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                    }

                }
            }

            @Override
            public void onFailure(Call<OrganicBenefit> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }



    private void parsejsonHomeSlot() {

        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<HomeSlot> call = redditAPI.GetHomeSloat();
        call.enqueue(new Callback<HomeSlot>() {

            @Override
            public void onResponse(Call<HomeSlot> call, retrofit2.Response<HomeSlot> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    getHomeSlot=response.body();
                    if(getHomeSlot.getAck()==1) {
                        if(response.body().getSlotData().size()>0) {
                           /* tvSlotOne.setText(getHomeSlot.getSlotData().get(0).getSlotName());
                            tvSlotTwo.setText(getHomeSlot.getSlotData().get(1).getSlotName());
                            tvSlotThree.setText(getHomeSlot.getSlotData().get(2).getSlotName());*/
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<HomeSlot> call, Throwable t) {

            }
        });
    }
    private void parsejson() {
        pDialog.show();
        cat_id = new ArrayList<>();
        cat_name = new ArrayList<>();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<Category> call = redditAPI.GeCategory();
        call.enqueue(new Callback<Category>() {

            @Override
            public void onResponse(Call<Category> call, retrofit2.Response<Category> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    getCategory = response.body();
                    if(getCategory.getAck()==1) {
                        if (getCategory.getCategoryData().size() > 0) {
                            setuplistview();
                            setSpinner();
                        }
                    }
                }

                //  getBannerData();
                //   tvSpecialInstruction.setText(getCategory.getDeliveryData().getContent());
                //pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void parsejsonDeals() {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<DeaslsProduct> call = redditAPI.GetDealsProduct("0", "0");
        call.enqueue(new Callback<DeaslsProduct>() {

            @Override
            public void onResponse(Call<DeaslsProduct> call, retrofit2.Response<DeaslsProduct> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    getDeals = response.body();
                    if(getDeals.getAck()==1) {
                        tvViewAllDeals.setText(getDeals.getText());
                        if (getDeals.getProductData().size() > 0) {
                            rv_deals.setVisibility(View.VISIBLE);
                            llEmptyDeal.setVisibility(View.GONE);
                            ivEmptyDeal.setVisibility(View.GONE);
                            setupDeals();
                        }
                    }
                    else
                    {
                        tvViewAllDeals.setVisibility(View.GONE);
                        rv_deals.setVisibility(View.GONE);
                        llEmptyDeal.setVisibility(View.VISIBLE);
                        try {
                            Picasso.with(mContext)
                                    .load(getDeals.getBanner_photo())
                                    .into(ivEmptyDeal, new com.squareup.picasso.Callback() {

                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });
                        } catch (Exception e) {

                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<DeaslsProduct> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parsejsonSlidingProduct() {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<SlidingProduct> call = redditAPI.GetSlidingProduct("0", "0");
        call.enqueue(new Callback<SlidingProduct>() {

            @Override
            public void onResponse(Call<SlidingProduct> call, retrofit2.Response<SlidingProduct> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    getSlidingProduct = response.body();
                    if(getSlidingProduct.getAck()==1) {
                        if (getSlidingProduct.getProductData().size() > 0) {
                            setupSliding();
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<SlidingProduct> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void parsejsonPrize() {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<MostSellingProduct> call = redditAPI.GetMostSellingProduct();
        call.enqueue(new Callback<MostSellingProduct>() {

            @Override
            public void onResponse(Call<MostSellingProduct> call, retrofit2.Response<MostSellingProduct> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    getMostSelling = response.body();
                    if(getMostSelling.getAck()==1) {
                        if (getMostSelling.getPrizeData().size() > 0) {
                            setupMostSelling();
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<MostSellingProduct> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void parsejsonBrand() {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<Brand> call = redditAPI.GetBrand();
        call.enqueue(new Callback<Brand>() {

            @Override
            public void onResponse(Call<Brand> call, retrofit2.Response<Brand> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    getBrand = response.body();
                    if(getBrand.getAck()==1) {
                        if (getBrand.getBrandData().size() > 0) {
                            setupBrand();
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<Brand> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void parsejsonOffer() {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<Offers> call = redditAPI.GetOffer();
        call.enqueue(new Callback<Offers>() {

            @Override
            public void onResponse(Call<Offers> call, retrofit2.Response<Offers> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    getOffers = response.body();
                    if(getOffers.getAck() == 1 && getOffers.getOfferData().size() > 0) {
                        //setupMostSelling();
                        setupRecylerview();
                        ll_offers.setVisibility(View.VISIBLE);
                        llEmptyOffer.setVisibility(View.GONE);
                    } else {
                        ll_offers.setVisibility(View.GONE);
                        llEmptyOffer.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Offers> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    private void getBannerData() {
        slider_image_list = new ArrayList<>();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<Banners> call = redditAPI.GetBanners(Preferences.get_userId(mContext));
        call.enqueue(new Callback<Banners>() {

            @Override
            public void onResponse(Call<Banners> call, retrofit2.Response<Banners> response) {
                Log.d("String", "" + response);
                pDialog.dismiss();
                if (response.isSuccessful()) {
                    banners = response.body();
                    // tv_delivery.setText(banners.getDeliveryText());
                    //  Log.i("delivery", banners.getDeliveryText());
                    if (banners.getAck() == 1) {
                        // Preferences.set_userComplete(mContext, banners.getProfileCompleted().trim());
                        // Toast.makeText(getApplicationContext(), Preferences.get_userComplete(mContext), Toast.LENGTH_SHORT).show();
                        whatsappNo = banners.getWhatsapp_no();
                        deliveryCharge = banners.getDelivery_charge_text();
                        tvDelivery.setText(deliveryCharge);

                        tv_cartprice.setVisibility(View.VISIBLE);
                        ll_Cart_Price.setVisibility(View.VISIBLE);
                        tv_cartprice.setText(banners.getTot_price());
                        Preferences.set_Cartount(mContext, banners.getTot_qty());
                        tv_cartcount.setText(banners.getTot_qty());
                        if(banners.getTot_qty().equals("")){
                            ll_Cart_Price.setVisibility(View.GONE);
                        }

                        Preferences.set_blockOrNot(mContext, banners.getUser_blocked());
                        if (banners.getBannerData().size() > 0) {
                            NUM_PAGES = banners.getBannerData().size();

                            for (int i = 0; i < banners.getBannerData().size(); i++) {
                                slider_image_list.add(banners.getBannerData().get(i).getBannerPhoto());
                            }

                            initslider();
                            addBottomDots(0);
                            setViewPagerScroll();
                        } else {
                            rl_image.setVisibility(View.GONE);
                        }
                    } else {
                        rl_image.setVisibility(View.GONE);
                        Preferences.set_Cartount(mContext, "0");
                        tv_cartcount.setText("0");
                        tv_cartprice.setVisibility(View.GONE);
                        ll_Cart_Price.setVisibility(View.GONE);
                    }
                }
                // getNotes();
                //  getReferEarn();
            }

            @Override
            public void onFailure(Call<Banners> call, Throwable t) {
                pDialog.dismiss();
            }
        });
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
        Call<Seasonal> call = redditAPI.GetSeasonalProduct("0", "0");
        call.enqueue(new Callback<Seasonal>() {

            @Override
            public void onResponse(Call<Seasonal> call, retrofit2.Response<Seasonal> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    getSeasonal = response.body();
                    if(getSeasonal.getAck()==1) {
                        if (getSeasonal.getProductData().size() > 0) {
                            setupSeasonal();
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<Seasonal> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setViewPagerScroll() {
        currentPage = 0;
        if (timer == null) {
            timer = new Timer();
        } else {

        }
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {

                if (vp_slider != null) {
                    vp_slider.setCurrentItem(currentPage, true);
                    if (currentPage == NUM_PAGES - 1) {
                        currentPage = 0;
                    } else {
                        currentPage++;
                    }
                }
            }
        };

        // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }


    private void fetchContactDetails() {
        //pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<ContactUsModel> call = redditAPI.GetContactUs();
        call.enqueue(new Callback<ContactUsModel>() {

            @Override
            public void onResponse(Call<ContactUsModel> call, retrofit2.Response<ContactUsModel> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    aboutUs = response.body();
                    if (aboutUs != null) {
                        //Preferences.set_contactNo(mContext, aboutUs.getContactData().contactNo1);
                    }
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ContactUsModel> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }


    private void setSpinner() {
        for (int i = 0; i < getCategory.getCategoryData().size(); i++) {
            Category.CategoryDatum datum = getCategory.getCategoryData().get(i);
            cat_id.add(datum.categoryId);
            cat_name.add(datum.categoryName);

        }

        cat_id.add(0,"0");
        cat_name.add(0,"Category");

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, cat_name);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        sp_caterory.setAdapter(aa);
    }

    private void setuplistview() {
        CategoryAdapter ca = new CategoryAdapter(mContext, getCategory.getCategoryData());
        rv_cat.setAdapter(ca);
    }

    private void setupDeals() {
        DealsAdapter ca = new DealsAdapter(getDeals.getProductData(), mContext, "Dashboard", this, this);
        rv_deals.setAdapter(ca);
    }

    private void setupRecylerview() {
        OfferAdapter ca = new OfferAdapter(getOffers.getOfferData(), mContext, "Dashboard", this, this);
        rv_offer.setAdapter(ca);
    }

    private void setupSliding() {
        SlidingProductAdapter ca = new SlidingProductAdapter(mContext,getSlidingProduct.getProductData());
        rv_sliding_product.setAdapter(ca);
    }

    private void setupMostSelling() {
        MostSellingGridAdapter ca = new MostSellingGridAdapter(getMostSelling.getPrizeData(), mContext, "Dashboard");
        rv_mostselling.setAdapter(ca);
    }

    private void setupSeasonal() {
        SeasonalAdapter ca = new SeasonalAdapter(getSeasonal.getProductData(), mContext, "Dashboard", this, this);
        rv_seasonal.setAdapter(ca);
    }

    private void setupBrand() {
        BrandAdapter ca = new BrandAdapter(mContext, getBrand.getBrandData());
        rv_brand.setAdapter(ca);
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
                drawer.openDrawer(GravityCompat.START);
                Preferences.set_checkClicked(mContext, "0");
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
                if (response.isSuccessful()) {
                    myCart = response.body();
                    if (myCart.getAck() == 1) {
                        //Log.d("String", "" + myCart.getPriceData().getTotal_quantity());
                        tv_cartprice.setVisibility(View.VISIBLE);
                        ll_Cart_Price.setVisibility(View.VISIBLE);
                        tv_cartprice.setText(myCart.getPriceData().grand_total);
                        Preferences.set_Cartount(mContext, myCart.getPriceData().getTotal_quantity());
                        tv_cartcount.setText(myCart.getPriceData().getTotal_quantity());
                        if(myCart.getPriceData().getTotal_quantity().equals("")){
                            ll_Cart_Price.setVisibility(View.GONE);
                        }
                    } else {
                        Preferences.set_Cartount(mContext, "0");
                        tv_cartcount.setText("0");
                        tv_cartprice.setVisibility(View.GONE);
                        ll_Cart_Price.setVisibility(View.GONE);
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

    private void getNotes() {
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<Notes> call = redditAPI.GetNotes();
        call.enqueue(new Callback<Notes>() {

            @Override
            public void onResponse(Call<Notes> call, retrofit2.Response<Notes> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    notes = response.body();
                    if (notes.deliveryData.ack == 1) {
                        tv_note.setVisibility(View.GONE);
                        //  tv_note.setText(notes.deliveryData.content);
                    } else {
                        tv_note.setVisibility(View.GONE);
                    }


                }
                //fetchContactDetails();
                //getBannerData();
                // pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Notes> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    private void dashboardfeatured_products() {
        mensFashionList.clear();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        // category_id="3";
        //sub_category_id="3";

        Call<ProductListAcfold> call = redditAPI.Getfeatured_products();
        call.enqueue(new Callback<ProductListAcfold>() {

            @Override
            public void onResponse(Call<ProductListAcfold> call, retrofit2.Response<ProductListAcfold> response) {
                Log.d("String", "" + response);
                mensFashion = response.body();
                if (response.body().getAck() == 1) {
                    mensFashionList.addAll(mensFashion.productData);

                } else {
                    llMens.setVisibility(View.GONE);
                }

                viewAdapterMens.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ProductListAcfold> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    private void dashboardAdvantage() {
        womensFashionList.clear();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        // category_id="3";
        //sub_category_id="3";

        Call<AdvantageList> call = redditAPI.GetMostSellingProduct();
        call.enqueue(new Callback<AdvantageList>() {

            @Override
            public void onResponse(Call<AdvantageList> call, retrofit2.Response<AdvantageList> response) {
                Log.d("String", "" + response);
                womensFashion = response.body();
                if (response.body().getAck() == 1) {
                    womensFashionList.addAll(womensFashion.getPrizeData());

                } else {
                    llWomens.setVisibility(View.GONE);
                }
                viewAdapterWomen.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<AdvantageList> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Utility.showToastShort(mContext, "Press again to exit");
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }



    @Override
    public void adapterPositions(int pos) {
        /*Intent a = new Intent(mContext, DashboardOffersProductDetails.class);
        a.putExtra("position", "" + pos);
        a.putExtra("category_id", "");
        mContext.startActivity(a);*/
    }

    @Override
    public void updateCartCount() {
        {
            tv_cartcount.setText(Preferences.get_Cartount(mContext));
        }
    }

    @Override
    public void updateCartCounts() {
        tv_cartcount.setText(Preferences.get_Cartount(mContext));
    }

    @Override
    public void adapterPositionD(int pos) {
        Intent a = new Intent(mContext, DashboardDealsforYouProductDetails.class);
        a.putExtra("position", "" + pos);
        a.putExtra("category_id", "");
        mContext.startActivity(a);
    }

    @Override
    public void updateCartCountD() {
        tv_cartcount.setText(Preferences.get_Cartount(mContext));
    }

//    @Override
//    public void adapterPositionO(int pos) {
//        Intent a = new Intent(mContext, OfferDetailsDetails.class);
//        a.putExtra("position", "" + pos);
//        a.putExtra("category_id", "");
//        mContext.startActivity(a);
//    }
//
//    @Override
//    public void updateCartCountO() {
//        tv_cartcount.setText(Preferences.get_Cartount(mContext));
//    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("TAG", "Connection suspended");
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
