package com.store.promtech;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.store.promtech.utils.CheckIsUpdateReady;
import com.store.promtech.utils.ConnectionDetector;
import com.store.promtech.utils.Preferences;
import com.store.promtech.utils.UrlResponce;

import java.util.Random;

public class SplashActivity extends AppCompatActivity/* implements WSCallerVersionListener */{

    boolean is_verfied = false;
    Context mContext;
    public static String playstoreVersion = "";
    String currentVersion = "";
    String playstoreVersionName = "";
    boolean isForceUpdate = true;
    ConnectionDetector cd;
    Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        cd = new ConnectionDetector(mContext);





        if (!Preferences.isgenerateUniqueKey(mContext)) {
            generateUniqueId();
            Preferences.setUniqueKey(mContext, true);
        }

        is_verfied = Preferences.getisVerified(mContext);

        new CheckIsUpdateReady("https://play.google.com/store/apps/details?id=" + getPackageName() + "&hl=en", new UrlResponce() {
            @Override
            public void onReceived(String resposeStr) {
                playstoreVersionName = resposeStr;
                if (!getVersionName().equals(playstoreVersionName)){
                    showDialogUpdate();
                    return;
                }else{

                }
            }
        }).execute();

        if (Preferences.get_userId(mContext).equalsIgnoreCase("0")) {
            Preferences.set_userId(mContext, "0");


            Thread background = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(1 * 1000);
                        Intent i = new Intent(getBaseContext(), Dashboard.class);
                        i.putExtra("tag","splash");
                        i.putExtra("msg","");
                        startActivity(i);
                        finish();
                    } catch (Exception e) {
                    }
                }
            };
            background.start();
        }else{

            Thread background = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(1 * 1000);
                        Intent i = new Intent(getBaseContext(), Dashboard.class);
                        i.putExtra("tag","splash");
                        i.putExtra("msg","");
                        startActivity(i);
                        finish();
                    } catch (Exception e) {
                    }
                }
            };
            background.start();
        }


      //  goToHomeActivity();



    }




    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void goToHomeActivity() {


        Thread background = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3 * 1000);
                    Intent i = new Intent(getBaseContext(), Dashboard.class);
                    i.putExtra("tag","splash");
                    i.putExtra("msg","");
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                }
            }
        };
        background.start();
    }

    private void generateUniqueId() {
        Random rand = new Random();
        String uniqueId = System.currentTimeMillis() + "" + rand.nextInt(500);
        Log.d("uniqueId", uniqueId);
        Preferences.set_UniqueId(mContext, uniqueId);
    }

    private void goToRegistrationActivity() {
        Thread background = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3 * 1000);
                    Intent i = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                }
            }
        };
        background.start();
    }

   /* @Override
    public void onGetResponse(boolean isUpdateAvailable) {
        if (isUpdateAvailable) {
            showUpdateDialog();
        }
        else{
            if (!Preferences.isgenerateUniqueKey(mContext)) {
                generateUniqueId();
                Preferences.setUniqueKey(mContext, true);
            }

            is_verfied = Preferences.getisVerified(mContext);
            if (Preferences.get_userId(mContext).equalsIgnoreCase("0")) {
                Preferences.set_userId(mContext, "0");
            }

            goToHomeActivity();
        }
    }*/
    @Override
    protected void onResume() {
        super.onResume();

//        if (cd.isConnected()) {
//            new GooglePlayStoreAppVersionNameLoader(getApplicationContext(), this).execute();
//        }
//        else{
//            Toast.makeText(SplashActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
//        }

    }

    public void showUpdateDialog() {
        //

        //
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(SplashActivity.this, R.style.AlertDialogCustom);
        alertDialogBuilder.setTitle("Update!");
        alertDialogBuilder.setMessage("New update available. Please update your application.\nCurrent application version : v" + currentVersion + "\n Latest Version available in the Play Store : v" + playstoreVersion);

        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SplashActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                dialog.cancel();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isForceUpdate) {
                    finish();
                }
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }

    public  void launchPlayStoreApp()
    {

        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }

    }

    public  String getVersionName()
    {
        String versionName="";
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = pInfo.versionName;
            //versionCode=pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    private void showDialogUpdate(){
        myDialog = new Dialog(SplashActivity.this);
        myDialog.setCancelable(false);
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.setContentView(R.layout.dialog_delete_layout);

        TextView tv_delete = myDialog.findViewById(R.id.tv_delete);
        TextView tv_cancel = myDialog.findViewById(R.id.tv_cancel);
        TextView tv_heading = myDialog.findViewById(R.id.tv_heading);

        tv_delete.setText("Update");
        tv_heading.setText("Please update the newest version from play store... ");
        tv_cancel.setVisibility(View.GONE);
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPlayStoreApp();
                myDialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();

    }
}
