package com.store.promtech;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.store.promtech.model.BaseResponse;
import com.store.promtech.model.MyProfile;
import com.store.promtech.model.ZipCodeVerify;
import com.store.promtech.retrofit.api.ApiServices;
import com.store.promtech.utils.CircularTextView;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Preferences;
import com.store.promtech.utils.Utility;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;

    Button btn_register;
    ConnectionDetector cd;
    ProgressDialog pDialog;
    MyProfile registration;
    BaseResponse baseResponse;
    ZipCodeVerify zipCodeVerify;
    Button btn_fetch;
    EditText et_name,et_business,et_gstNo;

    EditText et_phoneno;
    EditText et_email;
    EditText et_address;
    EditText et_state;
    EditText et_city;
    EditText et_zipcode;



    String user_firstname;

    String user_phoneno;
    String user_email;
    String user_address;
    String user_state;
    String user_city;
    String user_zipcode,businessname, gstNo;

//    String user_aadhar;
//    String user_gst;
//    String user_bank;
//    String user_branch;
//    String user_acNo;
//    String user_ifsc;
    String user_aadharFront_img;
    String user_aadharBack_img;
    String user_passbook_img;
    private String imagePath = "";
    private Bitmap bitmap;

    CircularTextView tv_cartcount;
    ImageView iv_cart, btn_back, btn_menu;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private final static int IMAGE_RESULT_ONE = 100;
    File upload_file_me_one=null;
    private final static int ALL_PERMISSIONS_RESULT = 107;

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
//    private int is_profile_photo_uploaded=0;
//    private ImageView iv_aadharFront,iv_aadharback,iv_passbook;

//    private int CAMERA_IMAGE_REQUEST = 2;
//    private int GALLERY_IMAGE_REQUEST = 1;
//    public File fileAadhar= new File("");
//    public File fileAadharBack= new File("");
//    public File filepassbook= new File("");
//    private Uri fileUriAadhar;
//    private int imageType;
    //private String photo1="",photo2="", photo3="", photo4="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Please Wait...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);

        initViews();
        getUserDetails();
    }

    private void initViews() {
        ImageView iv_home = findViewById(R.id.iv_home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, Dashboard.class);
                startActivity(i);
                finishAffinity();
            }
        });
        btn_fetch=findViewById(R.id.btn_fetch);
        et_name = findViewById(R.id.et_name);
        et_phoneno = findViewById(R.id.et_phoneno);
        et_email = findViewById(R.id.et_email);
        et_address = findViewById(R.id.et_address);
        et_state = findViewById(R.id.et_state);
        et_city = findViewById(R.id.et_city);
        et_zipcode = findViewById(R.id.et_zipcode);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setOnClickListener(this);
        tv_cartcount = findViewById(R.id.tv_cartcount);
        et_business = findViewById(R.id.et_business);
        et_gstNo = findViewById(R.id.et_gstNo);
        String colorStr = getResources().getString(R.string.green_color);
        tv_cartcount.setSolidColor(colorStr);
        btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setVisibility(View.GONE);
        iv_cart = findViewById(R.id.iv_cart);
        iv_cart.setOnClickListener(this);



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


        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        btn_fetch.setOnClickListener(this);

//        permissionsToRequest = findUnAskedPermissions(permissions);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//
//            if (permissionsToRequest.size() > 0)
//                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
//        }

        ImageView iv_phone=findViewById(R.id.iv_phone);
        iv_phone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Utility.CallContactNo(mContext);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_cartcount.setText(Preferences.get_Cartount(mContext));
    }


    @Override
    public void onClick(View v) {
        if (v == btn_register) {
            if (et_name.getText().toString().isEmpty()) {
                Utility.showToastShort(mContext, "Please Enter Name");
                return;
            }


            if (et_phoneno.getText().toString().isEmpty()) {
                Utility.showToastShort(mContext, "Please Enter Phone No.");
                return;
            }

            if (et_email.getText().toString().isEmpty()) {
                Utility.showToastShort(mContext, "Please Enter Your Email");
                return;
            }

            if (!Utility.isValidEmail(et_email.getText().toString())) {
                Utility.showToastShort(mContext, "Please Enter Valid Email");
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

            if (et_zipcode.getText().toString().isEmpty()) {
                Utility.showToastShort(mContext, "Please Enter ZipCode");
                return;
            }



            user_email = et_email.getText().toString().trim();
            user_firstname = et_name.getText().toString().trim();
            user_phoneno = et_phoneno.getText().toString().trim();
            user_email = et_email.getText().toString().trim();
            user_address = et_address.getText().toString().trim();
            user_state = et_state.getText().toString().trim();
            user_city = et_city.getText().toString().trim();
            user_zipcode = et_zipcode.getText().toString().trim();
            businessname = et_business.getText().toString().trim();
            gstNo = et_gstNo.getText().toString().trim();



            UpdateUserDetails();
        }

        if (v == btn_back) {
            finish();
            onBackPressed();
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        else if (v == iv_cart) {
            Intent i = new Intent(mContext, ProductCart.class);
            i.putExtra("From", "Dashboard");
            startActivity(i);
        }
        else if(v==btn_fetch)
        {
            Log.e("ll",Preferences.get_userGeoAddress(mContext)+" "+Preferences.get_userGeoState(mContext));
            Toast.makeText(mContext,"ll"+Preferences.get_userGeoAddress(mContext)+" "+Preferences.get_userGeoState(mContext),Toast.LENGTH_SHORT).show();
            et_address.setText( Preferences.get_userGeoAddress(mContext));
            et_state.setText( Preferences.get_userGeoState(mContext));
            et_city.setText( Preferences.get_userGeoCity(mContext));
            et_zipcode.setText( Preferences.get_userGeoPostCode(mContext));
        }


    }



    private void getUserDetails() {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);


        Call<MyProfile> call = redditAPI.GetMYProfile(Preferences.get_userId(mContext));
        call.enqueue(new Callback<MyProfile>() {

            @Override
            public void onResponse(Call<MyProfile> call, retrofit2.Response<MyProfile> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    registration = response.body();
                    if (registration.getAck() == 1) {
                        setData();
                    } else {

                        Utility.showToastShort(mContext, registration.getMsg());
                    }
                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<MyProfile> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }


    private void setData() {
        et_email.setText(registration.getUserData().get(0).getEmail());
        et_name.setText(registration.getUserData().get(0).getName());
        et_phoneno.setText(registration.getUserData().get(0).getPhone());
        et_address.setText(registration.getUserData().get(0).getAddress());
        et_state.setText(registration.getUserData().get(0).getState());
        et_city.setText(registration.getUserData().get(0).getCity());
        et_zipcode.setText(registration.getUserData().get(0).getZip());
        et_business.setText(registration.getUserData().get(0).getBusiness_name());
        et_gstNo.setText(registration.getUserData().get(0).getGst_no());

        Preferences.set_userEmail(mContext, registration.getUserData().get(0).getEmail());



    }


    private void VerifyZipCodeparsejson(final String zipcode) {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);
        Call<ZipCodeVerify> call = redditAPI.VerifyZipCode(zipcode);
        call.enqueue(new Callback<ZipCodeVerify>() {

            @Override
            public void onResponse(Call<ZipCodeVerify> call, retrofit2.Response<ZipCodeVerify> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    zipCodeVerify = response.body();
                    if (zipCodeVerify.getAck().equals("1")) {
                        btn_register.setEnabled(true);
                        //Utility.showToastShort(mContext, zipCodeVerify.getMsg());
                    } else {
                        btn_register.setEnabled(false);
                        Utility.showToastShort(mContext, zipCodeVerify.getMsg());
                    }
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ZipCodeVerify> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    private  File persistImage(Bitmap bitmap, String name) {

        File filesDir =getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

//Convert bitmap to byte array
        Bitmap bitmaps = bitmap;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmaps.compress(Bitmap.CompressFormat.JPEG, 50, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }




    private void UpdateUserDetails() {
        pDialog.show();
        String BASE_URL = getResources().getString(R.string.base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices redditAPI;
        redditAPI = retrofit.create(ApiServices.class);

        String isphoto1 ="0";
        String isphoto2 ="0";
        String isphoto3 ="0";


       /* if (fileAadhar.length()>0){
            isphoto1 = "1";
        }else{
            isphoto1="0";
        }
        if (fileAadharBack.length()>0){
            isphoto2 = "1";

        }else{
            isphoto2="0";
        }
        if (filepassbook.length()>0){
            isphoto3 = "1";
        }else{
            isphoto3="0";
            //filepassbook = new File("");
        }*/

        Random random = new Random();
        int randomNumber = random.nextInt(80-65) + 65;




        /*RequestBody fileReqBody1 = RequestBody.create(MediaType.parse("image/*"), fileAadhar);
        RequestBody fileReqBody2 = RequestBody.create(MediaType.parse("image/*"), fileAadharBack);
        RequestBody fileReqBody3 = RequestBody.create(MediaType.parse("image/*"), filepassbook);


        MultipartBody.Part part1 = MultipartBody.Part.createFormData("aadhar_card_front", fileAadhar.getName());
        MultipartBody.Part part2 = MultipartBody.Part.createFormData("aadhar_card_back", fileAadharBack.getName());
        MultipartBody.Part part3 = MultipartBody.Part.createFormData("gst_certificate", filepassbook.getName());
        if (fileAadhar.length()>0){
            part1 = MultipartBody.Part.createFormData("aadhar_card_front", fileAadhar.getName(), fileReqBody1);
        }
        if (fileAadharBack.length()>0){
            part2 = MultipartBody.Part.createFormData("aadhar_card_back", fileAadharBack.getName(), fileReqBody2);
        }
        if (filepassbook.length()>0){
            part3 = MultipartBody.Part.createFormData("gst_certificate", filepassbook.getName(), fileReqBody3);
        }*/
      //  Log.d("String", "" + part1.toString());

        Call<BaseResponse> call = redditAPI.UserUpdateDetails(Preferences.get_userId(mContext),
                user_firstname,
                user_email,user_phoneno, user_address,
                user_city,user_state, user_zipcode,businessname,gstNo);
        call.enqueue(new Callback<BaseResponse>() {

            @Override
            public void onResponse(Call<BaseResponse> call, retrofit2.Response<BaseResponse> response) {
                Log.d("String", "" + response);
                if (response.isSuccessful()) {
                    baseResponse = response.body();
                    if (baseResponse.getAck() == 1) {
                        Utility.showToastShort(mContext, baseResponse.getMsg());
                        Preferences.set_firstuserName(mContext, user_firstname);
                       // Preferences.set_lastName(mContext,);
                        Preferences.set_userEmail(mContext, user_email);
                        Preferences.set_userPhone(mContext, user_phoneno);
                       // Preferences.set_userId(mContext, );
                        Preferences.set_address(mContext,user_address );
                        Preferences.set_city(mContext,user_city );
                        Preferences.set_state(mContext, user_state);
                        Preferences.set_Zip(mContext,user_zipcode );
                        Preferences.set_business(mContext,businessname );
                        Preferences.set_gst(mContext,gstNo );
                    } else {
                        Utility.showToastShort(mContext, baseResponse.getMsg());
                    }
                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                pDialog.dismiss();
            }
        });
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



    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }
    public boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }


    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }



    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getApplicationContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


/*    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }*/

    /*private  File persistImage(Bitmap bitmap, String name) {

        File filesDir =getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

//Convert bitmap to byte array
        Bitmap bitmaps = bitmap;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmaps.compress(Bitmap.CompressFormat.JPEG, 50 *//*ignored for PNG*//*, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_RESULT_ONE) {

            try {
                String filePath = getImageFilePath(data);
                if (filePath != null) {
                    Bitmap selectedImage = BitmapFactory.decodeFile(filePath);


                    ExifInterface ei = null;
                    try {
                        ei = new ExifInterface(filePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);

                    Bitmap rotatedBitmap = null;
                    switch (orientation) {

                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotatedBitmap = rotateImage(selectedImage, 90);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotatedBitmap = rotateImage(selectedImage, 180);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotatedBitmap = rotateImage(selectedImage, 270);
                            break;

                        case ExifInterface.ORIENTATION_NORMAL:
                        default:
                            rotatedBitmap = selectedImage;
                    }


                    ivImage.setImageBitmap(rotatedBitmap);
                    is_profile_photo_uploaded = 1;
                    upload_file_me_one = new File(getImageFromFilePath(data));

                }


            } catch (Exception e) {

            }
        }

    }


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;

        if (isCamera) return getCaptureImageOutputUri().getPath();
        else return getPathFromURI(data.getData());

    }

    public String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }
    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
*/
    /*  public Intent getPickImageChooserIntent() {

        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }


    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }*/

}
