package com.store.promtech.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.MyOrderActivity;
import com.store.promtech.ProductOrderDetails;

import com.store.promtech.R;
import com.store.promtech.ReturnProductOrderDetails;
import com.store.promtech.model.BaseResponse;
import com.store.promtech.model.MyOrders;
import com.store.promtech.model.PostSuggession;
import com.store.promtech.model.ViewSuggession;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Preferences;
import com.store.promtech.utils.Utility;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyViewHolder> {
    private List<MyOrders.OrderDatum> moviesList;
    Context mContext;
    MyOrders.OrderDatum movie;
    MyViewHolder holder1;
    ProgressDialog progressDialog;
    ConnectionDetector cd;
    TextView tv_date;
    ProgressDialog pDialog;
    BaseResponse baseResponse;
    PostSuggession getPostSuggession;
    ViewSuggession getViewSuggession;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date, tv_totalitems, tv_totalprice, tv_paymentstatus, tv_orderid,tv_paymentmode,tv_deliverystatus,tv_orderstatus;
        Button btnDetails, btnCancel,btnReturn,btnReturnsend;

        LinearLayout v_parent;

        public MyViewHolder(View view) {
            super(view);
            tv_paymentmode=view.findViewById(R.id.tv_paymentmode);
            v_parent=view.findViewById(R.id.v_parent);
            tv_date = view.findViewById(R.id.tv_date);
            tv_totalitems = view.findViewById(R.id.tv_totalitems);
            tv_totalprice = view.findViewById(R.id.tv_totalprice);
            tv_paymentstatus = view.findViewById(R.id.tv_paymentstatus);

            tv_orderid = view.findViewById(R.id.tv_orderid);
            tv_date = view.findViewById(R.id.tv_date);
            btnDetails = view.findViewById(R.id.btnDetails);
            btnCancel = view.findViewById(R.id.btnCancel);
            btnReturn = view.findViewById(R.id.btnReturn);
            btnReturnsend = view.findViewById(R.id.btnReturnsend);
            tv_orderstatus = view.findViewById(R.id.tv_orderstatus);
            tv_deliverystatus = view.findViewById(R.id.tv_deliverystatus);
        }
    }

    public MyOrdersAdapter(List<MyOrders.OrderDatum> moviesList, Context mContext) {
        this.moviesList = moviesList;
        this.mContext = mContext;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        cd = new ConnectionDetector(mContext);

        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Please Wait...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder1 = holder;
        movie = moviesList.get(position);
       if(movie.getCancel_request_sent().equals("Yes"))
        {
            holder.v_parent.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            holder.btnCancel.setEnabled(false);
            holder.btnCancel.setText("  Cancellation Request Pending  ");
          //  holder.btnPostSuggession.setEnabled(false);
        }
        else
       {
           holder.v_parent.setBackgroundColor(mContext.getResources().getColor(R.color.white));
           holder.btnCancel.setEnabled(true);
           holder.btnCancel.setText("Cancel Order");
         //  holder.btnPostSuggession.setEnabled(true);
       }
       /* if(movie.getPayment_status().equals("Paid")){
            holder.btnCancel.setVisibility(View.GONE);
        }else {
            holder.btnCancel.setVisibility(View.VISIBLE);
        }*/
        if(movie.getDelivery_status().equalsIgnoreCase("Delivered")){
            holder.btnCancel.setVisibility(View.GONE);
        }else {
            holder.btnCancel.setVisibility(View.VISIBLE);
        }
         if(movie.getReturn_possible().equalsIgnoreCase("Yes") && movie.getReturn_request_sent().equalsIgnoreCase("No") && movie.getDelivery_status().equalsIgnoreCase("Delivered")){
            holder.btnReturn.setVisibility(View.VISIBLE);
        }else {
            holder.btnReturn.setVisibility(View.GONE);
        }
         if (movie.getReturn_request_sent().equalsIgnoreCase("yes") && movie.getReturn_confirm().equalsIgnoreCase("Pending")){
             holder.btnReturnsend.setText("Return Request Pending");
             holder.btnReturnsend.setVisibility(View.VISIBLE);
         }else if(movie.getReturn_request_sent().equalsIgnoreCase("yes") && movie.getReturn_confirm().equalsIgnoreCase("No")) {
             holder.btnReturnsend.setText("Return Request Declined");
             holder.btnReturnsend.setVisibility(View.VISIBLE);
         }else if(movie.getReturn_request_sent().equalsIgnoreCase("yes") && movie.getReturn_confirm().equalsIgnoreCase("Yes")) {
             holder.btnReturnsend.setText("Return Request Approved");
             holder.btnReturnsend.setVisibility(View.VISIBLE);
         }else{
             holder.btnReturnsend.setVisibility(View.GONE);
         }


       // holder.tv_paymentmode.setText(movie.getPayment_method());
        holder.tv_date.setText(Utility.getFormasssttedDate(movie.getOrder_date()));
        holder.tv_totalitems.setText( movie.getTotal_items());
        holder.tv_totalprice.setText(movie.getTotal_price());
        holder.tv_paymentstatus.setText( movie.getPayment_status());
        holder.tv_orderstatus.setText( movie.getOrder_status());
        holder.tv_deliverystatus.setText( movie.getDelivery_status());
        //holder.tv_deliverystatus.setText(movie.getDeliveryStatus());
        holder.tv_orderid.setText( movie.getOrderId());

        if(movie.getOrder_status().equalsIgnoreCase("Cancelled") || movie.getOrder_status().equalsIgnoreCase("Confirmed") || movie.getDelivery_status().equalsIgnoreCase("Delivered")){
            holder.btnCancel.setVisibility(View.GONE);
        }else{
            holder.btnCancel.setVisibility(View.VISIBLE);
        }

       /* holder.tv_deliveryDate.setText(movie.getPickup_date());
        holder.tv_deliveryTime.setText(movie.getPickup_time());

        if(movie.getPaymentStatus().equals("Pending"))
        {
            holder.btnCancel.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.btnCancel.setVisibility(View.GONE);
        }

        if(movie.getSuggesstinpost().equals("1"))
        {
            holder.btnViewSuggession.setVisibility(View.VISIBLE);
            holder.btnPostSuggession.setVisibility(View.GONE);
        }
        else
        {
           holder. btnPostSuggession.setVisibility(View.VISIBLE);
           holder. btnViewSuggession.setVisibility(View.GONE);
        }*/




        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ProductOrderDetails.class);
                i.putExtra("order_id", holder.tv_orderid.getText().toString().replace("Order Id :", "").trim());
                mContext.startActivity(i);
            }
        });
        holder.btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ReturnProductOrderDetails.class);
                i.putExtra("order_id", holder.tv_orderid.getText().toString().replace("Order Id :", "").trim());
                mContext.startActivity(i);
            }
        });
       /* holder.btnReturnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ReturnProductOrderDetails.class);
                i.putExtra("order_id", holder.tv_orderid.getText().toString().replace("Order Id :", "").trim());
                mContext.startActivity(i);
            }
        });*/
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                //showCancelDialog(mContext,holder.tv_orderid.getText().toString().replace("Order Id :", "").trim());
                showCancelDialog(mContext,holder.tv_orderid.getText().toString());

            }
        });

    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }



    public void showDialog(Context activity, final String OrderId) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_postfeedback_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final Button btnCancel = dialog.findViewById(R.id.btnCancel);
        final Button btnSubmit=dialog.findViewById(R.id.btnSubmit);
        final EditText etSuggesstion=dialog.findViewById(R.id.etSuggesstion);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etSuggesstion.getText().toString().trim().equals("")) {
                    postSuggession(OrderId, etSuggesstion.getText().toString().trim());
                }
                else
                {
                    Toast.makeText(mContext,"please enter suggession",Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void showCancelDialog(Context activity, final String OrderId) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_order_cancel_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final Button btnCancel = dialog.findViewById(R.id.btnCancel);
        final Button btnSubmit=dialog.findViewById(R.id.btnSubmit);
        final EditText etSuggesstion=dialog.findViewById(R.id.etSuggesstion);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etSuggesstion.getText().toString().trim().equals("")) {
                    cancelOrder(OrderId,etSuggesstion.getText().toString().trim());
                }
                else
                {
                    Toast.makeText(mContext,"please enter reason",Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void showViewDialog(Context activity, final String commect) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_viewfeedback_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final TextView tvSuggession = dialog.findViewById(R.id.tvSuggession);
        final Button tvCancel=dialog.findViewById(R.id.tvCancel);


        tvSuggession.setText(commect);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void cancelOrder(String OrderID,String request_comment) {

     //   Toast.makeText(mContext,OrderID,Toast.LENGTH_SHORT).show();
        pDialog.show();
        String BASE_URL = mContext.getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<BaseResponse> call = redditAPI.cancelOrder(Preferences.get_userId(mContext), OrderID,request_comment);
        call.enqueue(new Callback<BaseResponse>() {

            @Override
            public void onResponse(Call<BaseResponse> call, retrofit2.Response<BaseResponse> response) {
                Log.d("ResponseOTP", "" + response);
                if (response.isSuccessful()) {
                    //Log.d("Response",response.body().toString());
                    baseResponse = response.body();
                    if (baseResponse.getAck() == 1) {
                        Utility.showToastShort(mContext, baseResponse.getMsg());
                        ((MyOrderActivity) mContext).LoadCartProduct();
                    } else {
                        Utility.showToastShort(mContext, baseResponse.getMsg());
                    }
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    private void postSuggession(String OrderID,String comment) {
        pDialog.show();
        String BASE_URL = mContext.getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<PostSuggession> call = redditAPI.PostSuggession(Preferences.get_userId(mContext),comment, OrderID);
        call.enqueue(new Callback<PostSuggession>() {

            @Override
            public void onResponse(Call<PostSuggession> call, retrofit2.Response<PostSuggession> response) {
                Log.d("ResponseOTP", "" + response);
                if (response.isSuccessful()) {
                    pDialog.dismiss();

                    //Log.d("Response",response.body().toString());
                    getPostSuggession = response.body();
                    if (getPostSuggession.getAck() == 1) {
                        Utility.showToastShort(mContext, getPostSuggession.getMsg());
                        ((MyOrderActivity) mContext).LoadCartProduct();
                    } else {
                        Utility.showToastShort(mContext,  getPostSuggession.getMsg());
                    }
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<PostSuggession> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }


    private void viewSuggession(String OrderID) {
        pDialog.show();
        String BASE_URL = mContext.getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<ViewSuggession> call = redditAPI.ViewSuggession(Preferences.get_userId(mContext), OrderID);
        call.enqueue(new Callback<ViewSuggession>() {

            @Override
            public void onResponse(Call<ViewSuggession> call, retrofit2.Response<ViewSuggession> response) {
                Log.d("ResponseOTP", "" + response);
                if (response.isSuccessful()) {
                    pDialog.dismiss();

                    //Log.d("Response",response.body().toString());
                    getViewSuggession = response.body();
                    if (getViewSuggession.getSuggestionData().getAck() == 1) {

                        showViewDialog(mContext,getViewSuggession.getSuggestionData().getContent());


                    } else {
                        Utility.showToastShort(mContext, getViewSuggession.getSuggestionData().getMsg());
                    }
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ViewSuggession> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }



}
