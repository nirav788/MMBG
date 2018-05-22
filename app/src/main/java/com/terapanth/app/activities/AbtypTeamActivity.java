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

import com.terapanth.app.Adapters.MahasabhaTeamAdapter;
import com.terapanth.app.Models.MahasabhateamResponce;
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

public class AbtypTeamActivity extends AppCompatActivity {
    private static final String TAG = "MahasabhaTeamActivity" ;
    private AppPreference mAppPreference;
    private LinearLayoutManager mLayoutManager;
    private TextView No_Data;
    private Toolbar mToolbar;
    private TextView toolbar_title, tv_txt_no_data;
    private LinearLayout ll_main;
    private RecyclerView rv_mmbg_team;
    private ArrayList<MahasabhateamResponce.Mahasabhateam> mMahasabhateams;
    MahasabhaTeamAdapter mAdapter;
    private int page_token = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasabha_team);

        init();
        inItView();
        onhandleClick();
        getMahasabhaTeam(page_token);
    }

    private void init() {
        mAppPreference = new AppPreference(AbtypTeamActivity.this);
    }

    private void inItView() {
        
    }

    private void onhandleClick() {
        mMahasabhateams = new ArrayList<>();
        page_token = 0;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow_button);
        toolbar_title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("ABTYP Team");
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        rv_mmbg_team = (RecyclerView) findViewById(R.id.rv_mmbg_team);
        tv_txt_no_data = (TextView) findViewById(R.id.tv_txt_no_data);
        rv_mmbg_team.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(AbtypTeamActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_mmbg_team.setLayoutManager(mLayoutManager);
        // Disabled nested scrolling since Parent scrollview will scroll the content.
        rv_mmbg_team.setNestedScrollingEnabled(false);
        rv_mmbg_team.setAdapter(mAdapter);
    }

    private void getMahasabhaTeam(int offset) {

        Utills.showProgressBar(AbtypTeamActivity.this, getResources().getString(R.string.pleasewait));
        ApiInterface restInterface = ServiceGeneratorr.createService(ApiInterface.class);
        Call<MahasabhateamResponce> call = restInterface.Mahashabha(offset);
        call.enqueue(new Callback<MahasabhateamResponce>() {
            @Override
            public void onResponse(Call<MahasabhateamResponce> call, Response<MahasabhateamResponce> response) {
                Utills.hideProgressBar();

                if (response.body() != null) {
                    MahasabhateamResponce userObject = response.body();
                    boolean successCode = userObject.isStatus();
                    if (successCode) {
                        page_token = userObject.getPage_token();
                        mMahasabhateams = new ArrayList<>();
                        mMahasabhateams.addAll(userObject.getmMahasabhateams());
                        mAdapter = new MahasabhaTeamAdapter(mMahasabhateams, AbtypTeamActivity.this, rv_mmbg_team);
                        rv_mmbg_team.setAdapter(mAdapter);

                        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                            @Override
                            public void onLoadMore() {
                                mMahasabhateams.add(null);
                                mAdapter.notifyItemInserted(mMahasabhateams.size() - 1);
                                if (page_token > -1) {
                                    getMahasabhaTeamMore(page_token);
                                } else {
                                    mMahasabhateams.remove(mMahasabhateams.size() - 1);
                                    mAdapter.notifyItemRemoved(mMahasabhateams.size());
                                    mAdapter.notifyDataSetChanged();
                                    mAdapter.setLoaded();
                                }
                            }
                        });

                    } else {
                        Utills.setError(AbtypTeamActivity.this, userObject.getMessage().toString());
                    }
                } else {
                    Utills.setError(AbtypTeamActivity.this, getResources().getString(R.string.server_connection_problem));
                }
            }

            @Override
            public void onFailure(Call<MahasabhateamResponce> call, Throwable t) {
                Log.e("error", t.toString());
                Utills.hideProgressBar();
            }
        });
    }

    @SuppressLint("LongLogTag")
    private void getMahasabhaTeamMore(final int offset) {
        Log.e(TAG, "   offset data " + offset);
        ApiInterface restInterface = ServiceGeneratorr.createService(ApiInterface.class);
        Call<MahasabhateamResponce> call = restInterface.Mahashabha(offset);
        call.enqueue(new Callback<MahasabhateamResponce>() {
            @Override
            public void onResponse(Call<MahasabhateamResponce> call, Response<MahasabhateamResponce> response) {
                Log.e("tutor_request_responce", " " + response.body().getPage_token());
                if (response.body() != null) {
                    MahasabhateamResponce userObject = response.body();
                    boolean successCode = userObject.isStatus();
                    if (successCode) {
                        page_token = userObject.getPage_token();
                        mMahasabhateams.remove(mMahasabhateams.size() - 1);
                        mAdapter.notifyItemRemoved(mMahasabhateams.size());
                        mMahasabhateams.addAll(userObject.getmMahasabhateams());
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setLoaded();
                        setView();
                    } else {
                        Utills.setError(AbtypTeamActivity.this, userObject.getMessage().toString());
                    }
                } else {
                    Utills.setError(AbtypTeamActivity.this, getResources().getString(R.string.server_connection_problem));
                }
            }

            @Override
            public void onFailure(Call<MahasabhateamResponce> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }


    private void setView() {
        if (mMahasabhateams != null && mMahasabhateams.size() == 0) {

            rv_mmbg_team.setVisibility(View.GONE);
            No_Data.setVisibility(View.VISIBLE);

        } else {

            rv_mmbg_team.setVisibility(View.VISIBLE);
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
