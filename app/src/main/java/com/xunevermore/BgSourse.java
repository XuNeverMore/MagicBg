package com.xunevermore;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class BgSourse {
    private @ColorInt int  color;

    private Drawable drawable;

    private Bitmap bitmap;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
