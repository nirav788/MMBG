package com.terapanth.app.Utills;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tapadoo.alerter.Alerter;
import com.terapanth.app.R;


public class Utills {

    private static ProgressDialog progressDialog;

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else {
            return true;
        }

    }


    public static void showProgressBar(Context mContext, String message) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void hideProgressBar() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public static void setGlide(String url, ImageView img, Context context) {


        Glide.with(context).load(url)
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img);
    }

       /* Picasso.with(context).load(url)
                .error(new ColorDrawable(context.getResources().getColor(android.R.color.darker_gray)))
                .into(img);*/

    public static void setNormal(Context mContext, String message) {

        Alerter.create((Activity) mContext)
                .setTitle("Error")
                .setText(message)
                .setBackgroundColorRes(R.color.colorAccent)
                .setIconColorFilter(0) // Optional - Removes white tint
                .enableSwipeToDismiss()
                .show();
    }

    public static void setError(Context mContext, String message) {

        Alerter.create((Activity) mContext)
                .setTitle("Error")
                .setText(message)
                .setBackgroundColorRes(R.color.red)
                .setIconColorFilter(0) // Optional - Removes white tint
                .enableSwipeToDismiss()
                .show();
    }

}