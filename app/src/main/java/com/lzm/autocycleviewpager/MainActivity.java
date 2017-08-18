package com.lzm.autocycleviewpager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int[] PAGE_RESOUCE = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};

    private String[] DESCRIPTIONS = {"巩俐不低俗，我就不能低俗",
            " 扑树又回来啦！再唱经典老歌引万人大合唱",
            "  揭秘北京电影如何升级",
            " 乐视网TV版大派送",
            "  热血屌丝的反杀",
    };
    private TextView mTextView;
    private LinearLayout mPagell;
    private int previousPoint;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int currentItem = mViewPager.getCurrentItem();
            mViewPager.setCurrentItem(currentItem + 1);
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
    };
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTextView = (TextView) findViewById(R.id.page_title);
        mPagell = (LinearLayout) findViewById(R.id.page_dot);
        mViewPager.setAdapter(new MyPagerAdapter());
        int offset = Integer.MAX_VALUE / 2 % PAGE_RESOUCE.length;
        mViewPager.setCurrentItem( Integer.MAX_VALUE / 2 - offset);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // 页面滑动的时候会执行 但是程序启动的时候不会执行
            @Override
            public void onPageSelected(int position) {
                int pos = position % PAGE_RESOUCE.length;
                mTextView.setText(DESCRIPTIONS[pos]);
                mPagell.getChildAt(previousPoint).setEnabled(false);
                previousPoint = pos;
                mPagell.getChildAt(pos).setEnabled(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTextView.setText(DESCRIPTIONS[0]);

        for (int i = 0; i < PAGE_RESOUCE.length; i++) {
            ImageView point = new ImageView(MainActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i != 0) {
                int margin = (int) (getResources().getDisplayMetrics().density * 5 + 0.5f);
                params.leftMargin = margin;
                point.setEnabled(false);
            }
            point.setLayoutParams(params);
            point.setBackgroundResource(R.drawable.page_dot);
            mPagell.addView(point);
        }

        mHandler.sendEmptyMessageDelayed(0, 2000);

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.sendEmptyMessageDelayed(0, 2000);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            int pos = position % PAGE_RESOUCE.length;
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setBackgroundResource(PAGE_RESOUCE[pos]);
            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
