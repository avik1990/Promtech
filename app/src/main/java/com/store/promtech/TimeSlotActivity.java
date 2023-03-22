package com.store.promtech;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.adapter.SlotAdapter;
import com.store.promtech.model.Slot;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Preferences;
import com.store.promtech.utils.Utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TimeSlotActivity extends AppCompatActivity implements View.OnClickListener ,SlotAdapter.SelectSlotListner {

    TextView tvDate,tvEmptyText;
    String dateText = "",total_amount="",deliveryType="";
    RecyclerView rv_slot;
    Slot getSlot;
    Context mContext;
    ConnectionDetector cd;
    ProgressDialog pDialog;
    ImageView btn_menu, btn_back;
    TextView tv_pagename,tvDateOne;
    Button btn_nextdate,btn_prevdate;
    String quick_delivery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);

        quick_delivery = getIntent().getExtras().getString("quick_delivery");
        total_amount=getIntent().getExtras().getString("total_amount");
        deliveryType=getIntent().getExtras().getString("deliveryType");
        initView();
        getDte();
    }

    private void initView() {
        tvDateOne=findViewById(R.id.tvDateOne);
        ImageView iv_home = findViewById(R.id.iv_home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, Dashboard.class);
                startActivity(i);
                finishAffinity();
            }
        });
        btn_nextdate=findViewById(R.id.btn_nextdate);
        btn_nextdate.setVisibility(View.VISIBLE);
        btn_prevdate=findViewById(R.id.btn_prevdate);
        tv_pagename = findViewById(R.id.tv_pagename);
        tv_pagename.setText("Choose Delivery Schedule");
        tvDate = findViewById(R.id.tvDate);
        rv_slot = findViewById(R.id.rv_slot);
        rv_slot.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private void getDte() {
        tvEmptyText=findViewById(R.id.tvEmptyText);
        btn_back = findViewById(R.id.btn_back);
        btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setVisibility(View.GONE);
        btn_back.setVisibility(View.VISIBLE);
        btn_menu.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        Date c = calendar.getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final String formattedDate = df.format(c);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        final String tomorrow = df.format( calendar.getTime());
        tvDateOne.setText(formattedDate);


        formatDate(formattedDate);

        if (cd.isConnected()) {
            parsejsonSlot(formattedDate);
        }
        else {
            Utility.showToastShort(mContext, getString(R.string.no_internet_msg));
        }

        btn_nextdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cd.isConnected()) {
                    // Toast.makeText(getApplicationContext(), tomorrow, Toast.LENGTH_SHORT).show();
                    btn_nextdate.setVisibility(View.GONE);
                    btn_prevdate.setVisibility(View.VISIBLE);
                    parsejsonSlot(tomorrow);
                    formatDate(tomorrow);
                    tvDateOne.setText(tomorrow);
                }
                else {
                    Utility.showToastShort(mContext, getString(R.string.no_internet_msg));
                }
            }
        });

        btn_prevdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cd.isConnected()) {
                    // Toast.makeText(getApplicationContext(), tomorrow, Toast.LENGTH_SHORT).show();
                    btn_nextdate.setVisibility(View.VISIBLE);
                    btn_prevdate.setVisibility(View.GONE);
                    parsejsonSlot(formattedDate);
                    formatDate(formattedDate);
                    tvDateOne.setText(formattedDate);
                }
                else {
                    Utility.showToastShort(mContext, getString(R.string.no_internet_msg));
                }
            }
        });

    }

    private void formatDate(String date)
    {
        String[] word = date.split("-");
        switch (word[1]) {
            case "01":
                dateText = "Jan";
                break;
            case "02":
                dateText = "Feb";
                break;
            case "03":
                dateText = "Mar";
                break;
            case "04":
                dateText = "Apr";
                break;
            case "05":
                dateText = "May";
                break;
            case "06":
                dateText = "Jun";
                break;
            case "07":
                dateText = "Jul";
                break;
            case "08":
                dateText = "Aug";
                break;
            case "09":
                dateText = "Sep";
                break;
            case "10":
                dateText = "Oct";
                break;
            case "11":
                dateText = "Nov";
                break;
            case "12":
                dateText = "Dec";
                break;

        }
        dateText = dateText + " " + word[2] + ", " + word[0];
        tvDate.setText(dateText);
    }


    @Override
    public void onClick(View v) {
        if (v == btn_back) {
            finish();
            onBackPressed();
        }
    }

    private void parsejsonSlot(String date) {
      pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<Slot> call = redditAPI.GetSlot(date);
        call.enqueue(new Callback<Slot>() {

            @Override
            public void onResponse(Call<Slot> call, retrofit2.Response<Slot> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    pDialog.dismiss();

                    getSlot = response.body();
                    if(getSlot.getAck()==1) {

                        if (getSlot.getPickupTimeData().size() > 0) {
                            rv_slot.setVisibility(View.VISIBLE);
                            tvEmptyText.setVisibility(View.GONE);

                            setupDeals();
                        } else {
                            rv_slot.setVisibility(View.GONE);
                            tvEmptyText.setVisibility(View.VISIBLE);
                            tvEmptyText.setText(getSlot.getMsg());
                        }
                    }
                    else
                    {
                        rv_slot.setVisibility(View.GONE);
                        tvEmptyText.setVisibility(View.VISIBLE);
                        tvEmptyText.setText(getSlot.getMsg());

                    }
                }


            }

            @Override
            public void onFailure(Call<Slot> call, Throwable t) {
                pDialog.dismiss();

                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setupDeals() {
        SlotAdapter ca = new SlotAdapter(mContext,getSlot.getPickupTimeData(),this);
        rv_slot.setAdapter(ca);
    }

    @Override
    public void SelectSlot(String slot) {
       // Toast.makeText(getApplicationContext(), slot+tvDate.getText().toString().trim(), Toast.LENGTH_SHORT).show();
        Log.e("Online",slot+"-"+total_amount+"-"+ Preferences.get_userId(mContext));
        /*Intent i = new Intent(mContext, Shippingactivity.class);
        i.putExtra("quick_delivery",quick_delivery);
        i.putExtra("solt_date",tvDateOne.getText().toString().trim());
        i.putExtra("slot_time",slot);
        i.putExtra("total_amount",total_amount);
        i.putExtra("deliveryType",deliveryType);
        startActivity(i);*/
    }
}
