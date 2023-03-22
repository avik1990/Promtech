package com.store.promtech.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.R;

import com.store.promtech.model.BaseResponse;
import com.store.promtech.model.MostSellingProduct;
import com.store.promtech.model.MyCart;
import com.store.promtech.utils.ConnectionDetector;

import java.util.List;

/**
 * Created by Avik on 11-01-2017.
 */
public class MostSellingGridAdapter extends RecyclerView.Adapter<MostSellingGridAdapter.MyViewHolder> {
    private List<MostSellingProduct.ProductDatum> moviesList;
    Context mContext;

    private int amount = 0;
    ConnectionDetector cd;
    ProgressDialog pDialog;
    BaseResponse baseResponse;
    String product_id, packet_id;
    MyCart myCart;
    String tag;

    public interface AdapterPos {
        void adapterPosition(int pos);
    }

    public interface UpdateCartCount {
        void updateCartCount();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_prize;


        public MyViewHolder(View view) {
            super(view);
            tv_prize=view.findViewById(R.id.tv_prize);

        }
    }

    public MostSellingGridAdapter(List<MostSellingProduct.ProductDatum> moviesList, Context mContext, String tag) {
        this.mContext = mContext;
        this.moviesList = moviesList;
        //this.adapterPos = adapterPos;
        this.tag=tag;
      //  this.updateCartCount = updateCartCount;
        cd = new ConnectionDetector(mContext);
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_most_selling, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MostSellingProduct.ProductDatum c = moviesList.get(position);
        holder.tv_prize.setText(c.getPrize_text());
        holder.tv_prize.setBackgroundColor(Color.parseColor(c.getBackground_color()));
        holder.tv_prize.setTextColor(Color.parseColor(c.getFont_color()));
    }


    @Override
    public int getItemCount() {

        if(moviesList.size() > 4)
            return 4;

        return moviesList.size();
    }

}

