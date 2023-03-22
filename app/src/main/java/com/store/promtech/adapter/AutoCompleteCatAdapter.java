package com.store.promtech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.store.promtech.R;
import com.store.promtech.model.ProductListAcfold;

import java.util.List;

public class AutoCompleteCatAdapter extends ArrayAdapter<ProductListAcfold.ProductDatum> implements Filterable {
    private final List<ProductListAcfold.ProductDatum> allCatList;
    private final Context mContext;


    public AutoCompleteCatAdapter(@NonNull Context context, @NonNull List<ProductListAcfold.ProductDatum> list) {
        super(context, 0, list);
        mContext = context;
        allCatList = list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_cat_search, parent, false
            );
        }

        TextView cat_name = convertView.findViewById(R.id.cat_name);

        ProductListAcfold.ProductDatum item = getItem(position);
        if (item != null) {
            cat_name.setText(item.getProductNameEnglish());
        }

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private final Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            results.values = allCatList;
            results.count = allCatList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((ProductListAcfold.ProductDatum) resultValue).getProductNameEnglish();
        }
    };
}
