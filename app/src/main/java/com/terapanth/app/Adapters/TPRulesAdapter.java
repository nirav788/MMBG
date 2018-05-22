package com.terapanth.app.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.terapanth.app.Models.TPRulesResponce.TPRules;
import com.terapanth.app.R;
import com.terapanth.app.activities.RulesDetailsActivity;
import com.terapanth.app.interfaces.OnLoadMoreListener;
import com.zach.salman.springylib.springyRecyclerView.SpringyAdapterAnimationType;
import com.zach.salman.springylib.springyRecyclerView.SpringyAdapterAnimator;

import java.util.ArrayList;

/**
 * Created by Developer on 29-03-2018.
 */

public class TPRulesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "TPRulesAdapter";
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private SpringyAdapterAnimator mAnimator;

    private ArrayList<TPRules> mTPRules;
    Context mContext;

    public class TPRulesAdapterViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_main;
        private TextView tv_title, tv_details;

        public TPRulesAdapterViewHolder(View view) {
            super(view);
            ll_main = (LinearLayout) view.findViewById(R.id.ll_main);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_details = (TextView) view.findViewById(R.id.tv_details);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pb_loading);
        }
    }

    public TPRulesAdapter(ArrayList<TPRules> TPRules, Context mContext, RecyclerView recyclerView) {
        this.mTPRules = TPRules;
        this.mContext = mContext;
        mAnimator = new SpringyAdapterAnimator(recyclerView);
        mAnimator.setSpringAnimationType(SpringyAdapterAnimationType.SLIDE_FROM_BOTTOM);
        mAnimator.addConfig(85, 15);

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return mTPRules.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tprules_, parent, false);
            mAnimator.onSpringItemCreate(view);
            return new TPRulesAdapter.TPRulesAdapterViewHolder(view);

        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new TPRulesAdapter.LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TPRulesAdapter.TPRulesAdapterViewHolder) {

            final TPRulesAdapter.TPRulesAdapterViewHolder ViewHolders = (TPRulesAdapter.TPRulesAdapterViewHolder) holder;
            final TPRules TPRules = mTPRules.get(position);

            if (mTPRules.get(position).getRules_title().length() > 0) {

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_title.setText(Html.fromHtml(mTPRules.get(position).getRules_title(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_title.setText(Html.fromHtml(mTPRules.get(position).getRules_title()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_title.setText("");
            }
            if (mTPRules.get(position).getRules_name().length() > 0) {

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_details.setText(Html.fromHtml(mTPRules.get(position).getRules_body(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_details.setText(Html.fromHtml(mTPRules.get(position).getRules_body()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_details.setText("");
            }

            ViewHolders.ll_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext, RulesDetailsActivity.class);
                    intent.putExtra("Details", mTPRules.get(position).getRules_body());
                    intent.putExtra("Title", mTPRules.get(position).getRules_title());
                    Activity activity = (Activity) mContext;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }
            });


         /*   if (TPRules.getQuestionsPhoto1Maxi().toString().length() > 0) {

                Glide.with(mContext).load(TPRules.getQuestionsPhoto1Maxi()).placeholder(R.drawable.imgprofile)
                        .error(R.drawable.imgprofile).into(ViewHolders.iv_mainImage);
            } else {
                ViewHolders.iv_mainImage.setBackgroundResource(R.drawable.imgprofile);
            }
*/


        } else if (holder instanceof TPRulesAdapter.LoadingViewHolder)

        {
            TPRulesAdapter.LoadingViewHolder loadingViewHolder = (TPRulesAdapter.LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }


    @Override
    public int getItemCount() {
        return mTPRules == null ? 0 : mTPRules.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

}