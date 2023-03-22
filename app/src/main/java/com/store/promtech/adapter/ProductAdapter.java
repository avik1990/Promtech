package com.store.promtech.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Paint;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.R;
import com.store.promtech.model.BaseResponse;
import com.store.promtech.model.MyCart;
import com.store.promtech.model.ProductListAcfold;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Preferences;
import com.store.promtech.utils.Utility;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Avik on 11-01-2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    List<ProductListAcfold.ProductDatum> countryList;
    Context mContext;
    AdapterPos adapterPos;
    UpdateCartCount updateCartCount;
    private int amount = 0;
    ConnectionDetector cd;
    ProgressDialog pDialog;
    BaseResponse baseResponse;
    String product_id, packet_id;
    MyCart myCart;

    public interface AdapterPos {
        void adapterPosition(int pos);
    }

    public interface UpdateCartCount {
        void updateCartCount();
    }

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_chefimage;
        TextView tv_productname, tv_position, tv_productdetails, tv_dis_percent,tv_outof,tv_ava;
        ProgressBar progressbar;
        CardView card_view;
        TextView sp_packets, tv_price_orginal, tv_price_discount,tv_docSubmit;
        Button btn_add;
        LinearLayout ll_cart_quantity;
        ImageView iv_sub, iv_add;
        TextView et_qty;
        LinearLayout ll_mainbody,ll_price;
        RelativeLayout rl_mainbody;

        TextView tv_stockavailable;

        public MyViewHolder(View view) {
            super(view);
            ll_price =view.findViewById(R.id.ll_price);
            tv_stockavailable = view.findViewById(R.id.tv_stockavailable);
           // tv_overall_percent=view.findViewById(R.id.tv_overall_percent);
            iv_chefimage = view.findViewById(R.id.iv_chefimage);
            tv_productname = view.findViewById(R.id.tv_productname);
            progressbar = view.findViewById(R.id.progressbar);
            card_view = view.findViewById(R.id.card_view_root);
            tv_position = view.findViewById(R.id.tv_position);
            tv_productdetails = view.findViewById(R.id.tv_productdetails);
            sp_packets = view.findViewById(R.id.sp_packets);
            tv_price_orginal = view.findViewById(R.id.tv_price_orginal);
            tv_price_discount = view.findViewById(R.id.tv_price_discount);
            tv_dis_percent = view.findViewById(R.id.tv_dis_percent);
            btn_add = view.findViewById(R.id.btn_add);
            ll_mainbody = view.findViewById(R.id.ll_mainbody);
            rl_mainbody = view.findViewById(R.id.rl_mainbody);
            ll_cart_quantity = view.findViewById(R.id.ll_cart_quantity);
            iv_sub = view.findViewById(R.id.iv_sub);
            iv_add = view.findViewById(R.id.iv_add);
            et_qty = view.findViewById(R.id.et_qty);
            tv_outof=view.findViewById(R.id.tv_outof);
            tv_docSubmit=view.findViewById(R.id.tv_docSubmit);
            tv_ava=view.findViewById(R.id.tv_ava);
        }
    }

    public ProductAdapter(Context mContext, List<ProductListAcfold.ProductDatum> countryList, AdapterPos adapterPos, UpdateCartCount updateCartCount) {
        this.mContext = mContext;
        this.countryList = countryList;
        this.adapterPos = adapterPos;
        this.updateCartCount = updateCartCount;
        cd = new ConnectionDetector(mContext);
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);
    }



    @Override
    public int getItemCount() {
        return countryList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final int p= position;
        final ProductListAcfold.ProductDatum c = countryList.get(position);
        holder.tv_productname.setText(c.getProductNameEnglish());
        holder.progressbar.setVisibility(View.GONE);
        holder.tv_position.setText(String.valueOf(position));
        holder.iv_chefimage.setVisibility(View.VISIBLE);

        holder.rl_mainbody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterPos.adapterPosition(p);
            }
        });

        /*if(c.getStockAvailable().equals("Yes"))
        {

            holder.ll_price.setVisibility(View.VISIBLE);
            holder.tv_outof.setVisibility(View.GONE);

        }else if(c.getStockAvailable().equals("No"))
        {

            holder.ll_price.setVisibility(View.GONE);
            holder.tv_outof.setVisibility(View.VISIBLE);
        }

        if (Preferences.get_userComplete(mContext).equalsIgnoreCase("Yes")){
            holder.ll_price.setVisibility(View.VISIBLE);
        }else {
            holder.ll_price.setVisibility(View.GONE);
        }*/

      //  if (Preferences.get_userComplete(mContext).equalsIgnoreCase("Yes")){
            holder.tv_docSubmit.setVisibility(View.GONE);
            if(c.getStockAvailable().equalsIgnoreCase("yes")){
               // holder.ll_price.setVisibility(View.VISIBLE);
                holder.tv_outof.setVisibility(View.GONE);
                holder.tv_ava.setVisibility(View.VISIBLE);
            }else {
                //holder.ll_price.setVisibility(View.GONE);
                holder.tv_ava.setVisibility(View.GONE);
                holder.tv_outof.setVisibility(View.VISIBLE);
            }
       /* }else {
            holder.ll_price.setVisibility(View.GONE);
            holder.tv_docSubmit.setVisibility(View.VISIBLE);
            holder.tv_outof.setVisibility(View.GONE);
        }*/

                //  if (c.getPackets().get(c.getSelectedPos()).getDiscount().equals("0%")) {
                holder.tv_dis_percent.setVisibility(View.VISIBLE);
                holder.tv_price_orginal.setPaintFlags(holder.tv_price_orginal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tv_dis_percent.setText(" " + c.getDiscount()+" off");
                holder.tv_price_discount.setVisibility(View.VISIBLE);
                holder.tv_price_discount.setText(c.getSalePrice());
                holder.tv_price_orginal.setText(c.getOriginalPrice());
                holder.tv_stockavailable.setText("Stock: "+c.getStock());
                try {

                    Glide.with(mContext)
                            .load(c.getPhotos().get(0).getPhoto())
                            .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                            .into(holder.iv_chefimage);
                }catch (Exception e){
                    e.printStackTrace();
                }
//                } else {
//                    holder.tv_dis_percent.setVisibility(View.GONE);
//                    holder.tv_price_orginal.setPaintFlags(0);
//                    holder.tv_price_discount.setVisibility(View.GONE);
//




    }

/*    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ProductListAcfold.ProductDatum c = countryList.get(position);
        holder.tv_productname.setText(c.getProductNameEnglish());
        holder.progressbar.setVisibility(View.GONE);
        holder.tv_position.setText(String.valueOf(position));
        holder.iv_chefimage.setVisibility(View.VISIBLE);
        // holder.tv_productdetails.setText("dsfsdfsdfsdfsd");

       *//* if (!c.getBrand().isEmpty()) {
            holder.tv_productdetails.setVisibility(View.VISIBLE);
            holder.tv_productdetails.setText(c.getBrand());
        } else {
            holder.tv_productdetails.setVisibility(View.GONE);
        }*//*


        if(c.getStockAvailable().equals("Yes"))
        {
            holder.fl_layout.setVisibility(View.VISIBLE);
            holder.ll_price.setVisibility(View.VISIBLE);
            holder.tv_outof.setVisibility(View.GONE);

        }
        else if(c.getStockAvailable().equals("No"))
        {
            holder.fl_layout.setVisibility(View.GONE);
            holder.ll_price.setVisibility(View.GONE);
            holder.tv_outof.setVisibility(View.VISIBLE);
        }

        holder.ll_mainbody.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                adapterPos.adapterPosition(position);
            }
        });

        holder.iv_chefimage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                adapterPos.adapterPosition(position);
            }
        });


        ///handle button click
       *//* if (c.isClicked()) {
            holder.btn_add.setVisibility(View.GONE);
            holder.ll_cart_quantity.setVisibility(View.VISIBLE);
        } else {
            holder.btn_add.setVisibility(c.getStockAvailable().equals("No") ? View.GONE : View.VISIBLE);
            holder.ll_cart_quantity.setVisibility(View.GONE);
        }*//*

        final ProgressBar progressView = holder.progressbar;

        try {
//            Picasso.with(mContext)
//                    .load(c.getProductPhoto())
//                    .into(holder.iv_chefimage, new com.squareup.picasso.Callback() {
//
//                        @Override
//                        public void onSuccess() {
//                            progressView.setVisibility(View.GONE);
//                        }
//
//                        @Override
//                        public void onError() {
//                            progressView.setVisibility(View.GONE);
//                        }
//                    });

            *//*Glide.with(mContext)
                    .load(c.getProductPhoto())
                    .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                    .into(holder.iv_chefimage);*//*
        } catch (Exception e) {

        }

        *//*if(c.getOverallDiscount() == null || c.getOverallDiscount().equals("") || c.getOverallDiscount().equals("0")) {
            holder.tv_overall_percent.setVisibility(View.INVISIBLE);
        } else {
            holder.tv_overall_percent.setVisibility(View.VISIBLE);
            holder.tv_overall_percent.setText(c.getOverallDiscount() + "% off");
        }


        *//*

        *//*holder.btn_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (c.getPackets().size() > 0) {
                    if(Integer.parseInt(holder.et_qty.getText().toString())<=9) {
                    product_id = c.getProductId();
                    if (c.getSelectedPos() == -1) {
                        packet_id = c.getPackets().get(0).getPacketId();
                    } else {
                        packet_id = c.getPackets().get(c.getSelectedPos()).getPacketId();
                    }

                    holder.btn_add.setVisibility(View.GONE);
                    holder.ll_cart_quantity.setVisibility(View.VISIBLE);
                    c.setClicked(true);
                    amount = 1;
                    if (cd.isConnected()) {
                        AddtoCartServices("1");
                    }
                    }
                    else
                    {

                    }
                }else {
                    Utility.showToastShort(mContext, "Please select pack size");
                }

            }
        });*//*


        *//*holder.iv_sub.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (cd.isConnected()) {
                    if (holder.iv_sub.isPressed()) {
                        if (c.getPackets().size() > 0) {
                            if (holder.et_qty.getText().toString().equals("1")) {
                                return;
                            }
                            if (!holder.et_qty.getText().toString().isEmpty()) {
                                amount = Integer.parseInt(holder.et_qty.getText().toString());
                            } else {
                                amount = 0;
                            }
                            if (amount > 0) {
                                amount -= 1;
                            }
                            if (amount == 0) {
                                amount = 1;
                            }
                            if (amount != 0) {
                            }
                            holder.et_qty.setText(String.valueOf(amount));
                            //holder.et_qty.setText(String.valueOf(amount));


                            product_id = c.getProductId();

                            if (c.getSelectedPos() == -1) {
                                packet_id = c.getPackets().get(0).getPacketId();
                            } else {
                                packet_id = c.getPackets().get(c.getSelectedPos()).getPacketId();
                            }
                            //Utility.showToastShort(mContext, product_id + ":" + packet_id);
                            if (cd.isConnected()) {
                                AddtoCartServices("0");
                            }
                        }else{
                            Utility.showToastShort(mContext, "Please select pack size");
                        }
                    }
                } else {
                    Utility.showToastShort(mContext, "No Internet Connection");
                }
            }
        });*//*

        *//*holder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnected()) {
                    if (holder.iv_add.isPressed()) {
                        if (c.getPackets().size() > 0) {
                          //  if(Integer.parseInt(holder.et_qty.getText().toString())<=3) {
                            if (!holder.et_qty.getText().toString().isEmpty()) {
                                amount = Integer.parseInt(holder.et_qty.getText().toString());
                            } else {
                                amount = 0;
                            }
                            amount += 1;
                            holder.et_qty.setText(String.valueOf(amount));
                            if (amount != 0) {
                            }

                            product_id = c.getProductId();

                            if (c.getSelectedPos() == -1) {
                                packet_id = c.getPackets().get(0).getPacketId();
                            } else {
                                packet_id = c.getPackets().get(c.getSelectedPos()).getPacketId();
                            }

                            if (cd.isConnected()) {
                                AddtoCartServices("1");
                            }
                            *//**//*}
                            else
                            {

                            }*//**//*
                        }else{
                            Utility.showToastShort(mContext, "Please select pack size");
                        }
                    }
                } else {
                    Utility.showToastShort(mContext, "No Internet Connection");
                }
            }
        });*//*

    }*/

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void AddtoCartServices(String isCartAdd) {
        pDialog.show();
        String BASE_URL = mContext.getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);

        Call<BaseResponse> call = redditAPI.AddtoCartService(Preferences.get_userId(mContext), Preferences.get_UniqueId(mContext),
                product_id,
                packet_id,
                "1", isCartAdd);
        call.enqueue(new Callback<BaseResponse>() {

            @Override
            public void onResponse(Call<BaseResponse> call, retrofit2.Response<BaseResponse> response) {
                pDialog.dismiss();
                Log.d("String", "" + response);
                baseResponse = response.body();
                Log.d("String", "" + baseResponse.getAck());
                if (baseResponse.getAck()==1) {
                    Utility.showToastShort(mContext, baseResponse.getMsg());
                    LoadCartProduct();
                }
                else
                {
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
        String BASE_URL = mContext.getResources().getString(R.string.base_url);
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
                            //Preferences.set_Cartount(mContext, myCart.getPriceData().getTotalPrice());
                            updateCartCount.updateCartCount();
                        } else {
                            Preferences.set_Cartount(mContext,"0");

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
}
