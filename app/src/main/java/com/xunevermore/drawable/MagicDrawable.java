package com.xunevermore.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.xunevermore.BgSourse;
import com.xunevermore.IBgSourseProvider;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class MagicDrawable extends Drawable implements ViewPager.OnPageChangeListener {


    private IBgSourseProvider bgSourseProvider;
    private float holeHeight = 80f;

    private Path mPath = new Path();
    private int mPosition;
    private float mOffset;
    private int nextPosition;
    private int count;
    private ViewPager viewPager;

    float range = 0.001f;


    public MagicDrawable() {
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public void setUpWithViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        mPosition = viewPager.getCurrentItem();
        count = viewPager.getAdapter().getCount();
        viewPager.addOnPageChangeListener(this);
    }

    public IBgSourseProvider getBgSourseProvider() {
        return bgSourseProvider;
    }

    public void setBgSourseProvider(IBgSourseProvider bgSourseProvider) {
        this.bgSourseProvider = bgSourseProvider;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        mPath.reset();
        Rect bounds = getBounds();

        mPath.moveTo(0, bounds.height());
        mPath.rQuadTo(bounds.width() / 2, -holeHeight, bounds.width(), 0);
        mPath.lineTo(bounds.width(), 0);
        mPath.lineTo(0, 0);
        mPath.close();
        canvas.save();
        canvas.clipPath(mPath);

        paint.setColor(bgSourseProvider.getSourse(mPosition).getColor());
        canvas.drawPath(mPath, paint);

        float cx = 0;
        float cy = 0;

        boolean isRight = true;
        if (nextPosition < mPosition) {//从左边出
            isRight = false;
            if (mPosition > 0) {

                int color = bgSourseProvider.getSourse(mPosition - 1).getColor();
                paint.setColor(color);
            }
            mOffset = 1 - mOffset;
            cy = bounds.height() / 2;
        } else {//从右边出
            cy = bounds.height() / 2;
            cx = bounds.width();

            if (mPosition < count - 1) {
                paint.setColor(bgSourseProvider.getSourse(mPosition + 1).getColor());
            }
        }

        if (mOffset > range) {
            canvas.drawCircle(cx, cy, mOffset * (bounds.width() * 1.5f),
                    paint);
        }

        canvas.restore();

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    private static final String TAG = "MagicDrawable";

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mOffset = positionOffset;
        Log.i(TAG, "positionOffset: " + positionOffset);
        Log.i(TAG, "next position: " + position);
        nextPosition = position;

        if (position < mPosition) {//向右滑 positionOffset 1--->0
            if (positionOffset < range) {
                mPosition = viewPager.getCurrentItem();
            }
        } else {//向左滑 positionOffset 0--->1
            if (positionOffset > 1 - range) {
                mPosition = viewPager.getCurrentItem();
            }
        }

        invalidateSelf();
    }

    @Override
    public void onPageSelected(int position) {

        Log.i(TAG, "onPageSelected:-----------> " + position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
