package com.terapanth.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.terapanth.app.Adapters.MainCategoryAdapter;
import com.terapanth.app.Models.MainCategory;
import com.terapanth.app.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    RecyclerView rv_maincategory;
    LinearLayout ll_main;
    LinearLayout MLinearLayout;
    private Toolbar mToolbar;
    private TextView toolbar_title;

    private ArrayList<MainCategory> mainCategories = new ArrayList<>();
    private MainCategoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] title = {
                "About",
                "Vsion Mission",
                "History",
                "Rules",
                "MMBG Team",
                "MMBG Management",
                "MMBG Mahashabha Team",
                "Abtyp Team",
                "News",
                "Pravachan",
                "Latest News",
                "Prekshadhyan",
                "Login",
        };
        mainCategories = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            MainCategory SearchTutor = new MainCategory();
            SearchTutor.setCategoryName(title[i]);
            mainCategories.add(SearchTutor);
        }

        inItView();


    }

    private void inItView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Terapanth MMBG");
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        rv_maincategory = (RecyclerView) findViewById(R.id.rv_maincategory);

        setAdapter();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void setAdapter() {
        mAdapter = new MainCategoryAdapter(MainActivity.this, mainCategories, rv_maincategory);
        rv_maincategory.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        rv_maincategory.setLayoutManager(mLayoutManager);
        rv_maincategory.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }
}
