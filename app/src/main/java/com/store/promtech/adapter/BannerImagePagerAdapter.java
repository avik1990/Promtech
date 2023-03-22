package com.store.promtech.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.store.promtech.R;
import com.store.promtech.model.Banners;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



public class BannerImagePagerAdapter extends PagerAdapter {





    Context ctx;
    private final ArrayList<Banners.BannerDatum> mValue;
    private LayoutInflater layoutInflater;
   // private BannerImagePagerAdapter.ImagePagerListner imagePagerListner;

    @Override
    public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }


//    public interface ImagePagerListner{
//        void onItemClick(bannerResponse.ResponseDataBean.ImageListBean item, int position, String imageId);
//    }

    public BannerImagePagerAdapter(ArrayList<Banners.BannerDatum> mValue) {
        this.mValue = mValue;
    }
//    public void setAdapterListner(BannerImagePagerAdapter.ImagePagerListner imagePagerListner)
//    {
//        this.imagePagerListner=imagePagerListner;
//    }

    public void loadList(List<Banners.BannerDatum> items) {
        mValue.clear();
        mValue.addAll(items);


    }

    @Override
    public int getCount() {
        return mValue.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ctx = container.getContext();


        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.layout_image_banner, container, false);
     final ImageView ivBanner=itemView.findViewById(R.id.ivBanner);



       Banners.BannerDatum mDataBean = mValue.get(position);

        try {
            Picasso.with(ctx)
                    .load(mDataBean.getBannerPhoto())
                    .into(ivBanner, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            ivBanner.setImageResource(R.mipmap.ic_launcher);
                        }
                    });

        } catch (Exception e) {
        }

        container.addView(itemView);
        return itemView;
    }
}
