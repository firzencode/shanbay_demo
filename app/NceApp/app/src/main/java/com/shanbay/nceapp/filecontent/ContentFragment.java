package com.shanbay.nceapp.filecontent;


import android.app.Fragment;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private TextView mContentText;
    private TextView mContentWord;
    private TextView mContentTranslation;

    private int mCurrentWordLevel = 0;
    private boolean mIsShowMarkedWord = false;

    public interface IContentFragmentListener {
        void onCloseFragment();
    }

    private IContentFragmentListener mListener;

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
                    onBtnOpenTextClick();
                    break;
                case R.id.content_bottom_bar_word:
                    onBtnOpenWordClick();
                    break;
                case R.id.content_bottom_bar_translation:
                    onBtnOpenTranslationClick();
                    break;
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

        // slide bar
        mLayoutSlideBar = view.findViewById(R.id.content_layout_slider_bar);
        mSlideBar = (SeekBar) view.findViewById(R.id.content_slide_bar);
        mSlideBar.setOnSeekBarChangeListener(mSeekBarChangeListener);
        mTvSlideBar = (TextView) view.findViewById(R.id.content_slide_bar_text);
        mSlideBar.setMax(5);

        // ----- Content -----

        mTvTitle.setText(mData.getTitle());

        mContentText = (TextView) view.findViewById(R.id.content_text);
        mContentText.setText(mData.getText());


        ////// DEBUG

        mIsShowMarkedWord = true;
        setMarkedWordVisible(true);


        //////DEBUG
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

    private void onBtnOpenTextClick() {
        showTargetContentView(mLayoutText);
    }

    private void onBtnOpenWordClick() {
        showTargetContentView(mLayoutWords);
    }

    private void onBtnOpenTranslationClick() {
        showTargetContentView(mLayoutTranslation);
    }

    private void showTargetContentView(View v) {
        mLayoutText.setVisibility(View.GONE);
        mLayoutWords.setVisibility(View.GONE);
        mLayoutTranslation.setVisibility(View.GONE);
        v.setVisibility(View.VISIBLE);
    }

    private void setMarkedWordVisible(boolean visible) {
        if (!visible) {
            mContentText.setText(mData.getText());
        } else {
            // TODO ASYNC
            mContentText.setText(getMarkedText());
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
                    UnderlineSpan span = new UnderlineSpan();
                    builder.setSpan(span, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return builder;
    }

}
