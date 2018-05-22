package com.terapanth.app.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.terapanth.app.R;

public class LatestDetailActivity extends AppCompatActivity {

    private static final String TAG = "AboutTerapantActivity";
    private Toolbar mToolbar;
    private TextView toolbar_title;
    private TextView tv_details;
    String TitleToolbar = "Latest News Detail", Imagedtail = "", newsDetail = "";
    private ImageView img_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latestnews_detail);

        Intent intent = getIntent();
        if (intent.hasExtra("News") || intent.hasExtra("newsdetail")) {

            Imagedtail = intent.getStringExtra("News");
            newsDetail = intent.getStringExtra("newsdetail");

        } else {

        }
        Log.e(TAG, "init: " + TitleToolbar);
        initView();
        setData();

    }

    private void setData() {

        if (Imagedtail != null && Imagedtail.length() > 0) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tv_details.setText(Html.fromHtml(Imagedtail, Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
            } else {
                tv_details.setText(Html.fromHtml(Imagedtail), TextView.BufferType.SPANNABLE);
            }
        } else {
            tv_details.setText("");
        }


        if (newsDetail != null && newsDetail.length() > 0) {
            Glide.with(LatestDetailActivity.this).load(newsDetail).into(img_news);
        } else {
            img_news.setImageResource(R.mipmap.ic_launcher);
        }
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow_button);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(TitleToolbar);
        tv_details = (TextView) findViewById(R.id.tv_details);
        img_news = (ImageView) findViewById(R.id.img_news);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onClickback();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void onBackPressed() {
        onClickback();
        super.onBackPressed();
    }


    private void onClickback() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }


}
