package com.shanbay.nceapp.data;

import java.util.ArrayList;

/**
 * Created by firzencode on 15/12/17.
 */
public class DataLesson {
    public String getSubTitle() {
        return mSubTitle;
    }

    public static class DataWord {
        private String mWord;
        private String mTrans;

        public DataWord(String word, String trans) {
            mWord = word;
            mTrans = trans;
        }
    }

    private int mIndex;
    private String mTitle;
    private String mSubTitle;
    private String mText;
    private String mTranslation;

    private ArrayList<DataWord> mWordList;

    public DataLesson(int index) {
        mIndex = index;
        mWordList = new ArrayList<>();
    }

    public void setTitle(String title, String subTitle) {
        mTitle = title;
        mSubTitle = subTitle;
    }

    public void setText(String text) {
        mText = text;
    }

    public void addWord(String word, String trans) {
        DataWord dataWord = new DataWord(word, trans);
        mWordList.add(dataWord);
    }

    public void setTranslation(String translation) {
        mTranslation = translation;
    }

    public int getIndex() {
        return mIndex;
    }

    public String getTitle() {
        return mTitle;
    }

    @Override
    public String toString() {
        return "lesson : index = " + mIndex + " title = " + mTitle;
    }
}
