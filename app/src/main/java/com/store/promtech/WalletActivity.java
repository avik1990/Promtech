package com.store.promtech;

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

import com.store.promtech.adapter.WalletExpenseAdapter;
import com.store.promtech.model.Wallet;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Preferences;
import com.store.promtech.utils.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tv_earn,tv_expense,tv_balance;
    RecyclerView rv_wallet_earn,rv_wallet_expanse;
    ConnectionDetector cd;
    ProgressDialog pDialog;
    Context mContext;
    ImageView iv_home,iv_cart;
    Wallet getWallet;
    ImageView btn_menu, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        mContext = this;
        cd = new ConnectionDetector(mContext);
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);

        initView();
    }

    private void initView() {
        btn_menu=findViewById(R.id.btn_menu);
        tv_earn=findViewById(R.id.tv_earn);
        tv_expense=findViewById(R.id.tv_expense);
        tv_balance=findViewById(R.id.tv_balance);
        iv_home=findViewById(R.id.iv_home);
        rv_wallet_earn=findViewById(R.id.rv_wallet_earn);
        rv_wallet_expanse=findViewById(R.id.rv_wallet_expanse);
        rv_wallet_earn.setLayoutManager(new LinearLayoutManager(mContext));
        rv_wallet_expanse.setLayoutManager(new LinearLayoutManager(mContext));
        btn_back = findViewById(R.id.btn_back);
        iv_cart = findViewById(R.id.iv_cart);
        iv_cart.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_menu.setVisibility(View.GONE);
        btn_back.setVisibility(View.VISIBLE);

        if (cd.isConnected()) {
            parsejson();
        } else {
            Utility.showToastShort(mContext, getString(R.string.no_internet_msg));
        }

        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,Dashboard.class);
                startActivity(i);
                finishAffinity();
            }
        });

        tv_earn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_earn.setBackground(getResources().getDrawable(R.drawable.ondata_bg));
                tv_expense.setBackground(getResources().getDrawable(R.drawable.off_data_bg));
                tv_earn.setTextColor(getResources().getColor(R.color.white));
                tv_expense.setTextColor(getResources().getColor(R.color.black));
                rv_wallet_earn.setVisibility(View.VISIBLE);
                rv_wallet_expanse.setVisibility(View.GONE);
            }
        });
        tv_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_earn.setBackground(getResources().getDrawable(R.drawable.off_data_bg));
                tv_expense.setBackground(getResources().getDrawable(R.drawable.ondata_bg));
                tv_expense.setTextColor(getResources().getColor(R.color.white));
                tv_earn.setTextColor(getResources().getColor(R.color.black));
                rv_wallet_earn.setVisibility(View.GONE);
                rv_wallet_expanse.setVisibility(View.VISIBLE);
            }
        });
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
        Call<Wallet> call = redditAPI.GetWalletBenefit(Preferences.get_userId(mContext));
        call.enqueue(new Callback<Wallet>() {

            @Override
            public void onResponse(Call<Wallet> call, retrofit2.Response<Wallet> response) {
                Log.d("String", "" + response);
                pDialog.dismiss();
                if (response.isSuccessful()) {
                    getWallet = response.body();
                    if (getWallet.getAck()==1) {
                       // setupRecylerviewEarn();
                        setupRecylerviewExpense();
                        tv_balance.setText(getWallet.getWalletBalance());

                    }
                    else
                    {
                        tv_balance.setText("0");

                    }
                }

            }

            @Override
            public void onFailure(Call<Wallet> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setupRecylerviewEarn() {
//        WalletEarnAdapter ca = new WalletEarnAdapter(mContext, getWallet.getWalletData().get(0).getEarnData());
//        rv_wallet_earn.setAdapter(ca);
    }

    private void setupRecylerviewExpense() {
        WalletExpenseAdapter ca = new WalletExpenseAdapter(mContext, getWallet.getWalletData());
        rv_wallet_expanse.setAdapter(ca);
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
}
