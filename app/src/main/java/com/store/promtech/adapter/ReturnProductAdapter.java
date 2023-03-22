package com.store.promtech.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.store.promtech.R;
import com.store.promtech.model.CartDeleteAction;
import com.store.promtech.model.MyOrdersDetailsModel;
import com.store.promtech.model.ReturnList;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Utility;

import java.util.List;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static com.store.promtech.ReturnProductOrderDetails.returnArray;

public class ReturnProductAdapter extends RecyclerView.Adapter<ReturnProductAdapter.MyViewHolder> {
    private static List<MyOrdersDetailsModel.CartDatum> moviesList;

    Context mContext;
    private int amount = 0;
    MyOrdersDetailsModel.CartDatum movie;
    MyViewHolder holder1;
    ProgressDialog progressDialog;
    String cartstringjson = "";
    String cart_id = "";
    ConnectionDetector cd;
    CartDeleteAction cartDeleteAction;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_productname,tv_price, tv_position;
        TextView et_qty,tvquantity,et_delivered_qty;
        ImageView iv_product;
        ProgressBar progressbar;
        TextView tv_id, tv_unitprice, tv_quantity;
        CardView swipe_item;
        RelativeLayout v_main;
        AppCompatCheckBox cbox;
        EditText et_quantity;

        public MyViewHolder(View view) {
            super(view);
            v_main=view.findViewById(R.id.v_main);
            swipe_item=view.findViewById(R.id.swipe_item);
            tv_id = (TextView) view.findViewById(R.id.tv_id);
            tv_productname = (TextView) view.findViewById(R.id.tv_productname);
            tv_position = (TextView) view.findViewById(R.id.tv_position);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            et_qty = view.findViewById(R.id.et_qty);
            iv_product = (ImageView) view.findViewById(R.id.iv_product);
            progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
            //  btn_update = view.findViewById(R.id.btn_update);
            tv_unitprice = view.findViewById(R.id.tv_unitprice);
            tv_quantity = view.findViewById(R.id.tv_quantity);
            cbox = view.findViewById(R.id.cbox);
            et_quantity = view.findViewById(R.id.et_quantity);
            tvquantity = view.findViewById(R.id.tvquantity);
            et_delivered_qty = view.findViewById(R.id.et_delivered_qty);

            // this.myCustomEditTextListener = myCustomEditTextListener;
            //et_qty.addTextChangedListener(myCustomEditTextListener);
        }
    }

    public ReturnProductAdapter(List<MyOrdersDetailsModel.CartDatum> moviesList, Context mContext) {
        this.moviesList = moviesList;
        this.mContext = mContext;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        cd = new ConnectionDetector(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_ordered, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder1 = holder;
        movie = moviesList.get(position);
        holder.cbox.setVisibility(View.VISIBLE);

        holder.tv_productname.setText(movie.getProductNameEnglish());
        holder.tv_id.setText(movie.getProductId());
        holder.et_qty.setText(String.valueOf(movie.getQuantity()));
        holder.et_delivered_qty.setText(String.valueOf(movie.getDelivered_quantity()));

        holder.tv_price.setText("" + movie.getSubtotal());
     //   Toast.makeText(mContext,movie.getPacketSize(),Toast.LENGTH_SHORT).show();
        holder.tv_unitprice.setText(""+ String.valueOf(movie.getUnitPrice()));
        //holder.tv_quantity.setText("Packet      : " + String.valueOf(movie.quantity) + " Pc(s)");
       /* if (!movie.discount.equalsIgnoreCase("0")) {
            holder.tv_price.setVisibility(View.GONE);
            holder.tv_discount.setVisibility(View.GONE);
            //holder.tv_price.setText("\u20B9" + " " + movie.original_price + " " + movie.discount + " off");
            holder.tv_discount.setText(movie.discount + "% off");
            holder.tv_price.setText("\u20A8" + ". " + movie.originalPrice);
            holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.tv_price.setVisibility(View.GONE);
            holder.tv_discount.setVisibility(View.GONE);
        }*/


       /*holder.v_main.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i=new Intent(mContext, OrderProductDetailsActivity.class);
               i.putExtra("position",position+"");
               i.putExtra("cat_id",movie.getCartId());
               mContext.startActivity(i);
           }
       });*/
        String[] splited = movie.getDelivered_quantity().split(" ");
        holder.cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                       // if (Integer.parseInt(holder.et_quantity.getText().toString())<=Integer.parseInt(splited[0])) {
                            holder.et_quantity.setVisibility(View.VISIBLE);
                            holder.tvquantity.setVisibility(View.VISIBLE);
                            holder.et_quantity.setText(String.valueOf(splited[0]));

                            if (!returnArray.contains(movie.getProductId()))
                               // returnArray.add(new ReturnList(movie.getProductId(), holder.et_quantity.getText().toString()));
                                returnArray.add(new ReturnList(holder.tv_id.getText().toString(), holder.et_quantity.getText().toString()));
                    Log.i("getProduct_id", returnArray.get(0).getProduct_id());
//                        }else{
//                            Utility.showToastShort(mContext, "Please Check quantity which want to return!");
//                        }
                }else {
                    holder.et_quantity.setVisibility(View.GONE);
                    holder.tvquantity.setVisibility(View.GONE);
                    if (returnArray.contains(movie.getProductId()))
                        returnArray.remove(movie.getProductId());
                }
            }
        });

        holder.et_quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.et_quantity.getText().toString().equals("") || holder.et_quantity.getText().toString().equals("0")) {

                        for (int i = 0; i < returnArray.size(); i++) {
                            if (returnArray.get(i).getProduct_id() == movie.getProductId()) {
                                returnArray.set(i, new ReturnList(movie.getProductId(), "0"));
                            }
                        }
                        Utility.showToastShort(mContext, "Please enter quantity which want to return!");

                }else{
                    if (Integer.parseInt(holder.et_quantity.getText().toString()) <= Integer.parseInt(splited[0])) {
                        for (int i = 0; i < returnArray.size(); i++) {
                            if (returnArray.get(i).getProduct_id() == movie.getProductId()) {
                                returnArray.set(i, new ReturnList(movie.getProductId(), holder.et_quantity.getText().toString()));
                            }
                        }
                    } else {
                        Utility.showToastShort(mContext, "Please Check quantity which want to return!");
                    }
                }
            }
        });
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

        try {
            if (!holder.et_qty.getText().equals("0") && !holder.et_qty.getText().toString().isEmpty()) {
                amount = Integer.parseInt(holder.et_qty.getText().toString().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
