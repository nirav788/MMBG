package com.terapanth.app.activities;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hao.bannerlib.BannerViewPagerView;
import com.hao.bannerlib.control.BannerViewPager;
import com.hao.bannerlib.holder.BannerHolderCreator;
import com.hao.bannerlib.holder.BannerViewHolder;
import com.terapanth.app.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {


    public static final int[] RES_ARRAY = new int[]{R.drawable.gradiyantone, R.drawable.gradiyantwo, R.drawable.gradiyanthree, R.drawable.gradiyantforth, R.drawable.gradiyantfifth};
    private static final String TAG = "GalleryActivity";
    private BannerViewPagerView bannerGalleryRadius;//圆角gallery

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        initView();


    }

    private void initView() {

        bannerGalleryRadius = (BannerViewPagerView)findViewById(R.id.banner_gallery_radius);

        bannerGalleryRadius.setBannerPageClickListener(new BannerViewPagerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Toast.makeText(GalleryActivity.this, "点击:" + position, Toast.LENGTH_LONG).show();
            }
        });

        bannerGalleryRadius.addPageChangeLisnter(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG, "----->addPageChangeLisnter:" + position + "positionOffset:" + positionOffset + "positionOffsetPixels:" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG, "addPageChangeLisnter:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        List<Integer> bannerList = new ArrayList<>();
        for (int i = 0; i < RES_ARRAY.length; i++) {
            bannerList.add(RES_ARRAY[i]);
        }
        bannerGalleryRadius.setIndicatorVisible(true);

        bannerGalleryRadius.setPages(bannerList, new BannerHolderCreator<ViewHolderRadius>() {
            @Override
            public ViewHolderRadius createViewHolder() {
                return new ViewHolderRadius();
            }
        });
    }

    public static class ViewHolderRadius implements BannerViewHolder<Integer> {
        private ImageView mImageView;
        private TextView tv_page;

        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item_radius, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            tv_page = (TextView) view.findViewById(R.id.tv_page);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
            tv_page.setText(data.toString());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        bannerGalleryRadius.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        bannerGalleryRadius.start();
    }
}
