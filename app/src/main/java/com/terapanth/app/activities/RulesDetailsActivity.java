
package com.terapanth.app.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.terapanth.app.R;
import com.terapanth.app.Utills.AppPreference;

public class RulesDetailsActivity extends AppCompatActivity {

    AppPreference mAppPreference;
    TextView tv_title_rule, tv_details_rule;
    LinearLayout ll_main;
    private static final String TAG = "RulesDetailsActivity";
    private Toolbar mToolbar;
    private TextView toolbar_title;
    String Title, Details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_details);

        Intent intent = getIntent();
        if (intent.hasExtra("Details")) {

            Details = intent.getStringExtra("Details");
            Title = intent.getStringExtra("Title");
        } else {

            Title = "";
            Details = "";
        }

        init();
        inItView();
        onhandleClick();
        setViewData();
    }


    private void init() {

        mAppPreference = new AppPreference(RulesDetailsActivity.this);
    }

    private void inItView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow_button);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("MMBG RULES");

        tv_title_rule = (TextView) findViewById(R.id.tv_title_rule);
        tv_details_rule = (TextView) findViewById(R.id.tv_details_rule);
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
    }

    private void onhandleClick() {


    }

    private void setViewData() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv_title_rule.setText(Html.fromHtml(Title, Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
            tv_details_rule.setText(Html.fromHtml(Details, Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
        } else {
            tv_title_rule.setText(Html.fromHtml(Title), TextView.BufferType.SPANNABLE);
            tv_details_rule.setText(Html.fromHtml(Details), TextView.BufferType.SPANNABLE);
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
