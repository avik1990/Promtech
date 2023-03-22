package com.store.promtech.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.store.promtech.R;
import com.store.promtech.model.SubSubCategoryDataResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Avik on 11-01-2017.
 */
public class SubSubCategoryAdapter extends RecyclerView.Adapter<SubSubCategoryAdapter.MyViewHolder> {

    List<SubSubCategoryDataResponse.SubCategoryDatum> countryList;
    Context mContext;
    AdapterPos adapterPos;

    public interface AdapterPos {
        void adapterPositionSub(int pos);
    }

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_chefimage;
        TextView tv_productname, tv_position;
        ProgressBar progressbar;
        CardView card_view;


        public MyViewHolder(View view) {
            super(view);
            iv_chefimage = (ImageView) view.findViewById(R.id.iv_chefimage);
            tv_productname = (TextView) view.findViewById(R.id.tv_productname);
            progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
            card_view = (CardView) view.findViewById(R.id.card_view);
            tv_position = view.findViewById(R.id.tv_position);
        }
    }

    public SubSubCategoryAdapter(Context mContext, List<SubSubCategoryDataResponse.SubCategoryDatum> countryList, AdapterPos adapterPos) {
        this.mContext = mContext;
        this.countryList = countryList;
        this.adapterPos = adapterPos;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SubSubCategoryDataResponse.SubCategoryDatum c = countryList.get(position);
        holder.tv_productname.setText(c.getSubcategoryName());
        
        holder.tv_position.setText(String.valueOf(position));
        holder.iv_chefimage.setVisibility(View.VISIBLE);
        final ProgressBar progressView = holder.progressbar;
        progressView.setVisibility(View.VISIBLE);
        try {
            Picasso.with(mContext)
                    .load(c.getSubcategoryPhoto())
                    .into(holder.iv_chefimage, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            progressView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            progressView.setVisibility(View.GONE);
                        }
                    });
           /* Glide.with(mContext)
                    .load(c.getSubcategoryPhoto())
                    .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                    .into(holder.iv_chefimage);*/

        } catch (Exception e) {
        }

        holder.card_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                adapterPos.adapterPositionSub(Integer.parseInt(holder.tv_position.getText().toString()));
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_products, parent, false);
        return new MyViewHolder(v);
    }
}
