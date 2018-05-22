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
import android.widget.TextView;

import com.terapanth.app.Adapters.ManagementAdapter;
import com.terapanth.app.Models.ManagementResponce;
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

public class MmbgManagementActivity extends AppCompatActivity {

    private static final String TAG = "MmbgManagementActivity";
    private Toolbar mToolbar;
    private TextView toolbar_title, tv_txt_no_data;
    AppPreference mAppPreference;
    RecyclerView rv_management;
    private LinearLayoutManager mLayoutManager;
    private int page_token = 0;
    private ArrayList<ManagementResponce.Management> mMmbgTeams;
    ManagementAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmbg_management);

        init();
        inItView();
        onhandleClick();
        getAllManagement(page_token);
    }

    private void getAllManagement(int offset) {

        Utills.showProgressBar(MmbgManagementActivity.this, getResources().getString(R.string.pleasewait));
        ApiInterface restInterface = ServiceGeneratorr.createService(ApiInterface.class);
        Call<ManagementResponce> call = restInterface.Management(offset);
        call.enqueue(new Callback<ManagementResponce>() {
            @Override
            public void onResponse(Call<ManagementResponce> call, Response<ManagementResponce> response) {
                Utills.hideProgressBar();

                if (response.body() != null) {
                    ManagementResponce userObject = response.body();
                    boolean successCode = userObject.isStatus();
                    if (successCode) {
                        page_token = userObject.getPage_token();
                        mMmbgTeams = new ArrayList<>();
                        mMmbgTeams.addAll(userObject.getmManagement());
                        mAdapter = new ManagementAdapter(mMmbgTeams, MmbgManagementActivity.this, rv_management);
                        rv_management.setAdapter(mAdapter);

                        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                            @Override
                            public void onLoadMore() {
                                mMmbgTeams.add(null);
                                mAdapter.notifyItemInserted(mMmbgTeams.size() - 1);
                                if (page_token > -1) {
                                    getFriendListMore(page_token);
                                } else {
                                    mMmbgTeams.remove(mMmbgTeams.size() - 1);
                                    mAdapter.notifyItemRemoved(mMmbgTeams.size());
                                    mAdapter.notifyDataSetChanged();
                                    mAdapter.setLoaded();
                                }
                            }
                        });

                    } else {
                        Utills.setError(MmbgManagementActivity.this, userObject.getMessage().toString());
                    }
                } else {
                    Utills.setError(MmbgManagementActivity.this, getResources().getString(R.string.server_connection_problem));
                }
            }

            @Override
            public void onFailure(Call<ManagementResponce> call, Throwable t) {
                Log.e("error", t.toString());
                Utills.hideProgressBar();
            }
        });
    }


    @SuppressLint("LongLogTag")
    private void getFriendListMore(final int offset) {
        Log.e(TAG, "   offset data " + offset);
        ApiInterface restInterface = ServiceGeneratorr.createService(ApiInterface.class);
        Call<ManagementResponce> call = restInterface.Management(offset);
        call.enqueue(new Callback<ManagementResponce>() {
            @Override
            public void onResponse(Call<ManagementResponce> call, Response<ManagementResponce> response) {
                Log.e("tutor_request_responce", " " + response.body().getPage_token());
                if (response.body() != null) {
                    ManagementResponce userObject = response.body();
                    boolean successCode = userObject.isStatus();
                    if (successCode) {
                        page_token = userObject.getPage_token();
                        mMmbgTeams.remove(mMmbgTeams.size() - 1);
                        mAdapter.notifyItemRemoved(mMmbgTeams.size());
                        mMmbgTeams.addAll(userObject.getmManagement());
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setLoaded();
                        setView();
                    } else {
                        Utills.setError(MmbgManagementActivity.this, userObject.getMessage().toString());
                    }
                } else {
                    Utills.setError(MmbgManagementActivity.this, getResources().getString(R.string.server_connection_problem));
                }
            }

            @Override
            public void onFailure(Call<ManagementResponce> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }

    private void setView() {
        if (mMmbgTeams != null && mMmbgTeams.size() == 0) {
            rv_management.setVisibility(View.GONE);
            tv_txt_no_data.setVisibility(View.VISIBLE);

        } else {
            rv_management.setVisibility(View.VISIBLE);
            tv_txt_no_data.setVisibility(View.GONE);
        }
    }


    private void init() {
        mAppPreference = new AppPreference(MmbgManagementActivity.this);
    }

    private void inItView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow_button);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("MMBG MANAGEMENT");
        rv_management = (RecyclerView) findViewById(R.id.rv_management);
        tv_txt_no_data = (TextView) findViewById(R.id.tv_txt_no_data);
        rv_management.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(MmbgManagementActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_management.setLayoutManager(mLayoutManager);
        // Disabled nested scrolling since Parent scrollview will scroll the content.
        rv_management.setNestedScrollingEnabled(false);
        //  rv_management.setAdapter(mAdapter);

    }

    private void onhandleClick() {
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
