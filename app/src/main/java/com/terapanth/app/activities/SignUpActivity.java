package com.terapanth.app.activities;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.terapanth.app.R;
import com.terapanth.app.Utills.AppPreference;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignUpActivity";
    private AppPreference mAppPreference;
    private Toolbar mToolbar;
    private TextView toolbar_title, tv_txt_no_data;
    private ImageView img_profile,img_add;

    LinearLayout ll_signup_now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();
        inItView();
        onhandleClick();
    }

    private void init() {
        mAppPreference = new AppPreference(SignUpActivity.this);
    }

    private void inItView() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow_button);
        toolbar_title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Sign Up ");

        ll_signup_now = (LinearLayout) findViewById(R.id.ll_signup_now);
        img_profile = (ImageView)findViewById(R.id.img_profile);
        img_add = (ImageView)findViewById(R.id.img_add);

    }

    private void onhandleClick() {
        ll_signup_now.setOnClickListener(this);
        img_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_signup_now:
                break;
            case R.id.img_add:

                break;
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
