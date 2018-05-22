package com.terapanth.app.Adapters;

import android.content.Context;
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
import com.terapanth.app.Models.MMBGTeamResponce;
import com.terapanth.app.Models.ManagementResponce;
import com.terapanth.app.R;
import com.terapanth.app.activities.MmbgManagementActivity;
import com.terapanth.app.interfaces.OnLoadMoreListener;
import com.zach.salman.springylib.springyRecyclerView.SpringyAdapterAnimationType;
import com.zach.salman.springylib.springyRecyclerView.SpringyAdapterAnimator;

import java.util.ArrayList;

/**
 * Created by Developer on 29-03-2018.
 */

public class ManagementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "TPRulesAdapter";
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private SpringyAdapterAnimator mAnimator;

    private ArrayList<ManagementResponce.Management> mManagements;
    Context mContext;

    public class TPRulesAdapterViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_main;
        private TextView tv_username, tv_phno, tv_state, tv_city;
        private ImageView img_user;

        public TPRulesAdapterViewHolder(View view) {
            super(view);
            ll_main = (LinearLayout) view.findViewById(R.id.ll_main);

            tv_username = (TextView) view.findViewById(R.id.tv_username);
            tv_phno = (TextView) view.findViewById(R.id.tv_phno);
            tv_state = (TextView) view.findViewById(R.id.tv_state);
            tv_city = (TextView) view.findViewById(R.id.tv_city);

            img_user = (ImageView) view.findViewById(R.id.img_user);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pb_loading);
        }
    }

    public ManagementAdapter(ArrayList<ManagementResponce.Management> mmbgmanage, Context mContext, RecyclerView recyclerView) {
        this.mManagements = mmbgmanage;
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
        return mManagements.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_management, parent, false);
            mAnimator.onSpringItemCreate(view);
            return new ManagementAdapter.TPRulesAdapterViewHolder(view);

        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new ManagementAdapter.LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ManagementAdapter.TPRulesAdapterViewHolder) {

            final ManagementAdapter.TPRulesAdapterViewHolder ViewHolders = (ManagementAdapter.TPRulesAdapterViewHolder) holder;
            final ManagementResponce.Management mmbgTeam = mManagements.get(position);

            if (mManagements.get(position).getFull_name() != null && mManagements.get(position).getFull_name().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_username.setText(Html.fromHtml(mManagements.get(position).getFull_name(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_username.setText(Html.fromHtml(mManagements.get(position).getFull_name()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_username.setText("");
            }
            if (mManagements.get(position).getContact_number() != null && mManagements.get(position).getContact_number().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_phno.setText(Html.fromHtml(mManagements.get(position).getContact_number(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_phno.setText(Html.fromHtml(mManagements.get(position).getContact_number()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_phno.setText("");
            }
            if (mManagements.get(position).getState() != null && mManagements.get(position).getState().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_state.setText(Html.fromHtml(mManagements.get(position).getState(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_state.setText(Html.fromHtml(mManagements.get(position).getState()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_state.setText("");
            }
            if (mManagements.get(position).getCity() != null && mManagements.get(position).getCity().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_city.setText(Html.fromHtml(mManagements.get(position).getCity(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_city.setText(Html.fromHtml(mManagements.get(position).getCity()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_city.setText("");
            }

            if (mManagements.get(position).getProfile_pic() != null && mManagements.get(position).getProfile_pic().length() > 0) {

                Glide.with(mContext).load(mManagements.get(position).getProfile_pic()).into(ViewHolders.img_user);
            } else {
                ViewHolders.img_user.setBackgroundResource(R.mipmap.ic_launcher);
            }



            ViewHolders.ll_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   /* Intent intent = new Intent(mContext, RulesDetailsActivity.class);
                    intent.putExtra("Details", mmbgTeams.get(position).getRules_body());
                    intent.putExtra("Title", mmbgTeams.get(position).getRules_title());
                    Activity activity = (Activity) mContext;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);*/

                }
            });



        } else if (holder instanceof ManagementAdapter.LoadingViewHolder)

        {
            ManagementAdapter.LoadingViewHolder loadingViewHolder = (ManagementAdapter.LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }


    @Override
    public int getItemCount() {
        return mManagements == null ? 0 : mManagements.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

}