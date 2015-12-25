package com.loopeer.android.librarys.dropindicator.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.loopeer.android.librarys.dropindicator.DropIndicator;

import java.util.ArrayList;
import java.util.List;

public class DropIndicatorActivity extends ActionBarActivity {

    private DropIndicator indicator;
    private ViewPager viewPager;

    private List<View> views;
    private static final String TAG = "DropIndicatorActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_pager);

        viewPager = (ViewPager) findViewById(R.id.pager);
        indicator = (DropIndicator) findViewById(R.id.indicator);

        views = new ArrayList<>();
        views.add(getLayoutInflater().inflate(R.layout.layout_pager, null));
        views.add(getLayoutInflater().inflate(R.layout.layout_pager, null));
        views.add(getLayoutInflater().inflate(R.layout.layout_pager, null));
//        views.add(getLayoutInflater().inflate(R.layout.layout_pager, null));

        indicator.setNormalColor(getResources().getColor(R.color.theme_accent));
        indicator.setSelctColor(getResources().getColor(R.color.theme_accent_02));
        indicator.setPagerCount(views.size());
        int indicatorHeight = indicator.getLayoutParams().height;
        int indicatorWidth = indicator.getLayoutParams().width;
        indicator.setMaxCircleRadius(0.15F * indicatorHeight);
        indicator.setMinCircleRadius(0.15F * indicatorHeight);
        indicator.setNormalCircleRadius(0.15F * indicatorHeight);
//        indicator.setWidth(1 / 3 * indicatorHeight);
//        indicator.setMode(DropIndicator.MODE_BEND);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicator.setPositionAndOffset(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new MiniPageAdapter());
    }

    private int dptoPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int)((dp * displayMetrics.density) + 0.5);
    }


    private class MiniPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position), 0);
            return views.get(position);
        }
    }
}
