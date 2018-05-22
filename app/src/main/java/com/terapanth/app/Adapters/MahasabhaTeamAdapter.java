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
import com.terapanth.app.Models.MahasabhateamResponce;
import com.terapanth.app.R;
import com.terapanth.app.interfaces.OnLoadMoreListener;
import com.zach.salman.springylib.springyRecyclerView.SpringyAdapterAnimationType;
import com.zach.salman.springylib.springyRecyclerView.SpringyAdapterAnimator;

import java.util.ArrayList;

/**
 * Created by Developer on 29-03-2018.
 */

public class MahasabhaTeamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "TPRulesAdapter";
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private SpringyAdapterAnimator mAnimator;

    private ArrayList<MahasabhateamResponce.Mahasabhateam> mMahasabhateams;
    Context mContext;

    public class TPRulesAdapterViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_main;
        private TextView tv_username, tv_designation, tv_phno, tv_state, tv_city,tv_dob;
        private ImageView img_user;

        public TPRulesAdapterViewHolder(View view) {
            super(view);
            ll_main = (LinearLayout) view.findViewById(R.id.ll_main);
            tv_username = (TextView) view.findViewById(R.id.tv_username);
            tv_designation = (TextView) view.findViewById(R.id.tv_designation);
            tv_phno = (TextView) view.findViewById(R.id.tv_phno);
            tv_state = (TextView) view.findViewById(R.id.tv_state);
            tv_city = (TextView) view.findViewById(R.id.tv_city);
            tv_dob = (TextView) view.findViewById(R.id.tv_dob);
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

    public MahasabhaTeamAdapter(ArrayList<MahasabhateamResponce.Mahasabhateam> Mahasabhateam, Context mContext, RecyclerView recyclerView) {
        this.mMahasabhateams = Mahasabhateam;
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
        return mMahasabhateams.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_mahashabha_team, parent, false);
            mAnimator.onSpringItemCreate(view);
            return new MahasabhaTeamAdapter.TPRulesAdapterViewHolder(view);

        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new MahasabhaTeamAdapter.LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MahasabhaTeamAdapter.TPRulesAdapterViewHolder) {

            final MahasabhaTeamAdapter.TPRulesAdapterViewHolder ViewHolders = (MahasabhaTeamAdapter.TPRulesAdapterViewHolder) holder;
            final MahasabhateamResponce.Mahasabhateam mmbgTeam = mMahasabhateams.get(position);

            if (mMahasabhateams.get(position).getFull_name() != null && mMahasabhateams.get(position).getFull_name().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_username.setText(Html.fromHtml(mMahasabhateams.get(position).getFull_name(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_username.setText(Html.fromHtml(mMahasabhateams.get(position).getFull_name()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_username.setText("");
            }
            if (mMahasabhateams.get(position).getDesignation() != null && mMahasabhateams.get(position).getDesignation().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_designation.setText(Html.fromHtml(mMahasabhateams.get(position).getDesignation(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_designation.setText(Html.fromHtml(mMahasabhateams.get(position).getDesignation()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_designation.setText("");
            }
            if (mMahasabhateams.get(position).getContact_number() != null && mMahasabhateams.get(position).getContact_number().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_phno.setText(Html.fromHtml(mMahasabhateams.get(position).getContact_number(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_phno.setText(Html.fromHtml(mMahasabhateams.get(position).getContact_number()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_phno.setText("");
            }
            if (mMahasabhateams.get(position).getState() != null && mMahasabhateams.get(position).getState().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_state.setText(Html.fromHtml(mMahasabhateams.get(position).getState(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_state.setText(Html.fromHtml(mMahasabhateams.get(position).getState()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_state.setText("");
            }
            if (mMahasabhateams.get(position).getCity() != null && mMahasabhateams.get(position).getCity().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_city.setText(Html.fromHtml(mMahasabhateams.get(position).getCity(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_city.setText(Html.fromHtml(mMahasabhateams.get(position).getCity()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_city.setText("");
            }
            if (mMahasabhateams.get(position).getDob() != null && mMahasabhateams.get(position).getDob().length() > 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ViewHolders.tv_dob.setText(Html.fromHtml(mMahasabhateams.get(position).getDob(), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                } else {
                    ViewHolders.tv_dob.setText(Html.fromHtml(mMahasabhateams.get(position).getDob()), TextView.BufferType.SPANNABLE);
                }
            } else {
                ViewHolders.tv_dob.setText("");
            }

            if (mMahasabhateams.get(position).getCity() != null && mMahasabhateams.get(position).getCity().length() > 0) {

                Glide.with(mContext).load(mMahasabhateams.get(position).getProfile_pic()).into(ViewHolders.img_user);
            }else {
                ViewHolders.img_user.setBackgroundResource(R.mipmap.ic_launcher);
            }

            /*   if (TPRules.getQuestionsPhoto1Maxi().toString().length() > 0) {

                Glide.with(mContext).load(TPRules.getQuestionsPhoto1Maxi()).placeholder(R.drawable.imgprofile)
                        .error(R.drawable.imgprofile).into(ViewHolders.iv_mainImage);
            } else {
                ViewHolders.iv_mainImage.setBackgroundResource(R.drawable.imgprofile);
            }
*/


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


        } else if (holder instanceof MahasabhaTeamAdapter.LoadingViewHolder)

        {
            MahasabhaTeamAdapter.LoadingViewHolder loadingViewHolder = (MahasabhaTeamAdapter.LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }


    @Override
    public int getItemCount() {
        return mMahasabhateams == null ? 0 : mMahasabhateams.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

}