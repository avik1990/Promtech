package com.store.promtech;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.store.promtech.model.CartDeleteAction;
import com.store.promtech.model.Checkout;
import com.store.promtech.model.CheckoutText;
import com.store.promtech.model.MyProfile;
import com.store.promtech.model.ZipCodeVerify;
import com.store.promtech.model.ZipCodemodel;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Preferences;
import com.store.promtech.utils.Utility;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Shippingactivity extends AppCompatActivity implements View.OnClickListener {
    String OrderId = "";
    String amount = "";
    ImageView btn_back;
    Button btn_fetch;
    ImageView btn_menu;
    Button btn_placeorder;
    String businessname;
    FrameLayout cartvie;

    /* renamed from: cd */
    ConnectionDetector f96cd;
    Checkout checkout;
    String custid = "";
    String deliveryType;
    String delveryMsz = "";
    EditText et_address;
    EditText et_business;
    EditText et_city;
    EditText et_email;
    EditText et_gstNo;
    EditText et_landmark;
    EditText et_name;
    EditText et_phoneno;
    EditText et_pincode;
    EditText et_state;
    CheckoutText getCheckoutText;
    MyProfile getMyProfile;
    String gstNo;
    ImageView iv_edit_address;
    ImageView iv_edit_city;
    ImageView iv_edit_landmark;
    ImageView iv_edit_pincode;
    ImageView iv_edit_state;
    ImageView iv_pincode;
    List<String> list_text = new ArrayList();
    Context mContext;
    String mid = "";
    Dialog myDialog;
    ProgressDialog pDialog;
    String payment_mode = "COD";
    String quick_delivery;
    CartDeleteAction registration;
    String slotDate;
    String slotTime;
    String total_amount;
    String transc_status = "";
    TextView tv_checkout;
    TextView tv_pagename;
    String user_address;
    String user_city;
    String user_email;
    String user_landmark;
    String user_name;
    String user_phone;
    String user_pincode;
    String user_state;
    ZipCodeVerify zipCodeVerify;
    ZipCodemodel zipCodemodel;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_shipping);
        this.mContext = this;
        this.f96cd = new ConnectionDetector(this.mContext);
        this.pDialog = new ProgressDialog(this.mContext);
        this.pDialog.setMessage("Please Wait...");
        this.pDialog.setCanceledOnTouchOutside(false);
        this.pDialog.setCancelable(false);
        this.quick_delivery = getIntent().getExtras().getString("quick_delivery");
        this.slotDate = getIntent().getExtras().getString("solt_date");
        this.slotTime = getIntent().getExtras().getString("slot_time");
        this.total_amount = getIntent().getExtras().getString("total_amount");
        this.deliveryType = getIntent().getExtras().getString("deliveryType");
        initViews();
        if (this.f96cd.isConnected()) {
            getUserDetails();
        } else {
            Utility.showToastShort(this.mContext, getString(R.string.no_internet_msg));
        }
    }

    private void initViews() {
        ((ImageView) findViewById(R.id.iv_home)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Shippingactivity.this.startActivity(new Intent(Shippingactivity.this.mContext, Dashboard.class));
                Shippingactivity.this.finishAffinity();
            }
        });
        this.btn_fetch = (Button) findViewById(R.id.btn_fetch);
        this.iv_pincode = (ImageView) findViewById(R.id.iv_pincode);
        this.iv_edit_pincode = (ImageView) findViewById(R.id.iv_edit_pincode);
        this.iv_edit_state = (ImageView) findViewById(R.id.iv_edit_state);
        this.iv_edit_city = (ImageView) findViewById(R.id.iv_edit_city);
        this.iv_edit_address = (ImageView) findViewById(R.id.iv_edit_address);
        this.iv_edit_landmark = (ImageView) findViewById(R.id.iv_edit_landmark);
        this.cartvie = (FrameLayout) findViewById(R.id.cartvie);
        this.cartvie.setVisibility(View.VISIBLE);
        this.tv_pagename = (TextView) findViewById(R.id.tv_pagename);
        this.et_name = (EditText) findViewById(R.id.et_name);
        this.et_email = (EditText) findViewById(R.id.et_email);
        this.et_phoneno = (EditText) findViewById(R.id.et_phoneno);
        this.et_landmark = (EditText) findViewById(R.id.et_landmark);
        this.btn_placeorder = (Button) findViewById(R.id.btn_placeorder);
        this.tv_pagename.setText("Shipping Details");
        this.btn_placeorder.setOnClickListener(this);
        this.tv_checkout = (TextView) findViewById(R.id.tv_checkout);
        this.btn_menu = (ImageView) findViewById(R.id.btn_menu);
        this.btn_menu.setVisibility(View.VISIBLE);
        this.btn_back = (ImageView) findViewById(R.id.btn_back);
        this.btn_back.setVisibility(View.VISIBLE);
        this.et_address = (EditText) findViewById(R.id.et_address);
        this.et_city = (EditText) findViewById(R.id.et_city);
        this.et_state = (EditText) findViewById(R.id.et_state);
        this.et_pincode = (EditText) findViewById(R.id.et_pincode);
        this.et_business = (EditText) findViewById(R.id.et_business);
        this.et_gstNo = (EditText) findViewById(R.id.et_gstNo);
        this.btn_menu.setOnClickListener(this);
        this.btn_back.setOnClickListener(this);
        this.btn_fetch.setOnClickListener(this);
        ((ImageView) findViewById(R.id.iv_phone)).setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"MissingPermission"})
            public void onClick(View v) {
                Utility.CallContactNo(Shippingactivity.this.mContext);
            }
        });
        this.iv_pincode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Shippingactivity.this.fetchZipCode();
            }
        });
        this.et_pincode.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 6) {
                    Utility.hideKeyboard(Shippingactivity.this);
                }
            }

            public void afterTextChanged(Editable s) {
            }
        });
        this.iv_edit_address.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Shippingactivity.this.et_address.setText("");
                Shippingactivity.this.et_address.setFocusable(true);
            }
        });
        this.iv_edit_landmark.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Shippingactivity.this.et_landmark.setText("");
                Shippingactivity.this.et_landmark.setFocusable(true);
            }
        });
        this.iv_edit_city.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Shippingactivity.this.et_city.setText("");
                Shippingactivity.this.et_city.setFocusable(true);
            }
        });
        this.iv_edit_pincode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Shippingactivity.this.et_pincode.setText("");
                Shippingactivity.this.et_pincode.setFocusable(true);
            }
        });
        this.iv_edit_state.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Shippingactivity.this.et_state.setText("");
                Shippingactivity.this.et_state.setFocusable(true);
            }
        });
    }

    private void getUserDetails() {
        this.pDialog.show();
        ((ApiServices) new Retrofit.Builder().baseUrl(getResources().getString(R.string.base_url)).addConverterFactory(GsonConverterFactory.create()).build().create(ApiServices.class)).GetMYProfile(Preferences.get_userId(this.mContext)).enqueue(new Callback<MyProfile>() {
            public void onResponse(Call<MyProfile> call, Response<MyProfile> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    Shippingactivity.this.getMyProfile = response.body();
                    if (Shippingactivity.this.getMyProfile.getAck().intValue() == 1) {
                        Shippingactivity.this.et_name.setText(Shippingactivity.this.getMyProfile.getUserData().get(0).getName());
                        Shippingactivity.this.et_email.setText(Shippingactivity.this.getMyProfile.getUserData().get(0).getEmail());
                        Shippingactivity.this.et_phoneno.setText(Shippingactivity.this.getMyProfile.getUserData().get(0).getPhone());
                        Shippingactivity.this.et_address.setText(Shippingactivity.this.getMyProfile.getUserData().get(0).getAddress());
                        Shippingactivity.this.et_city.setText(Shippingactivity.this.getMyProfile.getUserData().get(0).getCity());
                        Shippingactivity.this.et_state.setText(Shippingactivity.this.getMyProfile.getUserData().get(0).getState());
                        Shippingactivity.this.et_pincode.setText(Shippingactivity.this.getMyProfile.getUserData().get(0).getZip());
                        Shippingactivity.this.et_business.setText(Shippingactivity.this.getMyProfile.getUserData().get(0).getBusiness_name());
                        Shippingactivity.this.et_gstNo.setText(Shippingactivity.this.getMyProfile.getUserData().get(0).getGst_no());
                    } else {
                        Utility.showToastShort(Shippingactivity.this.mContext, Shippingactivity.this.registration.getMsg());
                    }
                    Shippingactivity.this.pDialog.dismiss();
                }
            }

            public void onFailure(Call<MyProfile> call, Throwable t) {
                Shippingactivity.this.pDialog.dismiss();
            }
        });
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<>();
        Iterator<String> it = wanted.iterator();
        while (it.hasNext()) {
            String perm = it.next();
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    public boolean hasPermission(String permission) {
        if (!canMakeSmores() || Build.VERSION.SDK_INT < 23 || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private boolean canMakeSmores() {
        return Build.VERSION.SDK_INT > 22;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getApplicationContext()).setMessage(message).setPositiveButton("OK", okListener).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).create().show();
    }

    public void onClick(View v) {
        if (v == this.btn_placeorder) {
            if (this.et_name.getText().toString().isEmpty()) {
                Utility.showToastShort(this.mContext, "Please Enter First Name");
            } else if (this.et_email.getText().toString().isEmpty()) {
                Utility.showToastShort(this.mContext, "Please Enter Email");
            } else if (!Utility.isValidEmail(this.et_email.getText().toString())) {
                Utility.showToastShort(this.mContext, "Please Enter Valid Email");
            } else if (this.et_phoneno.getText().toString().isEmpty()) {
                Utility.showToastShort(this.mContext, "Please Enter Phone No.");
            } else if (this.et_address.getText().toString().isEmpty()) {
                Utility.showToastShort(this.mContext, "Please Enter Address");
            } else if (this.et_city.getText().toString().isEmpty()) {
                Utility.showToastShort(this.mContext, "Please Enter City");
            } else if (this.et_state.getText().toString().isEmpty()) {
                Utility.showToastShort(this.mContext, "Please Enter State");
            } else if (this.et_pincode.getText().toString().isEmpty()) {
                Utility.showToastShort(this.mContext, "Please Enter Pin Code");
            } else {
                this.user_address = this.et_address.getText().toString().trim();
                this.user_city = this.et_city.getText().toString().trim();
                this.user_state = this.et_state.getText().toString().trim();
                this.user_pincode = this.et_pincode.getText().toString().trim();
                this.user_landmark = this.et_landmark.getText().toString().trim();
                this.user_name = this.et_name.getText().toString().trim();
                this.user_email = this.et_email.getText().toString().trim();
                this.user_phone = this.et_phoneno.getText().toString().trim();
                this.businessname = this.et_business.getText().toString().trim();
                this.gstNo = this.et_gstNo.getText().toString().trim();
                postShippingDetails();
            }
        } else if (v == this.btn_back) {
            onBackPressed();
            finish();
        } else if (v == this.btn_fetch) {
            this.et_address.setText(Preferences.get_userGeoAddress(this.mContext));
            this.et_state.setText(Preferences.get_userGeoState(this.mContext));
            this.et_city.setText(Preferences.get_userGeoCity(this.mContext));
            this.et_pincode.setText(Preferences.get_userGeoPostCode(this.mContext));
        }
    }

    private void parsejson() {
        this.pDialog.show();
        ((ApiServices) new Retrofit.Builder().baseUrl(getResources().getString(R.string.base_url)).addConverterFactory(GsonConverterFactory.create()).build().create(ApiServices.class)).GetCheckoutText().enqueue(new Callback<CheckoutText>() {
            public void onResponse(Call<CheckoutText> call, Response<CheckoutText> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    Shippingactivity.this.pDialog.dismiss();
                    Shippingactivity.this.getCheckoutText = response.body();
                    Shippingactivity.this.tv_checkout.setText(Shippingactivity.this.getCheckoutText.getMsg());
                    Shippingactivity shippingactivity = Shippingactivity.this;
                    shippingactivity.delveryMsz = shippingactivity.getCheckoutText.getMsg();
                    Shippingactivity.this.btn_placeorder.setEnabled(true);
                    Shippingactivity.this.btn_placeorder.setClickable(true);
                    Shippingactivity.this.btn_placeorder.setBackground(Shippingactivity.this.getResources().getDrawable(R.drawable.rounded_corner_btn));
                }
            }

            public void onFailure(Call<CheckoutText> call, Throwable t) {
                Shippingactivity.this.pDialog.dismiss();
            }
        });
    }

    private void VerifyZipCodeparsejson(String zipcode) {
        this.pDialog.show();
        ((ApiServices) new Retrofit.Builder().baseUrl(getResources().getString(R.string.base_url)).addConverterFactory(GsonConverterFactory.create()).build().create(ApiServices.class)).VerifyZipCode(zipcode).enqueue(new Callback<ZipCodeVerify>() {
            public void onResponse(Call<ZipCodeVerify> call, Response<ZipCodeVerify> response) {
                Log.d("String", "" + response.message());
                if (response.isSuccessful()) {
                    Shippingactivity.this.pDialog.dismiss();
                    Shippingactivity.this.zipCodeVerify = response.body();
                    if (Shippingactivity.this.zipCodeVerify.getAck().equals("1")) {
                        Shippingactivity.this.btn_placeorder.setEnabled(true);
                        Shippingactivity.this.btn_placeorder.setClickable(true);
                        Shippingactivity.this.btn_placeorder.setBackground(Shippingactivity.this.getResources().getDrawable(R.drawable.rounded_corner_btn));
                        Shippingactivity.this.tv_checkout.setText("");
                        return;
                    }
                    Shippingactivity.this.btn_placeorder.setEnabled(false);
                    Shippingactivity.this.btn_placeorder.setClickable(false);
                    Shippingactivity.this.tv_checkout.setText(Shippingactivity.this.zipCodeVerify.getMsg());
                    Shippingactivity.this.btn_placeorder.setBackground(Shippingactivity.this.getResources().getDrawable(R.drawable.rounded_fade_blue_corner_btn));
                    Utility.showToastShort(Shippingactivity.this.mContext, Shippingactivity.this.zipCodeVerify.getMsg());
                }
            }

            public void onFailure(Call<ZipCodeVerify> call, Throwable t) {
                Shippingactivity.this.pDialog.dismiss();
            }
        });
    }

    private void VerifyZipCodeparsejsonSubmit(String zipcode) {
        this.pDialog.show();
        ((ApiServices) new Retrofit.Builder().baseUrl(getResources().getString(R.string.base_url)).addConverterFactory(GsonConverterFactory.create()).build().create(ApiServices.class)).VerifyZipCode(zipcode).enqueue(new Callback<ZipCodeVerify>() {
            public void onResponse(Call<ZipCodeVerify> call, Response<ZipCodeVerify> response) {
                Log.d("String", "" + response.message());
                if (response.isSuccessful()) {
                    Shippingactivity.this.pDialog.dismiss();
                    Shippingactivity.this.zipCodeVerify = response.body();
                    if (Shippingactivity.this.zipCodeVerify.getAck().equals("1")) {
                        Shippingactivity.this.btn_placeorder.setEnabled(true);
                        Shippingactivity.this.btn_placeorder.setClickable(true);
                        Shippingactivity.this.btn_placeorder.setBackground(Shippingactivity.this.getResources().getDrawable(R.drawable.rounded_corner_btn));
                        Shippingactivity.this.tv_checkout.setText("");
                        Shippingactivity.this.postShippingDetails();
                        return;
                    }
                    Shippingactivity.this.btn_placeorder.setEnabled(false);
                    Shippingactivity.this.btn_placeorder.setClickable(false);
                    Shippingactivity.this.tv_checkout.setText(Shippingactivity.this.zipCodeVerify.getMsg());
                    Shippingactivity.this.btn_placeorder.setBackground(Shippingactivity.this.getResources().getDrawable(R.drawable.rounded_fade_blue_corner_btn));
                    Utility.showToastShort(Shippingactivity.this.mContext, Shippingactivity.this.zipCodeVerify.getMsg());
                }
            }

            public void onFailure(Call<ZipCodeVerify> call, Throwable t) {
                Shippingactivity.this.pDialog.dismiss();
            }
        });
    }

    /* access modifiers changed from: private */
    public void postShippingDetails() {
        this.pDialog.show();
        Call<Checkout> call = ((ApiServices) new Retrofit.Builder().baseUrl(getResources().getString(R.string.base_url)).addConverterFactory(GsonConverterFactory.create()).build().create(ApiServices.class)).PostShipping(this.user_name, this.user_email, this.user_phone, this.user_address, this.user_city, this.user_state, this.user_pincode, Preferences.get_userId(this.mContext), Preferences.get_UniqueId(this.mContext), this.businessname, this.gstNo);
        Log.d("String", "" + this.quick_delivery);
        call.enqueue(new Callback<Checkout>() {
            public void onResponse(Call<Checkout> call, Response<Checkout> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    Shippingactivity.this.pDialog.dismiss();
                    Shippingactivity.this.checkout = response.body();
                    if (Shippingactivity.this.checkout.getAck().intValue() == 1) {
                        Preferences.set_Cartount(Shippingactivity.this.mContext, "0");
                        Shippingactivity.this.getMessage("COD", "Success", "");
                        return;
                    }
                    Context context = Shippingactivity.this.mContext;
                    Utility.showToastShort(context, Shippingactivity.this.checkout.getMsg() + Shippingactivity.this.checkout.getAck());
                }
            }

            public void onFailure(Call<Checkout> call, Throwable t) {
                Shippingactivity.this.pDialog.dismiss();
            }
        });
    }

    /* access modifiers changed from: private */
    public void getMessage(String payment_mode2, String status, String transaction_id) {
        this.pDialog.show();
        String format = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        String BASE_URL = getResources().getString(R.string.base_url);
        ((ApiServices) new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(ApiServices.class)).GetCartThankyouMessage(Preferences.get_userId(this.mContext), Preferences.get_UniqueId(this.mContext), payment_mode2, status, transaction_id).enqueue(new Callback<CartDeleteAction>() {
            public void onResponse(Call<CartDeleteAction> call, Response<CartDeleteAction> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    Shippingactivity.this.registration = response.body();
                    if (Shippingactivity.this.registration.getAck().intValue() == 1) {
                        Shippingactivity shippingactivity = Shippingactivity.this;
                        shippingactivity.showDialog(shippingactivity.mContext, Shippingactivity.this.registration.getMsg(), Shippingactivity.this.registration.getMsg2(), Shippingactivity.this.registration.getMsg3());
                    } else {
                        Utility.showToastShort(Shippingactivity.this.mContext, Shippingactivity.this.registration.getMsg());
                    }
                }
                Shippingactivity.this.pDialog.dismiss();
            }

            public void onFailure(Call<CartDeleteAction> call, Throwable t) {
                Shippingactivity.this.pDialog.dismiss();
            }
        });
    }

    public void showDialog(Context activity, String message, String msg2, String msg3) {
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_thankyou_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView textView = (TextView) dialog.findViewById(R.id.et_pincode);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.ivthanku);
        ((TextView) dialog.findViewById(R.id.et_msg)).setText(message);
        ((FrameLayout) dialog.findViewById(R.id.frmOk)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Preferences.setUniqueKey(Shippingactivity.this.mContext, false);
                Shippingactivity.this.startActivity(new Intent(Shippingactivity.this.mContext, Dashboard.class));
                Shippingactivity.this.finishAffinity();
            }
        });
        dialog.show();
    }

    /* access modifiers changed from: private */
    public void fetchZipCode() {
        this.pDialog.show();
        ((ApiServices) new Retrofit.Builder().baseUrl(getResources().getString(R.string.base_url)).addConverterFactory(GsonConverterFactory.create()).build().create(ApiServices.class)).GetZipCodeList().enqueue(new Callback<ZipCodemodel>() {
            public void onResponse(Call<ZipCodemodel> call, Response<ZipCodemodel> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    Shippingactivity.this.zipCodemodel = response.body();
                    if (Shippingactivity.this.zipCodemodel.getAck().intValue() != 1) {
                        Utility.showToastShort(Shippingactivity.this.mContext, Shippingactivity.this.zipCodemodel.getMsg());
                    } else if (Shippingactivity.this.zipCodemodel.getZipData().size() > 0) {
                        Shippingactivity.this.list_text.clear();
                        for (int i = 0; i < Shippingactivity.this.zipCodemodel.getZipData().size(); i++) {
                            Shippingactivity.this.list_text.add(Shippingactivity.this.zipCodemodel.getZipData().get(i).getAvailableZipcode());
                        }
                        Shippingactivity.this.showCustomDialog();
                    }
                }
                Shippingactivity.this.pDialog.dismiss();
            }

            public void onFailure(Call<ZipCodemodel> call, Throwable t) {
                Shippingactivity.this.pDialog.dismiss();
            }
        });
    }

    /* access modifiers changed from: private */
    public void showCustomDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialog, viewGroup, false);
        FlexboxLayout container = dialogView.findViewById(R.id.v_container);
        Button btn_proceed = (Button) dialogView.findViewById(R.id.btn_proceed);
        btn_proceed.setVisibility(View.VISIBLE);
        inflatelayout((FlexboxLayout) dialogView.findViewById(R.id.v_container));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        ((Button) dialogView.findViewById(R.id.btn_cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertDialog.dismiss();
                Shippingactivity.this.startActivity(new Intent(Shippingactivity.this.mContext, RegisterActivity.class));
            }
        });
    }

    private void inflatelayout(FlexboxLayout container) {
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(-2, -2);
        buttonLayoutParams.setMargins(5, 5, 5, 5);
        for (int i = 0; i < this.list_text.size(); i++) {
            TextView tv = new TextView(getApplicationContext());
            tv.setText(this.list_text.get(i));
            tv.setHeight(70);
            tv.setTextSize(16.0f);
            tv.setGravity(17);
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setBackground(getResources().getDrawable(R.drawable.rounded_corner_flex));
            tv.setId(i + 1);
            tv.setLayoutParams(buttonLayoutParams);
            tv.setTag(Integer.valueOf(i));
            tv.setPadding(20, 10, 20, 10);
            container.addView(tv);
        }
    }

    /*public void startPayment() {
        com.razorpay.Checkout checkout2 = new com.razorpay.Checkout();
        checkout2.setKeyID("rzp_test_IWoMASes0xcu0E");
        checkout2.setImage(R.drawable.acfoldlogo);
        try {
            JSONObject options = new JSONObject();
            options.put("name", "PromtechTest");
            options.put("description", "Reference");
            options.put("image", "");
            options.put("theme.color", R.color.colorAccent);
            options.put(FirebaseAnalytics.Param.CURRENCY, "INR");
            options.put("amount", this.amount + "00");
            options.put("prefill.email", this.getMyProfile.getUserData().get(0).getEmail());
            options.put("prefill.contact", Preferences.get_userPhone(this.mContext));
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);
            checkout2.open(this, options);
        } catch (Exception e) {
            Log.e("ContentValues", "Error in starting Razorpay Checkout", e);
        }
    }*/

    public void onPaymentSuccess(String s) {
        getMessage(this.payment_mode, "Success", s);
    }

    public void onPaymentError(int i, String s) {
        getMessage(this.payment_mode, "Error", s);
    }

    private void showDialogPaymentMethod() {
        this.myDialog = new Dialog(this);
        this.myDialog.setContentView(R.layout.dialog_payment_method);
        final RadioGroup radioGroup = (RadioGroup) this.myDialog.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
            }
        });
        ((Button) this.myDialog.findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(Shippingactivity.this, "No answer has been selected", Toast.LENGTH_LONG).show();
                    return;
                }
                Shippingactivity.this.myDialog.dismiss();
                Shippingactivity.this.payment_mode = ((RadioButton) radioGroup.findViewById(selectedId)).getText().toString();
                if (Shippingactivity.this.payment_mode.equals("COD")) {
                    Shippingactivity shippingactivity = Shippingactivity.this;
                    shippingactivity.transc_status = "Success";
                    shippingactivity.getMessage(shippingactivity.payment_mode, Shippingactivity.this.transc_status, "");
                } else if (Shippingactivity.this.payment_mode.equals("Online")) {
                    Shippingactivity shippingactivity2 = Shippingactivity.this;
                    shippingactivity2.amount = shippingactivity2.total_amount;
                    Shippingactivity shippingactivity3 = Shippingactivity.this;
                    shippingactivity3.custid = Preferences.get_userId(shippingactivity3.mContext);
                   // Shippingactivity.this.startPayment();
                }
            }
        });
        this.myDialog.show();
    }

    private void showDialogDetails(String msg, String status) {
        this.myDialog = new Dialog(this);
        this.myDialog.setCancelable(false);
        this.myDialog.setCanceledOnTouchOutside(false);
        this.myDialog.setContentView(R.layout.dialog_payment_layout);
        TextView tv_ok = (TextView) this.myDialog.findViewById(R.id.tv_ok);
        TextView tv_heading = (TextView) this.myDialog.findViewById(R.id.tv_heading);
        TextView tv_msg = (TextView) this.myDialog.findViewById(R.id.tv_msg);
        ImageView iv = (ImageView) this.myDialog.findViewById(R.id.iv);
        if (status.equals("Success")) {
            tv_heading.setText("Payment Successful");
            iv.setImageResource(R.drawable.tick);
        } else {
            tv_heading.setText("Payment Error");
            iv.setImageResource(R.drawable.fail);
        }
        tv_msg.setText(msg);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Shippingactivity.this.myDialog.dismiss();
                Shippingactivity.this.onBackPressed();
                Shippingactivity.this.finish();
            }
        });
        this.myDialog.show();
    }
}

