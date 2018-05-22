package com.terapanth.app.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.terapanth.app.Adapters.LatestNewsAdapter;
import com.terapanth.app.Adapters.PrekshadhyanAdapter;
import com.terapanth.app.Models.PrekshadhyanResponce;
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

public class PrekshadhyanListActivity extends AppCompatActivity {

    private static final String TAG = "PrekshadhyanList";
    private AppPreference mAppPreference;
    private LinearLayoutManager mLayoutManager;
    private int page_token = 0;
    private TextView No_Data;
    private Toolbar mToolbar;
    private TextView toolbar_title, tv_txt_no_data;
    private LinearLayout ll_main;
    private RecyclerView rv_prekshadhyan;
    private ArrayList<PrekshadhyanResponce.Prekshadhyan> mPrekshadhyans;
    private PrekshadhyanAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prekshadhyan);

        init();
        inItView();
        onhandleClick();
        getAllNews(page_token);
    }

    private void init() {
        mAppPreference = new AppPreference(PrekshadhyanListActivity.this);
    }

    private void inItView() {
        page_token = 0;
        mPrekshadhyans = new ArrayList<>();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow_button);
        toolbar_title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Prekshadhyan List");
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        rv_prekshadhyan = (RecyclerView) findViewById(R.id.rv_prekshadhyan);
        tv_txt_no_data = (TextView) findViewById(R.id.tv_txt_no_data);
        rv_prekshadhyan.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(PrekshadhyanListActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_prekshadhyan.setLayoutManager(mLayoutManager);
        // Disabled nested scrolling since Parent scrollview will scroll the content.
        rv_prekshadhyan.setNestedScrollingEnabled(false);
        rv_prekshadhyan.setAdapter(mAdapter);
    }

    private void onhandleClick() {

    }

    private void getAllNews(int offset) {
        Utills.showProgressBar(PrekshadhyanListActivity.this, getResources().getString(R.string.pleasewait));
        ApiInterface restInterface = ServiceGeneratorr.createService(ApiInterface.class);
        Call<PrekshadhyanResponce> call = restInterface.Prekshadhyan(offset);
        call.enqueue(new Callback<PrekshadhyanResponce>() {
            @Override
            public void onResponse(Call<PrekshadhyanResponce> call, Response<PrekshadhyanResponce> response) {
                Utills.hideProgressBar();

                if (response.body() != null) {
                    PrekshadhyanResponce userObject = response.body();
                    boolean successCode = userObject.isStatus();
                    if (successCode) {
                        page_token = userObject.getPage_token();
                        mPrekshadhyans = new ArrayList<>();
                        mPrekshadhyans.addAll(userObject.getmPrekshadhyan());
                        mAdapter = new PrekshadhyanAdapter(mPrekshadhyans, PrekshadhyanListActivity.this, rv_prekshadhyan);
                        rv_prekshadhyan.setAdapter(mAdapter);

                        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                            @Override
                            public void onLoadMore() {
                                mPrekshadhyans.add(null);
                                mAdapter.notifyItemInserted(mPrekshadhyans.size() - 1);
                                if (page_token > -1) {
                                    getMoreNews(page_token);
                                } else {
                                    mPrekshadhyans.remove(mPrekshadhyans.size() - 1);
                                    mAdapter.notifyItemRemoved(mPrekshadhyans.size());
                                    mAdapter.notifyDataSetChanged();
                                    mAdapter.setLoaded();
                                }
                            }
                        });

                    } else {
                        Utills.setError(PrekshadhyanListActivity.this, userObject.getMessage().toString());
                    }
                } else {
                    Utills.setError(PrekshadhyanListActivity.this, getResources().getString(R.string.server_connection_problem));
                }
            }

            @Override
            public void onFailure(Call<PrekshadhyanResponce> call, Throwable t) {
                Log.e("error", t.toString());
                Utills.hideProgressBar();
            }
        });
    }

    @SuppressLint("LongLogTag")
    private void getMoreNews(final int offset) {
        Log.e(TAG, "   offset data " + offset);
        ApiInterface restInterface = ServiceGeneratorr.createService(ApiInterface.class);
        Call<PrekshadhyanResponce> call = restInterface.Prekshadhyan(offset);
        call.enqueue(new Callback<PrekshadhyanResponce>() {
            @Override
            public void onResponse(Call<PrekshadhyanResponce> call, Response<PrekshadhyanResponce> response) {
                Log.e("tutor_request_responce", " " + response.body().getPage_token());
                if (response.body() != null) {
                    PrekshadhyanResponce userObject = response.body();
                    boolean successCode = userObject.isStatus();
                    if (successCode) {
                        page_token = userObject.getPage_token();
                        mPrekshadhyans.remove(mPrekshadhyans.size() - 1);
                        mAdapter.notifyItemRemoved(mPrekshadhyans.size());
                        mPrekshadhyans.addAll(userObject.getmPrekshadhyan());
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setLoaded();
                        setView();
                    } else {
                        Utills.setError(PrekshadhyanListActivity.this, userObject.getMessage().toString());
                    }
                } else {
                    Utills.setError(PrekshadhyanListActivity.this, getResources().getString(R.string.server_connection_problem));
                }
            }

            @Override
            public void onFailure(Call<PrekshadhyanResponce> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }


    private void setView() {
        if (mPrekshadhyans != null && mPrekshadhyans.size() == 0) {
            rv_prekshadhyan.setVisibility(View.GONE);
            No_Data.setVisibility(View.VISIBLE);

        } else {
            rv_prekshadhyan.setVisibility(View.VISIBLE);
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
