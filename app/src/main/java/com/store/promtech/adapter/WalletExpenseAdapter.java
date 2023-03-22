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
public class WalletExpenseAdapter extends RecyclerView.Adapter<WalletExpenseAdapter.MyViewHolder> {

    private List<Wallet.WalletDatum> countryList;
    Context mContext;
    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_purchaseDate,tv_totalAmount,tv_refundAmount;
        public MyViewHolder(View view) {
            super(view);
            tv_purchaseDate = view.findViewById(R.id.tv_purchaseDate);
            tv_totalAmount=view.findViewById(R.id.tv_totalAmount);
            tv_refundAmount=view.findViewById(R.id.tv_refundAmount);
        }
    }

    public WalletExpenseAdapter(Context mContext, List<Wallet.WalletDatum> countryList) {
        this.mContext = mContext;
        this.countryList = countryList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       final Wallet.WalletDatum c = countryList.get(position);



        holder.tv_purchaseDate.setText("Purchase Date : "+c.getPurchase_date());
        holder.tv_totalAmount.setText("Total Amount : ₹ "+c.getTotal_amount());
        holder.tv_refundAmount.setText("Refund Amount : ₹ "+c.getRefund_amount());

    }

    @Override
    public int getItemCount() {
        Log.d("RV", "Item size [" + countryList.size() + "]");
        return countryList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wallet_data, parent, false);
        return new MyViewHolder(v);
    }
}
