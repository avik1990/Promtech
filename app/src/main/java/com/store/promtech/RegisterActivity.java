package com.store.promtech;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.store.promtech.model.AboutUsmodel;
import com.store.promtech.model.LoginResponse;
import com.store.promtech.model.RegistrationResponse;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Preferences;
import com.store.promtech.utils.Utility;


import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;

    Button btn_register;
    ConnectionDetector cd;
    ProgressDialog pDialog;
    RegistrationResponse registration;
    LoginResponse loginResponse;
    AboutUsmodel zipCodeVerify;
    EditText et_firstname;
    EditText et_phoneno,et_password,et_Cpassword,et_refCode,et_email;


    String user_firstname;
    String user_lastname;
    String user_phoneno;
    String user_email;
    String user_password;
    String user_confirm_password;
    String user_address;
    String user_state;
    String user_city;
    String user_zipcode;
    String user_ref;
    String user_dob;
    String user_anniversary;
    String user_landmark;
    private int mYear, mMonth, mDay, mHour, mMinute;
    EditText et_address;
    EditText et_state;
    EditText et_city;
    EditText et_zip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Please Wait...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);

        initViews();
        VerifyZipCodeparsejson();

    }

    private void initViews() {
        et_firstname = findViewById(R.id.et_firstname);

        et_phoneno = findViewById(R.id.et_phoneno);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_Cpassword = findViewById(R.id.et_Cpassword);
        et_address = findViewById(R.id.et_address);
        et_state = findViewById(R.id.et_state);
        et_city = findViewById(R.id.et_city);
        et_zip = findViewById(R.id.et_zip);
        et_refCode = findViewById(R.id.et_refCode);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

//        et_zipcode.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.toString().length() == 6) {
//                    VerifyZipCodeparsejson(s.toString());
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


    }

    private void showDate(final  EditText editText)
    {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        editText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    @Override
    public void onClick(View v) {
        if (v == btn_register) {


            if (et_firstname.getText().toString().isEmpty()) {
                Utility.showToastShort(mContext, "Please Enter Name");
                return;
            }



            if (et_phoneno.getText().toString().isEmpty()) {
                Utility.showToastShort(mContext, "Please Enter Phone No.");
                return;
            }

            if (et_password.getText().toString().isEmpty()) {
                Utility.showToastShort(mContext, "Please Set Password");
                return;
            }
            user_confirm_password =et_Cpassword.getText().toString();

            if (!et_password.getText().toString().equals(user_confirm_password)) {
                Utility.showToastShort(mContext, "Please Confirm Password");
                return;
            }
            if (et_address.getText().toString().isEmpty()) {
                Utility.showToastShort(mContext, "Please Enter Address");
                return;
            }
            if (et_state.getText().toString().isEmpty()) {
                Utility.showToastShort(mContext, "Please Enter State");
                return;
            }
            if (et_city.getText().toString().isEmpty()) {
                Utility.showToastShort(mContext, "Please Enter City");
                return;
            }
            if (et_zip.getText().toString().isEmpty()) {
                Utility.showToastShort(mContext, "Please Enter Pin code");
                return;
            }




            user_firstname = et_firstname.getText().toString().trim();

            user_phoneno = et_phoneno.getText().toString().trim();
            user_email = et_email.getText().toString().trim();
            user_address = et_address.getText().toString().trim();
            user_state = et_state.getText().toString().trim();
            user_city = et_city.getText().toString().trim();
            user_zipcode = et_zip.getText().toString().trim();
            user_ref = et_refCode.getText().toString().trim();

         //   user_dob=et_dob.getText().toString().trim();
         //   user_anniversary=et_anniversary.getText().toString().trim();

            verifyUser();
        }
    }

    private void verifyUser() {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<RegistrationResponse> call = redditAPI.UserRegistration(user_firstname, user_phoneno,user_email, user_confirm_password,user_address,user_state,user_city,user_zipcode,user_ref);
        call.enqueue(new Callback<RegistrationResponse>() {

            @Override
            public void onResponse(Call<RegistrationResponse> call, retrofit2.Response<RegistrationResponse> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    registration = response.body();
                    pDialog.dismiss();
                    if (registration.getAck() == 1) {
                        Utility.showToastShort(mContext, registration.getMsg());
                        Preferences.set_userId(mContext, registration.getUserId());
                        Intent intent = new Intent(mContext, OTPActivity.class);
                        intent.putExtra("user_phoneno", user_phoneno);
                        startActivity(intent);
                        finish();
                    } else {
                        Utility.showToastShort(mContext, registration.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }





    private void VerifyZipCodeparsejson() {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<AboutUsmodel> call = redditAPI.beforeRegistration();
        call.enqueue(new Callback<AboutUsmodel>() {

            @Override
            public void onResponse(Call<AboutUsmodel> call, retrofit2.Response<AboutUsmodel> response) {
                Log.i("Registration", "" + response);
                pDialog.dismiss();
                if (response.isSuccessful()) {
                    zipCodeVerify = response.body();
                    if (response.body().getAboutData().getMsg().equals("Success")) {
                        //btn_register.setEnabled(true);
                        //Utility.showToastShort(mContext, zipCodeVerify.getMsg());
                        showDialog(RegisterActivity.this,zipCodeVerify.getAboutData().getContent().toString(),zipCodeVerify.getAboutData().getContentTitle());
                        //Toast.makeText(getApplicationContext(), zipCodeVerify.getAboutData().getContent(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<AboutUsmodel> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    public void showDialog(Context activity, String message,String heading) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_reg_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        final TextView et_msg=dialog.findViewById(R.id.et_msg);
        final TextView et_heading=dialog.findViewById(R.id.et_heading);
        final ImageView iv_cancel=dialog.findViewById(R.id.iv_cancel);
//        Picasso.with(mContext)
//                .load(image)
//                .into(ivthanku, new com.squareup.picasso.Callback() {
//                    @Override
//                    public void onSuccess() {
//                        //  Toast.makeText(getApplication(),"succ",Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError() {
//                        ivthanku.setImageResource(R.mipmap.logoo);
//                        // Toast.makeText(getApplication(),"fail",Toast.LENGTH_SHORT).show();
//                    }
//                });

        et_msg.setText(message);
        et_heading.setText(heading);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }


}
