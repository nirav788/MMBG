package com.terapanth.app.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.terapanth.app.Models.PravachanResponce;
import com.terapanth.app.Models.PrekshadhyanResponce;
import com.terapanth.app.R;
import com.terapanth.app.activities.PravachanDetailActivity;
import com.terapanth.app.activities.PrekshadhyanDetailActivity;
import com.terapanth.app.interfaces.OnLoadMoreListener;
import com.zach.salman.springylib.springyRecyclerView.SpringyAdapterAnimationType;
import com.zach.salman.springylib.springyRecyclerView.SpringyAdapterAnimator;

import java.util.ArrayList;

/**
 * Created by Developer on 29-03-2018.
 */

public class PrekshadhyanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "TPRulesAdapter";
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private SpringyAdapterAnimator mAnimator;

    private ArrayList<PrekshadhyanResponce.Prekshadhyan> mPrekshadhyans;
    Context mContext;

    public class TPRulesAdapterViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_main;
        private TextView tv_title,tv_description;
        private ImageView img_pra;

        public TPRulesAdapterViewHolder(View view) {
            super(view);
            ll_main = (LinearLayout) view.findViewById(R.id.ll_main);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_description = (TextView) view.findViewById(R.id.tv_description);
            img_pra = (ImageView) view.findViewById(R.id.img_pra);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pb_loading);
        }
    }

    public PrekshadhyanAdapter(ArrayList<PrekshadhyanResponce.Prekshadhyan> pravachans, Context mContext, RecyclerView recyclerView) {
        this.mPrekshadhyans = pravachans;
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
        return mPrekshadhyans.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_prekshadhyan, parent, false);
            mAnimator.onSpringItemCreate(view);
            return new PrekshadhyanAdapter.TPRulesAdapterViewHolder(view);

        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new PrekshadhyanAdapter.LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PrekshadhyanAdapter.TPRulesAdapterViewHolder) {

            final PrekshadhyanAdapter.TPRulesAdapterViewHolder ViewHolders = (PrekshadhyanAdapter.TPRulesAdapterViewHolder) holder;
            final PrekshadhyanResponce.Prekshadhyan Prekshadhyan= mPrekshadhyans.get(position);


            if (mPrekshadhyans.get(position).getPrekshadyan_title() != null && mPrekshadhyans.get(position).getPrekshadyan_title().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_title.setText(Html.fromHtml(mPrekshadhyans.get(position).getPrekshadyan_title(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_title.setText(Html.fromHtml(mPrekshadhyans.get(position).getPrekshadyan_title()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_title.setText("");
            }
            if (mPrekshadhyans.get(position).getPrekshadyan_body() != null && mPrekshadhyans.get(position).getPrekshadyan_body().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_description.setText(Html.fromHtml(mPrekshadhyans.get(position).getPrekshadyan_body(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_description.setText(Html.fromHtml(mPrekshadhyans.get(position).getPrekshadyan_body()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_description.setText("");
            }
            if (mPrekshadhyans.get(position).getPrekshadyan_image() != null && mPrekshadhyans.get(position).getPrekshadyan_image().length() > 0) {

                Glide.with(mContext).load(mPrekshadhyans.get(position).getPrekshadyan_image()).into(ViewHolders.img_pra);
            } else {
                ViewHolders.img_pra.setBackgroundResource(R.mipmap.ic_launcher);
            }

            ViewHolders.ll_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext, PrekshadhyanDetailActivity.class);
                    intent.putExtra("title", mPrekshadhyans.get(position).getPrekshadyan_title());
                    intent.putExtra("details", mPrekshadhyans.get(position).getPrekshadyan_body());
                    intent.putExtra("image", mPrekshadhyans.get(position).getPrekshadyan_image());
                    Activity activity = (Activity) mContext;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }
            });



        } else if (holder instanceof PrekshadhyanAdapter.LoadingViewHolder)

        {
            PrekshadhyanAdapter.LoadingViewHolder loadingViewHolder = (PrekshadhyanAdapter.LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }


    @Override
    public int getItemCount() {
        return mPrekshadhyans == null ? 0 : mPrekshadhyans.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

}