package com.store.promtech.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.ProductCart;
import com.store.promtech.R;
import com.store.promtech.model.CartDeleteAction;
import com.store.promtech.model.MyCart;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private List<MyCart.CartDatum> moviesList;
    private List<MyCart.CartDatum> moviesList1;
    private List<MyCart.CartDatum> mArrayList;
    Context mContext;
    private int amount = 0;
    MyCart.CartDatum movie;
    MyViewHolder holder1;
    ProgressDialog progressDialog;
    String cartstringjson = "";
    String cart_id = "";
    ConnectionDetector cd;
    CartDeleteAction cartDeleteAction;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_productname,  tv_producttype, tv_price, tv_position;
        ImageView iv_product,btn_delete,iv_sub, iv_add;
        ProgressBar progressbar;
        TextView tv_id, tv_unitprice, tv_discount, tv_quantity,et_qty;

        public MyViewHolder(View view) {
            super(view);
            tv_id = (TextView) view.findViewById(R.id.tv_id);
            tv_discount = (TextView) view.findViewById(R.id.tv_discount);
            tv_productname = (TextView) view.findViewById(R.id.tv_productname);
            tv_producttype = (TextView) view.findViewById(R.id.tv_producttype);

            tv_position = (TextView) view.findViewById(R.id.tv_position);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            et_qty = (TextView) view.findViewById(R.id.et_qty);

            iv_product = (ImageView) view.findViewById(R.id.iv_product);
            iv_sub = (ImageView) view.findViewById(R.id.iv_sub);
            iv_add = (ImageView) view.findViewById(R.id.iv_add);
            progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
            btn_delete = view.findViewById(R.id.btn_delete);
            //  btn_update = view.findViewById(R.id.btn_update);
            tv_unitprice = view.findViewById(R.id.tv_unitprice);
            tv_quantity = view.findViewById(R.id.tv_quantity);
           // tv_deliveryPrice = view.findViewById(R.id.tv_deliveryPrice);

            // this.myCustomEditTextListener = myCustomEditTextListener;
            //et_qty.addTextChangedListener(myCustomEditTextListener);
        }
    }

    public CartAdapter(List<MyCart.CartDatum> moviesList, Context mContext) {
        this.moviesList = moviesList;
        this.moviesList1 = moviesList;
        this.mArrayList = moviesList;
        this.mContext = mContext;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        cd = new ConnectionDetector(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder1 = holder;
        movie = moviesList.get(position);
        holder.tv_productname.setText(movie.getProductName());
        holder.tv_id.setText(movie.getCartId());
        holder.tv_unitprice.setText(String.valueOf(movie.unitPrice));
       /* //holder.tv_quantity.setText("Packet      : " + String.valueOf(movie.quantity) + " Pc(s)");
        if (!movie.discount.equalsIgnoreCase("0")) {
            holder.tv_price.setVisibility(View.VISIBLE);
            holder.tv_discount.setVisibility(View.VISIBLE);
            //holder.tv_price.setText("\u20B9" + " " + movie.original_price + " " + movie.discount + " off");
            holder.tv_discount.setText(movie.discount + " off");
            holder.tv_price.setText( movie.original_price);
            holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.tv_price.setVisibility(View.GONE);
            holder.tv_discount.setVisibility(View.GONE);
        }*/


        holder.tv_position.setText(movie.getCartId());

        try {
//            Picasso.with(mContext)
//                    .load(movie.getProductPhoto())
//                    .into(holder.iv_product, new com.squareup.picasso.Callback() {
//                        @Override
//                        public void onSuccess() {
//                            holder.progressbar.setVisibility(View.GONE);
//                        }
//
//                        @Override
//                        public void onError() {
//                            holder.iv_product.setImageResource(R.mipmap.ic_launcher);
//                        }
//                    });


            Glide.with(mContext)
                    .load(movie.getProductPhoto())
                    .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                    .into(holder.iv_product);
        } catch (Exception e) {
        }


        holder.et_qty.setText(movie.getQuantity());
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(mContext)
                        .setTitle("Are you sure?")
                        .setMessage("You want to remove this item.")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (cd.isConnected()) {
                                    dialog.dismiss();
                                    deletefromcart(holder.tv_position.getText().toString().trim());
                                } else {
                                    Utility.showToastShort(mContext, "No Internet Connection");
                                }
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

            }
        });

        holder.iv_sub.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (cd.isConnected()) {
                    if (holder.iv_sub.isPressed()) {
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
                        //Utility.showToastShort(mContext, holder.tv_position.getText().toString().trim());
                        holder.et_qty.setText(String.valueOf(amount));
                        Log.d("Position-", "" + holder.tv_position.getText().toString());
                        updateCart(holder.tv_position.getText().toString().trim(), "1", "0");
                    }
                } else {
                    Utility.showToastShort(mContext, "No Internet Connection");
                }
            }
        });

        holder.iv_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (cd.isConnected()) {
                    if (holder.iv_add.isPressed()) {
                    //    if (Integer.parseInt(holder.et_qty.getText().toString()) <= 3) {
                            if (!holder.et_qty.getText().toString().isEmpty()) {
                                amount = Integer.parseInt(holder.et_qty.getText().toString());
                            } else {
                                amount = 0;
                            }
                            amount += 1;
                            holder.et_qty.setText(String.valueOf(amount));
                            if (amount != 0) {
                            }
                            Log.d("Position+", "" + holder.tv_position.getText().toString());
                            //Utility.showToastShort(mContext, holder.tv_position.getText().toString().trim());
                            updateCart(holder.tv_position.getText().toString().trim(), "1", "1");
                    //    }
                    } else {

                    }
                } else {
                    Utility.showToastShort(mContext, "No Internet Connection");
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    private void deletefromcart(String cart_id) {
        progressDialog.show();
        String BASE_URL = mContext.getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<CartDeleteAction> call = redditAPI.GetCartDeleteAction(Preferences.get_userId(mContext), cart_id, Preferences.get_UniqueId(mContext));
        call.enqueue(new Callback<CartDeleteAction>() {

            @Override
            public void onResponse(Call<CartDeleteAction> call, retrofit2.Response<CartDeleteAction> response) {
                Log.d("String", "" + response);
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    cartDeleteAction = response.body();
                    if (cartDeleteAction.getAck() == 1) {
                        ((ProductCart) mContext).LoadCartProduct();
                    } else {
                        ((ProductCart) mContext).LoadCartProduct();
                        Utility.showToastShort(mContext, cartDeleteAction.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<CartDeleteAction> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }


    private void updateCart(String cart_id, String quantity, String isCartAdd) {
        progressDialog.show();
        String BASE_URL = mContext.getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<CartDeleteAction> call = redditAPI.UpdateMyCart(Preferences.get_userId(mContext), cart_id, Preferences.get_UniqueId(mContext), "1", isCartAdd);
        call.enqueue(new Callback<CartDeleteAction>() {

            @Override
            public void onResponse(Call<CartDeleteAction> call, retrofit2.Response<CartDeleteAction> response) {
                Log.d("String", "" + response);
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    cartDeleteAction = response.body();
                    if (cartDeleteAction.getAck() == 1) {
                        Utility.showToastShort(mContext, cartDeleteAction.getMsg());
                        ((ProductCart) mContext).LoadCartProduct();
                    } else {
                        ((ProductCart) mContext).LoadCartProduct();
                        Utility.showToastShort(mContext, cartDeleteAction.getMsg());
                    }
                }

            }

            @Override
            public void onFailure(Call<CartDeleteAction> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}