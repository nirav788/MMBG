package com.terapanth.app.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.terapanth.app.Models.MainCategory;
import com.terapanth.app.R;
import com.terapanth.app.activities.AboutTerapantActivity;
import com.terapanth.app.activities.AbtypTeamActivity;
import com.terapanth.app.activities.LetestNewsActivity;
import com.terapanth.app.activities.LoginActivity;
import com.terapanth.app.activities.MahasabhaTeamActivity;
import com.terapanth.app.activities.MmbgHistoryActivity;
import com.terapanth.app.activities.MmbgManagementActivity;
import com.terapanth.app.activities.MmbgRulesActivity;
import com.terapanth.app.activities.MmbgTeamActivity;
import com.terapanth.app.activities.NewsActivity;
import com.terapanth.app.activities.PravachanActivity;
import com.terapanth.app.activities.PrekshadhyanListActivity;
import com.terapanth.app.activities.VisionMissionActivity;
import com.zach.salman.springylib.SpringAnimationType;
import com.zach.salman.springylib.SpringyAnimator;
import com.zach.salman.springylib.springyRecyclerView.SpringyAdapterAnimationType;
import com.zach.salman.springylib.springyRecyclerView.SpringyAdapterAnimator;

import java.util.ArrayList;

/**
 * Created by Developer on 05-03-2018.
 */

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.MyViewHolder> {

    public static final String TAG = "MainCategoryAdapter";
    private final LayoutInflater inflater;
    private final Context mContext;
    ArrayList<MainCategory> mMainCategories;
    private SpringyAdapterAnimator mAnimator;

    public MainCategoryAdapter(Context mContext, ArrayList<MainCategory> mainCategories, RecyclerView recyclerView) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mMainCategories = mainCategories;
        mAnimator = new SpringyAdapterAnimator(recyclerView);
        mAnimator.setSpringAnimationType(SpringyAdapterAnimationType.SLIDE_FROM_BOTTOM);
        mAnimator.addConfig(85, 15);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_main_category, parent, false);
        MyViewHolder holer = new MyViewHolder(view);
        mAnimator.onSpringItemCreate(view);
        return holer;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final MainCategory category = mMainCategories.get(position);

        holder.tv_name.setText(category.getCategoryName());

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();

        String names = String.valueOf(category.getCategoryName().charAt(0));

        TextDrawable drawable2 = TextDrawable.builder()
                .buildRound(names, color);

        holder.images.setImageDrawable(drawable2);

        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (position == 0) {
                    Intent intent = new Intent(mContext, AboutTerapantActivity.class);
                    intent.putExtra("About", "About");
                    Activity activity = (Activity) mContext;
                    activity.startActivity(intent);

                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                if (position == 1) {
                    Intent intent = new Intent(mContext, VisionMissionActivity.class);
                    intent.putExtra("Vision", "Vision & Mission ");
                    Activity activity = (Activity) mContext;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                if (position == 2) {
                    Intent intent = new Intent(mContext, MmbgHistoryActivity.class);
                    intent.putExtra("History", "MMBG History");
                    Activity activity = (Activity) mContext;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                } if (position == 3) {
                    Intent intent = new Intent(mContext, MmbgRulesActivity.class);
                    Activity activity = (Activity) mContext;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }if (position == 4) {
                    Intent intent = new Intent(mContext, MmbgTeamActivity.class);
                    Activity activity = (Activity) mContext;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }if (position == 5) {
                    Intent intent = new Intent(mContext, MmbgManagementActivity.class);
                    Activity activity = (Activity) mContext;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }if (position == 6) {
                    Intent intent = new Intent(mContext, MahasabhaTeamActivity.class);
                    Activity activity = (Activity) mContext;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                if (position == 7) {
                    Intent intent = new Intent(mContext, AbtypTeamActivity.class);
                    Activity activity = (Activity) mContext;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }if (position == 8) {
                    Intent intent = new Intent(mContext, NewsActivity.class);
                    Activity activity = (Activity) mContext;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                if (position == 9) {
                    Intent intent = new Intent(mContext, PravachanActivity.class);
                    Activity activity = (Activity) mContext;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                if (position == 10) {
                    Intent intent = new Intent(mContext, LetestNewsActivity.class);
                    Activity activity = (Activity) mContext;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                if (position == 11) {
                    Intent intent = new Intent(mContext, PrekshadhyanListActivity.class);
                    Activity activity = (Activity) mContext;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }if (position == 12) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    Activity activity = (Activity) mContext;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mMainCategories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_name;
        private final LinearLayout ll_main;
        private final ImageView images;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_category_name);
            ll_main = (LinearLayout) itemView.findViewById(R.id.ll_main);
            images = (ImageView) itemView.findViewById(R.id.noteImages);

        }
    }

    private void animateView(View itemView) {

        SpringyAnimator springHelper = new SpringyAnimator(SpringAnimationType.SCALEXY, 100, 4, 0, 1);
        springHelper.startSpring(itemView);
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
