package com.store.promtech.adapter;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.DashboardSlidingProductDetails;
import com.store.promtech.R;
import com.store.promtech.model.SlidingProduct;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by Avik on 11-01-2017.
 */
public class SlidingProductAdapter extends RecyclerView.Adapter<SlidingProductAdapter.MyViewHolder> {

    private List<SlidingProduct.ProductDatum> countryList;
    Context mContext;

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_catimage;
        TextView tv_categoryname;
        ProgressBar progressbar;
        CardView card_view;


        public MyViewHolder(View view) {
            super(view);
            iv_catimage = view.findViewById(R.id.iv_catimage);
            tv_categoryname = view.findViewById(R.id.tv_categoryname);
            progressbar = view.findViewById(R.id.progressbar);
            card_view = view.findViewById(R.id.card_view);
        }
    }

    public SlidingProductAdapter(Context mContext, List<SlidingProduct.ProductDatum> countryList) {
        this.mContext = mContext;
        this.countryList = countryList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       final SlidingProduct.ProductDatum c = countryList.get(position);
        holder.progressbar.setVisibility(View.GONE);
        holder.iv_catimage.setVisibility(View.VISIBLE);
        final ProgressBar progressView = holder.progressbar;
//        Picasso.with(mContext)
//                .load(c.getProductPhoto())
//                .into(holder.iv_catimage, new com.squareup.picasso.Callback() {
//                    @Override
//                    public void onSuccess() {
//                        progressView.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                });

        Glide.with(mContext)
                .load(c.getProductPhoto())
                .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                .into(holder.iv_catimage);
        holder.tv_categoryname.setText(c.getProductNameEnglish());

        holder.card_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                    Intent a = new Intent(mContext, DashboardSlidingProductDetails.class);
                    a.putExtra("position", "" + position);
                    a.putExtra("category_id", "");
                    a.putExtra("from", holder.tv_categoryname.getText().toString().trim());
                    a.putExtra("sub_category_id","0");
                    a.putExtra("brand_id","0");
                    mContext.startActivity(a);

            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d("RV", "Item size [" + countryList.size() + "]");
        return countryList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sliding_bg, parent, false);
        return new MyViewHolder(v);
    }
}
