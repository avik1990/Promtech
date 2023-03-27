package com.store.promtech.productdialog.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.store.promtech.R;
import com.store.promtech.model.MyOrders;
import com.store.promtech.model.SubCategoryDataResponse;
import com.store.promtech.productdialog.model.ReturnProductDatum;

import java.util.List;

/**
 * Created by Avik on 11-01-2017.
 */
public class DialogProductAdapter extends RecyclerView.Adapter<DialogProductAdapter.MyViewHolder> {

    List<ReturnProductDatum> countryList;
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
        TextView tv_productname, tv_position,tv_returnquantity,tv_unitprice,tv_subtotal;
        ProgressBar progressbar;
        CardView card_view;


        public MyViewHolder(View view) {
            super(view);
            iv_chefimage = (ImageView) view.findViewById(R.id.iv_chefimage);
            tv_productname = (TextView) view.findViewById(R.id.tv_productname);
            progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
            card_view = (CardView) view.findViewById(R.id.card_view);
            tv_position = view.findViewById(R.id.tv_position);

            tv_returnquantity= view.findViewById(R.id.tv_returnquantity);
            tv_unitprice= view.findViewById(R.id.tv_unitprice);
            tv_subtotal= view.findViewById(R.id.tv_subtotal);
        }
    }

    public DialogProductAdapter(Context mContext, List<ReturnProductDatum> countryList) {
        this.mContext = mContext;
        this.countryList = countryList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ReturnProductDatum c = countryList.get(position);

        holder.tv_productname.setText(c.getProductNameEnglish());

        holder.tv_returnquantity.setText("Returned Quantity: "+c.getReturnQuantity());
        holder.tv_unitprice.setText("Unit Price: \u20B9"+c.getUnitPrice());
        holder.tv_subtotal.setText("Subtotal: \u20B9"+c.getSubtotal());

        holder.tv_position.setText(String.valueOf(position));
        holder.iv_chefimage.setVisibility(View.VISIBLE);
        final ProgressBar progressView = holder.progressbar;

        progressView.setVisibility(View.VISIBLE);

        try {
            Picasso.with(mContext)
                    .load(c.getProductPhoto())
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

        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_returned_products, parent, false);
        return new MyViewHolder(v);
    }
}
