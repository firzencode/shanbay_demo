package com.shanbay.nceapp.filecontent;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * This class is used to represent a clickable text view,
 * used to change background color when clicked text.
 */
public class ClickableTextView extends TextView {

    /**
     * Bound builder
     */
    private SpannableStringBuilder mSb = null;
    private int mSelectWordStartIndex = -1;

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {

    }

    @Override
    public boolean getDefaultEditable() {
        return false;
    }

    public ClickableTextView(Context context) {
        super(context);
    }

    public ClickableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        Layout layout = getLayout();
        if (layout != null) {
            switch (action) {
                case MotionEvent.ACTION_UP:
                    int line = layout.getLineForVertical(getScrollY() + (int) event.getY());
                    int curOff = layout.getOffsetForHorizontal(line, (int) event.getX());
                    setBackground(curOff);
                    break;
            }
        }
        return true;
    }

    public void bindSpannableStringBuilder(SpannableStringBuilder sb) {
        mSb = sb;
        mSelectWordStartIndex = -1;
    }

    private void setBackground(int offset) {
        if (offset < 0) {
            offset = 0;
        }

        if (offset >= getText().length()) {
            offset = getText().length() - 1;
        }

        if (getText().charAt(offset) == ' ' || getText().charAt(offset) == '\n') {
            return;
        }

        int startIndex = offset;
        int endIndex = offset;
        while (getText().charAt(startIndex) != ' ' && getText().charAt(startIndex) != '\n') {
            startIndex--;
            if (startIndex < 0) {
                break;
            }
        }
        startIndex++;

        while (getText().charAt(endIndex) != ' ' && getText().charAt(endIndex) != '\n') {
            endIndex++;
            if (endIndex >= getText().length()) {
                endIndex = getText().length();
                break;
            }
        }
        if (startIndex > endIndex) {
            // error
            return;
        }

        if (mSb == null) {
            return;
        }

        if (mSelectWordStartIndex == startIndex) {
            // we need to remove the select bg
            setText(mSb);
            mSelectWordStartIndex = -1;
        } else {
            // click another position
            mSelectWordStartIndex = startIndex;
            int targetColor = Color.RED;
            BackgroundColorSpan span = new BackgroundColorSpan(targetColor);
            SpannableStringBuilder builder = new SpannableStringBuilder(mSb.subSequence(0, mSb.length()));
            builder.setSpan(span, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(builder);
        }
//
//        BackgroundColorSpan[] sp = mSb.getSpans(offset, offset + 1, BackgroundColorSpan.class);
//        if (sp != null && sp.length > 0) {
//            // click position has a background
//
//        }
//
//
//        if (sp != null && sp.length > 0) {
//            // there is a background color at click position
//            mSb.removeSpan((sp[0]));
//            setText(mSb);
//        } else {
//            int targetColor = Color.RED;
//            BackgroundColorSpan span = new BackgroundColorSpan(targetColor);
//            mSb.setSpan(span, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            setText(mSb);
//        }
    }
}
