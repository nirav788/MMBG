package com.terapanth.app.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.terapanth.app.Models.HistoryResponce;
import com.terapanth.app.R;
import com.terapanth.app.Utills.ServiceGeneratorr;
import com.terapanth.app.Utills.Utills;
import com.terapanth.app.interfaces.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MmbgHistoryActivity extends AppCompatActivity {
    private static final String TAG = "MmbgHistoryActivity";
    private Toolbar mToolbar;
    private TextView toolbar_title;
    String TitleToolbar;
    TextView tv_mmbghistory_title,tv_mmbghistory_details;

    private ArrayList<HistoryResponce.About> mAbouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmbg_history);

        mAbouts = new ArrayList<>();
        Intent intent = getIntent();
        if (intent.hasExtra("History")) {
            TitleToolbar = intent.getStringExtra("History");
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
        tv_mmbghistory_details = (TextView) findViewById(R.id.tv_mmbghistory_details);
        tv_mmbghistory_title = (TextView) findViewById(R.id.tv_mmbghistory_title);

    }

    private void initData() {

        ApiInterface restInterface = ServiceGeneratorr.createService(ApiInterface.class);
        Utills.showProgressBar(MmbgHistoryActivity.this, getResources().getString(R.string.pleasewait));

        Call<HistoryResponce> call = restInterface.History(0);
        call.enqueue(new Callback<HistoryResponce>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<HistoryResponce> call, Response<HistoryResponce> response) {
                Utills.hideProgressBar();
                if (response.body() != null) {
                    HistoryResponce responceData = response.body();

                    boolean successCode = responceData.isStatus();
                    if (successCode) {
                        mAbouts = responceData.getAbout();

                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                            tv_mmbghistory_details.setText(Html.fromHtml(mAbouts.get(0).getAbout_body(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                            tv_mmbghistory_title.setText(Html.fromHtml(mAbouts.get(0).getAbout_title(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                        } else {
                            tv_mmbghistory_details.setText(Html.fromHtml(mAbouts.get(0).getAbout_body()), TextView.BufferType.SPANNABLE);
                            tv_mmbghistory_title.setText(Html.fromHtml(mAbouts.get(0).getAbout_title()), TextView.BufferType.SPANNABLE);
                        }
                        //Utills.setNormal(MmbgHistoryActivity.this, getResources().getString(R.string.server_connection_problem));

                    } else {
                        Utills.setError(MmbgHistoryActivity.this, responceData.getMessage().toString());
                    }
                } else {
                    Utills.setError(MmbgHistoryActivity.this, getResources().getString(R.string.server_connection_problem));
                }


            }

            @Override
            public void onFailure(Call<HistoryResponce> call, Throwable t) {
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
