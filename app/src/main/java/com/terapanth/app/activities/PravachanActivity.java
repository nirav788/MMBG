package com.terapanth.app.activities;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.terapanth.app.Adapters.PravanchanAdapter;
import com.terapanth.app.Models.PravachanResponce;
import com.terapanth.app.R;
import com.terapanth.app.Utills.AppPreference;
import com.terapanth.app.Utills.ServiceGeneratorr;
import com.terapanth.app.Utills.Utills;
import com.terapanth.app.interfaces.ApiInterface;
import com.terapanth.app.interfaces.OnLoadMoreListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PravachanActivity extends AppCompatActivity {

    private static final String TAG = "MmbgRulesActivity";
    private AppPreference mAppPreference;
    private LinearLayoutManager mLayoutManager;
    private int page_token = 0;
    private TextView No_Data;
    private Toolbar mToolbar;
    private TextView toolbar_title, tv_txt_no_data;
    private LinearLayout ll_main;
    private RecyclerView rv_pravachan;
    private ArrayList<PravachanResponce.Pravachan> mNuNews;
    private PravanchanAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pravachan);

        init();
        inItView();
        onhandleClick();
        getAllPravachan(page_token);
    }

    private void init() {
        mAppPreference = new AppPreference(PravachanActivity.this);
    }

    private void inItView() {
        page_token = 0;
        mNuNews = new ArrayList<>();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow_button);
        toolbar_title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Pravanchan");
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        rv_pravachan = (RecyclerView) findViewById(R.id.rv_pravachan);
        tv_txt_no_data = (TextView) findViewById(R.id.tv_txt_no_data);
        rv_pravachan.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(PravachanActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_pravachan.setLayoutManager(mLayoutManager);
        // Disabled nested scrolling since Parent scrollview will scroll the content.
        rv_pravachan.setNestedScrollingEnabled(false);
        rv_pravachan.setAdapter(mAdapter);
    }

    private void onhandleClick() {

    }


    private void getAllPravachan(int offset) {
        Utills.showProgressBar(PravachanActivity.this, getResources().getString(R.string.pleasewait));
        ApiInterface restInterface = ServiceGeneratorr.createService(ApiInterface.class);
        Call<PravachanResponce> call = restInterface.Pravachan(offset);
        call.enqueue(new Callback<PravachanResponce>() {
            @Override
            public void onResponse(Call<PravachanResponce> call, Response<PravachanResponce> response) {
                Utills.hideProgressBar();

                if (response.body() != null) {
                    PravachanResponce userObject = response.body();
                    boolean successCode = userObject.isStatus();
                    if (successCode) {
                        page_token = userObject.getPage_token();
                        mNuNews = new ArrayList<>();
                        mNuNews.addAll(userObject.getmPravachans());
                        mAdapter = new PravanchanAdapter(mNuNews, PravachanActivity.this, rv_pravachan);
                        rv_pravachan.setAdapter(mAdapter);

                        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                            @Override
                            public void onLoadMore() {
                                mNuNews.add(null);
                                mAdapter.notifyItemInserted(mNuNews.size() - 1);
                                if (page_token > -1) {
                                    getMoreNews(page_token);
                                } else {
                                    mNuNews.remove(mNuNews.size() - 1);
                                    mAdapter.notifyItemRemoved(mNuNews.size());
                                    mAdapter.notifyDataSetChanged();
                                    mAdapter.setLoaded();
                                }
                            }
                        });

                    } else {
                        Utills.setError(PravachanActivity.this, userObject.getMessage().toString());
                    }
                } else {
                    Utills.setError(PravachanActivity.this, getResources().getString(R.string.server_connection_problem));
                }
            }

            @Override
            public void onFailure(Call<PravachanResponce> call, Throwable t) {
                Log.e("error", t.toString());
                Utills.hideProgressBar();
            }
        });
    }

    @SuppressLint("LongLogTag")
    private void getMoreNews(final int offset) {
        Log.e(TAG, "   offset data " + offset);
        ApiInterface restInterface = ServiceGeneratorr.createService(ApiInterface.class);
        Call<PravachanResponce> call = restInterface.Pravachan(offset);
        call.enqueue(new Callback<PravachanResponce>() {
            @Override
            public void onResponse(Call<PravachanResponce> call, Response<PravachanResponce> response) {
                Log.e("tutor_request_responce", " " + response.body().getPage_token());
                if (response.body() != null) {
                    PravachanResponce userObject = response.body();
                    boolean successCode = userObject.isStatus();
                    if (successCode) {
                        page_token = userObject.getPage_token();
                        mNuNews.remove(mNuNews.size() - 1);
                        mAdapter.notifyItemRemoved(mNuNews.size());
                        mNuNews.addAll(userObject.getmPravachans());
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setLoaded();
                        setView();
                    } else {
                        Utills.setError(PravachanActivity.this, userObject.getMessage().toString());
                    }
                } else {
                    Utills.setError(PravachanActivity.this, getResources().getString(R.string.server_connection_problem));
                }
            }

            @Override
            public void onFailure(Call<PravachanResponce> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }


    private void setView() {
        if (mNuNews != null && mNuNews.size() == 0) {
            rv_pravachan.setVisibility(View.GONE);
            No_Data.setVisibility(View.VISIBLE);

        } else {
            rv_pravachan.setVisibility(View.VISIBLE);
            No_Data.setVisibility(View.GONE);
        }
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
