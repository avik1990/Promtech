package com.store.promtech.productdialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.store.promtech.R;
import com.store.promtech.model.WhyChooseText;
import com.store.promtech.productdialog.adapter.DialogProductAdapter;
import com.store.promtech.productdialog.model.ReturnProductData;
import com.store.promtech.productdialog.model.ReturnProductDatum;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Preferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReturnedProductDialog extends DialogFragment {

    String orderId;
    Context context;
    ProgressDialog pDialog;
    ReturnProductData returnedProduct;
    ConnectionDetector cd;
    RecyclerView recyclerView;
    DialogProductAdapter adapter;
    ImageView ivCancel;

    TextView tvReturnAmt;

    public ReturnedProductDialog(String orderId,Context context) {
        this.orderId = orderId;
        this.context = context;
        cd = new ConnectionDetector(context);
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View dialogLayout = inflater.inflate(R.layout.returned_product_dialog, container);
        recyclerView = dialogLayout.findViewById(R.id.recyclerView);
        ivCancel = dialogLayout.findViewById(R.id.ivCancel);
        tvReturnAmt = dialogLayout.findViewById(R.id.tvReturnAmt);
        ///return inflater.inflate(R.layout.returned_product_dialog,container,false);


        return dialogLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ivCancel.setOnClickListener(view -> dismiss());
    }

    @Override
    public void onResume() {
        super.onResume();
        if(cd.isConnected()){
            pDialog.show();
            getAllProducts(orderId);
        }
    }

    private void getAllProducts(String orderId) {

        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<ReturnProductData> call = redditAPI.UserReturnedProducts(Preferences.get_userId(context),orderId);
        call.enqueue(new Callback<ReturnProductData>() {

            @Override
            public void onResponse(Call<ReturnProductData> call, retrofit2.Response<ReturnProductData> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    returnedProduct = response.body();
                    if(returnedProduct.getAck() == 1) {
                        tvReturnAmt.setText("Returned Amount: \u20B9"+String.valueOf(returnedProduct.getReturnPriceData().getReturnAmount()));
                       if(returnedProduct.getReturnProductData()!=null) {
                           setData(returnedProduct.getReturnProductData());
                       }else{
                           Toast.makeText(context,"No Product List",Toast.LENGTH_SHORT).show();
                            dismiss();
                       }
                    }
                }
            }

            @Override
            public void onFailure(Call<ReturnProductData> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    private void setData(List<ReturnProductDatum> returnProductData) {
        if (returnProductData.size()>0) {
            adapter = new DialogProductAdapter(context, returnProductData);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

}
