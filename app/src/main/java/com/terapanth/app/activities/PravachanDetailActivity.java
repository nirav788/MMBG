package com.terapanth.app.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.terapanth.app.R;

public class PravachanDetailActivity extends AppCompatActivity {

    private static final String TAG = "AboutTerapantActivity";
    private Toolbar mToolbar;
    private TextView toolbar_title;
    private TextView tv_details;
    String Title, Description, Images;
    ImageView img_pravachan;
    TextView tv_title_rule, tv_details_rule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pravachan_detail);

        Intent intent = getIntent();
        if (intent.hasExtra("title") || intent.hasExtra("details") || intent.hasExtra("image")) {

            Title = intent.getStringExtra("title");
            Description = intent.getStringExtra("details");
            Images = intent.getStringExtra("image");

        } else {
            Title = "";
            Description = "";
            Images = "";
        }
        initView();
        setData();


    }

    private void setData() {

        if (Title != null && Title.length() > 0) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tv_title_rule.setText(Html.fromHtml(Title, Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
            } else {
                tv_title_rule.setText(Html.fromHtml(Title), TextView.BufferType.SPANNABLE);
            }
        } else {
            tv_title_rule.setText("");
        }
        if (Description != null && Description.length() > 0) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tv_details_rule.setText(Html.fromHtml(Description, Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
            } else {
                tv_details_rule.setText(Html.fromHtml(Description), TextView.BufferType.SPANNABLE);
            }
        } else {
            tv_details_rule.setText("");
        }
        if (Description != null && Description.length() > 0) {

            Glide.with(PravachanDetailActivity.this).load(Images).into(img_pravachan);
        } else {
            img_pravachan.setImageResource(R.mipmap.ic_launcher);
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
        toolbar_title.setText("Pravanchan Detail");

        img_pravachan = (ImageView) findViewById(R.id.img_pravachan);
        tv_title_rule = (TextView) findViewById(R.id.tv_title_rule);
        tv_details_rule = (TextView) findViewById(R.id.tv_details_rule);


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
