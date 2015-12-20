package com.shanbay.nceapp.filecontent;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by firzencode on 15/12/20.
 */
public class ClickableTextView extends TextView {

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
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        Layout layout = getLayout();
        if (layout != null) {
            int line = 0;
            Log.d("TEST", "action = " + event.getAction());
            switch (action) {
                case MotionEvent.ACTION_UP:
                    line = layout.getLineForVertical(getScrollY() + (int) event.getY());
                    int curOff = layout.getOffsetForHorizontal(line, (int) event.getX());
                    //Selection.setSelection(getEditableText(), off, curOff);
                    //Log.d("TEST2", " cur off = " + curOff);
                    setBackground(curOff);
                    break;
            }
        }
        return true;
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

        SpannableStringBuilder builder = new SpannableStringBuilder(getText());
//        BackgroundColorSpan[] sp = builder.getSpans(offset, offset + 1, BackgroundColorSpan.class);
//        int targetColor = Color.RED;
//        if (sp != null) {
//            Log.d("TEST", "SP array ! null size = " + sp.length);
//            for (int i = 0; i < sp.length; i++) {
//                Log.d("TEST", "SP color = " + sp[i].getBackgroundColor());
//            }
//        } else {
//            Log.d("TEST", "SP array is null!");
//        }
//        int targetBgColor;
//
//        if (sp != null && sp.length > 0) {
//            int curBgColor = sp[0].getBackgroundColor();
//            Log.d("TEST", "cur BgColor = " + curBgColor);
//            if (curBgColor != Color.RED) {
//                Log.d("TEST", "AAA");
//                targetBgColor = Color.RED;
//            } else {
//                Log.d("TEST", "BBB");
//                targetBgColor = Color.WHITE;
//            }
//            builder.removeSpan(sp[0]);
//        } else {
//            Log.d("TEST", "cur BgColor = null");
//            Log.d("TEST", "CCC");
//            targetBgColor = Color.RED;
//        }
//        Log.d("TEST", "target color = " + targetBgColor);

        int targetColor = Color.RED;
        BackgroundColorSpan span = new BackgroundColorSpan(targetColor);
        builder.setSpan(span, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(builder);
        //Log.d("TEST", "offset = " + offset + " start = " + startIndex + " end = " + endIndex);
//        Log.d("TEST", "555");
    }
}
