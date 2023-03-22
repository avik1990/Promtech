package com.store.promtech.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.R;
import com.store.promtech.model.WhyChoose;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by Avik on 11-01-2017.
 */
public class WhyChooseAdapter extends RecyclerView.Adapter<WhyChooseAdapter.MyViewHolder> {

    private List<WhyChoose.ChooseDatum> offerDatumList;
    Context mContext;
    private OnOfferClickListener offerClickListener;

    public void setOnOfferClickListener(OnOfferClickListener listener) {
        offerClickListener = listener;
    }

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_product;
        TextView tv_title,tv_text;
        ProgressBar progressbar;



        public MyViewHolder(View view) {
            super(view);
            iv_product = view.findViewById(R.id.iv_product);
            tv_text=view.findViewById(R.id.tv_text);
            tv_title=view.findViewById(R.id.tv_title);
            progressbar=view.findViewById(R.id.progressbar);

        }
    }

    public WhyChooseAdapter(Context mContext, List<WhyChoose.ChooseDatum> offerDatumList) {
        this.mContext = mContext;
        this.offerDatumList = offerDatumList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       final WhyChoose.ChooseDatum c = offerDatumList.get(position);


       holder.tv_text.setText(c.getChooseText());
       holder.tv_title.setText(c.getChooseTitle());
//        Picasso.with(mContext)
//                .load(c.getChoosePhoto())
//                .into(holder.iv_product, new com.squareup.picasso.Callback() {
//                    @Override
//                    public void onSuccess() {
//                        holder.progressbar.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onError() {
//                        holder.progressbar.setVisibility(View.GONE);
//                    }
//                });



        Glide.with(mContext)
                .load(c.getChoosePhoto())
                .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                .into(holder.iv_product);
//        holder.iv_product.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (offerClickListener != null)
//                    offerClickListener.onofferClicked(c.getOfferPhoto(),c.getOffer_text()); // event result object :)
//            }
//        });

    }

    @Override
    public int getItemCount() {
        Log.d("RV", "Item size [" + offerDatumList.size() + "]");
        return offerDatumList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_why_choose, parent, false);
        return new MyViewHolder(v);
    }

    public interface OnOfferClickListener {
        void onofferClicked(String offerImage, String offerText);

    }
}
