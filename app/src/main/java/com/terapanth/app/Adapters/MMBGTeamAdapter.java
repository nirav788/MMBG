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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.terapanth.app.Models.MMBGTeamResponce;
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

public class MMBGTeamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "TPRulesAdapter";
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private SpringyAdapterAnimator mAnimator;

    private ArrayList<MMBGTeamResponce.MMBGTeam> mmbgTeams;
    Context mContext;
    int[] drawables={R.drawable.gradiyantone,R.drawable.gradiyantwo,R.drawable.gradiyanthree,R.drawable.gradiyantforth,R.drawable.gradiyantfifth};

    public class TPRulesAdapterViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_main;
        private TextView tv_username, tv_position, tv_contactno, tv_bdate, tv_state, tv_city;

        public TPRulesAdapterViewHolder(View view) {
            super(view);
            ll_main = (LinearLayout) view.findViewById(R.id.ll_main);
            tv_username = (TextView) view.findViewById(R.id.tv_username);
            tv_position = (TextView) view.findViewById(R.id.tv_position);
            tv_contactno = (TextView) view.findViewById(R.id.tv_contactno);
            tv_bdate = (TextView) view.findViewById(R.id.tv_bdate);
            tv_state = (TextView) view.findViewById(R.id.tv_state);
            tv_city = (TextView) view.findViewById(R.id.tv_city);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pb_loading);
        }
    }

    public MMBGTeamAdapter(ArrayList<MMBGTeamResponce.MMBGTeam> mmbgTeam, Context mContext, RecyclerView recyclerView) {
        this.mmbgTeams = mmbgTeam;
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
        return mmbgTeams.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_mmbg_team, parent, false);
            mAnimator.onSpringItemCreate(view);
            return new MMBGTeamAdapter.TPRulesAdapterViewHolder(view);

        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new MMBGTeamAdapter.LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MMBGTeamAdapter.TPRulesAdapterViewHolder) {

            final MMBGTeamAdapter.TPRulesAdapterViewHolder ViewHolders = (MMBGTeamAdapter.TPRulesAdapterViewHolder) holder;
            final MMBGTeamResponce.MMBGTeam mmbgTeam = mmbgTeams.get(position);


            if(position>=5){
                int count=position%5;
                ViewHolders.ll_main.setBackgroundResource(drawables[count]);

            }else {
                ViewHolders.ll_main.setBackgroundResource((drawables[position]));
            }

            if (mmbgTeams.get(position).getFull_name() != null && mmbgTeams.get(position).getFull_name().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_username.setText(Html.fromHtml(mmbgTeams.get(position).getFull_name(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_username.setText(Html.fromHtml(mmbgTeams.get(position).getFull_name()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_username.setText("");
            }
            if (mmbgTeams.get(position).getDesignation() != null && mmbgTeams.get(position).getDesignation().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_position.setText(Html.fromHtml(mmbgTeams.get(position).getDesignation(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_position.setText(Html.fromHtml(mmbgTeams.get(position).getDesignation()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_position.setText("");
            }
            if (mmbgTeams.get(position).getContact_number() != null && mmbgTeams.get(position).getContact_number().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_contactno.setText(Html.fromHtml(mmbgTeams.get(position).getContact_number(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_contactno.setText(Html.fromHtml(mmbgTeams.get(position).getContact_number()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_contactno.setText("");
            }
            if (mmbgTeams.get(position).getDob() != null && mmbgTeams.get(position).getDob().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_bdate.setText(Html.fromHtml(mmbgTeams.get(position).getDob(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_bdate.setText(Html.fromHtml(mmbgTeams.get(position).getDob()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_bdate.setText("");
            }
            if (mmbgTeams.get(position).getState() != null && mmbgTeams.get(position).getState().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_state.setText(Html.fromHtml(mmbgTeams.get(position).getState(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_state.setText(Html.fromHtml(mmbgTeams.get(position).getState()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_state.setText("");
            }
            if (mmbgTeams.get(position).getCity() != null && mmbgTeams.get(position).getCity().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_city.setText(Html.fromHtml(mmbgTeams.get(position).getCity(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_city.setText(Html.fromHtml(mmbgTeams.get(position).getCity()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_city.setText("");
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


         /*   if (TPRules.getQuestionsPhoto1Maxi().toString().length() > 0) {

                Glide.with(mContext).load(TPRules.getQuestionsPhoto1Maxi()).placeholder(R.drawable.imgprofile)
                        .error(R.drawable.imgprofile).into(ViewHolders.iv_mainImage);
            } else {
                ViewHolders.iv_mainImage.setBackgroundResource(R.drawable.imgprofile);
            }
*/


        } else if (holder instanceof MMBGTeamAdapter.LoadingViewHolder)

        {
            MMBGTeamAdapter.LoadingViewHolder loadingViewHolder = (MMBGTeamAdapter.LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }


    @Override
    public int getItemCount() {
        return mmbgTeams == null ? 0 : mmbgTeams.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

}