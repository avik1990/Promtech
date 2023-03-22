package com.store.promtech.adapter;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.R;

import java.util.List;

/**
 * Created by Avik on 11-01-2017.
 */
public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.MyViewHolder> {

    private List<String> countryList;
    Context mContext;
    SlotAdapter.SelectSlotListner solt;


    public interface SelectSlotListner {
        void SelectSlot(String slot);
    }

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvSlot;
        LinearLayout ll_main;
        public MyViewHolder(View view) {
            super(view);
            tvSlot = view.findViewById(R.id.tvSlot);
            ll_main=view.findViewById(R.id.ll_main);
        }
    }

    public SlotAdapter(Context mContext, List<String> countryList, SlotAdapter.SelectSlotListner solt) {
        this.mContext = mContext;
        this.countryList = countryList;
        this.solt=solt;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       final String c = countryList.get(position);
        holder.tvSlot.setText(c);

        holder.ll_main.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                solt.SelectSlot(c);
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_slot, parent, false);
        return new MyViewHolder(v);
    }
}
