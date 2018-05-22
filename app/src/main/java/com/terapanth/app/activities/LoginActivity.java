package com.terapanth.app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.terapanth.app.R;
import com.terapanth.app.Utills.AppPreference;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "LoginActivity";
    private AppPreference mAppPreference;
    private Toolbar mToolbar;
    private TextView toolbar_title, tv_txt_no_data, tv_forgot;
    private LinearLayout ll_fb, ll_signin, ll_signup;
    private EditText edt_username, edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        inItView();
        onhandleClick();


    }


    private void init() {
        mAppPreference = new AppPreference(LoginActivity.this);
    }

    private void inItView() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow_button);
        toolbar_title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Login");

        ll_fb = (LinearLayout) findViewById(R.id.ll_fb);
        ll_signin = (LinearLayout) findViewById(R.id.ll_signin);
        ll_signup = (LinearLayout) findViewById(R.id.ll_signup);

        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);

        tv_forgot = (TextView) findViewById(R.id.tv_forgot);

    }

    private void onhandleClick() {
        ll_fb.setOnClickListener(this);
        tv_forgot.setOnClickListener(this);
        ll_signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_fb:
                break;
            case R.id.tv_forgot:
                break;
            case R.id.ll_signup:
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
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
