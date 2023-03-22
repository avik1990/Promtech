package com.store.promtech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.R;
import com.store.promtech.model.Referral;

import java.util.List;

/**
 * Created by Avik on 11-01-2017.
 */
public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.MyViewHolder> {

    private List<Referral.RefDatum> countryList;
    Context mContext;

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_wallet_name,tv_wallet_amount;
        ImageView ivImage;
        public MyViewHolder(View view) {
            super(view);

            tv_wallet_name = view.findViewById(R.id.tv_wallet_name);
            tv_wallet_amount=view.findViewById(R.id.tv_wallet_amount);
            ivImage=view.findViewById(R.id.ivImage);
        }
    }

    public WalletAdapter(Context mContext, List<Referral.RefDatum> countryList) {
        this.mContext = mContext;
        this.countryList = countryList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       final Referral.RefDatum c = countryList.get(position);

        holder.tv_wallet_amount.setText(c.getEarn());
        holder.tv_wallet_name.setText(c.getUser_name());


    }

    @Override
    public int getItemCount() {
       // Log.d("RV", "Item size [" + countryList.size() + "]");
        return countryList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_referral, parent, false);
        return new MyViewHolder(v);
    }
}
