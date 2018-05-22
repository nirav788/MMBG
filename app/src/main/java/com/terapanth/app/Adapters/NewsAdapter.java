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
import com.terapanth.app.R;
import com.terapanth.app.activities.NewsDetailActivity;
import com.terapanth.app.interfaces.OnLoadMoreListener;
import com.zach.salman.springylib.springyRecyclerView.SpringyAdapterAnimationType;
import com.zach.salman.springylib.springyRecyclerView.SpringyAdapterAnimator;

import java.util.ArrayList;

/**
 * Created by Developer on 29-03-2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "TPRulesAdapter";
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private SpringyAdapterAnimator mAnimator;

    private ArrayList<NewsResponce.News> mNews;
    Context mContext;

    public class TPRulesAdapterViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_main;
        private TextView tv_news;
        private ImageView img_news;

        public TPRulesAdapterViewHolder(View view) {
            super(view);
            ll_main = (LinearLayout) view.findViewById(R.id.ll_main);
            tv_news = (TextView) view.findViewById(R.id.tv_news);
            img_news = (ImageView) view.findViewById(R.id.img_news);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pb_loading);
        }
    }

    public NewsAdapter(ArrayList<NewsResponce.News> news, Context mContext, RecyclerView recyclerView) {
        this.mNews = news;
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
        return mNews.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news, parent, false);
            mAnimator.onSpringItemCreate(view);
            return new NewsAdapter.TPRulesAdapterViewHolder(view);

        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new NewsAdapter.LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NewsAdapter.TPRulesAdapterViewHolder) {

            final NewsAdapter.TPRulesAdapterViewHolder ViewHolders = (NewsAdapter.TPRulesAdapterViewHolder) holder;
            final NewsResponce.News news = mNews.get(position);


            if (mNews.get(position).getNews_body() != null && mNews.get(position).getNews_body().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_news.setText(Html.fromHtml(mNews.get(position).getNews_body(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_news.setText(Html.fromHtml(mNews.get(position).getNews_body()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_news.setText("");
            }
            if (mNews.get(position).getNews_image() != null && mNews.get(position).getNews_image().length() > 0) {

                Glide.with(mContext).load(mNews.get(position).getNews_image()).placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher).into(ViewHolders.img_news);
            } else {
                ViewHolders.img_news.setBackgroundResource(R.mipmap.ic_launcher);
            }

            ViewHolders.ll_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext, NewsDetailActivity.class);
                    intent.putExtra("News", mNews.get(position).getNews_body());
                    intent.putExtra("newsdetail", mNews.get(position).getNews_image());
                    Activity activity = (Activity) mContext;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }
            });



        } else if (holder instanceof NewsAdapter.LoadingViewHolder)

        {
            NewsAdapter.LoadingViewHolder loadingViewHolder = (NewsAdapter.LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }


    @Override
    public int getItemCount() {
        return mNews == null ? 0 : mNews.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

}