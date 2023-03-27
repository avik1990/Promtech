package com.store.promtech.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.ProductPage;
import com.store.promtech.R;
import com.store.promtech.model.Brand;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Avik on 11-01-2017.
 */
public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.MyViewHolder> {

    private List<Brand.BrandDatum> countryList;
    Context mContext;

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivBrand;
        TextView tvBrandId;
        CardView card_view;



        public MyViewHolder(View view) {
            super(view);
            ivBrand = view.findViewById(R.id.ivBrand);
            tvBrandId=view.findViewById(R.id.tvBrandId);
            card_view=view.findViewById(R.id.card_view);

        }
    }

    public BrandAdapter(Context mContext, List<Brand.BrandDatum> countryList) {
        this.mContext = mContext;
        this.countryList = countryList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
       final Brand.BrandDatum c = countryList.get(position);

        Picasso.with(mContext)
                .load(c.getBrandPhoto())
                .into(holder.ivBrand, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });

        holder.tvBrandId.setText(c.getBrandId());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(mContext, ProductPage.class);
                a.putExtra("position", "" + position);
                a.putExtra("category_id", "0");
                a.putExtra("from","");
                a.putExtra("sub_category_id","0");
                a.putExtra("brand_id",c.getBrandId());
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_brand, parent, false);
        return new MyViewHolder(v);
    }
}
