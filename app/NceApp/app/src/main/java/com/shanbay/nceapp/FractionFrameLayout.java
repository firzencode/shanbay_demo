package com.shanbay.nceapp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class FractionFrameLayout extends FrameLayout {
    public FractionFrameLayout(Context context) {
        super(context);
    }

    public FractionFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FractionFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setYFraction(final float fraction) {
        float translationY = getHeight() * fraction;
        setTranslationY(translationY);
    }

    public float getYFraction() {
        if (getHeight() == 0) {
            return 0;
        }
        return getTranslationY() / getHeight();
    }

    public void setXFraction(final float fraction) {
        float translationX = getWidth() * fraction;
        setTranslationX(translationX);
    }

    public float getXFraction() {
        if (getWidth() == 0) {
            return 0;
        }
        return getTranslationX() / getWidth();
    }
}
