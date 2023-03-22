package com.store.promtech.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.R;
import com.store.promtech.model.Wallet;

import java.util.List;

/**
 * Created by Avik on 11-01-2017.
 */
public class WalletEarnAdapter extends RecyclerView.Adapter<WalletEarnAdapter.MyViewHolder> {

    private List<Wallet.WalletDatum> countryList;
    Context mContext;
    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_wallet_name,tv_wallet_amount;
        public MyViewHolder(View view) {
            super(view);
            tv_wallet_name = view.findViewById(R.id.tv_wallet_name);
            tv_wallet_amount=view.findViewById(R.id.tv_wallet_amount);
        }
    }

    public WalletEarnAdapter(Context mContext, List<Wallet.WalletDatum> countryList) {
        this.mContext = mContext;
        this.countryList = countryList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       final Wallet.WalletDatum c = countryList.get(position);


        holder.tv_wallet_amount.setText(c.getRefund_amount());
        holder.tv_wallet_name.setText(c.getTotal_amount());

    }

    @Override
    public int getItemCount() {
        Log.d("RV", "Item size [" + countryList.size() + "]");
        return countryList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wallet, parent, false);
        return new MyViewHolder(v);
    }
}
