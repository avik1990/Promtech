package com.store.promtech.returnedproadapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.store.promtech.R;
import com.store.promtech.model.MyOrdersDetailsModel;
import com.store.promtech.model.ReturnList;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Utility;

import java.util.List;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class ReturnProductAdapter extends RecyclerView.Adapter<ReturnProductAdapter.MyViewHolder> {
    private static List<MyOrdersDetailsModel.CartDatum> moviesList;

    Context mContext;
    private int amount = 0;
    MyOrdersDetailsModel.CartDatum movie;
    ProgressDialog progressDialog;
    String cartstringjson = "";
    String cart_id = "";
    ConnectionDetector cd;
    Interaction interaction1;

    public interface Interaction {
        void onItemChecked(int position, MyOrdersDetailsModel.CartDatum data,int flag,int tempdata);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_productname,tv_price, tv_position;
        TextView et_qty,tvquantity,et_delivered_qty;
        ImageView iv_product;
        ProgressBar progressbar;
        TextView tv_id, tv_unitprice, tv_quantity;
        CardView swipe_item;
        RelativeLayout v_main,chkView;
        AppCompatCheckBox cbox;
        EditText et_quantity;
        LinearLayout viewQty;

        public MyViewHolder(View view) {
            super(view);
            chkView = view.findViewById(R.id.chkView);
            chkView.setVisibility(View.VISIBLE);
            viewQty = view.findViewById(R.id.viewQty);
            v_main = view.findViewById(R.id.v_main);
            swipe_item=view.findViewById(R.id.swipe_item);
            tv_id = (TextView) view.findViewById(R.id.tv_id);
            tv_productname = (TextView) view.findViewById(R.id.tv_productname);
            tv_position = (TextView) view.findViewById(R.id.tv_position);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            et_qty = view.findViewById(R.id.et_qty);
            iv_product = (ImageView) view.findViewById(R.id.iv_product);
            progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
            tv_unitprice = view.findViewById(R.id.tv_unitprice);
            tv_quantity = view.findViewById(R.id.tv_quantity);
            cbox = view.findViewById(R.id.cbox);
            et_quantity = view.findViewById(R.id.et_quantity);
            tvquantity = view.findViewById(R.id.tvquantity);
            et_delivered_qty = view.findViewById(R.id.et_delivered_qty);

        }
    }

    public ReturnProductAdapter(List<MyOrdersDetailsModel.CartDatum> moviesList, Context mContext,Interaction interaction) {
        ReturnProductAdapter.moviesList = moviesList;
        this.interaction1=interaction;
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
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        movie = moviesList.get(position);
       // holder.setIsRecyclable(false);
        holder.cbox.setVisibility(View.VISIBLE);
        holder.tv_productname.setText(movie.getProductNameEnglish() +" "+movie.getProductId());
        holder.tv_id.setText(movie.getProductId());
        holder.et_qty.setText(String.valueOf(movie.getQuantity()));
        holder.et_delivered_qty.setText(String.valueOf(movie.getDelivered_quantity()));

        holder.tv_price.setText("" + movie.getSubtotal());
        holder.tv_unitprice.setText(""+ String.valueOf(movie.getUnitPrice()));
        holder.et_quantity.setImeOptions(EditorInfo.IME_ACTION_DONE);

        String[] splited = movie.getDelivered_quantity().split(" ");
        if(moviesList.get(position).isSelectes()){
            holder.cbox.setChecked(true);
            holder.viewQty.setVisibility(View.VISIBLE);
            holder.et_quantity.setText(moviesList.get(position).getTempQuantity());
        }else {
            holder.cbox.setChecked(false);
            holder.viewQty.setVisibility(View.GONE);
            holder.et_quantity.setText(splited[0]);
        }


        holder.cbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(holder.cbox.isPressed()){
                if(isChecked){
                    //interaction1.onItemChecked(position,moviesList.get(position),1,Integer.parseInt(moviesList.get(position).getDelivered_quantity().replace("Pcs","").trim()));
                    moviesList.get(position).setSelectes(true);
                    holder.viewQty.setVisibility(View.VISIBLE);
                    holder.et_quantity.setText(String.valueOf(splited[0]));
                    moviesList.get(position).setTempQuantity(String.valueOf(splited[0]));
                }else {
                    //moviesList.get(position).setTempQuantity(String.valueOf(splited[0]));
                    interaction1.onItemChecked(position,moviesList.get(position),0,Integer.parseInt(moviesList.get(position).getDelivered_quantity().replace("Pcs","").trim()));
                    moviesList.get(position).setSelectes(false);
                    holder.viewQty.setVisibility(View.GONE);
                }
            }

        });

        if(holder.et_quantity.isFocusable()){

            holder.et_quantity.addTextChangedListener(new TextWatcher() {
                boolean isOnTextChanged = false;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    isOnTextChanged = true;
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (isOnTextChanged) {
                        isOnTextChanged = false;
                    }
                    if (holder.et_quantity.getText().toString().trim().isEmpty() ||
                            holder.et_quantity.getText().toString().equals("0")) {
                        moviesList.get(position).setTempQuantity("0");
                        interaction1.onItemChecked(position,moviesList.get(position),0,0);

                    } else{
                        if (Integer.parseInt(holder.et_quantity.getText().toString()) <= Integer.parseInt(holder.et_delivered_qty.getText().toString().trim().replace("Pcs","").trim())) {
                            moviesList.get(position).setTempQuantity(holder.et_quantity.getText().toString());
                            interaction1.onItemChecked(position,moviesList.get(position),1,Integer.parseInt(holder.et_quantity.getText().toString().trim()));
                        } else {
                            holder.et_quantity.setText(splited[0]);
                            Utility.showToastShort(mContext, "Please Check quantity which want to return!");
                        }
                    }

                }
            });
        }


        try {
            Glide.with(mContext)
                    .load(movie.getProductPhoto())
                    .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                    .into(holder.iv_product);
        } catch (Exception e) {
        }

        try {
            if (!holder.et_qty.getText().equals("0") && !holder.et_qty.getText().toString().isEmpty()) {
                amount = Integer.parseInt(holder.et_qty.getText().toString().trim().replace("Pcs","").trim());
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
