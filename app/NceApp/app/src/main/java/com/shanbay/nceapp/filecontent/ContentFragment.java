package com.shanbay.nceapp.filecontent;


import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.shanbay.nceapp.R;

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
    private SeekBar mSlideBar;

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

    public ContentFragment() {

    }

    public void setListener(IContentFragmentListener listener) {
        mListener = listener;
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
}
