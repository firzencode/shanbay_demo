package com.shanbay.nceapp.filecontent;


import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.shanbay.nceapp.R;
import com.shanbay.nceapp.data.DataLesson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {

    private View mLayoutText;
    private View mLayoutWords;
    private View mLayoutTranslation;
    private View mBtnBack;
    private TextView mTvTitle;
    private View mBtnSwitchSliderBar;
    private View mBtnOpenText;
    private View mBtnOpenWords;
    private View mBtnOpenTranslation;
    private View mLayoutSlideBar;

    private TextView mTvSlideBar;
    private SeekBar mSlideBar;

    private DataLesson mData;
    private HashMap<String, Integer> mWordMap;

    private ClickableTextView mContentText;
    private TextView mContentWord;
    private TextView mContentTranslation;
    private View mContentLoading;
    private View mBtnSwitchShowMarkedWord;
    private ImageView mBtnSwitchShowMarkedWordIcon;

    private int mCurrentWordLevel = 0;
    private boolean mIsShowMarkedWord = true;

    public interface IContentFragmentListener {
        void onCloseFragment();
    }

    private IContentFragmentListener mListener;
    private Handler mHandler = new Handler();
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.content_top_bar_back:
                    onBackButtonClick();
                    break;
                case R.id.content_top_bar_switch_slide_bar:
                    onSwitchSlideBar();
                    break;
                case R.id.content_bottom_bar_text:
                    onBtnOpenTextClick(v);
                    break;
                case R.id.content_bottom_bar_word:
                    onBtnOpenWordClick(v);
                    break;
                case R.id.content_bottom_bar_translation:
                    onBtnOpenTranslationClick(v);
                    break;
                case R.id.content_slide_bar_switch:
                    onSlideBarSwitch();
                default:
                    break;
            }
        }
    };


    private SeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mTvSlideBar.setText("Word Level " + progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.d("TEST", "Stop Tracking");
            mCurrentWordLevel = seekBar.getProgress();
            setMarkedWordVisible(mIsShowMarkedWord);
        }
    };

    public ContentFragment() {

    }

    public void setListener(IContentFragmentListener listener) {
        mListener = listener;
    }

    public void setData(DataLesson data, HashMap<String, Integer> wordMap) {
        mData = data;
        mWordMap = wordMap;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        // top bar
        mBtnBack = view.findViewById(R.id.content_top_bar_back);
        mTvTitle = (TextView) view.findViewById(R.id.content_top_bar_title);
        mBtnSwitchSliderBar = view.findViewById(R.id.content_top_bar_switch_slide_bar);

        mBtnBack.setOnClickListener(mOnClickListener);
        mBtnSwitchSliderBar.setOnClickListener(mOnClickListener);

        // content area
        mLayoutText = view.findViewById(R.id.content_layout_text);
        mLayoutWords = view.findViewById(R.id.content_layout_words);
        mLayoutTranslation = view.findViewById(R.id.content_layout_translation);

        // bottom bar
        mBtnOpenText = view.findViewById(R.id.content_bottom_bar_text);
        mBtnOpenWords = view.findViewById(R.id.content_bottom_bar_word);
        mBtnOpenTranslation = view.findViewById(R.id.content_bottom_bar_translation);

        mBtnOpenText.setOnClickListener(mOnClickListener);
        mBtnOpenWords.setOnClickListener(mOnClickListener);
        mBtnOpenTranslation.setOnClickListener(mOnClickListener);

        mBtnOpenText.setSelected(true);
        // slide bar
        mLayoutSlideBar = view.findViewById(R.id.content_layout_slider_bar);
        mSlideBar = (SeekBar) view.findViewById(R.id.content_slide_bar);
        mSlideBar.setOnSeekBarChangeListener(mSeekBarChangeListener);
        mTvSlideBar = (TextView) view.findViewById(R.id.content_slide_bar_text);
        mSlideBar.setMax(6);

        mBtnSwitchShowMarkedWord = view.findViewById(R.id.content_slide_bar_switch);
        mBtnSwitchShowMarkedWord.setOnClickListener(mOnClickListener);
        mBtnSwitchShowMarkedWordIcon = (ImageView) view.findViewById(R.id.cotent_slide_bar_switch_icon);

        updateBtnSwitchShowMarkedWordIcon();

        // ----- Content -----

        mTvTitle.setText(mData.getTitle());

        mContentText = (ClickableTextView) view.findViewById(R.id.content_text);
        mContentText.setHighlightColor(Color.TRANSPARENT);
        mContentLoading = view.findViewById(R.id.content_loading);


        mContentWord = (TextView) view.findViewById(R.id.content_words);
        ArrayList<DataLesson.DataWord> wordList = mData.getWordList();
        StringBuilder wordText = new StringBuilder();
        for (DataLesson.DataWord word : wordList) {
            String text = word.getWord() + "\n" + word.getTrans() + "\n";
            wordText.append(text);
        }
        mContentWord.setText(wordText);

        mContentTranslation = (TextView) view.findViewById(R.id.content_trans);
        mContentTranslation.setText(mData.getTrans());

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setMarkedWordVisible(mIsShowMarkedWord);
            }
        }, 600);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    mListener.onCloseFragment();
                    return true;
                }
                return false;
            }
        });
    }

    /* ---------- Callback ---------- */

    private void onBackButtonClick() {
        mListener.onCloseFragment();
    }

    private void onSwitchSlideBar() {
        if (mLayoutSlideBar.getVisibility() == View.VISIBLE) {
            mLayoutSlideBar.setVisibility(View.GONE);
        } else {
            mLayoutSlideBar.setVisibility(View.VISIBLE);
        }
    }

    private void onBtnOpenTextClick(View v) {
        showTargetContentView(mLayoutText, v);
    }

    private void onBtnOpenWordClick(View v) {
        showTargetContentView(mLayoutWords, v);
    }

    private void onBtnOpenTranslationClick(View v) {
        showTargetContentView(mLayoutTranslation, v);
    }

    private void onSlideBarSwitch() {
        mIsShowMarkedWord = !mIsShowMarkedWord;
        updateBtnSwitchShowMarkedWordIcon();
        setMarkedWordVisible(mIsShowMarkedWord);
    }

    private void showTargetContentView(View v, View button) {
        mLayoutText.setVisibility(View.GONE);
        mLayoutWords.setVisibility(View.GONE);
        mLayoutTranslation.setVisibility(View.GONE);
        v.setVisibility(View.VISIBLE);

        mBtnOpenText.setSelected(false);
        mBtnOpenTranslation.setSelected(false);
        mBtnOpenWords.setSelected(false);
        button.setSelected(true);
    }

    private void setMarkedWordVisible(boolean visible) {
        if (!visible) {
            SpannableStringBuilder sb = new SpannableStringBuilder(mData.getText());
            mContentText.bindSpannableStringBuilder(sb);
            mContentText.setText(mData.getText());
            mContentLoading.setVisibility(View.INVISIBLE);
        } else {
            new LoadMarkedWordTask().execute();
        }
    }

    private class LoadMarkedWordTask extends AsyncTask<Void, Void, SpannableStringBuilder> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mBtnSwitchShowMarkedWord.setEnabled(false);
            mContentLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected SpannableStringBuilder doInBackground(Void... params) {
            SpannableStringBuilder builder = getMarkedText();
            return builder;
        }

        @Override
        protected void onPostExecute(SpannableStringBuilder result) {
            mBtnSwitchShowMarkedWord.setEnabled(true);
            mContentLoading.setVisibility(View.INVISIBLE);
            mContentText.bindSpannableStringBuilder(result);
            mContentText.setText(result);
        }
    }

    private SpannableStringBuilder getMarkedText() {
        SpannableStringBuilder builder = new SpannableStringBuilder(mData.getText());

        Iterator iter = mWordMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iter.next();
            String word = entry.getKey();
            int level = entry.getValue();
            if (mCurrentWordLevel >= level) {
                Pattern p = Pattern.compile("\\W" + word + "\\W");
                Matcher m = p.matcher(builder);
                while (m.find()) {
                    int startIndex = m.start();
                    int endIndex = m.end();
                    BackgroundColorSpan span = new BackgroundColorSpan(Color.YELLOW);
                    builder.setSpan(span, startIndex + 1, endIndex - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return builder;
    }

    private void updateBtnSwitchShowMarkedWordIcon() {
        if (mIsShowMarkedWord) {
            mBtnSwitchShowMarkedWordIcon.setImageResource(R.drawable.mark_visible);
        } else {
            mBtnSwitchShowMarkedWordIcon.setImageResource(R.drawable.mark_invisible);
        }
    }

}
