package com.shanbay.nceapp.data;

import java.util.ArrayList;

/**
 * This class is used to represent a NCE lesson, including index, text, words and text translation.
 */
public class DataLesson {
    public static class DataWord {
        private String mWord;
        private String mTrans;

        public DataWord(String word, String trans) {
            mWord = word;
            mTrans = trans;
        }

        public String getWord() {
            return mWord;
        }

        public String getTrans() {
            return mTrans;
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

    public String getSubTitle() {
        return mSubTitle;
    }

    public int getIndex() {
        return mIndex;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getText() {
        return mText;
    }

    public ArrayList<DataWord> getWordList() {
        return mWordList;
    }

    public String getTrans() {
        return mTranslation;
    }
}
