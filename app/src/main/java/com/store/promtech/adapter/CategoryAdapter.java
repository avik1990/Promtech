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

import com.store.promtech.ProductPage;
import com.store.promtech.R;
import com.store.promtech.SubCategoryPage;

import com.store.promtech.model.Category;
import com.store.promtech.utils.Preferences;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Avik on 11-01-2017.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private List<Category.CategoryDatum> countryList;
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

    public CategoryAdapter(Context mContext, List<Category.CategoryDatum> countryList) {
        this.mContext = mContext;
        this.countryList = countryList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       final Category.CategoryDatum c = countryList.get(position);
        holder.progressbar.setVisibility(View.GONE);
        holder.iv_catimage.setVisibility(View.VISIBLE);


        Glide.with(mContext)
                .load(c.getCategoryPhoto())
               // .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                .into(holder.iv_catimage);

        holder.tv_categoryname.setText(c.getCategoryName());

        holder.card_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Preferences.set_dashCatId(mContext, countryList.get(position).getCategoryId());
                if(!c.noofSubcaterory.equals("0")) {
                    Intent i = new Intent(mContext, SubCategoryPage.class);
                    i.putExtra("from", holder.tv_categoryname.getText().toString().trim());
                    i.putExtra("cat_id", countryList.get(position).getCategoryId());
                    mContext.startActivity(i);
                }
                else  {
                    Intent a = new Intent(mContext, ProductPage.class);
                    a.putExtra("position", "" + position);
                    a.putExtra("category_id", countryList.get(position).getCategoryId());
                    a.putExtra("from", holder.tv_categoryname.getText().toString().trim());
                    a.putExtra("sub_category_id","0");
                    a.putExtra("brand_id","0");
                    mContext.startActivity(a);
                }
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category, parent, false);
        return new MyViewHolder(v);
    }
}
