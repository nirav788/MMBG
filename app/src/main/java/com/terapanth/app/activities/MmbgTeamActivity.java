
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

import com.terapanth.app.Adapters.MMBGTeamAdapter;
import com.terapanth.app.Models.MMBGTeamResponce;
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

public class MmbgTeamActivity extends AppCompatActivity {

    private static final String TAG = "MmbgRulesActivity";
    private AppPreference mAppPreference;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<MMBGTeamResponce.MMBGTeam> mMmbgTeams;
    private int page_token = 0;
    private TextView No_Data;
    private Toolbar mToolbar;
    private TextView toolbar_title, tv_txt_no_data;
    private LinearLayout ll_main;
    private RecyclerView rv_team;
    private MMBGTeamAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmbg_team);

        init();
        inItView();
        onhandleClick();
        getAllTeam(page_token);
    }

    private void init() {
        mAppPreference = new AppPreference(MmbgTeamActivity.this);
    }

    private void inItView() {
        mMmbgTeams = new ArrayList<>();
        page_token = 0;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow_button);
        toolbar_title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("MMBG Team");
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        rv_team = (RecyclerView) findViewById(R.id.rv_team);
        tv_txt_no_data = (TextView) findViewById(R.id.tv_txt_no_data);
        rv_team.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(MmbgTeamActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_team.setLayoutManager(mLayoutManager);
        // Disabled nested scrolling since Parent scrollview will scroll the content.
        rv_team.setNestedScrollingEnabled(false);
        rv_team.setAdapter(mAdapter);

    }

    private void onhandleClick() {

    }

    private void getAllTeam(int offset) {

        Utills.showProgressBar(MmbgTeamActivity.this, getResources().getString(R.string.pleasewait));
        ApiInterface restInterface = ServiceGeneratorr.createService(ApiInterface.class);
        Call<MMBGTeamResponce> call = restInterface.MMbgRules(offset);
        call.enqueue(new Callback<MMBGTeamResponce>() {
            @Override
            public void onResponse(Call<MMBGTeamResponce> call, Response<MMBGTeamResponce> response) {
                Utills.hideProgressBar();

                if (response.body() != null) {
                    MMBGTeamResponce userObject = response.body();
                    boolean successCode = userObject.isStatus();
                    if (successCode) {
                        page_token = userObject.getPage_token();
                        mMmbgTeams = new ArrayList<>();
                        mMmbgTeams.addAll(userObject.getMmbgTeams());
                        mAdapter = new MMBGTeamAdapter(mMmbgTeams, MmbgTeamActivity.this, rv_team);
                        rv_team.setAdapter(mAdapter);

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
                        Utills.setError(MmbgTeamActivity.this, userObject.getMessage().toString());
                    }
                } else {
                    Utills.setError(MmbgTeamActivity.this, getResources().getString(R.string.server_connection_problem));
                }
            }

            @Override
            public void onFailure(Call<MMBGTeamResponce> call, Throwable t) {
                Log.e("error", t.toString());
                Utills.hideProgressBar();
            }
        });

    }

    @SuppressLint("LongLogTag")
    private void getFriendListMore(final int offset) {
        Log.e(TAG, "   offset data " + offset);
        ApiInterface restInterface = ServiceGeneratorr.createService(ApiInterface.class);
        Call<MMBGTeamResponce> call = restInterface.MMbgRules(offset);
        call.enqueue(new Callback<MMBGTeamResponce>() {
            @Override
            public void onResponse(Call<MMBGTeamResponce> call, Response<MMBGTeamResponce> response) {
                Log.e("tutor_request_responce", " " + response.body().getPage_token());
                if (response.body() != null) {
                    MMBGTeamResponce userObject = response.body();
                    boolean successCode = userObject.isStatus();
                    if (successCode) {
                        page_token = userObject.getPage_token();
                        mMmbgTeams.remove(mMmbgTeams.size() - 1);
                        mAdapter.notifyItemRemoved(mMmbgTeams.size());
                        mMmbgTeams.addAll(userObject.getMmbgTeams());
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setLoaded();
                        setView();
                    } else {
                        Utills.setError(MmbgTeamActivity.this, userObject.getMessage().toString());
                    }
                } else {
                    Utills.setError(MmbgTeamActivity.this, getResources().getString(R.string.server_connection_problem));
                }
            }

            @Override
            public void onFailure(Call<MMBGTeamResponce> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }


    private void setView() {
        if (mMmbgTeams != null && mMmbgTeams.size() == 0) {
            rv_team.setVisibility(View.GONE);
            No_Data.setVisibility(View.VISIBLE);

        } else {
            rv_team.setVisibility(View.VISIBLE);
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
