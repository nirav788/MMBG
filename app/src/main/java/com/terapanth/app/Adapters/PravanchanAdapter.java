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
import com.terapanth.app.Models.NewsResponce;
import com.terapanth.app.Models.PravachanResponce;
import com.terapanth.app.R;
import com.terapanth.app.activities.NewsDetailActivity;
import com.terapanth.app.activities.PravachanDetailActivity;
import com.terapanth.app.interfaces.OnLoadMoreListener;
import com.zach.salman.springylib.springyRecyclerView.SpringyAdapterAnimationType;
import com.zach.salman.springylib.springyRecyclerView.SpringyAdapterAnimator;

import java.util.ArrayList;

/**
 * Created by Developer on 29-03-2018.
 */

public class PravanchanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "TPRulesAdapter";
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private SpringyAdapterAnimator mAnimator;

    private ArrayList<PravachanResponce.Pravachan> mPravachans;
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

    public PravanchanAdapter(ArrayList<PravachanResponce.Pravachan> pravachans, Context mContext, RecyclerView recyclerView) {
        this.mPravachans = pravachans;
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
        return mPravachans.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pravachan, parent, false);
            mAnimator.onSpringItemCreate(view);
            return new PravanchanAdapter.TPRulesAdapterViewHolder(view);

        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new PravanchanAdapter.LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PravanchanAdapter.TPRulesAdapterViewHolder) {

            final PravanchanAdapter.TPRulesAdapterViewHolder ViewHolders = (PravanchanAdapter.TPRulesAdapterViewHolder) holder;
            final PravachanResponce.Pravachan pravachan = mPravachans.get(position);


            if (mPravachans.get(position).getPravachan_title() != null && mPravachans.get(position).getPravachan_title().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_title.setText(Html.fromHtml(mPravachans.get(position).getPravachan_title(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_title.setText(Html.fromHtml(mPravachans.get(position).getPravachan_title()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_title.setText("");
            }
            if (mPravachans.get(position).getPravachan_body() != null && mPravachans.get(position).getPravachan_body().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_description.setText(Html.fromHtml(mPravachans.get(position).getPravachan_body(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_description.setText(Html.fromHtml(mPravachans.get(position).getPravachan_body()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_description.setText("");
            }
            if (mPravachans.get(position).getPravachan_image() != null && mPravachans.get(position).getPravachan_image().length() > 0) {

                Glide.with(mContext).load(mPravachans.get(position).getPravachan_image()).into(ViewHolders.img_pra);
            } else {
                ViewHolders.img_pra.setBackgroundResource(R.mipmap.ic_launcher);
            }

            ViewHolders.ll_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext, PravachanDetailActivity.class);
                    intent.putExtra("title", mPravachans.get(position).getPravachan_title());
                    intent.putExtra("details", mPravachans.get(position).getPravachan_body());
                    intent.putExtra("image", mPravachans.get(position).getPravachan_image());
                    Activity activity = (Activity) mContext;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }
            });



        } else if (holder instanceof PravanchanAdapter.LoadingViewHolder)

        {
            PravanchanAdapter.LoadingViewHolder loadingViewHolder = (PravanchanAdapter.LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }


    @Override
    public int getItemCount() {
        return mPravachans == null ? 0 : mPravachans.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

}