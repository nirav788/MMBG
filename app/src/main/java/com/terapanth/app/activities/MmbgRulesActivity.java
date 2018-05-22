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

import com.terapanth.app.Adapters.TPRulesAdapter;
import com.terapanth.app.Models.TPRulesResponce;
import com.terapanth.app.Models.TPRulesResponce.TPRules;
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

public class MmbgRulesActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "MmbgRulesActivity";
    private AppPreference mAppPreference;
    private TPRulesAdapter mAdapter = null;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<TPRules> mTpRules;
    private int page_token = 0;
    private TextView No_Data;
    private Toolbar mToolbar;
    private TextView toolbar_title, tv_txt_no_data;
    private LinearLayout ll_main;
    private RecyclerView rv_rules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmbg_rules);

        init();
        inItView();
        onhandleClick();
        getAllRules(page_token);


    }

    private void init() {
        mAppPreference = new AppPreference(MmbgRulesActivity.this);
    }

    private void inItView() {
        page_token = 0;
        mTpRules = new ArrayList<>();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow_button);
        toolbar_title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("MMBG RULES");
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        rv_rules = (RecyclerView) findViewById(R.id.rv_rules);
        tv_txt_no_data = (TextView) findViewById(R.id.tv_txt_no_data);
        rv_rules.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(MmbgRulesActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_rules.setLayoutManager(mLayoutManager);
        // Disabled nested scrolling since Parent scrollview will scroll the content.
        rv_rules.setNestedScrollingEnabled(false);
        rv_rules.setAdapter(mAdapter);
    }


    private void onhandleClick() {
    }

    @Override
    public void onClick(View view) {

    }


    private void getAllRules(int offset) {

        Utills.showProgressBar(MmbgRulesActivity.this, getResources().getString(R.string.pleasewait));
        ApiInterface restInterface = ServiceGeneratorr.createService(ApiInterface.class);
        Call<TPRulesResponce> call = restInterface.TPRules(offset);
        call.enqueue(new Callback<TPRulesResponce>() {
            @Override
            public void onResponse(Call<TPRulesResponce> call, Response<TPRulesResponce> response) {
                Utills.hideProgressBar();

                if (response.body() != null) {
                    TPRulesResponce userObject = response.body();
                    boolean successCode = userObject.isStatus();
                    if (successCode) {
                        page_token = userObject.getPage_token();
                        mTpRules = new ArrayList<>();
                        mTpRules.addAll(userObject.getmTpRules());
                        mAdapter = new TPRulesAdapter(mTpRules, MmbgRulesActivity.this, rv_rules);
                        rv_rules.setAdapter(mAdapter);

                        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                            @Override
                            public void onLoadMore() {
                                mTpRules.add(null);
                                mAdapter.notifyItemInserted(mTpRules.size() - 1);
                                if (page_token > -1) {
                                    getFriendListMore(page_token);
                                } else {
                                    mTpRules.remove(mTpRules.size() - 1);
                                    mAdapter.notifyItemRemoved(mTpRules.size());
                                    mAdapter.notifyDataSetChanged();
                                    mAdapter.setLoaded();
                                }
                            }
                        });

                    } else {
                        Utills.setError(MmbgRulesActivity.this, userObject.getMessage().toString());
                    }
                } else {
                    Utills.setError(MmbgRulesActivity.this, getResources().getString(R.string.server_connection_problem));
                }
            }

            @Override
            public void onFailure(Call<TPRulesResponce> call, Throwable t) {
                Log.e("error", t.toString());
                Utills.hideProgressBar();
            }
        });

    }

    @SuppressLint("LongLogTag")
    private void getFriendListMore(final int offset) {
        Log.e(TAG, "   offset data " + offset);
        ApiInterface restInterface = ServiceGeneratorr.createService(ApiInterface.class);
        Call<TPRulesResponce> call = restInterface.TPRules(offset);
        call.enqueue(new Callback<TPRulesResponce>() {
            @Override
            public void onResponse(Call<TPRulesResponce> call, Response<TPRulesResponce> response) {
                Log.e("tutor_request_responce", " " + response.body().getPage_token());
                if (response.body() != null) {
                    TPRulesResponce userObject = response.body();
                    boolean successCode = userObject.isStatus();
                    if (successCode) {
                        page_token = userObject.getPage_token();
                        mTpRules.remove(mTpRules.size() - 1);
                        mAdapter.notifyItemRemoved(mTpRules.size());
                        mTpRules.addAll(userObject.getmTpRules());
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setLoaded();
                        setView();
                    } else {
                        Utills.setError(MmbgRulesActivity.this, userObject.getMessage().toString());
                    }
                } else {
                    Utills.setError(MmbgRulesActivity.this, getResources().getString(R.string.server_connection_problem));
                }
            }

            @Override
            public void onFailure(Call<TPRulesResponce> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }

    private void setView() {
        if (mTpRules != null && mTpRules.size() == 0) {
            rv_rules.setVisibility(View.GONE);
            No_Data.setVisibility(View.VISIBLE);

        } else {
            rv_rules.setVisibility(View.VISIBLE);
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
