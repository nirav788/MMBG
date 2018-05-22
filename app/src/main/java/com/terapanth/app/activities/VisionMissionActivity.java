package com.terapanth.app.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.pixplicity.htmlcompat.HtmlCompat;
import com.terapanth.app.Models.VisionMissionResponce;
import com.terapanth.app.R;
import com.terapanth.app.Utills.ServiceGeneratorr;
import com.terapanth.app.Utills.Utills;
import com.terapanth.app.interfaces.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisionMissionActivity extends AppCompatActivity {

    private static final String TAG = "VisionMissionActivity";
    private Toolbar mToolbar;
    private TextView toolbar_title;
    private TextView tv_vision_title, tv_vision_details;
    String TitleToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision_mission);

        Intent intent = getIntent();
        if (intent.hasExtra("Vision")) {
            TitleToolbar = intent.getStringExtra("Vision");
        } else {

            TitleToolbar = "";
        }
        Log.e(TAG, "init: " + TitleToolbar);
        initView();
        initData();
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
        tv_vision_details = (TextView) findViewById(R.id.tv_vision_details);
        tv_vision_title = (TextView) findViewById(R.id.tv_vision_title);

    }

    private void initData() {

        ApiInterface restInterface = ServiceGeneratorr.createService(ApiInterface.class);
        Utills.showProgressBar(VisionMissionActivity.this, getResources().getString(R.string.pleasewait));

        Call<VisionMissionResponce> call = restInterface.VisionMission();
        call.enqueue(new Callback<VisionMissionResponce>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<VisionMissionResponce> call, Response<VisionMissionResponce> response) {
                Utills.hideProgressBar();
                if (response.body() != null) {
                    VisionMissionResponce responceData = response.body();

                    boolean successCode = responceData.isStatus();
                    if (successCode) {

                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            tv_vision_details.setText(Html.fromHtml(responceData.getAbout().getAbout_body(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                            tv_vision_title.setText(Html.fromHtml(responceData.getAbout().getAbout_title(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                        } else {
                            tv_vision_details.setText(Html.fromHtml(responceData.getAbout().getAbout_body()), TextView.BufferType.SPANNABLE);
                            tv_vision_title.setText(Html.fromHtml(responceData.getAbout().getAbout_title()), TextView.BufferType.SPANNABLE);
                        }

                    } else {
                        Utills.setError(VisionMissionActivity.this, responceData.getMessage().toString());
                    }
                } else {
                    Utills.setError(VisionMissionActivity.this, getResources().getString(R.string.server_connection_problem));
                }


            }

            @Override
            public void onFailure(Call<VisionMissionResponce> call, Throwable t) {
                Log.e("fail", t.toString());
                Utills.hideProgressBar();
            }
        });
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
