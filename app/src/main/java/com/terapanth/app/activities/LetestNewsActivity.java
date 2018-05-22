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

import com.terapanth.app.Adapters.LatestNewsAdapter;
import com.terapanth.app.Adapters.NewsAdapter;
import com.terapanth.app.Models.NewsResponce;
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

public class LetestNewsActivity extends AppCompatActivity {

    private static final String TAG = "MmbgRulesActivity";
    private AppPreference mAppPreference;
    private LinearLayoutManager mLayoutManager;
    private int page_token = 0;
    private TextView No_Data;
    private Toolbar mToolbar;
    private TextView toolbar_title, tv_txt_no_data;
    private LinearLayout ll_main;
    private RecyclerView rv_latest_news;
    private ArrayList<NewsResponce.News> mNuNews;
    private LatestNewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letest_news);

        init();
        inItView();
        onhandleClick();
        getAllNews(page_token);
    }

    private void init() {
        mAppPreference = new AppPreference(LetestNewsActivity.this);
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
        toolbar_title.setText("Latest News");
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        rv_latest_news = (RecyclerView) findViewById(R.id.rv_latest_news);
        tv_txt_no_data = (TextView) findViewById(R.id.tv_txt_no_data);
        rv_latest_news.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(LetestNewsActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_latest_news.setLayoutManager(mLayoutManager);
        // Disabled nested scrolling since Parent scrollview will scroll the content.
        rv_latest_news.setNestedScrollingEnabled(false);
        rv_latest_news.setAdapter(mAdapter);
    }

    private void onhandleClick() {

    }

    private void getAllNews(int offset) {
        Utills.showProgressBar(LetestNewsActivity.this, getResources().getString(R.string.pleasewait));
        ApiInterface restInterface = ServiceGeneratorr.createService(ApiInterface.class);
        Call<NewsResponce> call = restInterface.Letestnews(offset);
        call.enqueue(new Callback<NewsResponce>() {
            @Override
            public void onResponse(Call<NewsResponce> call, Response<NewsResponce> response) {
                Utills.hideProgressBar();

                if (response.body() != null) {
                    NewsResponce userObject = response.body();
                    boolean successCode = userObject.isStatus();
                    if (successCode) {
                        page_token = userObject.getPage_token();
                        mNuNews = new ArrayList<>();
                        mNuNews.addAll(userObject.getmNews());
                        mAdapter = new LatestNewsAdapter(mNuNews, LetestNewsActivity.this, rv_latest_news);
                        rv_latest_news.setAdapter(mAdapter);

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
                        Utills.setError(LetestNewsActivity.this, userObject.getMessage().toString());
                    }
                } else {
                    Utills.setError(LetestNewsActivity.this, getResources().getString(R.string.server_connection_problem));
                }
            }

            @Override
            public void onFailure(Call<NewsResponce> call, Throwable t) {
                Log.e("error", t.toString());
                Utills.hideProgressBar();
            }
        });
    }

    @SuppressLint("LongLogTag")
    private void getMoreNews(final int offset) {
        Log.e(TAG, "   offset data " + offset);
        ApiInterface restInterface = ServiceGeneratorr.createService(ApiInterface.class);
        Call<NewsResponce> call = restInterface.Letestnews(offset);
        call.enqueue(new Callback<NewsResponce>() {
            @Override
            public void onResponse(Call<NewsResponce> call, Response<NewsResponce> response) {
                Log.e("tutor_request_responce", " " + response.body().getPage_token());
                if (response.body() != null) {
                    NewsResponce userObject = response.body();
                    boolean successCode = userObject.isStatus();
                    if (successCode) {
                        page_token = userObject.getPage_token();
                        mNuNews.remove(mNuNews.size() - 1);
                        mAdapter.notifyItemRemoved(mNuNews.size());
                        mNuNews.addAll(userObject.getmNews());
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setLoaded();
                        setView();
                    } else {
                        Utills.setError(LetestNewsActivity.this, userObject.getMessage().toString());
                    }
                } else {
                    Utills.setError(LetestNewsActivity.this, getResources().getString(R.string.server_connection_problem));
                }
            }

            @Override
            public void onFailure(Call<NewsResponce> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }


    private void setView() {
        if (mNuNews != null && mNuNews.size() == 0) {
            rv_latest_news.setVisibility(View.GONE);
            No_Data.setVisibility(View.VISIBLE);

        } else {
            rv_latest_news.setVisibility(View.VISIBLE);
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
