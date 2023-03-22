package com.store.promtech.adapter;

import android.app.ProgressDialog;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.R;
import com.store.promtech.model.BaseResponse;
import com.store.promtech.model.MyCart;
import com.store.promtech.model.Offers;
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
public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {
    private List<Offers.ProductDatum> moviesList;
    Context mContext;
    OfferAdapter.AdapterPos adapterPos;
    OfferAdapter.UpdateCartCount updateCartCount;
    private int amount = 0;
    ConnectionDetector cd;
    ProgressDialog pDialog;
    BaseResponse baseResponse;
    String product_id, packet_id;
    MyCart myCart;
    String tag;

    public interface AdapterPos {
        void adapterPositions(int pos);
    }

    public interface UpdateCartCount {
        void updateCartCount();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_chefimage;
        TextView tv_productname, tv_position, tv_productdetails, tv_dis_percent,tv_outof, tv_offer_text;
        ProgressBar progressbar;
        RelativeLayout card_view;
        TextView sp_packets, tv_price_orginal, tv_price_discount;
        Button btn_add;
        LinearLayout ll_cart_quantity;
        ImageView iv_sub, iv_add;
        TextView et_qty;
        LinearLayout ll_mainbody,ll_price;
        FrameLayout fl_layout;

        public MyViewHolder(View view) {
            super(view);

            iv_chefimage = view.findViewById(R.id.iv_chefimage);

        }
    }

    public OfferAdapter(List<Offers.ProductDatum> moviesList, Context mContext, String tag, OfferAdapter.AdapterPos adapterPos, OfferAdapter.UpdateCartCount updateCartCount) {
        this.mContext = mContext;
        this.moviesList = moviesList;
        this.adapterPos = adapterPos;
        this.tag=tag;
        this.updateCartCount = updateCartCount;
        cd = new ConnectionDetector(mContext);
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Offers.ProductDatum c = moviesList.get(position);


        try {


            Glide.with(mContext)
                    .load(c.getOffer_photo())
                    .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                    .into(holder.iv_chefimage);
        } catch (Exception e) {

        }




    }


    @Override
    public int getItemCount() {
        return moviesList.size();
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
                            //Preferences.set_Cartount(mContext, myCart.getPriceData().getTotal_quantity());
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
