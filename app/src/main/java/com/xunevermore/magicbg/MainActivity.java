package com.xunevermore.magicbg;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xunevermore.BgSourse;
import com.xunevermore.IBgSourseProvider;
import com.xunevermore.drawable.MagicDrawable;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FrameLayout flContainer = (FrameLayout) findViewById(R.id.fl_container);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setPageMargin(100);
        VpAdapter adapter = new VpAdapter();
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);

        viewPager.setAdapter(adapter);
        MagicDrawable drawable = new MagicDrawable();


        final BgSourse[] bgSourse = new BgSourse[colors.length];
        for (int i = 0; i < colors.length; i++) {
            bgSourse[i] = new BgSourse();
            bgSourse[i].setColor(colors[i]);
        }
        drawable.setBgSourseProvider(new IBgSourseProvider() {
            @Override
            public BgSourse getSourse(int position) {
                return bgSourse[position];
            }
        });
        drawable.setUpWithViewPager(viewPager);
        ivBack.setImageDrawable(drawable);

    }

    private int[] colors = new int[]{Color.CYAN, Color.YELLOW, Color.GRAY, Color.GREEN, Color.MAGENTA};

    private class VpAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return colors.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        float radius = 10;
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textView = new TextView(MainActivity.this);
            textView.setTextSize(25);


            RoundRectShape shape = new RoundRectShape(new float[]{radius,radius,radius,radius,radius,radius,radius,radius},null,null);
            ShapeDrawable drawable = new ShapeDrawable();
            drawable.getPaint().setColor(colors[position]);
            drawable.setShape(shape);
            ViewCompat.setElevation(textView,16);

            textView.setBackgroundDrawable(drawable);
            textView.setGravity(Gravity.CENTER);
            textView.setText(String.format(Locale.CHINA,"page:%d", position));
            container.addView(textView);
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }
    }


}
