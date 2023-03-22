package com.store.promtech;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.store.promtech.model.PushTest;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.Preferences;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PushTestActivity extends AppCompatActivity {

    EditText edt;
    Button btn;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_test);
        edt=findViewById(R.id.edt);
        btn=findViewById(R.id.btn);
        mContext=this;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("String", "" + "response");
                verifyUser(edt.getText().toString());
            }
        });
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(PushTestActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {

                Log.e("newToken", instanceIdResult.getToken());

            }
        });

    }

    private void verifyUser(String comments) {
        Log.d("String", "enter" );
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<PushTest> call = redditAPI.PushTest( Preferences.get_userId(mContext),comments);
        call.enqueue(new Callback<PushTest>() {

            @Override
            public void onResponse(Call<PushTest> call, retrofit2.Response<PushTest> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {

                    Log.d("String", "" + response.message());
                    } else {

                    }


            }

            @Override
            public void onFailure(Call<PushTest> call, Throwable t) {

            }
        });
    }
}
