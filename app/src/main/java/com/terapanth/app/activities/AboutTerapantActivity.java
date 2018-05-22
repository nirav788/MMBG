package com.terapanth.app.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.htmlcompat.HtmlCompat;
import com.terapanth.app.Models.AboutResponce;
import com.terapanth.app.R;
import com.terapanth.app.Utills.ServiceGeneratorr;
import com.terapanth.app.Utills.Utills;
import com.terapanth.app.interfaces.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutTerapantActivity extends AppCompatActivity {
    private static final String TAG = "AboutTerapantActivity" ;
    private Toolbar mToolbar;
    private TextView toolbar_title;
    private TextView Tv_Details, tv_title;
    String TitleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_terapant);

        Intent intent = getIntent();
        if (intent.hasExtra("About")) {

            TitleToolbar = intent.getStringExtra("About");
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
        Tv_Details = (TextView) findViewById(R.id.tv_details);
        tv_title = (TextView) findViewById(R.id.tv_title);

    }

    private void initData() {

        ApiInterface restInterface = ServiceGeneratorr.createService(ApiInterface.class);
        Utills.showProgressBar(AboutTerapantActivity.this, getResources().getString(R.string.pleasewait));

        Call<AboutResponce> call = restInterface.About();
        call.enqueue(new Callback<AboutResponce>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<AboutResponce> call, Response<AboutResponce> response) {
                Utills.hideProgressBar();
                if (response.body() != null) {
                    AboutResponce responceData = response.body();

                    boolean successCode = responceData.isStatus();
                    if (successCode) {

                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Tv_Details.setText(Html.fromHtml(responceData.getAbout().getAbout_body(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                            tv_title.setText(Html.fromHtml(responceData.getAbout().getAbout_title(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                        } else {
                            Tv_Details.setText(Html.fromHtml(responceData.getAbout().getAbout_body()), TextView.BufferType.SPANNABLE);
                            tv_title.setText(Html.fromHtml(responceData.getAbout().getAbout_title()), TextView.BufferType.SPANNABLE);
                        }

                    } else {
                        Utills.setError(AboutTerapantActivity.this, responceData.getMessage().toString());
                    }
                } else {
                    Utills.setError(AboutTerapantActivity.this, getResources().getString(R.string.server_connection_problem));
                }


            }

            @Override
            public void onFailure(Call<AboutResponce> call, Throwable t) {
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
